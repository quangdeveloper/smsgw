package vn.vnpay.daoimp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.vnpay.bean.Roles;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.dao.RolesDAO;
import vn.vnpay.modal.RoleModal;
import vn.vnpay.constant.Message;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class RolesIMP implements RolesDAO {
    private final static Logger LOG = LogManager.getLogger(RolesIMP.class);
    private final static String CREATE_ROLES = " call create_roles(:p_name, :result_insert)";
    private final static String UPDATE_ROLES = " call update_roles(:p_id, :p_name)";
    private final static String DELETE_ROLES = " call delete_roles(:p_id)";
    private final static String GET_ALL_ROLE = "get_role_all";

    @Override
    @Transactional
    public List<RoleModal> getRoleAll() {
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_ALL_ROLE, RoleModal.class);
            return query.getResultList();
        } catch (Exception exception) {
            LOG.error("Error from database: ", exception);
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public ResponseEnum createRole(Roles roles) {
        if (roles == null) {
            LOG.info("roles is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_ROLES);
            entityManager.getTransaction().begin();
            query.setParameter("p_name", roles.getName());
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create roles success");
            return ResponseEnum.OK;
        } catch (Exception exception) {
            LOG.error("Error from database: ", exception);
            return ResponseEnum.ERROR_INSERT;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public ResponseEnum updateRole(Roles roles) {
        if (roles == null) {
            LOG.info("roles is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_ROLES);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", Integer.parseInt(roles.getId()));
            query.setParameter("p_name", roles.getName());
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update roles success");
            return ResponseEnum.OK;
        } catch (Exception exception) {
            LOG.error("Error from database: ", exception);
            return ResponseEnum.ERROR_UPDATE;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public ResponseEnum deleteRole(Roles roles) {
        if (roles == null) {
            LOG.info("roles is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(DELETE_ROLES);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", Integer.parseInt(roles.getId()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("delete roles success");
            return ResponseEnum.OK;
        } catch (Exception exception) {
            LOG.error("Error from database: ", exception);
            return ResponseEnum.ERROR_DELETE;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
