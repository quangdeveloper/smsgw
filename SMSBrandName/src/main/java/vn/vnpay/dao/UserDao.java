package vn.vnpay.dao;

import vn.vnpay.bean.Permissions;
import vn.vnpay.bean.ResponseCustom;
import vn.vnpay.bean.UserRequest;
import vn.vnpay.common.Response;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.modal.UserEntities;
import vn.vnpay.modal.UserModal;

import java.util.List;


public interface UserDao {
    ResponseCustom checkLogin(UserRequest request);
    UserModal getUserByUsername(String username);
    ResponseEnum createUser(UserRequest request);
    ResponseEnum updateUser(UserRequest request);
    ResponseEnum updatePassword(UserRequest request);
    List<UserEntities> getAllUser();
    ResponseEnum deleteUser(UserRequest userRequest);
}
