package vn.vnpay.validator;

import vn.vnpay.bean.Provider;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class ProviderValidate extends Validator {
    private final static int MAX_LENGTH_ID = 20;
    private final static int MAX_LENGTH_SHORT_NAME = 100;
    private final static int MAX_LENGTH_DESCRIPTION = 255;
    private final static int MAX_LENGTH_PREFIX = 255;
    private final static int MAX_LENGTH_QUEUE = 255;
    private final static int MAX_LENGTH_CREATED_BY = 45;
    private final static int MAX_LENGTH_UPDATED_BY = 45;
    private final static int MAX_LENGTH_STATUS = 32;

    private static ProviderValidate INSTANCE;

    public static ProviderValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (ProviderValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ProviderValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(Provider request, Action action) {
        Map<String, String> mapError = new HashMap<>(8);

        String errorId = validateNullOrMaxLengthStr(request.getId(), MAX_LENGTH_ID);
        if (!EMPTY.equals(errorId)) {
            mapError.put("id", errorId);
        }

        String errorName = validateNullOrMaxLengthStr(request.getShortName(), MAX_LENGTH_SHORT_NAME);
        if (!EMPTY.equals(errorName)) {
            mapError.put("shortName", errorName);
        }

        String errorDescription = validateMaxLengthStr(request.getDescription(), MAX_LENGTH_DESCRIPTION);
        if (!EMPTY.equals(errorDescription)) {
            mapError.put("description", errorDescription);
        }

        String errorPrefix = validateMaxLengthStr(request.getPrefix(), MAX_LENGTH_PREFIX);
        if (!EMPTY.equals(errorPrefix)) {
            mapError.put("prefix", errorPrefix);
        }

        String errorQueue = validateMaxLengthStr(request.getQueue(), MAX_LENGTH_QUEUE);
        if (!EMPTY.equals(errorQueue)) {
            mapError.put("queue", errorQueue);
        }

        String errorLogo = checkRequired(request.getLogo());
        if (!EMPTY.equals(errorLogo)) {
            mapError.put("logo", errorLogo);
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
