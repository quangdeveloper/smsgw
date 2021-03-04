package vn.vnpay.daoimp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import vn.vnpay.bean.RolePermissions;
import vn.vnpay.connect.EntityManagerFactoryConfig;
import vn.vnpay.constant.Message;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.dao.RolePermissionDAO;
import vn.vnpay.modal.RolePermissionModal;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;
@Repository
public class RolePermissionIMP implements RolePermissionDAO {
    private final static Logger LOG = LogManager.getLogger(RolePermissionIMP.class);
    private final static String CREATE_ROLE_PERMISSIONS = " call create_role_permissions(:p_role_id, :p_permissions_id, :result_insert)";
    private final static String DELETE_ROLE_PERMISSIONS = " call delete_role_permission(:p_role_id)";
    private final static String GET_ALL_ROLE_PERMISSIONS = "get_role_permission_by_filter";

    @Override
    public List getRolePermissionAll(String roleId, String getList) {
        List<RolePermissionModal> rolePermissionModals = new ArrayList<>();
        List<Integer> listPermissionId = new ArrayList<>();
        List<String> listPermissionName = new ArrayList<>();
        if (roleId == null) {
            LOG.info("roleId is null");
            return null;
        }
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
            if (entityManager == null) {
                LOG.warn("entityManager not available");
                return null;
            }
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(GET_ALL_ROLE_PERMISSIONS, RolePermissionModal.class);
            query.registerStoredProcedureParameter("p_role_id", Integer.class, ParameterMode.IN);
            query.setParameter("p_role_id", Integer.parseInt(roleId));
            rolePermissionModals = query.getResultList();
            for(int i = 0; i < rolePermissionModals.size(); i++){
                listPermissionId.add(rolePermissionModals.get(i).getId());
                listPermissionName.add(rolePermissionModals.get(i).getName());
            }
            if(getList.equals(Message.LIST_PERMISSION_ID)){
                return listPermissionId;
            } else if(getList.equals(Message.LIST_PERMISSION_NAME)){
                return listPermissionName;
            }
            return rolePermissionModals;
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
    public ResponseEnum createAndDeleteRolePermission(RolePermissions rolePermissions) {
        if (rolePermissions == null) {
            LOG.info("rolePermissions is null");
            return ResponseEnum.ERROR_VALIDATE;
        }
            EntityManager entityManager = null;
            try {
                entityManager = EntityManagerFactoryConfig.getEntityManagerFactory().createEntityManager();
                if (entityManager == null) {
                    LOG.warn("entityManager not available");
                    return ResponseEnum.INTERNAL_SERVER;
                }
                entityManager.getTransaction().begin();
                    Query queryDelete = entityManager.createNativeQuery(DELETE_ROLE_PERMISSIONS);
                    queryDelete.setParameter("p_role_id", Integer.parseInt(rolePermissions.getRoleId()));
                    queryDelete.executeUpdate();
                for(int i = 0; i < rolePermissions.getListPermissionsId().size(); i++) {
                    Query queryAdd = entityManager.createNativeQuery(CREATE_ROLE_PERMISSIONS);
                    queryAdd.setParameter("p_role_id", Integer.parseInt(rolePermissions.getRoleId()));
                    queryAdd.setParameter("p_permissions_id", Integer.parseInt(rolePermissions.getListPermissionsId().get(i)));
                    queryAdd.setParameter("result_insert", 0);
                    queryAdd.getSingleResult();
                }
                    entityManager.getTransaction().commit();



            } catch (Exception exception) {
                LOG.error("Error from database: ", exception);
                return ResponseEnum.ERROR_INSERT;
            } finally {
                if (entityManager != null) {
                    entityManager.close();
                }
            }
        LOG.info("create and delete permissions success");
        return ResponseEnum.OK;
    }
    }

