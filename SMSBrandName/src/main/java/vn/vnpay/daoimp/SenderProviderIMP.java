package vn.vnpay.daoimp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import vn.vnpay.bean.SenderProvider;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.dao.SenderProviderDAO;
import vn.vnpay.modal.SenderProviderModal;
import vn.vnpay.search.SenderProviderSearch;
import vn.vnpay.constant.Message;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class SenderProviderIMP implements SenderProviderDAO {
    private final static Logger LOG = LogManager.getLogger(SenderProviderIMP.class);
    private final static String CREATE_SENDER_PROVIDER = " call create_sender_provider(:p_sender, :p_create_user, :p_provider_id, :p_created_by, :result_insert)";
    private final static String UPDATE_SENDER_PROVIDER = " call update_sender_provider(:p_sender_old, :p_sender_new, :p_provider_id_old, :p_provider_id_new, :p_create_user,:p_updated_by, :p_status)";
    private final static String GET_SENDER_PROVIDER_BY_FILTER = "get_sender_provider_by_filter";

    @Override
    public List<SenderProviderModal> getSenderProviderByFilter(SenderProviderSearch senderProviderSearch) {
        if (senderProviderSearch == null) {
            LOG.info("senderProviderSearch is null");
            return null;
        }

        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_SENDER_PROVIDER_BY_FILTER, SenderProviderModal.class);
            query.registerStoredProcedureParameter("p_sender", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_provider_id", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_status", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("fromdate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("todate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pagesize", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pageindex", Integer.class, ParameterMode.IN);
            query.setParameter("p_sender", senderProviderSearch.getSender());
            query.setParameter("p_provider_id", senderProviderSearch.getProviderId());
            query.setParameter("p_status", senderProviderSearch.getStatus());
            query.setParameter("fromdate", senderProviderSearch.getFromDate());
            query.setParameter("todate", senderProviderSearch.getToDate());
            query.setParameter("pagesize", senderProviderSearch.getPageSize());
            query.setParameter("pageindex", senderProviderSearch.getPageIndex());
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
    public ResponseEnum createSenderProvider(SenderProvider senderProvider) {
        if (senderProvider == null) {
            LOG.info("senderProvider is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_SENDER_PROVIDER);
            entityManager.getTransaction().begin();
            query.setParameter("p_sender", senderProvider.getSender());
            query.setParameter("p_create_user", senderProvider.getCreateUser());
            query.setParameter("p_provider_id", senderProvider.getProviderId());
            query.setParameter("p_created_by", new TypedParameterValue(StandardBasicTypes.STRING, senderProvider.getCreatedBy()));
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create senderProvider success");
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
    public ResponseEnum updateSenderProvider(SenderProvider senderProvider) {
        if (senderProvider == null) {
            LOG.info("senderProvider is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_SENDER_PROVIDER);
            entityManager.getTransaction().begin();
            query.setParameter("p_sender_old", senderProvider.getSender());
            query.setParameter("p_sender_new", senderProvider.getSenderNew());
            query.setParameter("p_create_user", senderProvider.getCreateUser());
            query.setParameter("p_provider_id_old", senderProvider.getProviderId());
            query.setParameter("p_provider_id_new", senderProvider.getProviderIdNew());
            query.setParameter("p_updated_by", new TypedParameterValue(StandardBasicTypes.STRING, senderProvider.getUpdatedBy()));
            query.setParameter("p_status", Integer.parseInt(senderProvider.getStatus()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update senderProvider success");
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
