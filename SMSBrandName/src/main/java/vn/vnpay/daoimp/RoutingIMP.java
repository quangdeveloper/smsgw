package vn.vnpay.daoimp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import vn.vnpay.bean.Routing;
import vn.vnpay.common.Convert;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.dao.RoutingDAO;
import vn.vnpay.entities.RoutingQueryDto;
import vn.vnpay.modal.RoutingModal;
import vn.vnpay.search.RoutingSearch;
import vn.vnpay.constant.Message;

import javax.persistence.*;
import java.util.List;

@Repository
public class RoutingIMP implements RoutingDAO {
    private final static Logger LOG = LogManager.getLogger(RoutingIMP.class);
    private final static String CREATE_ROUTING = " call create_routing(" +
            ":p_provider_id, :p_partner_id, :p_provider_channel_id, :p_sender, :p_keyword_id, :p_queue, :p_description, :p_created_by, :result_insert)";
    private final static String UPDATE_ROUTING = " call update_routing(" +
            ":p_id, :p_provider_id, :p_partner_id, :p_provider_channel_id, :p_sender, :p_keyword_id, :p_queue, :p_description, :p_updated_by, :p_status)";
    private final static String GET_ALL_ROUTING = "get_all_routing";
    private final static String GET_ROUTING_BY_FILTER = "get_routing_by_filter";

    @Override
    public List<RoutingModal> getRoutingByFilter(RoutingSearch routingSearch) {
        if (routingSearch == null) {
            LOG.info("routingSearch is null");
            return null;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_ROUTING_BY_FILTER, RoutingModal.class);
            query.registerStoredProcedureParameter("routingname", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_channel_id", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_keyword_id", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_provider_id", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_partner_id", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_sender", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_status", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("fromdate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("todate", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pagesize", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("pageindex", Integer.class, ParameterMode.IN);
            query.setParameter("routingname", routingSearch.getName());
            query.setParameter("p_channel_id", Convert.getInstance().convertStringToInteger(routingSearch.getProviderChannelId()));
            query.setParameter("p_keyword_id", Convert.getInstance().convertStringToInteger(routingSearch.getKeywordId()));
            query.setParameter("p_provider_id", routingSearch.getProviderId());
            query.setParameter("p_partner_id", routingSearch.getPartnerId());
            query.setParameter("p_sender", routingSearch.getSender());
            query.setParameter("p_status", routingSearch.getStatus());
            query.setParameter("fromdate", routingSearch.getFromDate());
            query.setParameter("todate", routingSearch.getToDate());
            query.setParameter("pagesize", routingSearch.getPageSize());
            query.setParameter("pageindex", routingSearch.getPageIndex());
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
    public List<RoutingQueryDto> getAllRouting() {
        LOG.info("Begin get all routing");
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_ALL_ROUTING, RoutingQueryDto.class);
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
    public ResponseEnum createRouting(Routing routing) {
        if (routing == null) {
            LOG.info("routing is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_ROUTING);
            entityManager.getTransaction().begin();
            query.setParameter("p_provider_id", routing.getProviderId());
            query.setParameter("p_partner_id", routing.getPartnerId());
            query.setParameter("p_provider_channel_id", Integer.parseInt(routing.getProviderChannelId()));
            query.setParameter("p_sender", routing.getSender());
            query.setParameter("p_keyword_id", Integer.parseInt(routing.getKeywordId()));
            query.setParameter("p_queue", routing.getQueue());
            query.setParameter("p_description", new TypedParameterValue(StandardBasicTypes.STRING, routing.getDescription()));
            query.setParameter("p_created_by", new TypedParameterValue(StandardBasicTypes.STRING, routing.getCreatedBy()));
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create routing success");
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
    public ResponseEnum updateRouting(Routing routing) {
        if (routing == null) {
            LOG.info("routing is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_ROUTING);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", Integer.parseInt(routing.getId()));
            query.setParameter("p_provider_id", routing.getProviderId());
            query.setParameter("p_partner_id", routing.getPartnerId());
            query.setParameter("p_provider_channel_id", Integer.parseInt(routing.getProviderChannelId()));
            query.setParameter("p_sender", routing.getSender());
            query.setParameter("p_keyword_id", Integer.parseInt(routing.getKeywordId()));
            query.setParameter("p_queue", routing.getQueue());
            query.setParameter("p_description", new TypedParameterValue(StandardBasicTypes.STRING, routing.getDescription()));
            query.setParameter("p_updated_by", new TypedParameterValue(StandardBasicTypes.STRING, routing.getUpdatedBy()));
            query.setParameter("p_status", Integer.parseInt(routing.getStatus()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update routing success");
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
