package vn.vnpay.validator;

import vn.vnpay.bean.Roles;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class RoleValidate extends Validator {
    private final static int MAX_LENGTH_NAME = 50;
    private final static int MAX_LENGTH_ID = 32;

    private static RoleValidate INSTANCE;

    public static RoleValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (RoleValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RoleValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(Roles request, Action action) {
        Map<String, String> mapError = new HashMap<>(2);
        if (action == Action.INSERT || action == Action.UPDATE) {
            String errorName = validateNullOrMaxLengthStr(request.getName(), MAX_LENGTH_NAME);
            if (!EMPTY.equals(errorName)) {
                mapError.put("name", errorName);
            }
        }
        switch (action) {
            case UPDATE:
            case DELETE:
                String errorId = validateNullOrMaxLengthStr(request.getId(), MAX_LENGTH_ID);
                if (!EMPTY.equals(errorId)) {
                    mapError.put("id", errorId);
                }
        }

        return mapError;
    }
}
