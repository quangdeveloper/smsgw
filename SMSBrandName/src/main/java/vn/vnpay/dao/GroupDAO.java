package vn.vnpay.dao;

import vn.vnpay.bean.Group;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.modal.GroupModal;

import java.util.List;

public interface GroupDAO {
    List<GroupModal> getGroupAll();
    ResponseEnum createGroup(Group group);
    ResponseEnum updateGroup(Group group);
    ResponseEnum deleteGroup(Group group);
}
