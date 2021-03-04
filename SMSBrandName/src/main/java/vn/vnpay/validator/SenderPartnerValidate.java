package vn.vnpay.validator;

import vn.vnpay.bean.SenderPartner;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class SenderPartnerValidate extends Validator {
    private final static int MAX_LENGTH_SENDER = 30;
    private final static int MAX_LENGTH_PARTNER_ID = 20;
    private final static int MAX_LENGTH_CREATED_BY = 45;
    private final static int MAX_LENGTH_UPDATED_BY = 45;
    private final static int MAX_LENGTH_STATUS = 32;

    private static SenderPartnerValidate INSTANCE;

    public static SenderPartnerValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (SenderPartnerValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SenderPartnerValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(SenderPartner request, Action action) {
        Map<String, String> mapError = new HashMap<>(5);

        String errorName = validateNullOrMaxLengthStr(request.getSender(), MAX_LENGTH_SENDER);
        if (!EMPTY.equals(errorName)) {
            mapError.put("sender", errorName);
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
                String errorIdNew = validateNullOrMaxLengthStr(request.getSenderNew(), MAX_LENGTH_SENDER);
                if (!EMPTY.equals(errorIdNew)) {
                    mapError.put("senderNew", errorIdNew);
                }
                String errorPartnerIdNew = validateNullOrMaxLengthStr(request.getPartnerIdNew(), MAX_LENGTH_PARTNER_ID);
                if (!EMPTY.equals(errorPartnerIdNew)) {
                    mapError.put("partnerIdNew", errorPartnerIdNew);
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
