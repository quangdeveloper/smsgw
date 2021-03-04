package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.vnpay.bean.Permissions;
import vn.vnpay.common.*;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.PermissionsIMP;
import vn.vnpay.modal.PermissionsModal;
import vn.vnpay.search.PermissionsSearch;
import vn.vnpay.validator.PermissionValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class PermissionsController {
    private static final Logger LOG = LogManager.getLogger(PermissionsController.class);

    @Autowired
    PermissionsIMP permissionsIMP;

    @RequestMapping(value = "/get_permissions", method = RequestMethod.POST)
    public List<PermissionsModal> getPermissions(@RequestBody PermissionsSearch permissionsSearch) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return permissionsIMP.getPermissionsAll(permissionsSearch);
    }

    @RequestMapping(value = "/create_permissions", method = RequestMethod.POST)
    public Response create(@RequestBody Permissions permissions) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create permissions with request: {}", GsonCustom.getGsonBuilder().toJson(permissions));
        Map<String, String> mapError = PermissionValidate.getInstance().validateInput(permissions, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(permissionsIMP.createPermissions(permissions));
    }

    @RequestMapping(value = "/update_permissions", method = RequestMethod.POST)
    public Response update(@RequestBody Permissions permissions) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update permissions with request: {}", GsonCustom.getGsonBuilder().toJson(permissions));
        Map<String, String> mapError = PermissionValidate.getInstance().validateInput(permissions, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(permissionsIMP.updatePermissions(permissions));
    }

    @RequestMapping(value = "/delete_permissions", method = RequestMethod.POST)
    public Response delete(@RequestBody Permissions permissions) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin delete permissions with request: {}", GsonCustom.getGsonBuilder().toJson(permissions));
        Map<String, String> mapError = PermissionValidate.getInstance().validateInput(permissions, Action.DELETE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(permissionsIMP.deletePermissions(permissions));
    }
}
