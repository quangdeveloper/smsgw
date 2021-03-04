package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.bean.Routing;
import vn.vnpay.common.*;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.RoutingIMP;
import vn.vnpay.modal.RoutingModal;
import vn.vnpay.search.RoutingSearch;
import vn.vnpay.validator.RoutingValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class RoutingController {
    private static final Logger LOG = LogManager.getLogger(RoutingController.class);

    @Autowired
    RoutingIMP routingIMP;

    @RequestMapping(value = "/get_routing_by_filter", method = RequestMethod.POST)
    public List<RoutingModal> getRouting(@RequestBody RoutingSearch routingSearch) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return routingIMP.getRoutingByFilter(routingSearch);
    }
    @RequestMapping(value = "/create_routing", method = RequestMethod.POST)
    public Response create(@RequestBody Routing routing) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create routing with request: {}", GsonCustom.getGsonBuilder().toJson(routing));
        Map<String, String> mapError = RoutingValidate.getInstance().validateInput(routing, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(routingIMP.createRouting(routing));
    }
    @RequestMapping(value = "/update_routing", method = RequestMethod.POST)
    public Response update(@RequestBody Routing routing) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update routing with request: {}", GsonCustom.getGsonBuilder().toJson(routing));
        Map<String, String> mapError = RoutingValidate.getInstance().validateInput(routing, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(routingIMP.updateRouting(routing));
    }
}
