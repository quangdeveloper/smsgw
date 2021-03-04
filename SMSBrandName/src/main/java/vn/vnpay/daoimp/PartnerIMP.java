package vn.vnpay.daoimp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import vn.vnpay.bean.Partner;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.dao.PartnerDAO;
import vn.vnpay.entities.PartnerQueryDto;
import vn.vnpay.modal.PartnerModal;
import vn.vnpay.search.PartnerSearch;
import vn.vnpay.constant.Message;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class PartnerIMP implements PartnerDAO {
    private final static Logger LOG = LogManager.getLogger(ProviderChannelIMP.class);
    private final static String CREATE_PARTNER = " call create_partner(" +
            ":p_id, :p_name, :p_description, :p_logo, :p_created_by, :result_insert)";
    private final static String UPDATE_PARTNER = " call update_partner(" +
            ":p_id, :p_name, :p_description, :p_logo, :p_updated_by, :p_status)";
    private final static String GET_ALL_PARTNER = "get_all_partner";
    private final static String GET_PARTNER_BY_FILTER = "get_partner_by_filter";

    @Override
    public List<PartnerModal> getPartnerByFilter(PartnerSearch partnerSearch) {
        if (partnerSearch == null) {
            LOG.info("partnerSearch is null");
            return null;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_PARTNER_BY_FILTER, PartnerModal.class);
            query.registerStoredProcedureParameter("shortname", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_status", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("fromdate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("todate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pagesize", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pageindex", Integer.class, ParameterMode.IN);
            query.setParameter("shortname", partnerSearch.getShortName());
            query.setParameter("p_status", partnerSearch.getStatus());
            query.setParameter("fromdate", partnerSearch.getFromDate());
            query.setParameter("todate", partnerSearch.getToDate());
            query.setParameter("pagesize", partnerSearch.getPageSize());
            query.setParameter("pageindex", partnerSearch.getPageIndex());
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
    public List<PartnerQueryDto> getAllPartner() {
        LOG.info("Begin get all partner");
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_ALL_PARTNER, PartnerQueryDto.class);
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
    public ResponseEnum createPartner(Partner partner) {
        if (partner == null) {
            LOG.info("partner is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_PARTNER);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", partner.getId());
            query.setParameter("p_name", partner.getShortName());
            query.setParameter("p_description", new TypedParameterValue(StandardBasicTypes.STRING, partner.getDescription()));
            query.setParameter("p_logo", partner.getLogo());
            query.setParameter("p_created_by", new TypedParameterValue(StandardBasicTypes.STRING, partner.getCreatedBy()));
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create partner success");
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
    public ResponseEnum updatePartner(Partner partner) {
        if (partner == null) {
            LOG.info("partner is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_PARTNER);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", partner.getId());
            query.setParameter("p_name", partner.getShortName());
            query.setParameter("p_description", new TypedParameterValue(StandardBasicTypes.STRING, partner.getDescription()));
            query.setParameter("p_logo", partner.getLogo());
            query.setParameter("p_updated_by", new TypedParameterValue(StandardBasicTypes.STRING, partner.getUpdatedBy()));
            query.setParameter("p_status", Integer.parseInt(partner.getStatus()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update partner success");
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

