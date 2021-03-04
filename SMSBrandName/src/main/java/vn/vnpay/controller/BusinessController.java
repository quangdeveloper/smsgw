package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.bean.Business;
import vn.vnpay.common.*;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.BusinessIMP;
import vn.vnpay.modal.BusinessModal;
import vn.vnpay.search.BusinessSearch;
import vn.vnpay.validator.BusinessValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class BusinessController {
    private static final Logger LOG = LogManager.getLogger(BusinessController.class);

    @Autowired
    BusinessIMP businessIMP;

    @RequestMapping(value = "/get_business_by_filter", method = RequestMethod.POST)
    public List<BusinessModal> getBusiness(@RequestBody BusinessSearch businessSearch) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return businessIMP.getBusinessByFilter(businessSearch);
    }
    @RequestMapping(value = "/create_business", method = RequestMethod.POST)
    public Response create(@RequestBody Business business) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create business with request: {}", GsonCustom.getGsonBuilder().toJson(business));
        Map<String, String> mapError = BusinessValidate.getInstance().validateInput(business, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(businessIMP.createBusiness(business));
    }
    @RequestMapping(value = "/update_business", method = RequestMethod.POST)
    public Response update(@RequestBody Business business) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update business with request: {}", GsonCustom.getGsonBuilder().toJson(business));
        Map<String, String> mapError = BusinessValidate.getInstance().validateInput(business, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(businessIMP.updateBusiness(business));
    }

}
