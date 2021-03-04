package vn.vnpay.validator;

import vn.vnpay.bean.Sender;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class SenderValidate extends Validator {
    private final static int MAX_LENGTH_SENDER = 30;
    private final static int MAX_LENGTH_SENDER_NEW = 30;
    private final static int MAX_LENGTH_CREATED_BY = 45;
    private final static int MAX_LENGTH_UPDATED_BY = 45;
    private final static int MAX_LENGTH_STATUS = 32;

    private static SenderValidate INSTANCE;

    public static SenderValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (SenderValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SenderValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(Sender request, Action action) {
        Map<String, String> mapError = new HashMap<>(4);

        String errorName = validateNullOrMaxLengthStr(request.getSender(), MAX_LENGTH_SENDER);
        if (!EMPTY.equals(errorName)) {
            mapError.put("sender", errorName);
        }

        switch (action) {
            case INSERT:
                String errorCreatedBy = validateMaxLengthStr(request.getCreatedBy(), MAX_LENGTH_CREATED_BY);
                if (!EMPTY.equals(errorCreatedBy)) {
                    mapError.put("createdBy", errorCreatedBy);
                }
                break;
            case UPDATE:
                String errorIdNew = validateMaxLengthStr(request.getSenderNew(), MAX_LENGTH_SENDER_NEW);
                if (!EMPTY.equals(errorIdNew)) {
                    mapError.put("senderNew", errorIdNew);
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
