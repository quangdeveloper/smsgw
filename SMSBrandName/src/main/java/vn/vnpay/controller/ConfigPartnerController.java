package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.bean.PartnerConfig;
import vn.vnpay.common.*;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.ConfigPartnerIMP;
import vn.vnpay.modal.ConfigPartnerModal;
import vn.vnpay.search.ConfigPartnerSearch;
import vn.vnpay.validator.ConfigPartnerValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class ConfigPartnerController {
    private static final Logger LOG = LogManager.getLogger(ConfigPartnerController.class);

    @Autowired
    ConfigPartnerIMP configPartnerIMP;

    @RequestMapping(value = "/get_config_partner_by_filter", method = RequestMethod.POST)
    public List<ConfigPartnerModal> getConfigPartner(@RequestBody ConfigPartnerSearch configPartnerSearch) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return configPartnerIMP.getConfigPartnerByFilter(configPartnerSearch);
    }
    @RequestMapping(value = "/create_config_partner", method = RequestMethod.POST)
    public Response create(@RequestBody PartnerConfig partnerConfig) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create config partner with request: {}", GsonCustom.getGsonBuilder().toJson(partnerConfig));
        Map<String, String> mapError = ConfigPartnerValidate.getInstance().validateInput(partnerConfig, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(configPartnerIMP.createConfigPartner(partnerConfig));
    }
    @RequestMapping(value = "/update_config_partner", method = RequestMethod.POST)
    public Response update(@RequestBody PartnerConfig partnerConfig) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update config partner with request: {}", GsonCustom.getGsonBuilder().toJson(partnerConfig));
        Map<String, String> mapError = ConfigPartnerValidate.getInstance().validateInput(partnerConfig, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(configPartnerIMP.updateConfigPartner(partnerConfig));
    }
}
