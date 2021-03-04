package vn.vnpay.dao;


import vn.vnpay.bean.Roles;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.modal.RoleModal;


import java.util.List;

public interface RolesDAO {
    List<RoleModal> getRoleAll();
    ResponseEnum createRole(Roles roles);
    ResponseEnum updateRole(Roles roles);
    ResponseEnum deleteRole(Roles roles);
}
