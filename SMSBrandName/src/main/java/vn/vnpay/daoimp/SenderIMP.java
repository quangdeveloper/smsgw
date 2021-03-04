package vn.vnpay.daoimp;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import vn.vnpay.bean.Sender;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.dao.SenderDAO;
import vn.vnpay.entities.SenderQueryDto;
import vn.vnpay.modal.SenderModal;
import vn.vnpay.search.SenderSearch;
import vn.vnpay.constant.Message;


import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class SenderIMP implements SenderDAO {
    private final static Logger LOG = LogManager.getLogger(SenderIMP.class);
    private final static String GET_SENDER_BY_FILTER = "get_sender_by_filter";
    private final static String CREATE_SENDER = "call create_sender(:p_sender, :p_created_by, :result_insert)";
    private final static String UPDATE_SENDER = "call update_sender(:p_sender_old, :p_sender_new, :p_updated_by, :p_status)";
    private final static String GET_ALL_SENDER = "get_all_sender";

    @Override
    public List<SenderModal> getSenderByFilter(SenderSearch senderSearch) {
        if (senderSearch == null) {
            LOG.info("senderSearch is null");
            return null;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_SENDER_BY_FILTER, SenderModal.class);
            query.registerStoredProcedureParameter("p_sender", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_status", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("fromdate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("todate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pagesize", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pageindex", Integer.class, ParameterMode.IN);
            query.setParameter("p_sender", senderSearch.getSender());
            query.setParameter("p_status", senderSearch.getStatus());
            query.setParameter("fromdate", senderSearch.getFromDate());
            query.setParameter("todate", senderSearch.getToDate());
            query.setParameter("pagesize", senderSearch.getPageSize());
            query.setParameter("pageindex", senderSearch.getPageIndex());
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
    public List<SenderQueryDto> getAllSender() {
        LOG.info("Begin get all sender");
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_ALL_SENDER, SenderQueryDto.class);
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
    public ResponseEnum createSender(Sender sender) {
        if (sender == null) {
            LOG.info("sender is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_SENDER);
            entityManager.getTransaction().begin();
            query.setParameter("p_sender", sender.getSender());
            query.setParameter("p_created_by", new TypedParameterValue(StandardBasicTypes.STRING, sender.getCreatedBy()));
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create sender success");
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
    public ResponseEnum updateSender(Sender sender) {
        if (sender == null) {
            LOG.info("sender is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_SENDER);
            entityManager.getTransaction().begin();
            query.setParameter("p_sender_old", sender.getSender());
            query.setParameter("p_sender_new", sender.getSenderNew());
            query.setParameter("p_updated_by", new TypedParameterValue(StandardBasicTypes.STRING, sender.getUpdatedBy()));
            query.setParameter("p_status", Integer.parseInt(sender.getStatus()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update sender success");
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
