package vn.vnpay.daoimp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import vn.vnpay.bean.Keyword;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.dao.KeywordDAO;
import vn.vnpay.entities.KeywordQueryDto;
import vn.vnpay.modal.KeywordModal;
import vn.vnpay.search.KeywordSearch;
import vn.vnpay.constant.Message;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class KeywordIMP implements KeywordDAO {
    private final static Logger LOG = LogManager.getLogger(KeywordIMP.class);
    private final static String CREATE_KEYWORD = " call create_keyword(:p_name, :p_partner_id, :p_created_by, :result_insert)";
    private final static String UPDATE_KEYWORD = " call update_keyword(:p_id, :p_name, :p_partner_id, :p_updated_by, :p_status)";
    private final static String GET_ALL_KEYWORD = "get_all_keyword";
    private final static String GET_KEYWORD_BY_FILTER = "get_keyword_by_filter";

    @Override
    public List<KeywordModal> getKeywordByFilter(KeywordSearch keywordSearch) {
        if (keywordSearch == null) {
            LOG.info("keywordSearch is null");
            return null;
        }

        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_KEYWORD_BY_FILTER, KeywordModal.class);
            query.registerStoredProcedureParameter("p_name", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_partner_id", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_status", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("fromdate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("todate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pagesize", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pageindex", Integer.class, ParameterMode.IN);
            query.setParameter("p_name", keywordSearch.getName());
            query.setParameter("p_partner_id", keywordSearch.getPartnerId());
            query.setParameter("p_status", keywordSearch.getStatus());
            query.setParameter("fromdate", keywordSearch.getFromDate());
            query.setParameter("todate", keywordSearch.getToDate());
            query.setParameter("pagesize", keywordSearch.getPageSize());
            query.setParameter("pageindex", keywordSearch.getPageIndex());
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
    public List<KeywordQueryDto> getAllKeyword() {
        LOG.info("Begin get all keyword");
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_ALL_KEYWORD, KeywordQueryDto.class);
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
    public ResponseEnum createKeyword(Keyword keyword) {
        if (keyword == null) {
            LOG.info("keyword is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_KEYWORD);
            entityManager.getTransaction().begin();
            query.setParameter("p_name", keyword.getName());
            query.setParameter("p_partner_id", keyword.getPartnerId());
            query.setParameter("p_created_by", new TypedParameterValue(StandardBasicTypes.STRING, keyword.getCreatedBy()));
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create keyword success");
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
    public ResponseEnum updateKeyword(Keyword keyword) {
        if (keyword == null) {
            LOG.info("keyword is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_KEYWORD);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", Integer.parseInt(keyword.getId()));
            query.setParameter("p_name", keyword.getName());
            query.setParameter("p_partner_id", keyword.getPartnerId());
            query.setParameter("p_updated_by", new TypedParameterValue(StandardBasicTypes.STRING, keyword.getUpdatedBy()));
            query.setParameter("p_status", Integer.parseInt(keyword.getStatus()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update keyword success");
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
