package vn.vnpay.validator;

import vn.vnpay.bean.Routing;
import vn.vnpay.constant.Action;
import java.util.HashMap;
import java.util.Map;

public class RoutingValidate extends Validator {
    private final static int MAX_LENGTH_PROVIDER_ID = 20;
    private final static int MAX_LENGTH_PARTNER_ID = 20;
    private final static int MAX_LENGTH_PROVIDER_CHANNEL_ID = 32;
    private final static int MAX_LENGTH_SENDER = 30;
    private final static int MAX_LENGTH_KEYWORD_ID= 32;
    private final static int MAX_LENGTH_QUEUE = 100;
    private final static int MAX_LENGTH_DESCRIPTION = 255;
    private final static int MAX_LENGTH_CREATED_BY = 45;
    private final static int MAX_LENGTH_UPDATED_BY = 45;
    private final static int MAX_LENGTH_ID = 64;
    private final static int MAX_LENGTH_STATUS = 32;

    private static RoutingValidate INSTANCE;

    public static RoutingValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (RoutingValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RoutingValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(Routing request, Action action) {
        Map<String, String> mapError = new HashMap<>(10);

        String errorProviderId = validateNullOrMaxLengthStr(request.getProviderId(), MAX_LENGTH_PROVIDER_ID);
        if (!EMPTY.equals(errorProviderId)) {
            mapError.put("providerId", errorProviderId);
        }

        String errorPartnerId = validateNullOrMaxLengthStr(request.getPartnerId(), MAX_LENGTH_PARTNER_ID);
        if (!EMPTY.equals(errorPartnerId)) {
            mapError.put("partnerId", errorPartnerId);
        }

        String errorProviderChannelId = validateNullOrMaxLengthInt(request.getProviderChannelId(), MAX_LENGTH_PROVIDER_CHANNEL_ID);
        if (!EMPTY.equals(errorProviderChannelId)) {
            mapError.put("providerChannelId", errorProviderChannelId);
        }

        String errorSender = validateNullOrMaxLengthStr(request.getSender(), MAX_LENGTH_SENDER);
        if (!EMPTY.equals(errorSender)) {
            mapError.put("sender", errorSender);
        }

        String errorKeyword = validateNullOrMaxLengthInt(request.getKeywordId(), MAX_LENGTH_KEYWORD_ID);
        if (!EMPTY.equals(errorKeyword)) {
            mapError.put("keywordId", errorKeyword);
        }

        String errorQueue = validateNullOrMaxLengthStr(request.getQueue(), MAX_LENGTH_QUEUE);
        if (!EMPTY.equals(errorQueue)) {
            mapError.put("routeName", errorQueue);
        }

        String errorDescription = validateMaxLengthStr(request.getDescription(), MAX_LENGTH_DESCRIPTION);
        if (!EMPTY.equals(errorDescription)) {
            mapError.put("description", errorDescription);
        }

        String errorCreatedBy = validateMaxLengthStr(request.getCreatedBy(), MAX_LENGTH_CREATED_BY);
        if (!EMPTY.equals(errorCreatedBy)) {
            mapError.put("createdBy", errorCreatedBy);
        }

        String errorUpdatedBy = validateMaxLengthStr(request.getUpdatedBy(), MAX_LENGTH_UPDATED_BY);
        if (!EMPTY.equals(errorUpdatedBy)) {
            mapError.put("updatedBy", errorUpdatedBy);
        }

        switch (action){
            case UPDATE:{
                String errorId = validateNullOrMaxLengthInt(request.getId(), MAX_LENGTH_ID);
                if (!EMPTY.equals(errorId)) {
                    mapError.put("id", errorId);
                }
                String errorStatus = validateNullOrMaxLengthInt(request.getStatus(), MAX_LENGTH_STATUS);
                if (!EMPTY.equals(errorStatus)) {
                    mapError.put("status", errorStatus);
                }
                break;
            }
        }

        return mapError;
    }
}
