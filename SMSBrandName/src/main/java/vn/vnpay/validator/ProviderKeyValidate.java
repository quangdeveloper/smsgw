package vn.vnpay.validator;

import vn.vnpay.bean.ProviderKey;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class ProviderKeyValidate extends Validator {
    private final static int MAX_LENGTH_SENDER = 30;
    private final static int MAX_LENGTH_ID = 32;
    private final static int MAX_LENGTH_PROVIDER_ID = 20;
    private final static int MAX_LENGTH_PRI_KEY = 4000;
    private final static int MAX_LENGTH_PRIV_PKCS8 = 4000;
    private final static int MAX_LENGTH_PUB_KEY = 4000;
    private final static int MAX_LENGTH_SECRET_KEY = 4000;
    private final static int MAX_LENGTH_DESCRIPTION = 255;
    private final static int MAX_LENGTH_STATUS = 32;

    private static ProviderKeyValidate INSTANCE;

    public static ProviderKeyValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (ProviderKeyValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ProviderKeyValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(ProviderKey request, Action action) {
        Map<String, String> mapError = new HashMap<>(8);

        String errorSender = validateNullOrMaxLengthStr(request.getSender(), MAX_LENGTH_SENDER);
        if (!EMPTY.equals(errorSender)) {
            mapError.put("sender", errorSender);
        }

        String errorProviderId = validateNullOrMaxLengthStr(request.getProviderId(), MAX_LENGTH_PROVIDER_ID);
        if (!EMPTY.equals(errorProviderId)) {
            mapError.put("providerId", errorProviderId);
        }
        String errorPriKey = validateNullOrMaxLengthStr(request.getPriKey(), MAX_LENGTH_PRI_KEY);
        if (!EMPTY.equals(errorPriKey)) {
            mapError.put("priKey", errorPriKey);
        }

        String errorPrivPkcs8 = validateNullOrMaxLengthStr(request.getPrivPkcs8(), MAX_LENGTH_PRIV_PKCS8);
        if (!EMPTY.equals(errorPrivPkcs8)) {
            mapError.put("privPkcs8", errorPrivPkcs8);
        }
        String errorPubKey = validateNullOrMaxLengthStr(request.getPubKey(), MAX_LENGTH_PUB_KEY);
        if (!EMPTY.equals(errorPubKey)) {
            mapError.put("pubKey", errorPubKey);
        }

        String errorSecretKey = validateNullOrMaxLengthStr(request.getSecretKey(), MAX_LENGTH_SECRET_KEY);
        if (!EMPTY.equals(errorSecretKey)) {
            mapError.put("secretKey", errorSecretKey);
        }

        String errorDescription = validateMaxLengthStr(request.getDescription(), MAX_LENGTH_DESCRIPTION);
        if (!EMPTY.equals(errorDescription)) {
            mapError.put("description", errorDescription);
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
