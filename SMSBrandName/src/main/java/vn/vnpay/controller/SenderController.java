package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.bean.Sender;
import vn.vnpay.common.*;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.SenderIMP;
import vn.vnpay.modal.SenderModal;
import vn.vnpay.search.SenderSearch;
import vn.vnpay.validator.SenderValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class SenderController {
    private static final Logger LOG = LogManager.getLogger(SenderController.class);

    @Autowired
    SenderIMP senderIMP;

    @RequestMapping(value = "/get_sender_by_filter", method = RequestMethod.POST)
    public List<SenderModal> getSender(@RequestBody SenderSearch senderSearch) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return senderIMP.getSenderByFilter(senderSearch);
    }

    @RequestMapping(value = "/create_sender", method = RequestMethod.POST)
    public Response create(@RequestBody Sender sender) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create sender with request: {}", GsonCustom.getGsonBuilder().toJson(sender));
        Map<String, String> mapError = SenderValidate.getInstance().validateInput(sender, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(senderIMP.createSender(sender));
    }

    @RequestMapping(value = "/update_sender", method = RequestMethod.POST)
    public Response update(@RequestBody Sender sender) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update sender with request: {}", GsonCustom.getGsonBuilder().toJson(sender));
        Map<String, String> mapError = SenderValidate.getInstance().validateInput(sender, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(senderIMP.updateSender(sender));
    }
}
