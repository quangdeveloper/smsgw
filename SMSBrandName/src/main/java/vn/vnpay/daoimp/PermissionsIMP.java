package vn.vnpay.daoimp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.vnpay.bean.Permissions;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.dao.PermissionsDAO;
import vn.vnpay.modal.PermissionsModal;
import vn.vnpay.search.PermissionsSearch;
import vn.vnpay.constant.Message;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class PermissionsIMP implements PermissionsDAO {
    private final static Logger LOG = LogManager.getLogger(PermissionsIMP.class);
    private final static String CREATE_PERMISSIONS = " call create_permissions(:p_permissions_name, :p_group_id, :result_insert)";
    private final static String UPDATE_PERMISSIONS = " call update_permissions(:p_id, :p_permissions_name, :p_group_id)";
    private final static String DELETE_PERMISSIONS = " call delete_permissions(:p_id)";
    private final static String GET_ALL_PERMISSIONS = "get_permissions_all";

    @Override
    @Transactional
    public List<PermissionsModal> getPermissionsAll(PermissionsSearch permissionsSearch) {
        if (permissionsSearch == null) {
            LOG.info("permissionsSearch is null");
            return null;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_ALL_PERMISSIONS, PermissionsModal.class);
            query.registerStoredProcedureParameter("p_group_id", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pagesize", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pageindex", Integer.class, ParameterMode.IN);
            query.setParameter("p_group_id", permissionsSearch.getGroupId());
            query.setParameter("pagesize", permissionsSearch.getPageSize());
            query.setParameter("pageindex", permissionsSearch.getPageIndex());
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
    public ResponseEnum createPermissions(Permissions permissions) {
        if (permissions == null) {
            LOG.info("permissions is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_PERMISSIONS);
            entityManager.getTransaction().begin();
            query.setParameter("p_permissions_name", permissions.getName());
            query.setParameter("p_group_id", Integer.parseInt(permissions.getGroupId()));
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create permissions success");
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
    public ResponseEnum updatePermissions(Permissions permissions) {
        if (permissions == null) {
            LOG.info("permissions is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_PERMISSIONS);
            entityManager.getTransaction().begin();
            query.setParameter("p_permissions_name", permissions.getName());
            query.setParameter("p_group_id", Integer.parseInt(permissions.getGroupId()));
            query.setParameter("p_id", Integer.parseInt(permissions.getId()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update permissions success");
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
    public ResponseEnum deletePermissions(Permissions permissions) {
        if (permissions == null) {
            LOG.info("permissions is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(DELETE_PERMISSIONS);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", Integer.parseInt(permissions.getId()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("delete permissions success");
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
