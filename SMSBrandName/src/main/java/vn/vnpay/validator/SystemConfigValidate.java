package vn.vnpay.validator;

import vn.vnpay.bean.SystemConfig;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class SystemConfigValidate extends Validator {
    private final static int MAX_LENGTH_APP_ID = 50;
    private final static int MAX_LENGTH_KEY = 50;
    private final static int MAX_LENGTH_VALUE = 4000;

    private static SystemConfigValidate INSTANCE;

    public static SystemConfigValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (SystemConfigValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SystemConfigValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(SystemConfig request, Action action) {
        Map<String, String> mapError = new HashMap<>(4);

        switch (action){
            case UPDATE:{
                String errorAppIdNew = validateNullOrMaxLengthStr(request.getAppIdNew(), MAX_LENGTH_APP_ID);
                if (!EMPTY.equals(errorAppIdNew)) {
                    mapError.put("appIdNew", errorAppIdNew);
                }
            }
            case INSERT:{
                String errorKey = validateNullOrMaxLengthStr(request.getKey(), MAX_LENGTH_KEY);
                if (!EMPTY.equals(errorKey)) {
                    mapError.put("key", errorKey);
                }

                String errorValue = validateNullOrMaxLengthStr(request.getValue(), MAX_LENGTH_VALUE);
                if (!EMPTY.equals(errorValue)) {
                    mapError.put("value", errorValue);
                }
            }
            case DELETE:{
                String errorAppId = validateNullOrMaxLengthStr(request.getAppId(), MAX_LENGTH_APP_ID);
                if (!EMPTY.equals(errorAppId)) {
                    mapError.put("appId", errorAppId);
                }
                break;
            }
        }
        return mapError;
    }
}
