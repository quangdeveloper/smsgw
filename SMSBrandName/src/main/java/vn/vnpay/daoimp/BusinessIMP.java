package vn.vnpay.daoimp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import vn.vnpay.bean.Business;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.dao.BusinessDAO;
import vn.vnpay.modal.BusinessModal;
import vn.vnpay.search.BusinessSearch;
import vn.vnpay.constant.Message;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class BusinessIMP implements BusinessDAO {
    private final static Logger LOG = LogManager.getLogger(BusinessIMP.class);
    private final static String CREATE_BUSINESS = " call create_business(:p_id, :p_name, :p_created_by, :result_insert)";
    private final static String UPDATE_BUSINESS = " call update_business(:p_id, :p_name, :p_updated_by, :p_status)";
    private final static String GET_BUSINESS_BY_FILTER = "V2_PKG_BUSINESS.GET_BUSINESS_BY_FILTER";

    @Override
    public List<BusinessModal> getBusinessByFilter(BusinessSearch businessSearch) {
        if (businessSearch == null) {
            LOG.info("businessSearch is null");
            return null;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_BUSINESS_BY_FILTER, Object.class);
            query.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_name", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_status", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("fromdate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("todate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pagesize", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pageindex", Integer.class, ParameterMode.IN);
            query.setParameter("p_id", businessSearch.getId());
            query.setParameter("p_name", businessSearch.getName());
            query.setParameter("p_status", businessSearch.getStatus());
            query.setParameter("fromdate", businessSearch.getFromDate());
            query.setParameter("todate", businessSearch.getToDate());
            query.setParameter("pagesize", businessSearch.getPageSize());
            query.setParameter("pageindex", businessSearch.getPageIndex());
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
    public ResponseEnum createBusiness(Business business) {
        if (business == null) {
            LOG.info("business is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_BUSINESS);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", Integer.parseInt(business.getId()));
            query.setParameter("p_name", business.getName());
            query.setParameter("p_created_by", new TypedParameterValue(StandardBasicTypes.STRING, business.getCreatedBy()));
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create business success");
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
    public ResponseEnum updateBusiness(Business business) {
        if (business == null) {
            LOG.info("business is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_BUSINESS);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", Integer.parseInt(business.getId()));
            query.setParameter("p_name", business.getName());
            query.setParameter("p_updated_by", new TypedParameterValue(StandardBasicTypes.STRING, business.getUpdatedBy()));
            query.setParameter("p_status", new TypedParameterValue(StandardBasicTypes.INTEGER, Integer.parseInt(business.getStatus())));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update business success");
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
