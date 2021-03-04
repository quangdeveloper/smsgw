package vn.vnpay.dao;

import vn.vnpay.bean.SystemConfig;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.modal.SystemConfigModal;
import vn.vnpay.search.SystemConfigSearch;

import java.util.List;

public interface SystemConfigDAO {
    List<SystemConfigModal> getSystemConfigByFilter(SystemConfigSearch systemConfigSearch);
    ResponseEnum createSystemConfig(SystemConfig systemConfig);
    ResponseEnum updateSystemConfig(SystemConfig systemConfig);
    ResponseEnum deleteSystemConfig(SystemConfig systemConfig);
}
