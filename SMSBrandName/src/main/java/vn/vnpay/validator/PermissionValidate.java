package vn.vnpay.validator;

import vn.vnpay.bean.Permissions;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class PermissionValidate extends Validator {
    private final static int MAX_LENGTH_NAME = 100;
    private final static int MAX_LENGTH_GROUP_ID = 32;
    private final static int MAX_LENGTH_ID = 32;

    private static PermissionValidate INSTANCE;

    public static PermissionValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (PermissionValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PermissionValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(Permissions request, Action action) {
        Map<String, String> mapError = new HashMap<>(3);

        if (action == Action.UPDATE || action == Action.INSERT) {
            String errorName = validateNullOrMaxLengthStr(request.getName(), MAX_LENGTH_NAME);
            if (!EMPTY.equals(errorName)) {
                mapError.put("name", errorName);
            }

            String errorGroupId = validateNullOrMaxLengthInt(request.getGroupId(), MAX_LENGTH_GROUP_ID);
            if (!EMPTY.equals(errorGroupId)) {
                mapError.put("groupId", errorGroupId);
            }
        }
        switch (action) {
            case UPDATE:
            case DELETE:
                String errorId = validateNullOrMaxLengthInt(request.getId(), MAX_LENGTH_ID);
                if (!EMPTY.equals(errorId)) {
                    mapError.put("id", errorId);
                }
                break;
        }

        return mapError;
    }
}
