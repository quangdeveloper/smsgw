package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.bean.SenderProvider;
import vn.vnpay.common.*;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.SenderProviderIMP;
import vn.vnpay.modal.SenderProviderModal;
import vn.vnpay.search.SenderProviderSearch;
import vn.vnpay.validator.SenderProviderValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class SenderProviderController {
    private static final Logger LOG = LogManager.getLogger(SenderProviderController.class);

    @Autowired
    SenderProviderIMP senderProviderIMP;

    @RequestMapping(value = "/get_sender_provider_by_filter", method = RequestMethod.POST)
    public List<SenderProviderModal> getSenderProvider(@RequestBody SenderProviderSearch senderProviderSearch) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return senderProviderIMP.getSenderProviderByFilter(senderProviderSearch);
    }

    @RequestMapping(value = "/create_sender_provider", method = RequestMethod.POST)
    public Response create(@RequestBody SenderProvider senderProvider) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create sender provider with request: {}", GsonCustom.getGsonBuilder().toJson(senderProvider));
        Map<String, String> mapError = SenderProviderValidate.getInstance().validateInput(senderProvider, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(senderProviderIMP.createSenderProvider(senderProvider));
    }

    @RequestMapping(value = "/update_sender_provider", method = RequestMethod.POST)
    public Response update(@RequestBody SenderProvider senderProvider) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update sender provider with request: {}", GsonCustom.getGsonBuilder().toJson(senderProvider));
        Map<String, String> mapError = SenderProviderValidate.getInstance().validateInput(senderProvider, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(senderProviderIMP.updateSenderProvider(senderProvider));
    }
}
