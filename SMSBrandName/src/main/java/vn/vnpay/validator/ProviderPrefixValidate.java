package vn.vnpay.validator;

import vn.vnpay.bean.ProviderPrefix;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class ProviderPrefixValidate extends Validator {
    private final static int MAX_LENGTH_ID = 20;
    private final static int MAX_LENGTH_NAME = 20;
    private final static int MAX_LENGTH_QUEUE = 255;
    private final static int MAX_LENGTH_LENGTH = 32;
    private final static int MAX_LENGTH_PROVIDER_ID = 20;
    private final static int MAX_LENGTH_CREATED_BY = 45;
    private final static int MAX_LENGTH_UPDATED_BY = 45;
    private final static int MAX_LENGTH_STATUS = 32;

    private static ProviderPrefixValidate INSTANCE;

    public static ProviderPrefixValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (ProviderPrefixValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ProviderPrefixValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(ProviderPrefix request, Action action) {
        Map<String, String> mapError = new HashMap<>(8);

        String errorId = validateNullOrMaxLengthInt(request.getId(), MAX_LENGTH_ID);
        if (!EMPTY.equals(errorId)) {
            mapError.put("id", errorId);
        }

        String errorName = validateNullOrMaxLengthStr(request.getName(), MAX_LENGTH_NAME);
        if (!EMPTY.equals(errorName)) {
            mapError.put("name", errorName);
        }
        String errorQueue = validateNullOrMaxLengthStr(request.getQueue(), MAX_LENGTH_QUEUE);
        if (!EMPTY.equals(errorQueue)) {
            mapError.put("queue", errorQueue);
        }

        String errorLength = validateNullOrMaxLengthInt(request.getLength(), MAX_LENGTH_LENGTH);
        if (!EMPTY.equals(errorLength)) {
            mapError.put("length", errorLength);
        }
        String errorProviderId = validateNullOrMaxLengthStr(request.getProviderId(), MAX_LENGTH_PROVIDER_ID);
        if (!EMPTY.equals(errorProviderId)) {
            mapError.put("providerId", errorProviderId);
        }

        switch (action) {
            case INSERT:
                String errorCreatedBy = validateMaxLengthStr(request.getCreatedBy(), MAX_LENGTH_CREATED_BY);
                if (!EMPTY.equals(errorCreatedBy)) {
                    mapError.put("createdBy", errorCreatedBy);
                }
                break;
            case UPDATE:
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
