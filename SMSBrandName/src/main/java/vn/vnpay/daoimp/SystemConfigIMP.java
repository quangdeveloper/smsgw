package vn.vnpay.daoimp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import vn.vnpay.bean.SystemConfig;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.dao.SystemConfigDAO;
import vn.vnpay.modal.SystemConfigModal;
import vn.vnpay.search.SystemConfigSearch;
import vn.vnpay.constant.Message;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class SystemConfigIMP implements SystemConfigDAO {
    private final static Logger LOG = LogManager.getLogger(SystemConfigIMP.class);
    private final static String CREATE_SYSTEM_CONFIG = " call create_system_config(:p_app_id, :p_key, :p_value, :result_insert)";
    private final static String UPDATE_SYSTEM_CONFIG = " call update_system_config(:p_app_id_old, :p_app_id_new, :p_key, :p_value)";
    private final static String DELETE_SYSTEM_CONFIG = " call delete_system_config(:p_app_id)";
    private final static String GET_SYSTEM_CONFIG_BY_FILTER = "get_system_config_by_filter";

    @Override
    public List<SystemConfigModal> getSystemConfigByFilter(SystemConfigSearch systemConfigSearch) {
        if (systemConfigSearch == null) {
            LOG.info("systemConfigSearch is null");
            return null;
        }

        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_SYSTEM_CONFIG_BY_FILTER, SystemConfigModal.class);
            query.registerStoredProcedureParameter("p_app_id", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_key", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pagesize", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pageindex", Integer.class, ParameterMode.IN);
            query.setParameter("p_app_id", systemConfigSearch.getAppId());
            query.setParameter("p_key", systemConfigSearch.getKey());
            query.setParameter("pagesize", systemConfigSearch.getPageSize());
            query.setParameter("pageindex", systemConfigSearch.getPageIndex());
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
    public ResponseEnum createSystemConfig(SystemConfig systemConfig) {
        if (systemConfig == null) {
            LOG.info("systemConfig is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_SYSTEM_CONFIG);
            entityManager.getTransaction().begin();
            query.setParameter("p_app_id", systemConfig.getAppId());
            query.setParameter("p_key", systemConfig.getKey());
            query.setParameter("p_value", systemConfig.getValue());
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create systemConfig success");
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
    public ResponseEnum updateSystemConfig(SystemConfig systemConfig) {
        if (systemConfig == null) {
            LOG.info("systemConfig is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_SYSTEM_CONFIG);
            entityManager.getTransaction().begin();
            query.setParameter("p_app_id_old", systemConfig.getAppId());
            query.setParameter("p_app_id_new", systemConfig.getAppIdNew());
            query.setParameter("p_key", systemConfig.getKey());
            query.setParameter("p_value", systemConfig.getValue());
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update systemConfig success");
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
    public ResponseEnum deleteSystemConfig(SystemConfig systemConfig) {
        if (systemConfig == null) {
            LOG.info("systemConfig is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(DELETE_SYSTEM_CONFIG);
            entityManager.getTransaction().begin();
            query.setParameter("p_app_id", systemConfig.getAppId());
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("create systemConfig success");
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
