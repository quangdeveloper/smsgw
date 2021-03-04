package vn.vnpay.daoimp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import vn.vnpay.bean.ProviderPrefix;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.dao.ProviderPrefixDAO;
import vn.vnpay.entities.PrefixQueryDto;
import vn.vnpay.modal.ProviderPrefixModal;
import vn.vnpay.search.ProviderPrefixSearch;
import vn.vnpay.constant.Message;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class ProviderPrefixIMP implements ProviderPrefixDAO {
    private final static Logger LOG = LogManager.getLogger(ProviderPrefixIMP.class);
    private final static String CREATE_PROVIDER_PREFIX = " call create_provider_prefix(:p_id, :p_prefix, :p_route, :p_length, :p_provider_id, :p_created_by, :result_insert)";
    private final static String UPDATE_PROVIDER_PREFIX = " call update_provider_prefix(:p_id, :p_prefix, :p_route, :p_length, :p_provider_id, :p_updated_by, :p_status)";
    private final static String GET_ALL_PREFIX = "get_all_provider_prefix";
    private final static String GET_PREFIX_BY_FILTER = "get_provider_prefix_by_filter";

    @Override
    public List<ProviderPrefixModal> getProviderPrefixByFilter(ProviderPrefixSearch providerPrefixSearch) {
        if (providerPrefixSearch == null) {
            LOG.info("providerPrefixSearch is null");
            return null;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_PREFIX_BY_FILTER, ProviderPrefixModal.class);
            query.registerStoredProcedureParameter("p_prefix", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_provider_id", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_status", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("fromdate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("todate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pagesize", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pageindex", Integer.class, ParameterMode.IN);
            query.setParameter("p_prefix", providerPrefixSearch.getPrefix());
            query.setParameter("p_provider_id", providerPrefixSearch.getProviderId());
            query.setParameter("p_status", providerPrefixSearch.getStatus());
            query.setParameter("fromdate", providerPrefixSearch.getFromDate());
            query.setParameter("todate", providerPrefixSearch.getToDate());
            query.setParameter("pagesize", providerPrefixSearch.getPageSize());
            query.setParameter("pageindex", providerPrefixSearch.getPageIndex());
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
    public List<PrefixQueryDto> getAllProviderPrefix() {
        LOG.info("Begin get all prefix");
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_ALL_PREFIX, PrefixQueryDto.class);
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
    public ResponseEnum createProviderPrefix(ProviderPrefix providerPrefix) {
        if (providerPrefix == null) {
            LOG.info("providerPrefix is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_PROVIDER_PREFIX);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", Integer.parseInt(providerPrefix.getId()));
            query.setParameter("p_prefix", providerPrefix.getName());
            query.setParameter("p_route", providerPrefix.getQueue());
            query.setParameter("p_length", Integer.parseInt(providerPrefix.getLength()));
            query.setParameter("p_provider_id", providerPrefix.getProviderId());
            query.setParameter("p_created_by", new TypedParameterValue(StandardBasicTypes.STRING, providerPrefix.getCreatedBy()));
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create providerPrefix success");
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
    public ResponseEnum updateProviderPrefix(ProviderPrefix providerPrefix) {
        if (providerPrefix == null) {
            LOG.info("providerPrefix is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_PROVIDER_PREFIX);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", Integer.parseInt(providerPrefix.getId()));
            query.setParameter("p_prefix", providerPrefix.getName());
            query.setParameter("p_route", providerPrefix.getQueue());
            query.setParameter("p_length", Integer.parseInt(providerPrefix.getLength()));
            query.setParameter("p_provider_id", providerPrefix.getProviderId());
            query.setParameter("p_updated_by", new TypedParameterValue(StandardBasicTypes.STRING, providerPrefix.getUpdatedBy()));
            query.setParameter("p_status", Integer.parseInt(providerPrefix.getStatus()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update providerPrefix success");
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
