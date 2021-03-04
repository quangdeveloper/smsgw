package vn.vnpay.validator;

import vn.vnpay.bean.RolePermissions;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class RolePermissionValidate extends Validator{
    private final static int MAX_LENGTH_INT = 32;

    private static RolePermissionValidate INSTANCE;

    public static RolePermissionValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (RolePermissionValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RolePermissionValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(RolePermissions request, Action action) {
        Map<String, String> mapError = new HashMap<>(2);
        if (action == Action.INSERT || action == Action.UPDATE) {
            String errorRoleId = validateNullOrMaxLengthStr(request.getRoleId(), MAX_LENGTH_INT);
            if (!EMPTY.equals(errorRoleId)) {
                mapError.put("roleId", errorRoleId);
            }
            for(int i = 0; i < request.getListPermissionsId().size(); i++){
                String errorPermissionId = validateNullOrMaxLengthStr(request.getListPermissionsId().get(i), MAX_LENGTH_INT);
                if (!EMPTY.equals(errorPermissionId)) {
                    mapError.put("permissionId", errorPermissionId);
                }
            }

        }
        return mapError;
    }
}
