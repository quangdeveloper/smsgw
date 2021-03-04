package vn.vnpay.daoimp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import vn.vnpay.bean.Group;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.dao.GroupDAO;
import vn.vnpay.modal.GroupModal;
import vn.vnpay.constant.Message;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class GroupIMP implements GroupDAO {
    private final static Logger LOG = LogManager.getLogger(GroupIMP.class);
    private final static String CREATE_GROUP = " call create_group(:p_name, :result_insert)";
    private final static String UPDATE_GROUP = " call update_group(:p_id, :p_name)";
    private final static String DELETE_GROUP = " call delete_group(:p_id)";
    private final static String GET_ALL_GROUP = "get_group_all";

    @Override
    public List<GroupModal> getGroupAll() {
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_ALL_GROUP, GroupModal.class);
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
    public ResponseEnum createGroup(Group group) {
        if (group == null) {
            LOG.info("group is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_GROUP);
            entityManager.getTransaction().begin();
            query.setParameter("p_name", group.getName());
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create group success");
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
    public ResponseEnum updateGroup(Group group) {
        if (group == null) {
            LOG.info("group is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_GROUP);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", Integer.parseInt(group.getId()));
            query.setParameter("p_name", group.getName());
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("Update group success");
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

    @Override
    public ResponseEnum deleteGroup(Group group) {
        if (group == null) {
            LOG.info("group is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(DELETE_GROUP);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", Integer.parseInt(group.getId()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("Delete group success");
            return ResponseEnum.OK;
        } catch (Exception exception) {
            LOG.error("Error from database: ", exception);
            return ResponseEnum.ERROR_DELETE;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
