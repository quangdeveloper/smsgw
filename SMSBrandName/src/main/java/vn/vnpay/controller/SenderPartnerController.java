package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.bean.SenderPartner;
import vn.vnpay.common.*;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.SenderPartnerIMP;
import vn.vnpay.modal.SenderPartnerModal;
import vn.vnpay.search.SenderPartnerSearch;
import vn.vnpay.validator.SenderPartnerValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class SenderPartnerController {
    private static final Logger LOG = LogManager.getLogger(SenderPartnerController.class);

    @Autowired
    SenderPartnerIMP senderPartnerIMP;

    @RequestMapping(value = "/get_sender_partner_by_filter", method = RequestMethod.POST)
    public List<SenderPartnerModal> getSender(@RequestBody SenderPartnerSearch senderPartnerSearch) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return senderPartnerIMP.getSenderPartnerByFilter(senderPartnerSearch);
    }
    @RequestMapping(value = "/create_sender_partner", method = RequestMethod.POST)
    public Response create(@RequestBody SenderPartner senderPartner) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create sender partner with request: {}", GsonCustom.getGsonBuilder().toJson(senderPartner));
        Map<String, String> mapError = SenderPartnerValidate.getInstance().validateInput(senderPartner, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(senderPartnerIMP.createSenderPartner(senderPartner));
    }
    @RequestMapping(value = "/update_sender_partner", method = RequestMethod.POST)
    public Response update(@RequestBody SenderPartner senderPartner) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update sender partner with request: {}", GsonCustom.getGsonBuilder().toJson(senderPartner));
        Map<String, String> mapError = SenderPartnerValidate.getInstance().validateInput(senderPartner, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(senderPartnerIMP.updateSenderPartner(senderPartner));
    }
}
