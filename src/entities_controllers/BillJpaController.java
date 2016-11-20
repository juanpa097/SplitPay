/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities_controllers;

import entities.Bill;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.UserXGroup;
import entities.Deuda;
import entities_controllers.exceptions.IllegalOrphanException;
import entities_controllers.exceptions.NonexistentEntityException;
import entities_controllers.exceptions.PreexistingEntityException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import oracle.jdbc.OracleDriver;

/**
 *
 * @author JuanPablo
 */
public class BillJpaController implements Serializable {

    public BillJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bill bill) throws PreexistingEntityException, Exception {
        if (bill.getDeudaList() == null) {
            bill.setDeudaList(new ArrayList<Deuda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserXGroup userXGroup = bill.getUserXGroup();
            if (userXGroup != null) {
                userXGroup = em.getReference(userXGroup.getClass(), userXGroup.getUserXGroupPK());
                bill.setUserXGroup(userXGroup);
            }
            List<Deuda> attachedDeudaList = new ArrayList<Deuda>();
            for (Deuda deudaListDeudaToAttach : bill.getDeudaList()) {
                deudaListDeudaToAttach = em.getReference(deudaListDeudaToAttach.getClass(), deudaListDeudaToAttach.getDeudaPK());
                attachedDeudaList.add(deudaListDeudaToAttach);
            }
            bill.setDeudaList(attachedDeudaList);
            em.persist(bill);
            if (userXGroup != null) {
                userXGroup.getBillList().add(bill);
                userXGroup = em.merge(userXGroup);
            }
            for (Deuda deudaListDeuda : bill.getDeudaList()) {
                Bill oldBillOfDeudaListDeuda = deudaListDeuda.getBill();
                deudaListDeuda.setBill(bill);
                deudaListDeuda = em.merge(deudaListDeuda);
                if (oldBillOfDeudaListDeuda != null) {
                    oldBillOfDeudaListDeuda.getDeudaList().remove(deudaListDeuda);
                    oldBillOfDeudaListDeuda = em.merge(oldBillOfDeudaListDeuda);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBill(bill.getId()) != null) {
                throw new PreexistingEntityException("Bill " + bill + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bill bill) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bill persistentBill = em.find(Bill.class, bill.getId());
            UserXGroup userXGroupOld = persistentBill.getUserXGroup();
            UserXGroup userXGroupNew = bill.getUserXGroup();
            List<Deuda> deudaListOld = persistentBill.getDeudaList();
            List<Deuda> deudaListNew = bill.getDeudaList();
            List<String> illegalOrphanMessages = null;
            for (Deuda deudaListOldDeuda : deudaListOld) {
                if (!deudaListNew.contains(deudaListOldDeuda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Deuda " + deudaListOldDeuda + " since its bill field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userXGroupNew != null) {
                userXGroupNew = em.getReference(userXGroupNew.getClass(), userXGroupNew.getUserXGroupPK());
                bill.setUserXGroup(userXGroupNew);
            }
            List<Deuda> attachedDeudaListNew = new ArrayList<Deuda>();
            for (Deuda deudaListNewDeudaToAttach : deudaListNew) {
                deudaListNewDeudaToAttach = em.getReference(deudaListNewDeudaToAttach.getClass(), deudaListNewDeudaToAttach.getDeudaPK());
                attachedDeudaListNew.add(deudaListNewDeudaToAttach);
            }
            deudaListNew = attachedDeudaListNew;
            bill.setDeudaList(deudaListNew);
            bill = em.merge(bill);
            if (userXGroupOld != null && !userXGroupOld.equals(userXGroupNew)) {
                userXGroupOld.getBillList().remove(bill);
                userXGroupOld = em.merge(userXGroupOld);
            }
            if (userXGroupNew != null && !userXGroupNew.equals(userXGroupOld)) {
                userXGroupNew.getBillList().add(bill);
                userXGroupNew = em.merge(userXGroupNew);
            }
            for (Deuda deudaListNewDeuda : deudaListNew) {
                if (!deudaListOld.contains(deudaListNewDeuda)) {
                    Bill oldBillOfDeudaListNewDeuda = deudaListNewDeuda.getBill();
                    deudaListNewDeuda.setBill(bill);
                    deudaListNewDeuda = em.merge(deudaListNewDeuda);
                    if (oldBillOfDeudaListNewDeuda != null && !oldBillOfDeudaListNewDeuda.equals(bill)) {
                        oldBillOfDeudaListNewDeuda.getDeudaList().remove(deudaListNewDeuda);
                        oldBillOfDeudaListNewDeuda = em.merge(oldBillOfDeudaListNewDeuda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = bill.getId();
                if (findBill(id) == null) {
                    throw new NonexistentEntityException("The bill with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bill bill;
            try {
                bill = em.getReference(Bill.class, id);
                bill.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bill with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Deuda> deudaListOrphanCheck = bill.getDeudaList();
            for (Deuda deudaListOrphanCheckDeuda : deudaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Bill (" + bill + ") cannot be destroyed since the Deuda " + deudaListOrphanCheckDeuda + " in its deudaList field has a non-nullable bill field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            UserXGroup userXGroup = bill.getUserXGroup();
            if (userXGroup != null) {
                userXGroup.getBillList().remove(bill);
                userXGroup = em.merge(userXGroup);
            }
            em.remove(bill);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bill> findBillEntities() {
        return findBillEntities(true, -1, -1);
    }

    public List<Bill> findBillEntities(int maxResults, int firstResult) {
        return findBillEntities(false, maxResults, firstResult);
    }

    private List<Bill> findBillEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bill.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Bill findBill(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bill.class, id);
        } finally {
            em.close();
        }
    }

    public int getBillCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bill> rt = cq.from(Bill.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public void insertBill (BigDecimal amount, String tittle, String responsable, BigDecimal idGroup) throws FileNotFoundException, SQLException, IOException {
        
        String thinConn = "jdbc:oracle:thin:@orion.javeriana.edu.co:1521:PUJDISOR";
        String user = "is102621";
        String passwd = "jQPXnBbKRt";
                
        DriverManager.registerDriver(new OracleDriver());
        
        Connection conn = DriverManager.getConnection(thinConn, user, passwd);
        conn.setAutoCommit(true);
        PreparedStatement ps = conn.prepareStatement("INSERT INTO BILL (IMAGE, AMOUNT, TITTLE, ID_RESPONSABLE, ID_GROUP) VALUES ('null',?,?,?,?)");
        ps.setBigDecimal(1, amount);
        ps.setString(2, tittle);
        ps.setString(3, responsable);
        ps.setBigDecimal(4, idGroup);
        
        ps.executeUpdate();
        ps.close();
    }
    
    public BigDecimal getLastBillID () throws SQLException {
        String thinConn = "jdbc:oracle:thin:@orion.javeriana.edu.co:1521:PUJDISOR";
        String user = "is102621";
        String passwd = "jQPXnBbKRt";
        DriverManager.registerDriver(new OracleDriver());
        Connection conn = DriverManager.getConnection(thinConn, user, passwd);
        conn.setAutoCommit(true);
        PreparedStatement ps = conn.prepareStatement("select ID from BILL where ID = ( select max(ID) from BILL )");
        ResultSet resultSet = ps.executeQuery("select ID from BILL where ID = ( select max(ID) from BILL )");
        BigDecimal lastId = null; 
        while (resultSet.next())
            lastId = resultSet.getBigDecimal("ID");
        return lastId;
    }
    
    
}
