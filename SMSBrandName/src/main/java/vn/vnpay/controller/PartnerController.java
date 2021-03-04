package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.bean.Partner;
import vn.vnpay.common.*;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.PartnerIMP;
import vn.vnpay.modal.PartnerModal;
import vn.vnpay.search.PartnerSearch;
import vn.vnpay.validator.PartnerValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class PartnerController {
    private static final Logger LOG = LogManager.getLogger(PartnerController.class);

    @Autowired
    PartnerIMP partnerIMP;

    @RequestMapping(value = "/partner", method = RequestMethod.POST)
    public List<PartnerModal> getPartner(@RequestBody PartnerSearch partnerSearch) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));

        return partnerIMP.getPartnerByFilter(partnerSearch);
    }

    @RequestMapping(value = "/create_partner", method = RequestMethod.POST)
    public Response create(@RequestBody Partner partner) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create partner with request: {}", GsonCustom.getGsonBuilder().toJson(partner));
        Map<String, String> mapError = PartnerValidate.getInstance().validateInput(partner, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(partnerIMP.createPartner(partner));
    }

    @RequestMapping(value = "/update_partner", method = RequestMethod.POST)
    public Response update(@RequestBody Partner partner) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update partner with request: {}", GsonCustom.getGsonBuilder().toJson(partner));
        Map<String, String> mapError = PartnerValidate.getInstance().validateInput(partner, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(partnerIMP.updatePartner(partner));
    }
}
