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
import entities.Grupo;
import entities.Usuario;
import entities.Bill;
import entities.UserXGroup;
import entities.UserXGroupPK;
import entities_controllers.exceptions.IllegalOrphanException;
import entities_controllers.exceptions.NonexistentEntityException;
import entities_controllers.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JuanPablo
 */
public class UserXGroupJpaController implements Serializable {

    public UserXGroupJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserXGroup userXGroup) throws PreexistingEntityException, Exception {
        if (userXGroup.getUserXGroupPK() == null) {
            userXGroup.setUserXGroupPK(new UserXGroupPK());
        }
        if (userXGroup.getBillList() == null) {
            userXGroup.setBillList(new ArrayList<Bill>());
        }
        userXGroup.getUserXGroupPK().setUserEmail(userXGroup.getUsuario().getEmail());
        userXGroup.getUserXGroupPK().setGroupId(userXGroup.getGrupo().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo grupo = userXGroup.getGrupo();
            if (grupo != null) {
                grupo = em.getReference(grupo.getClass(), grupo.getId());
                userXGroup.setGrupo(grupo);
            }
            Usuario usuario = userXGroup.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getEmail());
                userXGroup.setUsuario(usuario);
            }
            List<Bill> attachedBillList = new ArrayList<Bill>();
            for (Bill billListBillToAttach : userXGroup.getBillList()) {
                billListBillToAttach = em.getReference(billListBillToAttach.getClass(), billListBillToAttach.getId());
                attachedBillList.add(billListBillToAttach);
            }
            userXGroup.setBillList(attachedBillList);
            em.persist(userXGroup);
            if (grupo != null) {
                grupo.getUserXGroupList().add(userXGroup);
                grupo = em.merge(grupo);
            }
            if (usuario != null) {
                usuario.getUserXGroupList().add(userXGroup);
                usuario = em.merge(usuario);
            }
            for (Bill billListBill : userXGroup.getBillList()) {
                UserXGroup oldUserXGroupOfBillListBill = billListBill.getUserXGroup();
                billListBill.setUserXGroup(userXGroup);
                billListBill = em.merge(billListBill);
                if (oldUserXGroupOfBillListBill != null) {
                    oldUserXGroupOfBillListBill.getBillList().remove(billListBill);
                    oldUserXGroupOfBillListBill = em.merge(oldUserXGroupOfBillListBill);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUserXGroup(userXGroup.getUserXGroupPK()) != null) {
                throw new PreexistingEntityException("UserXGroup " + userXGroup + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserXGroup userXGroup) throws IllegalOrphanException, NonexistentEntityException, Exception {
        userXGroup.getUserXGroupPK().setUserEmail(userXGroup.getUsuario().getEmail());
        userXGroup.getUserXGroupPK().setGroupId(userXGroup.getGrupo().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserXGroup persistentUserXGroup = em.find(UserXGroup.class, userXGroup.getUserXGroupPK());
            Grupo grupoOld = persistentUserXGroup.getGrupo();
            Grupo grupoNew = userXGroup.getGrupo();
            Usuario usuarioOld = persistentUserXGroup.getUsuario();
            Usuario usuarioNew = userXGroup.getUsuario();
            List<Bill> billListOld = persistentUserXGroup.getBillList();
            List<Bill> billListNew = userXGroup.getBillList();
            List<String> illegalOrphanMessages = null;
            for (Bill billListOldBill : billListOld) {
                if (!billListNew.contains(billListOldBill)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bill " + billListOldBill + " since its userXGroup field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (grupoNew != null) {
                grupoNew = em.getReference(grupoNew.getClass(), grupoNew.getId());
                userXGroup.setGrupo(grupoNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getEmail());
                userXGroup.setUsuario(usuarioNew);
            }
            List<Bill> attachedBillListNew = new ArrayList<Bill>();
            for (Bill billListNewBillToAttach : billListNew) {
                billListNewBillToAttach = em.getReference(billListNewBillToAttach.getClass(), billListNewBillToAttach.getId());
                attachedBillListNew.add(billListNewBillToAttach);
            }
            billListNew = attachedBillListNew;
            userXGroup.setBillList(billListNew);
            userXGroup = em.merge(userXGroup);
            if (grupoOld != null && !grupoOld.equals(grupoNew)) {
                grupoOld.getUserXGroupList().remove(userXGroup);
                grupoOld = em.merge(grupoOld);
            }
            if (grupoNew != null && !grupoNew.equals(grupoOld)) {
                grupoNew.getUserXGroupList().add(userXGroup);
                grupoNew = em.merge(grupoNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getUserXGroupList().remove(userXGroup);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getUserXGroupList().add(userXGroup);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Bill billListNewBill : billListNew) {
                if (!billListOld.contains(billListNewBill)) {
                    UserXGroup oldUserXGroupOfBillListNewBill = billListNewBill.getUserXGroup();
                    billListNewBill.setUserXGroup(userXGroup);
                    billListNewBill = em.merge(billListNewBill);
                    if (oldUserXGroupOfBillListNewBill != null && !oldUserXGroupOfBillListNewBill.equals(userXGroup)) {
                        oldUserXGroupOfBillListNewBill.getBillList().remove(billListNewBill);
                        oldUserXGroupOfBillListNewBill = em.merge(oldUserXGroupOfBillListNewBill);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                UserXGroupPK id = userXGroup.getUserXGroupPK();
                if (findUserXGroup(id) == null) {
                    throw new NonexistentEntityException("The userXGroup with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(UserXGroupPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserXGroup userXGroup;
            try {
                userXGroup = em.getReference(UserXGroup.class, id);
                userXGroup.getUserXGroupPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userXGroup with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Bill> billListOrphanCheck = userXGroup.getBillList();
            for (Bill billListOrphanCheckBill : billListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UserXGroup (" + userXGroup + ") cannot be destroyed since the Bill " + billListOrphanCheckBill + " in its billList field has a non-nullable userXGroup field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Grupo grupo = userXGroup.getGrupo();
            if (grupo != null) {
                grupo.getUserXGroupList().remove(userXGroup);
                grupo = em.merge(grupo);
            }
            Usuario usuario = userXGroup.getUsuario();
            if (usuario != null) {
                usuario.getUserXGroupList().remove(userXGroup);
                usuario = em.merge(usuario);
            }
            em.remove(userXGroup);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserXGroup> findUserXGroupEntities() {
        return findUserXGroupEntities(true, -1, -1);
    }

    public List<UserXGroup> findUserXGroupEntities(int maxResults, int firstResult) {
        return findUserXGroupEntities(false, maxResults, firstResult);
    }

    private List<UserXGroup> findUserXGroupEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserXGroup.class));
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

    public UserXGroup findUserXGroup(UserXGroupPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserXGroup.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserXGroupCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserXGroup> rt = cq.from(UserXGroup.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
