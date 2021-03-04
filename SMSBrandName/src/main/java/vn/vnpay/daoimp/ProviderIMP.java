package vn.vnpay.daoimp;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import vn.vnpay.bean.Provider;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.dao.ProviderDAO;
import vn.vnpay.entities.ProviderQueryDto;
import vn.vnpay.modal.ProviderModal;
import vn.vnpay.search.ProviderSearch;
import vn.vnpay.constant.Message;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class ProviderIMP implements ProviderDAO {
    private final static Logger LOG = LogManager.getLogger(ProviderIMP.class);
    private final static String CREATE_PROVIDER = " call create_provider(:p_id, :p_short_name, :p_description, " +
            ":p_prefix, :p_route_name, :p_logo, :p_created_by, :result_insert)";
    private final static String UPDATE_PROVIDER = " call update_provider(:p_id, :p_short_name, :p_description, " +
            ":p_prefix, :p_route_name, :p_logo, :p_updated_by, :p_status)";
    private final static String GET_ALL_PROVIDER = "get_all_provider";
    private final static String GET_PROVIDER_BY_FILTER = "get_provider_by_filter";

    @Override
    public List<ProviderModal> getProviderByFilter(ProviderSearch providerSearch) {
        if (providerSearch == null) {
            LOG.info("providerSearch is null");
            return null;
        }

        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_PROVIDER_BY_FILTER, ProviderModal.class);
            query.registerStoredProcedureParameter("providername", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_status", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("fromdate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("todate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pagesize", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pageindex", Integer.class, ParameterMode.IN);
            query.setParameter("providername", providerSearch.getShortName());
            query.setParameter("p_status", providerSearch.getStatus());
            query.setParameter("fromdate", providerSearch.getFromDate());
            query.setParameter("todate", providerSearch.getToDate());
            query.setParameter("pagesize", providerSearch.getPageSize());
            query.setParameter("pageindex", providerSearch.getPageIndex());
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
    public List<ProviderQueryDto> getAllProvider() {
        LOG.info("Begin get all provider");
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_ALL_PROVIDER, ProviderQueryDto.class);
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
    public ResponseEnum createProvider(Provider provider) {
        if (provider == null) {
            LOG.info("provider is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_PROVIDER);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", provider.getId());
            query.setParameter("p_short_name", provider.getShortName());
            query.setParameter("p_description", new TypedParameterValue(StandardBasicTypes.STRING, provider.getDescription()));
            query.setParameter("p_prefix", new TypedParameterValue(StandardBasicTypes.STRING, provider.getPrefix()));
            query.setParameter("p_route_name", new TypedParameterValue(StandardBasicTypes.STRING, provider.getQueue()));
            query.setParameter("p_logo", provider.getLogo());
            query.setParameter("p_created_by", new TypedParameterValue(StandardBasicTypes.STRING, provider.getCreatedBy()));
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create provider success");
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
    public ResponseEnum updateProvider(Provider provider) {
        if (provider == null) {
            LOG.info("provider is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_PROVIDER);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", provider.getId());
            query.setParameter("p_short_name", provider.getShortName());
            query.setParameter("p_description", new TypedParameterValue(StandardBasicTypes.STRING, provider.getDescription()));
            query.setParameter("p_prefix", new TypedParameterValue(StandardBasicTypes.STRING, provider.getPrefix()));
            query.setParameter("p_route_name", new TypedParameterValue(StandardBasicTypes.STRING, provider.getQueue()));
            query.setParameter("p_logo", provider.getLogo());
            query.setParameter("p_updated_by", new TypedParameterValue(StandardBasicTypes.STRING, provider.getUpdatedBy()));
            query.setParameter("p_status", Integer.parseInt(provider.getStatus()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update provider success");
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
