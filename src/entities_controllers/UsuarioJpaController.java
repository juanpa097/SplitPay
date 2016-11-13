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
import java.util.ArrayList;
import java.util.List;
import entities.UserXGroup;
import entities.Deuda;
import entities.Usuario;
import entities_controllers.exceptions.IllegalOrphanException;
import entities_controllers.exceptions.NonexistentEntityException;
import entities_controllers.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JuanPablo
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getGrupoList() == null) {
            usuario.setGrupoList(new ArrayList<Grupo>());
        }
        if (usuario.getUserXGroupList() == null) {
            usuario.setUserXGroupList(new ArrayList<UserXGroup>());
        }
        if (usuario.getDeudaList() == null) {
            usuario.setDeudaList(new ArrayList<Deuda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Grupo> attachedGrupoList = new ArrayList<Grupo>();
            for (Grupo grupoListGrupoToAttach : usuario.getGrupoList()) {
                grupoListGrupoToAttach = em.getReference(grupoListGrupoToAttach.getClass(), grupoListGrupoToAttach.getId());
                attachedGrupoList.add(grupoListGrupoToAttach);
            }
            usuario.setGrupoList(attachedGrupoList);
            List<UserXGroup> attachedUserXGroupList = new ArrayList<UserXGroup>();
            for (UserXGroup userXGroupListUserXGroupToAttach : usuario.getUserXGroupList()) {
                userXGroupListUserXGroupToAttach = em.getReference(userXGroupListUserXGroupToAttach.getClass(), userXGroupListUserXGroupToAttach.getUserXGroupPK());
                attachedUserXGroupList.add(userXGroupListUserXGroupToAttach);
            }
            usuario.setUserXGroupList(attachedUserXGroupList);
            List<Deuda> attachedDeudaList = new ArrayList<Deuda>();
            for (Deuda deudaListDeudaToAttach : usuario.getDeudaList()) {
                deudaListDeudaToAttach = em.getReference(deudaListDeudaToAttach.getClass(), deudaListDeudaToAttach.getDeudaPK());
                attachedDeudaList.add(deudaListDeudaToAttach);
            }
            usuario.setDeudaList(attachedDeudaList);
            em.persist(usuario);
            for (Grupo grupoListGrupo : usuario.getGrupoList()) {
                Usuario oldLeaderEmailOfGrupoListGrupo = grupoListGrupo.getLeaderEmail();
                grupoListGrupo.setLeaderEmail(usuario);
                grupoListGrupo = em.merge(grupoListGrupo);
                if (oldLeaderEmailOfGrupoListGrupo != null) {
                    oldLeaderEmailOfGrupoListGrupo.getGrupoList().remove(grupoListGrupo);
                    oldLeaderEmailOfGrupoListGrupo = em.merge(oldLeaderEmailOfGrupoListGrupo);
                }
            }
            for (UserXGroup userXGroupListUserXGroup : usuario.getUserXGroupList()) {
                Usuario oldUsuarioOfUserXGroupListUserXGroup = userXGroupListUserXGroup.getUsuario();
                userXGroupListUserXGroup.setUsuario(usuario);
                userXGroupListUserXGroup = em.merge(userXGroupListUserXGroup);
                if (oldUsuarioOfUserXGroupListUserXGroup != null) {
                    oldUsuarioOfUserXGroupListUserXGroup.getUserXGroupList().remove(userXGroupListUserXGroup);
                    oldUsuarioOfUserXGroupListUserXGroup = em.merge(oldUsuarioOfUserXGroupListUserXGroup);
                }
            }
            for (Deuda deudaListDeuda : usuario.getDeudaList()) {
                Usuario oldUsuarioOfDeudaListDeuda = deudaListDeuda.getUsuario();
                deudaListDeuda.setUsuario(usuario);
                deudaListDeuda = em.merge(deudaListDeuda);
                if (oldUsuarioOfDeudaListDeuda != null) {
                    oldUsuarioOfDeudaListDeuda.getDeudaList().remove(deudaListDeuda);
                    oldUsuarioOfDeudaListDeuda = em.merge(oldUsuarioOfDeudaListDeuda);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getEmail()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getEmail());
            List<Grupo> grupoListOld = persistentUsuario.getGrupoList();
            List<Grupo> grupoListNew = usuario.getGrupoList();
            List<UserXGroup> userXGroupListOld = persistentUsuario.getUserXGroupList();
            List<UserXGroup> userXGroupListNew = usuario.getUserXGroupList();
            List<Deuda> deudaListOld = persistentUsuario.getDeudaList();
            List<Deuda> deudaListNew = usuario.getDeudaList();
            List<String> illegalOrphanMessages = null;
            for (Grupo grupoListOldGrupo : grupoListOld) {
                if (!grupoListNew.contains(grupoListOldGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Grupo " + grupoListOldGrupo + " since its leaderEmail field is not nullable.");
                }
            }
            for (UserXGroup userXGroupListOldUserXGroup : userXGroupListOld) {
                if (!userXGroupListNew.contains(userXGroupListOldUserXGroup)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserXGroup " + userXGroupListOldUserXGroup + " since its usuario field is not nullable.");
                }
            }
            for (Deuda deudaListOldDeuda : deudaListOld) {
                if (!deudaListNew.contains(deudaListOldDeuda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Deuda " + deudaListOldDeuda + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Grupo> attachedGrupoListNew = new ArrayList<Grupo>();
            for (Grupo grupoListNewGrupoToAttach : grupoListNew) {
                grupoListNewGrupoToAttach = em.getReference(grupoListNewGrupoToAttach.getClass(), grupoListNewGrupoToAttach.getId());
                attachedGrupoListNew.add(grupoListNewGrupoToAttach);
            }
            grupoListNew = attachedGrupoListNew;
            usuario.setGrupoList(grupoListNew);
            List<UserXGroup> attachedUserXGroupListNew = new ArrayList<UserXGroup>();
            for (UserXGroup userXGroupListNewUserXGroupToAttach : userXGroupListNew) {
                userXGroupListNewUserXGroupToAttach = em.getReference(userXGroupListNewUserXGroupToAttach.getClass(), userXGroupListNewUserXGroupToAttach.getUserXGroupPK());
                attachedUserXGroupListNew.add(userXGroupListNewUserXGroupToAttach);
            }
            userXGroupListNew = attachedUserXGroupListNew;
            usuario.setUserXGroupList(userXGroupListNew);
            List<Deuda> attachedDeudaListNew = new ArrayList<Deuda>();
            for (Deuda deudaListNewDeudaToAttach : deudaListNew) {
                deudaListNewDeudaToAttach = em.getReference(deudaListNewDeudaToAttach.getClass(), deudaListNewDeudaToAttach.getDeudaPK());
                attachedDeudaListNew.add(deudaListNewDeudaToAttach);
            }
            deudaListNew = attachedDeudaListNew;
            usuario.setDeudaList(deudaListNew);
            usuario = em.merge(usuario);
            for (Grupo grupoListNewGrupo : grupoListNew) {
                if (!grupoListOld.contains(grupoListNewGrupo)) {
                    Usuario oldLeaderEmailOfGrupoListNewGrupo = grupoListNewGrupo.getLeaderEmail();
                    grupoListNewGrupo.setLeaderEmail(usuario);
                    grupoListNewGrupo = em.merge(grupoListNewGrupo);
                    if (oldLeaderEmailOfGrupoListNewGrupo != null && !oldLeaderEmailOfGrupoListNewGrupo.equals(usuario)) {
                        oldLeaderEmailOfGrupoListNewGrupo.getGrupoList().remove(grupoListNewGrupo);
                        oldLeaderEmailOfGrupoListNewGrupo = em.merge(oldLeaderEmailOfGrupoListNewGrupo);
                    }
                }
            }
            for (UserXGroup userXGroupListNewUserXGroup : userXGroupListNew) {
                if (!userXGroupListOld.contains(userXGroupListNewUserXGroup)) {
                    Usuario oldUsuarioOfUserXGroupListNewUserXGroup = userXGroupListNewUserXGroup.getUsuario();
                    userXGroupListNewUserXGroup.setUsuario(usuario);
                    userXGroupListNewUserXGroup = em.merge(userXGroupListNewUserXGroup);
                    if (oldUsuarioOfUserXGroupListNewUserXGroup != null && !oldUsuarioOfUserXGroupListNewUserXGroup.equals(usuario)) {
                        oldUsuarioOfUserXGroupListNewUserXGroup.getUserXGroupList().remove(userXGroupListNewUserXGroup);
                        oldUsuarioOfUserXGroupListNewUserXGroup = em.merge(oldUsuarioOfUserXGroupListNewUserXGroup);
                    }
                }
            }
            for (Deuda deudaListNewDeuda : deudaListNew) {
                if (!deudaListOld.contains(deudaListNewDeuda)) {
                    Usuario oldUsuarioOfDeudaListNewDeuda = deudaListNewDeuda.getUsuario();
                    deudaListNewDeuda.setUsuario(usuario);
                    deudaListNewDeuda = em.merge(deudaListNewDeuda);
                    if (oldUsuarioOfDeudaListNewDeuda != null && !oldUsuarioOfDeudaListNewDeuda.equals(usuario)) {
                        oldUsuarioOfDeudaListNewDeuda.getDeudaList().remove(deudaListNewDeuda);
                        oldUsuarioOfDeudaListNewDeuda = em.merge(oldUsuarioOfDeudaListNewDeuda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getEmail();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getEmail();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Grupo> grupoListOrphanCheck = usuario.getGrupoList();
            for (Grupo grupoListOrphanCheckGrupo : grupoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Grupo " + grupoListOrphanCheckGrupo + " in its grupoList field has a non-nullable leaderEmail field.");
            }
            List<UserXGroup> userXGroupListOrphanCheck = usuario.getUserXGroupList();
            for (UserXGroup userXGroupListOrphanCheckUserXGroup : userXGroupListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the UserXGroup " + userXGroupListOrphanCheckUserXGroup + " in its userXGroupList field has a non-nullable usuario field.");
            }
            List<Deuda> deudaListOrphanCheck = usuario.getDeudaList();
            for (Deuda deudaListOrphanCheckDeuda : deudaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Deuda " + deudaListOrphanCheckDeuda + " in its deudaList field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
