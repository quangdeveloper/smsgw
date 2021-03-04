package vn.vnpay.daoimp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.vnpay.bean.ProviderKey;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.dao.ProviderKeyDAO;
import vn.vnpay.modal.ProviderKeyModal;
import vn.vnpay.search.ProviderKeySearch;
import vn.vnpay.constant.Message;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class ProviderKeyIMP implements ProviderKeyDAO {
    private final static Logger LOG = LogManager.getLogger(ProviderKeyIMP.class);
    private final static String CREATE_PROVIDER_KEY = " call create_provider_key(" +
            ":p_provider_id, :p_sender,:p_private_key, :p_private_key_pkcs8,:p_public_key, :p_secret_key, :p_description, :result_insert)";
    private final static String UPDATE_PROVIDER_KEY = " call update_provider_key(" +
            ":p_id, :p_provider_id, :p_sender,:p_private_key, :p_private_key_pkcs8,:p_public_key, :p_secret_key, :p_description, :p_status)";
    private final static String GET_PROVIDER_KEY_BY_FILTER = "get_provider_key_by_filter";


    @Override
    @Transactional
    public List<ProviderKeyModal> getProviderKeyByFilter(ProviderKeySearch providerKeySearch) {
        if (providerKeySearch == null) {
            LOG.info("providerKeySearch is null");
            return null;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_PROVIDER_KEY_BY_FILTER, ProviderKeyModal.class);
            query.registerStoredProcedureParameter("p_sender", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_provider_id", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_status", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pagesize", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pageindex", Integer.class, ParameterMode.IN);
            query.setParameter("p_sender", providerKeySearch.getSender());
            query.setParameter("p_provider_id", providerKeySearch.getProviderId());
            query.setParameter("p_status", providerKeySearch.getStatus());
            query.setParameter("pagesize", providerKeySearch.getPageSize());
            query.setParameter("pageindex", providerKeySearch.getPageIndex());
            query.execute();
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
    public ResponseEnum createProviderKey(ProviderKey providerKey) {
        if (providerKey == null) {
            LOG.info("providerKey is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_PROVIDER_KEY);
            entityManager.getTransaction().begin();
            query.setParameter("p_provider_id", providerKey.getProviderId());
            query.setParameter("p_sender", providerKey.getSender());
            query.setParameter("p_private_key", providerKey.getPriKey());
            query.setParameter("p_private_key_pkcs8", providerKey.getPrivPkcs8());
            query.setParameter("p_public_key", providerKey.getPubKey());
            query.setParameter("p_secret_key", providerKey.getSecretKey());
            query.setParameter("p_description", new TypedParameterValue(StandardBasicTypes.STRING, providerKey.getDescription()));
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create providerKey success");
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
    public ResponseEnum updateProviderKey(ProviderKey providerKey) {
        if (providerKey == null) {
            LOG.info("providerKey is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_PROVIDER_KEY);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", Integer.parseInt(providerKey.getId()));
            query.setParameter("p_provider_id", providerKey.getProviderId());
            query.setParameter("p_sender", providerKey.getSender());
            query.setParameter("p_private_key", providerKey.getPriKey());
            query.setParameter("p_private_key_pkcs8", providerKey.getPrivPkcs8());
            query.setParameter("p_public_key", providerKey.getPubKey());
            query.setParameter("p_secret_key", providerKey.getSecretKey());
            query.setParameter("p_description", new TypedParameterValue(StandardBasicTypes.STRING, providerKey.getDescription()));
            query.setParameter("p_status", Integer.parseInt(providerKey.getStatus()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update providerKey success");
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
}
