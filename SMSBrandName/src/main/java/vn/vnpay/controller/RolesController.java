package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.bean.Roles;
import vn.vnpay.common.*;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.RolesIMP;
import vn.vnpay.modal.RoleModal;
import vn.vnpay.validator.RoleValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;
@RestController
@RequestMapping("/smsgw")
public class RolesController {
    private static final Logger LOG = LogManager.getLogger(RolesController.class);

    @Autowired
    RolesIMP rolesIMP;

    @RequestMapping(value = "/get_role", method = RequestMethod.GET)
    public List<RoleModal> getRoles() {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return rolesIMP.getRoleAll();
    }
    @RequestMapping(value = "/create_role", method = RequestMethod.POST)
    public Response create(@RequestBody Roles roles) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create role with request: {}", GsonCustom.getGsonBuilder().toJson(roles));
        Map<String, String> mapError = RoleValidate.getInstance().validateInput(roles, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(rolesIMP.createRole(roles));
    }
    @RequestMapping(value = "/update_role", method = RequestMethod.POST)
    public Response update(@RequestBody Roles roles) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update role with request: {}", GsonCustom.getGsonBuilder().toJson(roles));
        Map<String, String> mapError = RoleValidate.getInstance().validateInput(roles, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(rolesIMP.updateRole(roles));
    }
    @RequestMapping(value = "/delete_role", method = RequestMethod.POST)
    public Response delete(@RequestBody Roles roles) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin delete role with request: {}", GsonCustom.getGsonBuilder().toJson(roles));
        Map<String, String> mapError = RoleValidate.getInstance().validateInput(roles, Action.DELETE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(rolesIMP.deleteRole(roles));
    }
}
