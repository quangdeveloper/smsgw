package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.bean.ProviderPrefix;
import vn.vnpay.common.*;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.ProviderPrefixIMP;
import vn.vnpay.modal.ProviderPrefixModal;
import vn.vnpay.search.ProviderPrefixSearch;
import vn.vnpay.validator.ProviderPrefixValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class ProviderPrefixController {
    private static final Logger LOG = LogManager.getLogger(ProviderPrefixController.class);

    @Autowired
    ProviderPrefixIMP providerPrefixIMP;

    @RequestMapping(value = "/provider_prefix", method = RequestMethod.POST)
    public List<ProviderPrefixModal> getProviderPrefix(@RequestBody ProviderPrefixSearch providerPrefixSearch) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return providerPrefixIMP.getProviderPrefixByFilter(providerPrefixSearch);
    }
    @RequestMapping(value = "/create_provider_prefix", method = RequestMethod.POST)
    public Response create(@RequestBody ProviderPrefix providerPrefix) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create provider prefix with request: {}", GsonCustom.getGsonBuilder().toJson(providerPrefix));
        Map<String, String> mapError = ProviderPrefixValidate.getInstance().validateInput(providerPrefix, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(providerPrefixIMP.createProviderPrefix(providerPrefix));
    }
    @RequestMapping(value = "/update_provider_prefix", method = RequestMethod.POST)
    public Response update(@RequestBody ProviderPrefix providerPrefix) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update provider prefix with request: {}", GsonCustom.getGsonBuilder().toJson(providerPrefix));
        Map<String, String> mapError = ProviderPrefixValidate.getInstance().validateInput(providerPrefix, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(providerPrefixIMP.updateProviderPrefix(providerPrefix));
    }
}
