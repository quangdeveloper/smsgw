package vn.vnpay.service;

import org.springframework.stereotype.Service;
import vn.vnpay.bean.ResponseCustom;
import vn.vnpay.bean.UserRequest;
import vn.vnpay.bean.User;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.UserIMP;
import vn.vnpay.modal.UserEntities;
import vn.vnpay.modal.UserModal;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    public UserModal getUserByUsername(String username) {
        return UserIMP.getInstance().getUserByUsername(username);
    }

    public ResponseCustom checkLogin(UserRequest user) {
        return UserIMP.getInstance().checkLogin(user);
    }

    public ResponseEnum createUser(UserRequest user) {
        return UserIMP.getInstance().createUser(user);
    }

    public ResponseEnum updateUser(UserRequest user) {
        return UserIMP.getInstance().updateUser(user);
    }
    public ResponseEnum updatePassword(UserRequest user) {
        return UserIMP.getInstance().updatePassword(user);
    }

    public List<UserEntities> getAllUser() {
        return UserIMP.getInstance().getAllUser();
    }
    public ResponseEnum deleteUser(UserRequest user) {
        return UserIMP.getInstance().deleteUser(user);
    }
}
