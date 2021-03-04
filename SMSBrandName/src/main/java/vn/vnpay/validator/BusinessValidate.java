package vn.vnpay.validator;

import vn.vnpay.bean.Business;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class BusinessValidate extends Validator {
    private final static int MAX_LENGTH_ID = 64;
    private final static int MAX_LENGTH_NAME = 100;
    private final static int MAX_LENGTH_CREATED_BY = 45;
    private final static int MAX_LENGTH_UPDATED_BY = 45;
    private final static int MAX_LENGTH_STATUS = 32;

    private static BusinessValidate INSTANCE;

    public static BusinessValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (BusinessValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BusinessValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(Business request, Action action) {
        Map<String, String> mapError = new HashMap<>(4);

        String errorId = validateNullOrMaxLengthInt(request.getId(), MAX_LENGTH_ID);
        if (!EMPTY.equals(errorId)) {
            mapError.put("id", errorId);
        }

        String errorName = validateNullOrMaxLengthStr(request.getName(), MAX_LENGTH_NAME);
        if (!EMPTY.equals(errorName)) {
            mapError.put("name", errorName);
        }

        switch (action) {
            case UPDATE:
                String errorStatus = validateNullOrMaxLengthInt(request.getStatus(), MAX_LENGTH_STATUS);
                if (!EMPTY.equals(errorStatus)) {
                    mapError.put("status", errorStatus);
                }
                String errorUpdatedBy = validateMaxLengthStr(request.getUpdatedBy(), MAX_LENGTH_UPDATED_BY);
                if (!EMPTY.equals(errorUpdatedBy)) {
                    mapError.put("updatedBy", errorUpdatedBy);
                }
                break;
            case INSERT:
                String errorCreatedBy = validateMaxLengthStr(request.getCreatedBy(), MAX_LENGTH_CREATED_BY);
                if (!EMPTY.equals(errorCreatedBy)) {
                    mapError.put("createdBy", errorCreatedBy);
                }
                break;
        }
        return mapError;
    }
}
