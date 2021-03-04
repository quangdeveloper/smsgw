package vn.vnpay.validator;

import vn.vnpay.bean.Partner;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class PartnerValidate extends Validator {
    private final static int MAX_LENGTH_ID = 20;
    private final static int MAX_LENGTH_STATUS = 32;
    private final static int MAX_LENGTH_NAME = 255;
    private final static int MAX_LENGTH_DESCRIPTION = 255;
    private final static int MAX_LENGTH_CREATED_BY = 45;
    private final static int MAX_LENGTH_UPDATED_BY = 45;

    private static PartnerValidate INSTANCE;

    public static PartnerValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (PartnerValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PartnerValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(Partner request, Action action) {
        Map<String, String> mapError = new HashMap<>(6);

        String errorId = validateNullOrMaxLengthStr(request.getId(), MAX_LENGTH_ID);
        if (!EMPTY.equals(errorId)) {
            mapError.put("id", errorId);
        }

        String errorShortName = validateNullOrMaxLengthStr(request.getShortName(), MAX_LENGTH_NAME);
        if (!EMPTY.equals(errorShortName)) {
            mapError.put("shortName", errorShortName);
        }

        String errorDescription = validateMaxLengthStr(request.getDescription(), MAX_LENGTH_DESCRIPTION);
        if (!EMPTY.equals(errorDescription)) {
            mapError.put("description", errorDescription);
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
