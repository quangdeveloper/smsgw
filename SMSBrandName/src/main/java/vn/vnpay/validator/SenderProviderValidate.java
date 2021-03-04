package vn.vnpay.validator;

import vn.vnpay.bean.SenderProvider;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class SenderProviderValidate extends Validator {
    private final static int MAX_LENGTH_SENDER = 30;
    private final static int MAX_LENGTH_PROVIDER_ID = 20;
    private final static int MAX_LENGTH_CREATE_USER = 255;
    private final static int MAX_LENGTH_CREATED_BY = 45;
    private final static int MAX_LENGTH_UPDATED_BY = 45;
    private final static int MAX_LENGTH_STATUS = 32;

    private static SenderProviderValidate INSTANCE;

    public static SenderProviderValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (SenderProviderValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SenderProviderValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(SenderProvider request, Action action) {
        Map<String, String> mapError = new HashMap<>(7);

        String errorName = validateNullOrMaxLengthStr(request.getSender(), MAX_LENGTH_SENDER);
        if (!EMPTY.equals(errorName)) {
            mapError.put("sender", errorName);
        }

        String errorProviderId = validateNullOrMaxLengthStr(request.getProviderId(), MAX_LENGTH_PROVIDER_ID);
        if (!EMPTY.equals(errorProviderId)) {
            mapError.put("providerId", errorProviderId);
        }

        String errorPartnerId = validateNullOrMaxLengthStr(request.getCreateUser(), MAX_LENGTH_CREATE_USER);
        if (!EMPTY.equals(errorPartnerId)) {
            mapError.put("createUser", errorPartnerId);
        }

        switch (action) {
            case INSERT:
                String errorCreatedBy = validateMaxLengthStr(request.getCreatedBy(), MAX_LENGTH_CREATED_BY);
                if (!EMPTY.equals(errorCreatedBy)) {
                    mapError.put("createdBy", errorCreatedBy);
                }
                break;
            case UPDATE:
                String errorIdNew = validateNullOrMaxLengthStr(request.getSenderNew(), MAX_LENGTH_SENDER);
                if (!EMPTY.equals(errorIdNew)) {
                    mapError.put("senderNew", errorIdNew);
                }
                String errorPartnerIdNew = validateNullOrMaxLengthStr(request.getProviderIdNew(), MAX_LENGTH_PROVIDER_ID);
                if (!EMPTY.equals(errorPartnerIdNew)) {
                    mapError.put("providerIdNew", errorPartnerIdNew);
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
