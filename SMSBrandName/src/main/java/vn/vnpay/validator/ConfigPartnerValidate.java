package vn.vnpay.validator;

import vn.vnpay.bean.PartnerConfig;
import vn.vnpay.constant.Action;

import java.util.HashMap;
import java.util.Map;

public class ConfigPartnerValidate extends Validator {
    private final static int MAX_LENGTH_PARTNER_ID = 20;
    private final static int MAX_LENGTH_API_KEY = 100;
    private final static int MAX_LENGTH_API_URL = 200;
    private final static int MAX_LENGTH_KEYWORD_LIST = 100;
    private final static int MAX_LENGTH_SENDER = 255;
    private final static int MAX_LENGTH_IP_LIST = 250;
    private final static int MAX_LENGTH_IS_SEND_SMS = 32;
    private final static int MAX_LENGTH_IS_CHECK_MOBILE = 32;
    private final static int MAX_LENGTH_IS_ENCRYPT_MESSAGE = 32;
    private final static int MAX_LENGTH_IS_CHECK_SENDER = 32;
    private final static int MAX_LENGTH_IS_CHECK_TEMPLATE = 32;
    private final static int MAX_LENGTH_IS_CHECK_DUPLICATE = 32;
    private final static int MAX_LENGTH_DUPLICATE_TYPE = 32;
    private final static int MAX_LENGTH_DUPLICATE_TIME = 32;
    private final static int MAX_LENGTH_WHITE_LIST_MOBILE = 250;
    private final static int MAX_LENGTH_BLACK_LIST_MOBILE = 600;
    private final static int MAX_LENGTH_CREATED_BY = 45;
    private final static int MAX_LENGTH_UPDATED_BY = 45;
    private final static int MAX_LENGTH_STATUS = 32;

    private static ConfigPartnerValidate INSTANCE;

    public static ConfigPartnerValidate getInstance() {
        if (INSTANCE == null) {
            synchronized (ConfigPartnerValidate.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ConfigPartnerValidate();
                }
            }
        }
        return INSTANCE;
    }

    public Map<String, String> validateInput(PartnerConfig request, Action action) {
        Map<String, String> mapError = new HashMap<>(21);

        String errorPartnerId = validateNullOrMaxLengthStr(request.getPartnerId(), MAX_LENGTH_PARTNER_ID);
        if (!EMPTY.equals(errorPartnerId)) {
            mapError.put("partnerId", errorPartnerId);
        }

        String errorApiKey = validateNullOrMaxLengthStr(request.getApiKey(), MAX_LENGTH_API_KEY);
        if (!EMPTY.equals(errorApiKey)) {
            mapError.put("apiKey", errorApiKey);
        }

        String errorApiUrl = validateMaxLengthStr(request.getApiUrl(), MAX_LENGTH_API_URL);
        if (!EMPTY.equals(errorApiUrl)) {
            mapError.put("apiUrl", errorApiUrl);
        }

        String errorKeywordList = validateNullOrMaxLengthStr(request.getKeywordList(), MAX_LENGTH_KEYWORD_LIST);
        if (!EMPTY.equals(errorKeywordList)) {
            mapError.put("keywordList", errorKeywordList);
        }

        String errorSender = validateNullOrMaxLengthStr(request.getSender(), MAX_LENGTH_SENDER);
        if (!EMPTY.equals(errorSender)) {
            mapError.put("sender", errorSender);
        }

        String errorIpList = validateMaxLengthStr(request.getIpList(), MAX_LENGTH_IP_LIST);
        if (!EMPTY.equals(errorIpList)) {
            mapError.put("ipList", errorIpList);
        }

        String errorIsSendSMS = validateNullOrMaxLengthInt(request.getIsSendSMS(), MAX_LENGTH_IS_SEND_SMS);
        if (!EMPTY.equals(errorIsSendSMS)) {
            mapError.put("IsSendSMS", errorIsSendSMS);
        }

        String errorIsCheckMobile = validateNullOrMaxLengthInt(request.getIsCheckMobile(), MAX_LENGTH_IS_CHECK_MOBILE);
        if (!EMPTY.equals(errorIsCheckMobile)) {
            mapError.put("IsCheckMobile", errorIsCheckMobile);
        }

        String errorIsEncryptMessage = validateNullOrMaxLengthInt(request.getIsEncryptMessage(), MAX_LENGTH_IS_ENCRYPT_MESSAGE);
        if (!EMPTY.equals(errorIsEncryptMessage)) {
            mapError.put("IsEncryptMessage", errorIsEncryptMessage);
        }

        String errorIsCheckSender = validateNullOrMaxLengthInt(request.getIsCheckSender(), MAX_LENGTH_IS_CHECK_SENDER);
        if (!EMPTY.equals(errorIsCheckSender)) {
            mapError.put("IsCheckSender", errorIsCheckSender);
        }


        String errorIsCheckTemplate = validateNullOrMaxLengthInt(request.getIsCheckTemplate(), MAX_LENGTH_IS_CHECK_TEMPLATE);
        if (!EMPTY.equals(errorIsCheckTemplate)) {
            mapError.put("IsCheckTemplate", errorIsCheckTemplate);
        }

        String errorIsCheckDuplicate = validateNullOrMaxLengthInt(request.getIsCheckDuplicate(), MAX_LENGTH_IS_CHECK_DUPLICATE);
        if (!EMPTY.equals(errorIsCheckDuplicate)) {
            mapError.put("IsCheckDuplicate", errorIsCheckDuplicate);
        }

        String errorDuplicateType = validateNullOrMaxLengthInt(request.getDuplicateType(), MAX_LENGTH_DUPLICATE_TYPE);
        if (!EMPTY.equals(errorDuplicateType)) {
            mapError.put("DuplicateType", errorDuplicateType);
        }

        String errorDuplicateTime = validateNullOrMaxLengthInt(request.getDuplicateTime(), MAX_LENGTH_DUPLICATE_TIME);
        if (!EMPTY.equals(errorDuplicateTime)) {
            mapError.put("DuplicateTime", errorDuplicateTime);
        }

        String errorWhiteListMobile = validateMaxLengthStr(request.getWhiteListMobile(), MAX_LENGTH_WHITE_LIST_MOBILE);
        if (!EMPTY.equals(errorWhiteListMobile)) {
            mapError.put("WhiteListMobile", errorWhiteListMobile);
        }

        String errorBlackListMobile = validateMaxLengthStr(request.getBlackListMobile(), MAX_LENGTH_BLACK_LIST_MOBILE);
        if (!EMPTY.equals(errorBlackListMobile)) {
            mapError.put("BlackListMobile", errorBlackListMobile);
        }

        String errorFromDate = validateTimestamp(request.getDFromDate());
        if (!EMPTY.equals(errorFromDate)) {
            mapError.put("dFromDate", errorFromDate);
        }

        String errorToDate = validateTimestamp(request.getDToDate());
        if (!EMPTY.equals(errorToDate)) {
            mapError.put("dToDate", errorToDate);
        }

        switch (action){
            case INSERT:{
                String errorCreatedBy = validateMaxLengthStr(request.getCreatedBy(), MAX_LENGTH_CREATED_BY);
                if (!EMPTY.equals(errorCreatedBy)) {
                    mapError.put("createdBy", errorCreatedBy);
                }
                break;
            }
            case UPDATE:{
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
        }
        return mapError;
    }
}
