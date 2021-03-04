package vn.vnpay.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerConfig {
    private String partnerIdOld;
    private String partnerId;
    private String apiUser;
    private String apiKey;
    private String apiUrl;
    private String keywordList;
    private String ipList;
    private String isCheckIp;
    private String isSendSMS;
    private String isCheckMobile;
    private String whiteListMobile;
    private String blackListMobile;
    private String status;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
    private String sender;
    private String isEncryptMessage;
    private String isCheckSender;
    private String isCheckTemplate;
    private String isCheckDuplicate;
    private String duplicateType;
    private String duplicateTime;
    private String dFromDate;
    private String dToDate;
    private String nFromDate;
    private String nToDate;
    private String isCheckUnicode;


}
