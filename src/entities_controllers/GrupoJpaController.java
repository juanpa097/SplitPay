/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities_controllers;

import entities.Grupo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Usuario;
import entities.UserXGroup;
import entities_controllers.exceptions.IllegalOrphanException;
import entities_controllers.exceptions.NonexistentEntityException;
import entities_controllers.exceptions.PreexistingEntityException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author JuanPablo
 */
public class GrupoJpaController implements Serializable {

    public GrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grupo grupo) throws PreexistingEntityException, Exception {
        if (grupo.getUserXGroupList() == null) {
            grupo.setUserXGroupList(new ArrayList<UserXGroup>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario leaderEmail = grupo.getLeaderEmail();
            if (leaderEmail != null) {
                leaderEmail = em.getReference(leaderEmail.getClass(), leaderEmail.getEmail());
                grupo.setLeaderEmail(leaderEmail);
            }
            List<UserXGroup> attachedUserXGroupList = new ArrayList<UserXGroup>();
            for (UserXGroup userXGroupListUserXGroupToAttach : grupo.getUserXGroupList()) {
                userXGroupListUserXGroupToAttach = em.getReference(userXGroupListUserXGroupToAttach.getClass(), userXGroupListUserXGroupToAttach.getUserXGroupPK());
                attachedUserXGroupList.add(userXGroupListUserXGroupToAttach);
            }
            grupo.setUserXGroupList(attachedUserXGroupList);
            em.persist(grupo);
            if (leaderEmail != null) {
                leaderEmail.getGrupoList().add(grupo);
                leaderEmail = em.merge(leaderEmail);
            }
            for (UserXGroup userXGroupListUserXGroup : grupo.getUserXGroupList()) {
                Grupo oldGrupoOfUserXGroupListUserXGroup = userXGroupListUserXGroup.getGrupo();
                userXGroupListUserXGroup.setGrupo(grupo);
                userXGroupListUserXGroup = em.merge(userXGroupListUserXGroup);
                if (oldGrupoOfUserXGroupListUserXGroup != null) {
                    oldGrupoOfUserXGroupListUserXGroup.getUserXGroupList().remove(userXGroupListUserXGroup);
                    oldGrupoOfUserXGroupListUserXGroup = em.merge(oldGrupoOfUserXGroupListUserXGroup);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGrupo(grupo.getId()) != null) {
                throw new PreexistingEntityException("Grupo " + grupo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupo grupo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getId());
            Usuario leaderEmailOld = persistentGrupo.getLeaderEmail();
            Usuario leaderEmailNew = grupo.getLeaderEmail();
            List<UserXGroup> userXGroupListOld = persistentGrupo.getUserXGroupList();
            List<UserXGroup> userXGroupListNew = grupo.getUserXGroupList();
            List<String> illegalOrphanMessages = null;
            for (UserXGroup userXGroupListOldUserXGroup : userXGroupListOld) {
                if (!userXGroupListNew.contains(userXGroupListOldUserXGroup)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserXGroup " + userXGroupListOldUserXGroup + " since its grupo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (leaderEmailNew != null) {
                leaderEmailNew = em.getReference(leaderEmailNew.getClass(), leaderEmailNew.getEmail());
                grupo.setLeaderEmail(leaderEmailNew);
            }
            List<UserXGroup> attachedUserXGroupListNew = new ArrayList<UserXGroup>();
            for (UserXGroup userXGroupListNewUserXGroupToAttach : userXGroupListNew) {
                userXGroupListNewUserXGroupToAttach = em.getReference(userXGroupListNewUserXGroupToAttach.getClass(), userXGroupListNewUserXGroupToAttach.getUserXGroupPK());
                attachedUserXGroupListNew.add(userXGroupListNewUserXGroupToAttach);
            }
            userXGroupListNew = attachedUserXGroupListNew;
            grupo.setUserXGroupList(userXGroupListNew);
            grupo = em.merge(grupo);
            if (leaderEmailOld != null && !leaderEmailOld.equals(leaderEmailNew)) {
                leaderEmailOld.getGrupoList().remove(grupo);
                leaderEmailOld = em.merge(leaderEmailOld);
            }
            if (leaderEmailNew != null && !leaderEmailNew.equals(leaderEmailOld)) {
                leaderEmailNew.getGrupoList().add(grupo);
                leaderEmailNew = em.merge(leaderEmailNew);
            }
            for (UserXGroup userXGroupListNewUserXGroup : userXGroupListNew) {
                if (!userXGroupListOld.contains(userXGroupListNewUserXGroup)) {
                    Grupo oldGrupoOfUserXGroupListNewUserXGroup = userXGroupListNewUserXGroup.getGrupo();
                    userXGroupListNewUserXGroup.setGrupo(grupo);
                    userXGroupListNewUserXGroup = em.merge(userXGroupListNewUserXGroup);
                    if (oldGrupoOfUserXGroupListNewUserXGroup != null && !oldGrupoOfUserXGroupListNewUserXGroup.equals(grupo)) {
                        oldGrupoOfUserXGroupListNewUserXGroup.getUserXGroupList().remove(userXGroupListNewUserXGroup);
                        oldGrupoOfUserXGroupListNewUserXGroup = em.merge(oldGrupoOfUserXGroupListNewUserXGroup);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = grupo.getId();
                if (findGrupo(id) == null) {
                    throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.");
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
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<UserXGroup> userXGroupListOrphanCheck = grupo.getUserXGroupList();
            for (UserXGroup userXGroupListOrphanCheckUserXGroup : userXGroupListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the UserXGroup " + userXGroupListOrphanCheckUserXGroup + " in its userXGroupList field has a non-nullable grupo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario leaderEmail = grupo.getLeaderEmail();
            if (leaderEmail != null) {
                leaderEmail.getGrupoList().remove(grupo);
                leaderEmail = em.merge(leaderEmail);
            }
            em.remove(grupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grupo> findGrupoEntities() {
        return findGrupoEntities(true, -1, -1);
    }

    public List<Grupo> findGrupoEntities(int maxResults, int firstResult) {
        return findGrupoEntities(false, maxResults, firstResult);
    }

    private List<Grupo> findGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupo.class));
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

    public Grupo findGrupo(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupo> rt = cq.from(Grupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Object[][] getGroupMembers (BigDecimal groupID) {
        EntityManager em = getEntityManager();
        //USUARIO.MOBILE
        Query usersNameInGroup = em.createNativeQuery("SELECT USUARIO.NAME AS NAME FROM GRUPO NATURAL JOIN USER_X_GROUP JOIN USUARIO ON  USER_X_GROUP.USER_EMAIL = USUARIO.EMAIL WHERE USER_X_GROUP.GROUP_ID = GRUPO.ID AND GRUPO.ID = ?1 ORDER BY USUARIO.NAME");
        usersNameInGroup.setParameter(1, groupID);
        List<String> usersName = usersNameInGroup.getResultList();
        
        Query usersEmailInGroup = em.createNativeQuery("SELECT USER_X_GROUP.USER_EMAIL FROM GRUPO NATURAL JOIN USER_X_GROUP JOIN USUARIO ON  USER_X_GROUP.USER_EMAIL = USUARIO.EMAIL WHERE USER_X_GROUP.GROUP_ID = GRUPO.ID AND GRUPO.ID = ?1 ORDER BY USUARIO.NAME");
        usersEmailInGroup.setParameter(1, groupID);
        List<String> usersEmail = usersEmailInGroup.getResultList();
        
        Object[][] usersGroupTable = new Object[usersName.size()][2];
        for (int i = 0; i < usersName.size(); ++i) {
            usersGroupTable[i][0] = usersName.get(i);
            usersGroupTable[i][1] = usersEmail.get(i);
        }

        return usersGroupTable;
    }
    
}
