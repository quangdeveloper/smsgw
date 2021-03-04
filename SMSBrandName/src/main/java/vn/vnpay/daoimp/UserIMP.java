package vn.vnpay.daoimp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import vn.vnpay.bean.Permissions;
import vn.vnpay.bean.ResponseCustom;
import vn.vnpay.bean.SystemConfig;
import vn.vnpay.bean.UserRequest;
import vn.vnpay.common.Response;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.constant.Message;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.dao.UserDao;
import vn.vnpay.entities.KeywordQueryDto;
import vn.vnpay.modal.UserEntities;
import vn.vnpay.modal.UserModal;
import vn.vnpay.sercutity.HashUtils;

import javax.persistence.*;
import java.util.List;


@Repository
public class UserIMP implements UserDao {

    private final static Logger LOG = LogManager.getLogger(UserIMP.class);
    private final static String CHECK_LOGIN = "smsgw.check_login";
    private final static String CREATE_USER = "call smsgw.create_user(:p_user_name, :p_full_name, :p_pass_word, :p_email, :p_role_id, :result_insert)";
    private final static String UPDATE_USER = "call smsgw.update_user(:p_id, :p_user_name, :p_full_name, :p_email, :p_role_id)";
    private final static String GET_ALL_USER = "smsgw.get_all_user";
    private final static String DELETE_USER = " call smsgw.delete_user(:p_id)";
    private final static String UPDATE_PASSWORD = " call smsgw.update_password(:p_user_name, :p_password)";
    private final static int LOGIN_SUCCESS = 1;
    private static UserIMP instance;

    public static UserIMP getInstance() {
        if (instance == null) {
            synchronized (UserIMP.class) {
                if (instance == null)
                    instance = new UserIMP();
            }
        }
        return instance;
    }



    @Override
    public ResponseCustom checkLogin(UserRequest request) {
        if (request == null) {
            LOG.info("request is null");
            return ResponseCustom.LOGIN_FAIL;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseCustom.SERVER_INTERNAL;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(CHECK_LOGIN);
            query.registerStoredProcedureParameter("p_username", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_password", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("result_login", Integer.class, ParameterMode.OUT);
            query.setParameter("p_username", request.getUsername());
            query.setParameter("p_password", HashUtils.sha1Base64(request.getPassword()));
            query.execute();
            if ((Integer) query.getOutputParameterValue("result_login") == LOGIN_SUCCESS) {
                return ResponseCustom.SUCCESS;
            }
            return ResponseCustom.LOGIN_FAIL;
        } catch (Exception exception) {
            LOG.error("Error from database: ", exception);
            return ResponseCustom.SERVER_INTERNAL;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public UserModal getUserByUsername(String username) {
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("set_token", UserModal.class);
            query.registerStoredProcedureParameter("p_username", String.class, ParameterMode.IN);
            query.setParameter("p_username", username);
            query.execute();
            return (UserModal) query.getSingleResult();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public ResponseEnum createUser(UserRequest userRequest) {
        if (userRequest == null) {
            LOG.info("userRequest is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(CREATE_USER);
            entityManager.getTransaction().begin();
            query.setParameter("p_user_name", userRequest.getUsername());
            query.setParameter("p_full_name", userRequest.getFullName());
            query.setParameter("p_pass_word", HashUtils.sha1Base64(userRequest.getPassword()));
            query.setParameter("p_email", userRequest.getEmail());
            query.setParameter("p_role_id", Integer.parseInt(userRequest.getRoleId()));
            query.setParameter("result_insert", 0);
            int resultInsert = (Integer) query.getSingleResult();
            entityManager.getTransaction().commit();
            if (resultInsert == Message.DUPLICATE) {
                LOG.warn("insert error because key exists");
                return ResponseEnum.ERROR_DUPLICATE;
            }
            LOG.info("create user success");
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
    public ResponseEnum updateUser(UserRequest userRequest) {
        if (userRequest == null) {
            LOG.info("userRequest is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_USER);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", Integer.parseInt(userRequest.getId()));
            query.setParameter("p_user_name", userRequest.getUsername());
            query.setParameter("p_full_name", userRequest.getFullName());
            query.setParameter("p_email", userRequest.getEmail());
            query.setParameter("p_role_id", Integer.parseInt(userRequest.getRoleId()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update user success");
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
    public ResponseEnum updatePassword(UserRequest request) {
        if (request == null) {
            LOG.info("request is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(UPDATE_PASSWORD);
            entityManager.getTransaction().begin();
            query.setParameter("p_user_name", request.getUsername());
            query.setParameter("p_password", HashUtils.sha1Base64(request.getPassword()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("update password success");
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
    public List<UserEntities> getAllUser() {
        LOG.info("Begin get all user");
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_ALL_USER, UserEntities.class);
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
    public ResponseEnum deleteUser(UserRequest userRequest) {
        if (userRequest == null) {
            LOG.info("userRequest is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return ResponseEnum.INTERNAL_SERVER;
            }
            Query query = entityManager.createNativeQuery(DELETE_USER);
            entityManager.getTransaction().begin();
            query.setParameter("p_id", Integer.parseInt(userRequest.getId()));
            query.executeUpdate();
            entityManager.getTransaction().commit();
            LOG.info("delete userRequest success");
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

