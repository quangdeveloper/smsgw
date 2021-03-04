package vn.vnpay.validator;

import vn.vnpay.bean.UserRequest;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class UserValidate extends Validator {
    private final static int MAX_LENGTH_USER_NAME = 30;
    private final static int MAX_LENGTH_PASS_WORD = 64;
    private final static int MAX_LENGTH_FULL_NAME = 50;
    private final static int MAX_LENGTH_EMAIL = 50;
    private final static int MAX_LENGTH_ROLE_ID = 32;
    private final static int MAX_LENGTH_ID = 32;

    private static UserValidate INSTANCE;

    public static UserValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (UserValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(UserRequest request, Action action) {
        Map<String, String> mapError = new HashMap<>(5);



        switch (action) {
            case INSERT:
                String errorPassword = getInstance().validateNullOrMaxLengthStr(request.getPassword(), MAX_LENGTH_PASS_WORD);
                if (!EMPTY.equals(errorPassword)) {
                    mapError.put("password", errorPassword);
                }
                String errorUserName = getInstance().validateNullOrMaxLengthStr(request.getUsername(), MAX_LENGTH_USER_NAME);
                if (!EMPTY.equals(errorUserName)) {
                    mapError.put("username", errorUserName);
                }
            case UPDATE:
                String errorFullName = getInstance().validateNullOrMaxLengthStr(request.getFullName(), MAX_LENGTH_FULL_NAME);
                if (!EMPTY.equals(errorFullName)) {
                    mapError.put("fullName", errorFullName);
                }
                String errorEmail = getInstance().validateNullOrMaxLengthStr(request.getEmail(), MAX_LENGTH_EMAIL);
                if (!EMPTY.equals(errorEmail)) {
                    mapError.put("email", errorEmail);
                }
                String errorRoleId = getInstance().validateNullOrMaxLengthInt(request.getRoleId(), MAX_LENGTH_ROLE_ID);
                if (!EMPTY.equals(errorRoleId)) {
                    mapError.put("roleId", errorRoleId);
                }
                break;
            case UPDATE_PASSWORD:
                String errorPasswordUpdate = getInstance().validateNullOrMaxLengthStr(request.getPassword(), MAX_LENGTH_PASS_WORD);
                if (!EMPTY.equals(errorPasswordUpdate)) {
                    mapError.put("password", errorPasswordUpdate);
                }
                String errorUserNameUpdate = getInstance().validateNullOrMaxLengthStr(request.getUsername(), MAX_LENGTH_USER_NAME);
                if (!EMPTY.equals(errorUserNameUpdate)) {
                    mapError.put("username", errorUserNameUpdate);
                }
                break;
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
