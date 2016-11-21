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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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

    public void insertBill(BigDecimal amount, String tittle, String responsable, BigDecimal idGroup, File image) throws FileNotFoundException, SQLException, IOException {
        Connection conn = Conexion.getConnection();
        conn.setAutoCommit(true);
        FileInputStream input = new FileInputStream(image);
        PreparedStatement ps = conn.prepareStatement("INSERT INTO BILL (AMOUNT, TITTLE, ID_RESPONSABLE, ID_GROUP, IMAGE) VALUES (?,?,?,?,?)");
        ps.setBigDecimal(1, amount);
        ps.setString(2, tittle);
        ps.setString(3, responsable);
        ps.setBigDecimal(4, idGroup);
        ps.setBinaryStream(5, input);
        ps.executeUpdate();
        ps.close();
    }

    public BigDecimal getLastBillID() throws SQLException {
        Connection conn = Conexion.getConnection();
        conn.setAutoCommit(true);
        PreparedStatement ps = conn.prepareStatement("select ID from BILL where ID = ( select max(ID) from BILL )");
        ResultSet resultSet = ps.executeQuery("select ID from BILL where ID = ( select max(ID) from BILL )");
        BigDecimal lastId = null;
        while (resultSet.next()) {
            lastId = resultSet.getBigDecimal("ID");
        }
        return lastId;
    }

    public String getListGroups() {
        EntityManager em = getEntityManager();
        Query getList = em.createNativeQuery("SELECT distinct (GRUPO.NAME) FROM BILL JOIN GRUPO ON BILL.ID_GROUP = GRUPO.ID");
        List<Object> names = getList.getResultList();
        String list = "";
        for (int i = 0; i < names.size(); ++i) {
            String nam = (String) names.get(i);
            String temp = "'";
            temp += nam;
            if (i != names.size() - 1) {
                temp += "',";
            } else {
                temp += "'";
            }
            list += temp;
        }
        return list;
    }

    public String[] getArrayGroupNames() {
        EntityManager em = getEntityManager();
        Query getList = em.createNativeQuery("SELECT distinct (GRUPO.NAME) FROM BILL JOIN GRUPO ON BILL.ID_GROUP = GRUPO.ID");
        List<Object> names = getList.getResultList();
        String[] grpNames = new String[names.size() + 2];
        grpNames[0] = "Fecha";
        for (int i = 0; i < names.size(); ++i) {
            grpNames[i + 1] = (String) names.get(i);
        }
        grpNames[names.size() + 1] = "Total";
        return grpNames;
    }

    public int getCountGroups() {
        EntityManager em = getEntityManager();
        Query getList = em.createNativeQuery("SELECT distinct (GRUPO.NAME) FROM BILL JOIN GRUPO ON BILL.ID_GROUP = GRUPO.ID");
        List<Object> names = getList.getResultList();
        int cont = names.size();
        return cont;
    }

    public Object[][] billReport() {
        EntityManager em = getEntityManager();
        String listGroups = getListGroups();
        Query getList = em.createNativeQuery("WITH CONSULT AS (SELECT TO_CHAR(fecha,'DD/MM/YYYY') AS FECHA, AMOUNT, GRUPO.NAME FROM BILL JOIN GRUPO ON BILL.ID_GROUP = GRUPO.ID) SELECT * FROM CONSULT PIVOT (SUM(AMOUNT) FOR (NAME) IN (" + listGroups + "))");
        List<Object[]> result = getList.getResultList();
        Object[][] model = new Object[result.size() + 1][getCountGroups() + 2];
        for (int i = 0; i < result.size(); ++i) {
            Object[] temp = result.get(i);

            String date = (String) temp[0];
            model[i][0] = date;

            for (int j = 1; j < temp.length; ++j) {
                if (temp[j] == null) {
                    model[i][j] = BigDecimal.ZERO;
                } else {
                    model[i][j] = (BigDecimal) temp[j];
                }
            }
        }

        for (int j = 0; j < model.length - 1; ++j) {
            Object[] objects = model[j];
            int total = 0;
            for (int i = 1; i < objects.length - 1; ++i) {
                BigDecimal current = (BigDecimal) objects[i];
                total += current.intValue();
            }
            model[j][objects.length - 1] = new BigDecimal(total);
        }
        model[model.length - 1][0] = "TOTAL";

        for (int j = 1; j < model[0].length; ++j) {
            int total = 0;
            for (int i = 0; i < model.length - 1; ++i) {
                BigDecimal current = (BigDecimal) model[i][j];
                total += current.intValue();
            }
            model[model.length - 1][j] = new BigDecimal(total);
        }
        return model;
    }

    public String[][] getGroupBill(BigDecimal idGroup) {
        EntityManager em = getEntityManager();
        Query groupBill = em.createNativeQuery("SELECT TO_CHAR(ID), TO_CHAR(fecha,'DD/MM/YYYY'), TITTLE, ID_RESPONSABLE, TO_CHAR(AMOUNT) FROM Bill where ID_GROUP = ?");
        groupBill.setParameter(1, idGroup);
        List<Object[]> result = groupBill.getResultList();
        String[][] model = new String[result.size()][5];
        for (int i = 0; i < result.size(); ++i) {
            for (int j = 0; j < 5; ++j) {
                model[i][j] = (String) result.get(i)[j];
            }
        }
        return model;
    }

    public File getBillImage(BigDecimal id) throws SQLException, IOException {
        Connection conn = Conexion.getConnection();
        PreparedStatement stmt = conn.prepareCall("SELECT IMAGE FROM BILL WHERE ID = ?");
        stmt.setBigDecimal(1, id);
        ResultSet resultSet = stmt.executeQuery();
        File image = null;
        while (resultSet.next()) {
            String path = System.getProperty("user.dir")+File.pathSeparator+"url.jpg";
            image = new File(path);
            FileOutputStream fos = new FileOutputStream(image);

            byte[] buffer = new byte[1];
            InputStream is = resultSet.getBinaryStream("IMAGE");
            while (is.read(buffer) > 0) {
                fos.write(buffer);
            }
            fos.close();
        }
        conn.close();
        return image;
    }

}
