package vn.vnpay.daoimp;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import vn.vnpay.bean.SenderPartner;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.dao.SenderPartnerDAO;
import vn.vnpay.modal.SenderPartnerModal;
import vn.vnpay.search.SenderPartnerSearch;
import vn.vnpay.constant.Message;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class SenderPartnerIMP implements SenderPartnerDAO {
    private final static Logger LOG = LogManager.getLogger(SenderPartnerIMP.class);
    private final static String CREATE_SENDER_PARTNER = " call create_sender_partner(:p_sender, :p_partner_id, :p_created_by, :result_insert)";
    private final static String UPDATE_SENDER_PARTNER = " call update_sender_partner(:p_sender_old, :p_sender_new, :p_partner_id_old, :p_partner_id_new, :p_updated_by, :p_status)";
    private final static String GET_SENDER_PARTNER_BY_FILTER = "get_sender_partner_by_filter";

    @Override
    public List<SenderPartnerModal> getSenderPartnerByFilter(SenderPartnerSearch senderPartnerSearch) {
        if (senderPartnerSearch == null) {
            LOG.info("senderPartnerSearch is null");
            return null;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_SENDER_PARTNER_BY_FILTER, SenderPartnerModal.class);
            query.registerStoredProcedureParameter("p_sender", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_partner_id", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_status", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("fromdate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("todate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pagesize", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pageindex", Integer.class, ParameterMode.IN);
            query.setParameter("p_sender", senderPartnerSearch.getSender());
            query.setParameter("p_partner_id", senderPartnerSearch.getPartnerId());
            query.setParameter("p_status", senderPartnerSearch.getStatus());
            query.setParameter("fromdate", senderPartnerSearch.getFromDate());
            query.setParameter("todate", senderPartnerSearch.getToDate());
            query.setParameter("pagesize", senderPartnerSearch.getPageSize());
            query.setParameter("pageindex", senderPartnerSearch.getPageIndex());
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
    public ResponseEnum createSenderPartner(SenderPartner senderPartner) {
        if (senderPartner == null) {
            LOG.info("senderPartner is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_SENDER_PARTNER);
            entityManager.getTransaction().begin();
            query.setParameter("p_sender", senderPartner.getSender());
            query.setParameter("p_partner_id", senderPartner.getPartnerId());
            query.setParameter("p_created_by", new TypedParameterValue(StandardBasicTypes.STRING, senderPartner.getCreatedBy()));
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create senderPartner success");
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
    public ResponseEnum updateSenderPartner(SenderPartner senderPartner) {
        if (senderPartner == null) {
            LOG.info("senderPartner is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_SENDER_PARTNER);
            entityManager.getTransaction().begin();
            query.setParameter("p_sender_old", senderPartner.getSender());
            query.setParameter("p_sender_new", senderPartner.getSenderNew());
            query.setParameter("p_partner_id_old", senderPartner.getPartnerId());
            query.setParameter("p_partner_id_new", senderPartner.getPartnerIdNew());
            query.setParameter("p_updated_by", new TypedParameterValue(StandardBasicTypes.STRING, senderPartner.getUpdatedBy()));
            query.setParameter("p_status", Integer.parseInt(senderPartner.getStatus()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update senderPartner success");
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
