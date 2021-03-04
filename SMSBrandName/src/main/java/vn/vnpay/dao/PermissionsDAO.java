package vn.vnpay.dao;

import vn.vnpay.bean.Permissions;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.modal.PermissionsModal;
import vn.vnpay.search.PermissionsSearch;

import java.util.List;

public interface PermissionsDAO {
    List<PermissionsModal> getPermissionsAll(PermissionsSearch permissionsSearch);
    ResponseEnum createPermissions(Permissions permissions);
    ResponseEnum updatePermissions(Permissions permissions);
    ResponseEnum deletePermissions(Permissions permissions);
}
