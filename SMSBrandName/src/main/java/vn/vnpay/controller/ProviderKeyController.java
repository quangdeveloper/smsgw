package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.bean.ProviderKey;
import vn.vnpay.common.*;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.ProviderKeyIMP;
import vn.vnpay.modal.ProviderKeyModal;
import vn.vnpay.search.ProviderKeySearch;
import vn.vnpay.validator.ProviderKeyValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class ProviderKeyController {
    private static final Logger LOG = LogManager.getLogger(ProviderKeyController.class);

    @Autowired
    ProviderKeyIMP providerKeyIMP;

    @RequestMapping(value = "/provider_key", method = RequestMethod.POST)
    public List<ProviderKeyModal> getProviderKey(@RequestBody ProviderKeySearch providerKeySearch) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return providerKeyIMP.getProviderKeyByFilter(providerKeySearch);
    }

    @RequestMapping(value = "/create_provider_key", method = RequestMethod.POST)
    public Response create(@RequestBody ProviderKey providerKey) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create provider key with request: {}", GsonCustom.getGsonBuilder().toJson(providerKey));
        Map<String, String> mapError = ProviderKeyValidate.getInstance().validateInput(providerKey, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(providerKeyIMP.createProviderKey(providerKey));
    }

    @RequestMapping(value = "/update_provider_key", method = RequestMethod.POST)
    public Response update(@RequestBody ProviderKey providerKey) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update provider key with request: {}", GsonCustom.getGsonBuilder().toJson(providerKey));
        Map<String, String> mapError = ProviderKeyValidate.getInstance().validateInput(providerKey, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(providerKeyIMP.updateProviderKey(providerKey));
    }
}
