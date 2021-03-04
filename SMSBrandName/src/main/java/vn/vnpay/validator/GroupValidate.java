package vn.vnpay.validator;

import vn.vnpay.bean.Group;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class GroupValidate extends Validator {
    private final static int MAX_LENGTH_NAME = 255;
    private final static int MAX_LENGTH_ID = 32;

    private static GroupValidate INSTANCE;

    public static GroupValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (GroupValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GroupValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(Group request, Action action) {
        Map<String, String> mapError = new HashMap<>(2);

        if (action == Action.UPDATE || action == Action.INSERT) {
            String errorName = validateNullOrMaxLengthStr(request.getName(), MAX_LENGTH_NAME);
            if (!EMPTY.equals(errorName)) {
                mapError.put("name", errorName);
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
