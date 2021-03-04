package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.vnpay.bean.RolePermissions;
import vn.vnpay.common.GsonCustom;
import vn.vnpay.common.LogCommon;
import vn.vnpay.common.Response;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.Message;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.RolePermissionIMP;
import vn.vnpay.validator.RolePermissionValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class RolePermissionController {
    private static final Logger LOG = LogManager.getLogger(RolePermissionController.class);

    @Autowired
    RolePermissionIMP rolePermissionIMP;

    @RequestMapping(value = "/get_role_permission", method = RequestMethod.GET)
    public List getRolePermission(@RequestParam String roleId) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return rolePermissionIMP.getRolePermissionAll(roleId, Message.LIST_PERMISSION_ID);
    }
    @RequestMapping(value = "/create_and_delete_role_permission", method = RequestMethod.POST)
    public Response createAndDelete(@RequestBody RolePermissions rolePermissions) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create role permission with request: {}", GsonCustom.getGsonBuilder().toJson(rolePermissions));
        Map<String, String> mapError = RolePermissionValidate.getInstance().validateInput(rolePermissions, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(rolePermissionIMP.createAndDeleteRolePermission(rolePermissions));
    }
}
