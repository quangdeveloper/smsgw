package vn.vnpay.validator;

import vn.vnpay.bean.Keyword;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class KeywordValidate extends Validator {
    private final static int MAX_LENGTH_ID = 32;
    private final static int MAX_LENGTH_NAME = 50;
    private final static int MAX_LENGTH_STATUS = 32;
    private final static int MAX_LENGTH_PARTNER_ID = 20;
    private final static int MAX_LENGTH_CREATED_BY = 45;
    private final static int MAX_LENGTH_UPDATED_BY = 45;

    private static KeywordValidate INSTANCE;

    public static KeywordValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (KeywordValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new KeywordValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(Keyword request, Action action) {
        Map<String, String> mapError = new HashMap<>(6);

        String errorName = validateNullOrMaxLengthStr(request.getName(), MAX_LENGTH_NAME);
        if (!EMPTY.equals(errorName)) {
            mapError.put("name", errorName);
        }

        String errorPartnerId = validateNullOrMaxLengthStr(request.getPartnerId(), MAX_LENGTH_PARTNER_ID);
        if (!EMPTY.equals(errorPartnerId)) {
            mapError.put("partnerId", errorPartnerId);
        }

        switch (action) {
            case INSERT:
                String errorCreatedBy = validateMaxLengthStr(request.getCreatedBy(), MAX_LENGTH_CREATED_BY);
                if (!EMPTY.equals(errorCreatedBy)) {
                    mapError.put("createdBy", errorCreatedBy);
                }
                break;
            case UPDATE:
                String errorId = validateNullOrMaxLengthInt(request.getId(), MAX_LENGTH_ID);
                if (!EMPTY.equals(errorId)) {
                    mapError.put("id", errorId);
                }
            case DELETE:
                String errorUpdatedBy = validateMaxLengthStr(request.getUpdatedBy(), MAX_LENGTH_UPDATED_BY);
                if (!EMPTY.equals(errorUpdatedBy)) {
                    mapError.put("updatedBy", errorUpdatedBy);
                }
                String errorStatus = validateNullOrMaxLengthInt(request.getStatus(), MAX_LENGTH_STATUS);
                if (!EMPTY.equals(errorStatus)) {
                    mapError.put("status", errorStatus);
                }
                break;
        }
        return mapError;
    }
}
