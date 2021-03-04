package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpay.bean.*;
import vn.vnpay.common.GsonCustom;
import vn.vnpay.common.Response;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.Message;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.RolePermissionIMP;
import vn.vnpay.modal.BusinessModal;
import vn.vnpay.modal.UserEntities;
import vn.vnpay.modal.UserModal;
import vn.vnpay.service.JwtService;
import vn.vnpay.service.UserService;
import vn.vnpay.common.LogCommon;
import vn.vnpay.validator.PermissionValidate;
import vn.vnpay.validator.SystemConfigValidate;
import vn.vnpay.validator.UserValidate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class UserController {
    private final static Logger LOG = LogManager.getLogger(UserController.class);

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private RolePermissionIMP rolePermissionIMP;

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody UserRequest request, HttpServletRequest http) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        UserResponse userResponse = UserResponse.builder().build();
        ResponseCustom responseCustom = userService.checkLogin(request);
        if (responseCustom.equals(ResponseCustom.SUCCESS)) {
            userResponse.setToken(jwtService.generateTokenLogin(request.getUsername()));
            userResponse.setUserName(request.getUsername());
            int role = userService.getUserByUsername(request.getUsername()).getRole();
            System.out.println(role);
             userResponse.setPermissions(rolePermissionIMP.getRolePermissionAll(String.valueOf(role), Message.LIST_PERMISSION_NAME));
        }
        userResponse.setCode(responseCustom.getCode());
        userResponse.setDescription(responseCustom.getDescription());
        return ResponseEntity.ok(userResponse);
    }

    @RequestMapping(value = "/create_user", method = RequestMethod.POST)
    public Response createUser(@RequestBody UserRequest userRequest) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create user with request: {}", GsonCustom.getGsonBuilder().toJson(userRequest));
        Map<String, String> mapError = UserValidate.getInstance().validateInput(userRequest, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(userService.createUser(userRequest));
    }

    @RequestMapping(value = "/update_user", method = RequestMethod.POST)
    public Response updateUser(@RequestBody UserRequest userRequest) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update user with request: {}", GsonCustom.getGsonBuilder().toJson(userRequest));
        Map<String, String> mapError = UserValidate.getInstance().validateInput(userRequest, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(userService.updateUser(userRequest));
    }

    @RequestMapping(value = "/update_password", method = RequestMethod.POST)
    public Response updatePassword(@RequestBody UserRequest userRequest) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update password with request: {}", GsonCustom.getGsonBuilder().toJson(userRequest));
        Map<String, String> mapError = UserValidate.getInstance().validateInput(userRequest, Action.UPDATE_PASSWORD);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(userService.updatePassword(userRequest));
    }

    @RequestMapping(value = "/get_all_user", method = RequestMethod.GET)
    public List<UserEntities> getAllUser() {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return userService.getAllUser();
    }
    @RequestMapping(value = "/delete_user", method = RequestMethod.POST)
    public Response delete(@RequestBody UserRequest userRequest) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin delete user with request: {}", GsonCustom.getGsonBuilder().toJson(userRequest));
        Map<String, String> mapError = UserValidate.getInstance().validateInput(userRequest, Action.DELETE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(userService.deleteUser(userRequest));
    }

}
