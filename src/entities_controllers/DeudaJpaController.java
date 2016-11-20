/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities_controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Bill;
import entities.Deuda;
import entities.DeudaPK;
import entities.Usuario;
import entities.Transaction;
import entities.UserXGroup;
import entities.UserXGroupPK;
import entities_controllers.exceptions.IllegalOrphanException;
import entities_controllers.exceptions.NonexistentEntityException;
import entities_controllers.exceptions.PreexistingEntityException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import view_controller.EntityFactorySingleton;

/**
 *
 * @author JuanPablo
 */
public class DeudaJpaController implements Serializable {

    public DeudaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Deuda deuda) throws PreexistingEntityException, Exception {
        if (deuda.getDeudaPK() == null) {
            deuda.setDeudaPK(new DeudaPK());
        }
        if (deuda.getTransactionList() == null) {
            deuda.setTransactionList(new ArrayList<Transaction>());
        }
        deuda.getDeudaPK().setBillId(deuda.getBill().getId());
        deuda.getDeudaPK().setDeudor(deuda.getUsuario().getEmail());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bill bill = deuda.getBill();
            if (bill != null) {
                bill = em.getReference(bill.getClass(), bill.getId());
                deuda.setBill(bill);
            }
            Usuario usuario = deuda.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getEmail());
                deuda.setUsuario(usuario);
            }
            List<Transaction> attachedTransactionList = new ArrayList<Transaction>();
            for (Transaction transactionListTransactionToAttach : deuda.getTransactionList()) {
                transactionListTransactionToAttach = em.getReference(transactionListTransactionToAttach.getClass(), transactionListTransactionToAttach.getIdTransaction());
                attachedTransactionList.add(transactionListTransactionToAttach);
            }
            deuda.setTransactionList(attachedTransactionList);
            em.persist(deuda);
            if (bill != null) {
                bill.getDeudaList().add(deuda);
                bill = em.merge(bill);
            }
            if (usuario != null) {
                usuario.getDeudaList().add(deuda);
                usuario = em.merge(usuario);
            }
            for (Transaction transactionListTransaction : deuda.getTransactionList()) {
                Deuda oldDeudaOfTransactionListTransaction = transactionListTransaction.getDeuda();
                transactionListTransaction.setDeuda(deuda);
                transactionListTransaction = em.merge(transactionListTransaction);
                if (oldDeudaOfTransactionListTransaction != null) {
                    oldDeudaOfTransactionListTransaction.getTransactionList().remove(transactionListTransaction);
                    oldDeudaOfTransactionListTransaction = em.merge(oldDeudaOfTransactionListTransaction);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDeuda(deuda.getDeudaPK()) != null) {
                throw new PreexistingEntityException("Deuda " + deuda + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Deuda deuda) throws IllegalOrphanException, NonexistentEntityException, Exception {
        deuda.getDeudaPK().setBillId(deuda.getBill().getId());
        deuda.getDeudaPK().setDeudor(deuda.getUsuario().getEmail());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Deuda persistentDeuda = em.find(Deuda.class, deuda.getDeudaPK());
            Bill billOld = persistentDeuda.getBill();
            Bill billNew = deuda.getBill();
            Usuario usuarioOld = persistentDeuda.getUsuario();
            Usuario usuarioNew = deuda.getUsuario();
            List<Transaction> transactionListOld = persistentDeuda.getTransactionList();
            List<Transaction> transactionListNew = deuda.getTransactionList();
            List<String> illegalOrphanMessages = null;
            for (Transaction transactionListOldTransaction : transactionListOld) {
                if (!transactionListNew.contains(transactionListOldTransaction)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Transaction " + transactionListOldTransaction + " since its deuda field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (billNew != null) {
                billNew = em.getReference(billNew.getClass(), billNew.getId());
                deuda.setBill(billNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getEmail());
                deuda.setUsuario(usuarioNew);
            }
            List<Transaction> attachedTransactionListNew = new ArrayList<Transaction>();
            for (Transaction transactionListNewTransactionToAttach : transactionListNew) {
                transactionListNewTransactionToAttach = em.getReference(transactionListNewTransactionToAttach.getClass(), transactionListNewTransactionToAttach.getIdTransaction());
                attachedTransactionListNew.add(transactionListNewTransactionToAttach);
            }
            transactionListNew = attachedTransactionListNew;
            deuda.setTransactionList(transactionListNew);
            deuda = em.merge(deuda);
            if (billOld != null && !billOld.equals(billNew)) {
                billOld.getDeudaList().remove(deuda);
                billOld = em.merge(billOld);
            }
            if (billNew != null && !billNew.equals(billOld)) {
                billNew.getDeudaList().add(deuda);
                billNew = em.merge(billNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getDeudaList().remove(deuda);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getDeudaList().add(deuda);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Transaction transactionListNewTransaction : transactionListNew) {
                if (!transactionListOld.contains(transactionListNewTransaction)) {
                    Deuda oldDeudaOfTransactionListNewTransaction = transactionListNewTransaction.getDeuda();
                    transactionListNewTransaction.setDeuda(deuda);
                    transactionListNewTransaction = em.merge(transactionListNewTransaction);
                    if (oldDeudaOfTransactionListNewTransaction != null && !oldDeudaOfTransactionListNewTransaction.equals(deuda)) {
                        oldDeudaOfTransactionListNewTransaction.getTransactionList().remove(transactionListNewTransaction);
                        oldDeudaOfTransactionListNewTransaction = em.merge(oldDeudaOfTransactionListNewTransaction);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DeudaPK id = deuda.getDeudaPK();
                if (findDeuda(id) == null) {
                    throw new NonexistentEntityException("The deuda with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DeudaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Deuda deuda;
            try {
                deuda = em.getReference(Deuda.class, id);
                deuda.getDeudaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The deuda with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Transaction> transactionListOrphanCheck = deuda.getTransactionList();
            for (Transaction transactionListOrphanCheckTransaction : transactionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Deuda (" + deuda + ") cannot be destroyed since the Transaction " + transactionListOrphanCheckTransaction + " in its transactionList field has a non-nullable deuda field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Bill bill = deuda.getBill();
            if (bill != null) {
                bill.getDeudaList().remove(deuda);
                bill = em.merge(bill);
            }
            Usuario usuario = deuda.getUsuario();
            if (usuario != null) {
                usuario.getDeudaList().remove(deuda);
                usuario = em.merge(usuario);
            }
            em.remove(deuda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Deuda> findDeudaEntities() {
        return findDeudaEntities(true, -1, -1);
    }

    public List<Deuda> findDeudaEntities(int maxResults, int firstResult) {
        return findDeudaEntities(false, maxResults, firstResult);
    }

    private List<Deuda> findDeudaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Deuda.class));
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

    public Deuda findDeuda(DeudaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Deuda.class, id);
        } finally {
            em.close();
        }
    }

    public int getDeudaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Deuda> rt = cq.from(Deuda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public void addOwers(ArrayList<String> emails, BigDecimal amoutToEncreaseToEach, Bill bill) throws NonexistentEntityException, Exception {
        EntityManager em = getEntityManager();
        UsuarioJpaController usrCtrl = new UsuarioJpaController(EntityFactorySingleton.getEMF());
        for (int i = 0; i < emails.size(); ++i) {
            Usuario tempUsr = usrCtrl.findUsuario(emails.get(i));
            DeudaPK pk = new DeudaPK(emails.get(i), bill.getId());
            Deuda newDeuda = new Deuda(pk, amoutToEncreaseToEach);
            newDeuda.setBill(bill);
            newDeuda.setUsuario(tempUsr);
            create(newDeuda);
        }
    }
    
}
