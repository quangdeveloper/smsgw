package vn.vnpay.daoimp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import vn.vnpay.bean.PartnerConfig;
import vn.vnpay.common.Convert;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.dao.ConfigPartnerDAO;
import vn.vnpay.entities.PartnerConfigQueryDto;
import vn.vnpay.modal.ConfigPartnerModal;
import vn.vnpay.search.ConfigPartnerSearch;
import vn.vnpay.constant.Message;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class ConfigPartnerIMP implements ConfigPartnerDAO {
    private final static Logger LOG = LogManager.getLogger(ConfigPartnerIMP.class);
    private final static String CREATE_CONFIG_PARTNER = " call create_config_partner(" +
            ":p_partner_id, :p_api_key, :p_api_url, :p_list_keyword, :p_list_sender, :p_list_ip, :p_check_ip," +
            " :p_send_sms, :p_check_mobile, :p_encrypt_message, :p_check_sender, :p_check_template, :p_check_duplicate," +
            " :p_duplicate_type, :p_duplicate_time, :p_white_list_mobile, :p_black_list_mobile, :p_from_date, " +
            " :p_to_date, :p_n_from_date, :p_n_to_date, :p_created_by, :result_insert)";
    private final static String UPDATE_CONFIG_PARTNER = " call update_config_partner(" +
            " :p_partner_id_old, :p_partner_id_new, :p_api_key, :p_api_url, :p_list_keyword, :p_list_sender, :p_list_ip, :p_check_ip," +
            " :p_send_sms, :p_check_mobile, :p_encrypt_message, :p_check_sender, :p_check_template, :p_check_duplicate," +
            " :p_duplicate_type, :p_duplicate_time, :p_white_list_mobile, :p_black_list_mobile, :p_from_date, " +
            " :p_to_date, :p_n_from_date, :p_n_to_date, :p_updated_by, :p_status)";
    private final static String GET_ALL_CONFIG_PARTNER = "get_all_config_partner";
    private final static String GET_CONFIG_PARTNER_BY_FILTER = "get_config_partner_by_filter";

    @Override
    public List<ConfigPartnerModal> getConfigPartnerByFilter(ConfigPartnerSearch configPartnerSearch) {
        if (configPartnerSearch == null) {
            LOG.info("configPartnerSearch is null");
            return null;
        }

        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_CONFIG_PARTNER_BY_FILTER, ConfigPartnerModal.class);
            query.registerStoredProcedureParameter("p_partner_id", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_keyword", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_api_key", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_sender", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_status", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("fromdate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("todate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pagesize", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pageindex", Integer.class, ParameterMode.IN);
            query.setParameter("p_partner_id", configPartnerSearch.getPartnerId());
            query.setParameter("p_keyword", configPartnerSearch.getKeyword());
            query.setParameter("p_api_key", configPartnerSearch.getApiKey());
            query.setParameter("p_sender", configPartnerSearch.getSender());
            query.setParameter("p_status", configPartnerSearch.getStatus());
            query.setParameter("fromdate", configPartnerSearch.getFromDate());
            query.setParameter("todate", configPartnerSearch.getToDate());
            query.setParameter("pagesize", configPartnerSearch.getPageSize());
            query.setParameter("pageindex", configPartnerSearch.getPageIndex());
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
    public List<PartnerConfigQueryDto> getAllConfigPartner() {
        LOG.info("Begin get all config partner");
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_ALL_CONFIG_PARTNER, PartnerConfigQueryDto.class);
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
    public ResponseEnum createConfigPartner(PartnerConfig partnerConfig) {
        if (partnerConfig == null) {
            LOG.info("partnerConfig is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_CONFIG_PARTNER);
            entityManager.getTransaction().begin();
            query.setParameter("p_partner_id", partnerConfig.getPartnerId());
            query.setParameter("p_api_key", partnerConfig.getApiKey());
            query.setParameter("p_api_url", new TypedParameterValue(StandardBasicTypes.STRING, partnerConfig.getApiUrl()));
            query.setParameter("p_list_keyword", partnerConfig.getKeywordList());
            query.setParameter("p_list_sender", partnerConfig.getSender());
            query.setParameter("p_list_ip", new TypedParameterValue(StandardBasicTypes.STRING, partnerConfig.getIpList()));
            query.setParameter("p_check_ip", Integer.parseInt(partnerConfig.getIsCheckIp()));
            query.setParameter("p_send_sms", Integer.parseInt(partnerConfig.getIsSendSMS()));
            query.setParameter("p_check_mobile", Integer.parseInt(partnerConfig.getIsCheckMobile()));
            query.setParameter("p_encrypt_message", Integer.parseInt(partnerConfig.getIsEncryptMessage()));
            query.setParameter("p_check_sender", Integer.parseInt(partnerConfig.getIsCheckSender()));
            query.setParameter("p_check_template", Integer.parseInt(partnerConfig.getIsCheckTemplate()));
            query.setParameter("p_check_duplicate", Integer.parseInt(partnerConfig.getIsCheckDuplicate()));
            query.setParameter("p_duplicate_type", Integer.parseInt(partnerConfig.getDuplicateType()));
            query.setParameter("p_duplicate_time", Integer.parseInt(partnerConfig.getDuplicateTime()));
            query.setParameter("p_white_list_mobile", new TypedParameterValue(StandardBasicTypes.STRING, partnerConfig.getWhiteListMobile()));
            query.setParameter("p_black_list_mobile", new TypedParameterValue(StandardBasicTypes.STRING, partnerConfig.getBlackListMobile()));
            query.setParameter("p_from_date", Convert.getInstance().convertTimeStampJavaToPostgresql(partnerConfig.getDFromDate()));
            query.setParameter("p_to_date", Convert.getInstance().convertTimeStampJavaToPostgresql(partnerConfig.getDToDate()));
            query.setParameter("p_n_from_date", Convert.getInstance().convertTimeStampToUnix(partnerConfig.getDFromDate()));
            query.setParameter("p_n_to_date", Convert.getInstance().convertTimeStampToUnix(partnerConfig.getDToDate()));
            query.setParameter("p_created_by", new TypedParameterValue(StandardBasicTypes.STRING, partnerConfig.getCreatedBy()));
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create partnerConfig success");
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
    public ResponseEnum updateConfigPartner(PartnerConfig partnerConfig) {
        if (partnerConfig == null) {
            LOG.info("partnerConfig is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_CONFIG_PARTNER);
            entityManager.getTransaction().begin();
            query.setParameter("p_partner_id_old", partnerConfig.getPartnerIdOld());
            query.setParameter("p_partner_id_new", partnerConfig.getPartnerId());
            query.setParameter("p_api_key", partnerConfig.getApiKey());
            query.setParameter("p_api_url", new TypedParameterValue(StandardBasicTypes.STRING, partnerConfig.getApiUrl()));
            query.setParameter("p_list_keyword", partnerConfig.getKeywordList());
            query.setParameter("p_list_sender", partnerConfig.getSender());
            query.setParameter("p_list_ip", new TypedParameterValue(StandardBasicTypes.STRING, partnerConfig.getIpList()));
            query.setParameter("p_check_ip", Integer.parseInt(partnerConfig.getIsCheckIp()));
            query.setParameter("p_send_sms", Integer.parseInt(partnerConfig.getIsSendSMS()));
            query.setParameter("p_check_mobile", Integer.parseInt(partnerConfig.getIsCheckMobile()));
            query.setParameter("p_encrypt_message", Integer.parseInt(partnerConfig.getIsEncryptMessage()));
            query.setParameter("p_check_sender", Integer.parseInt(partnerConfig.getIsCheckSender()));
            query.setParameter("p_check_template", Integer.parseInt(partnerConfig.getIsCheckTemplate()));
            query.setParameter("p_check_duplicate", Integer.parseInt(partnerConfig.getIsCheckDuplicate()));
            query.setParameter("p_duplicate_type", Integer.parseInt(partnerConfig.getDuplicateType()));
            query.setParameter("p_duplicate_time", Integer.parseInt(partnerConfig.getDuplicateTime()));
            query.setParameter("p_white_list_mobile", new TypedParameterValue(StandardBasicTypes.STRING, partnerConfig.getWhiteListMobile()));
            query.setParameter("p_black_list_mobile", new TypedParameterValue(StandardBasicTypes.STRING, partnerConfig.getBlackListMobile()));
            query.setParameter("p_from_date", Convert.getInstance().convertTimeStampJavaToPostgresql(partnerConfig.getDFromDate()));
            query.setParameter("p_to_date", Convert.getInstance().convertTimeStampJavaToPostgresql(partnerConfig.getDToDate()));
            query.setParameter("p_n_from_date", Convert.getInstance().convertTimeStampToUnix(partnerConfig.getDFromDate()));
            query.setParameter("p_n_to_date", Convert.getInstance().convertTimeStampToUnix(partnerConfig.getDToDate()));
            query.setParameter("p_updated_by", new TypedParameterValue(StandardBasicTypes.STRING, partnerConfig.getUpdatedBy()));
            query.setParameter("p_status", Integer.parseInt(partnerConfig.getStatus()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update partnerConfig success");
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
