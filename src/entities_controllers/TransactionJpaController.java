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
import entities.Deuda;
import entities.Transaction;
import entities_controllers.exceptions.NonexistentEntityException;
import entities_controllers.exceptions.PreexistingEntityException;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JuanPablo
 */
public class TransactionJpaController implements Serializable {

    public TransactionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaction transaction) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Deuda deuda = transaction.getDeuda();
            if (deuda != null) {
                deuda = em.getReference(deuda.getClass(), deuda.getDeudaPK());
                transaction.setDeuda(deuda);
            }
            em.persist(transaction);
            if (deuda != null) {
                deuda.getTransactionList().add(transaction);
                deuda = em.merge(deuda);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTransaction(transaction.getIdTransaction()) != null) {
                throw new PreexistingEntityException("Transaction " + transaction + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaction transaction) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaction persistentTransaction = em.find(Transaction.class, transaction.getIdTransaction());
            Deuda deudaOld = persistentTransaction.getDeuda();
            Deuda deudaNew = transaction.getDeuda();
            if (deudaNew != null) {
                deudaNew = em.getReference(deudaNew.getClass(), deudaNew.getDeudaPK());
                transaction.setDeuda(deudaNew);
            }
            transaction = em.merge(transaction);
            if (deudaOld != null && !deudaOld.equals(deudaNew)) {
                deudaOld.getTransactionList().remove(transaction);
                deudaOld = em.merge(deudaOld);
            }
            if (deudaNew != null && !deudaNew.equals(deudaOld)) {
                deudaNew.getTransactionList().add(transaction);
                deudaNew = em.merge(deudaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = transaction.getIdTransaction();
                if (findTransaction(id) == null) {
                    throw new NonexistentEntityException("The transaction with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaction transaction;
            try {
                transaction = em.getReference(Transaction.class, id);
                transaction.getIdTransaction();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaction with id " + id + " no longer exists.", enfe);
            }
            Deuda deuda = transaction.getDeuda();
            if (deuda != null) {
                deuda.getTransactionList().remove(transaction);
                deuda = em.merge(deuda);
            }
            em.remove(transaction);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transaction> findTransactionEntities() {
        return findTransactionEntities(true, -1, -1);
    }

    public List<Transaction> findTransactionEntities(int maxResults, int firstResult) {
        return findTransactionEntities(false, maxResults, firstResult);
    }

    private List<Transaction> findTransactionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaction.class));
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

    public Transaction findTransaction(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaction.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaction> rt = cq.from(Transaction.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
