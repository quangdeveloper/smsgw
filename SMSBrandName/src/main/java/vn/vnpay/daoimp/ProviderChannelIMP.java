package vn.vnpay.daoimp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import vn.vnpay.bean.ProviderChannel;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.dao.ProviderChannelDAO;
import vn.vnpay.entities.ChannelQueryDto;
import vn.vnpay.modal.ProviderChannelModal;
import vn.vnpay.search.ProviderChannelSearch;
import vn.vnpay.constant.Message;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class ProviderChannelIMP implements ProviderChannelDAO {
    private final static Logger LOG = LogManager.getLogger(ProviderChannelIMP.class);
    private final static String CREATE_PROVIDER_CHANNEL = " call create_provider_channel(" +
            ":p_id, :p_name, :p_provider_id, :p_route, :p_description, :p_created_by, :result_insert)";
    private final static String UPDATE_PROVIDER_CHANNEL = " call update_provider_channel(" +
            ":p_id, :p_name, :p_provider_id, :p_route, :p_description, :p_updated_by, :p_status)";
    private final static String GET_ALL_CHANNEL = "get_all_channel";
    private final static String GET_CHANNEL_BY_FILTER = "get_provider_channel_by_filter";

    @Override
    public List<ProviderChannelModal> getChannelByFilter(ProviderChannelSearch providerChannelSearch) {
        if (providerChannelSearch == null) {
            LOG.info("request is null");
            return null;
        }

        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_CHANNEL_BY_FILTER, ProviderChannelModal.class);
            query.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_name", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_provider_id", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_queue", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_status", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("fromdate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("todate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pagesize", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pageindex", Integer.class, ParameterMode.IN);
            query.setParameter("p_id", providerChannelSearch.getId());
            query.setParameter("p_name", providerChannelSearch.getName());
            query.setParameter("p_provider_id", providerChannelSearch.getProviderId());
            query.setParameter("p_queue", providerChannelSearch.getQueue());
            query.setParameter("p_status", providerChannelSearch.getStatus());
            query.setParameter("fromdate", providerChannelSearch.getFromDate());
            query.setParameter("todate", providerChannelSearch.getToDate());
            query.setParameter("pagesize", providerChannelSearch.getPageSize());
            query.setParameter("pageindex", providerChannelSearch.getPageIndex());
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
    public List<ChannelQueryDto> getAllChannel() {
        LOG.info("Begin get all channel");
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_ALL_CHANNEL, ChannelQueryDto.class);
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
    public ResponseEnum createProviderChannel(ProviderChannel providerChannel) {
        if (providerChannel == null) {
            LOG.info("providerChannel is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_PROVIDER_CHANNEL);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", Integer.parseInt(providerChannel.getId()));
            query.setParameter("p_name", providerChannel.getName());
            query.setParameter("p_provider_id", providerChannel.getProviderId());
            query.setParameter("p_route", providerChannel.getQueue());
            query.setParameter("p_description", new TypedParameterValue(StandardBasicTypes.STRING, providerChannel.getDescription()));
            query.setParameter("p_created_by", new TypedParameterValue(StandardBasicTypes.STRING, providerChannel.getCreatedBy()));
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create providerChannel success");
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
    public ResponseEnum updateProviderChannel(ProviderChannel providerChannel) {
        if (providerChannel == null) {
            LOG.info("providerChannel is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_PROVIDER_CHANNEL);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", Integer.parseInt(providerChannel.getId()));
            query.setParameter("p_name", providerChannel.getName());
            query.setParameter("p_provider_id", providerChannel.getProviderId());
            query.setParameter("p_route", providerChannel.getQueue());
            query.setParameter("p_description", new TypedParameterValue(StandardBasicTypes.STRING, providerChannel.getDescription()));
            query.setParameter("p_updated_by", new TypedParameterValue(StandardBasicTypes.STRING, providerChannel.getUpdatedBy()));
            query.setParameter("p_status", Integer.parseInt(providerChannel.getStatus()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update providerChannel success");
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
