package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.bean.Provider;
import vn.vnpay.common.*;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.ProviderIMP;
import vn.vnpay.modal.ProviderModal;
import vn.vnpay.search.ProviderSearch;
import vn.vnpay.validator.ProviderValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class ProviderController {
    private static final Logger LOG = LogManager.getLogger(ProviderController.class);

    @Autowired
    ProviderIMP providerIMP;

    @RequestMapping(value = "/provider", method = RequestMethod.POST)
    public List<ProviderModal> getProvider(@RequestBody ProviderSearch providerSearch) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return providerIMP.getProviderByFilter(providerSearch);
    }
    @RequestMapping(value = "/create_provider", method = RequestMethod.POST)
    public Response create(@RequestBody Provider provider) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create provider with request: {}", GsonCustom.getGsonBuilder().toJson(provider));
        Map<String, String> mapError = ProviderValidate.getInstance().validateInput(provider, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(providerIMP.createProvider(provider));
    }
    @RequestMapping(value = "/update_provider", method = RequestMethod.POST)
    public Response update(@RequestBody Provider provider) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update provider with request: {}", GsonCustom.getGsonBuilder().toJson(provider));
        Map<String, String> mapError = ProviderValidate.getInstance().validateInput(provider, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(providerIMP.updateProvider(provider));
    }
}
