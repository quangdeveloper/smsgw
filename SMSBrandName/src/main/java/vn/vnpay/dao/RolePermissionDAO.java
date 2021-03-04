package vn.vnpay.dao;

import vn.vnpay.bean.RolePermissions;
import vn.vnpay.constant.ResponseEnum;

import java.util.List;

public interface RolePermissionDAO {
    List getRolePermissionAll(String roleId, String getList);
    ResponseEnum createAndDeleteRolePermission(RolePermissions rolePermissions);
}
