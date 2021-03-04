package vn.vnpay.validator;

import vn.vnpay.bean.ProviderChannel;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class ProviderChannelValidate extends Validator {
    private final static int MAX_LENGTH_ID = 32;
    private final static int MAX_LENGTH_STATUS = 32;
    private final static int MAX_LENGTH_NAME = 50;
    private final static int MAX_LENGTH_PROVIDER_ID = 20;
    private final static int MAX_LENGTH_QUEUE = 100;
    private final static int MAX_LENGTH_DESCRIPTION = 255;
    private final static int MAX_LENGTH_CREATED_BY = 45;
    private final static int MAX_LENGTH_UPDATED_BY = 45;

    private static ProviderChannelValidate INSTANCE;

    public static ProviderChannelValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (ProviderChannelValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ProviderChannelValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(ProviderChannel request, Action action) {
        Map<String, String> mapError = new HashMap<>(7);

        String errorId = validateNullOrMaxLengthInt(request.getId(), MAX_LENGTH_ID);
        if (!EMPTY.equals(errorId)) {
            mapError.put("id", errorId);
        }

        String errorName = validateNullOrMaxLengthStr(request.getName(), MAX_LENGTH_NAME);
        if (!EMPTY.equals(errorName)) {
            mapError.put("name", errorName);
        }

        String errorProviderId = validateNullOrMaxLengthStr(request.getProviderId(), MAX_LENGTH_PROVIDER_ID);
        if (!EMPTY.equals(errorProviderId)) {
            mapError.put("providerId", errorProviderId);
        }

        String errorQueue = validateNullOrMaxLengthStr(request.getQueue(), MAX_LENGTH_QUEUE);
        if (!EMPTY.equals(errorQueue)) {
            mapError.put("queue", errorQueue);
        }

        String errorDescription = validateMaxLengthStr(request.getDescription(), MAX_LENGTH_DESCRIPTION);
        if (!EMPTY.equals(errorDescription)) {
            mapError.put("description", errorDescription);
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
