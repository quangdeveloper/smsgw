package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.bean.Group;
import vn.vnpay.common.*;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.GroupIMP;
import vn.vnpay.modal.GroupModal;
import vn.vnpay.validator.GroupValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class GroupController {
    private static final Logger LOG = LogManager.getLogger(GroupController.class);

    @Autowired
    GroupIMP groupIMP;

    @RequestMapping(value = "/get_group", method = RequestMethod.GET)
    public List<GroupModal> getGroup() {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return groupIMP.getGroupAll();
    }

    @RequestMapping(value = "/create_group", method = RequestMethod.POST)
    public Response create(@RequestBody Group group) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create group with request: {}", GsonCustom.getGsonBuilder().toJson(group));
        Map<String, String> mapError = GroupValidate.getInstance().validateInput(group, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(groupIMP.createGroup(group));
    }

    @RequestMapping(value = "/update_group", method = RequestMethod.POST)
    public Response update(@RequestBody Group group) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update group with request: {}", GsonCustom.getGsonBuilder().toJson(group));
        Map<String, String> mapError = GroupValidate.getInstance().validateInput(group, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(groupIMP.updateGroup(group));
    }

    @RequestMapping(value = "/delete_group", method = RequestMethod.POST)
    public Response delete(@RequestBody Group group) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin delete group with request: {}", GsonCustom.getGsonBuilder().toJson(group));
        Map<String, String> mapError = GroupValidate.getInstance().validateInput(group, Action.DELETE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(groupIMP.deleteGroup(group));
    }
}
