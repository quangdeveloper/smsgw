package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.bean.SystemConfig;
import vn.vnpay.common.*;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.SystemConfigIMP;
import vn.vnpay.modal.SystemConfigModal;
import vn.vnpay.search.SystemConfigSearch;
import vn.vnpay.validator.SystemConfigValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class SystemConfigController {
    private static final Logger LOG = LogManager.getLogger(SystemConfigController.class);

    @Autowired
    SystemConfigIMP systemConfigIMP;

    @RequestMapping(value = "/get_system_config_by_filter", method = RequestMethod.POST)
    public List<SystemConfigModal> getSystemConfig(@RequestBody SystemConfigSearch systemConfigSearch) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return systemConfigIMP.getSystemConfigByFilter(systemConfigSearch);
    }

    @RequestMapping(value = "/create_system_config", method = RequestMethod.POST)
    public Response create(@RequestBody SystemConfig systemConfig) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create system config with request: {}", GsonCustom.getGsonBuilder().toJson(systemConfig));
        Map<String, String> mapError = SystemConfigValidate.getInstance().validateInput(systemConfig, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(systemConfigIMP.createSystemConfig(systemConfig));
    }

    @RequestMapping(value = "/update_system_config", method = RequestMethod.POST)
    public Response update(@RequestBody SystemConfig systemConfig) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update system config with request: {}", GsonCustom.getGsonBuilder().toJson(systemConfig));
        Map<String, String> mapError = SystemConfigValidate.getInstance().validateInput(systemConfig, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(systemConfigIMP.updateSystemConfig(systemConfig));
    }

    @RequestMapping(value = "/delete_system_config", method = RequestMethod.POST)
    public Response delete(@RequestBody SystemConfig systemConfig) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin delete system config with request: {}", GsonCustom.getGsonBuilder().toJson(systemConfig));
        Map<String, String> mapError = SystemConfigValidate.getInstance().validateInput(systemConfig, Action.DELETE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(systemConfigIMP.deleteSystemConfig(systemConfig));
    }
}
