package vn.vnpay.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class PartnerConfigQueryDto {
    @Id
    @Column(name="partner_id")
    private String partnerId;
    @Column(name="api_user")
    private String apiUser;
    @Column(name="api_key")
    private String apiKey;
    @Column(name="api_url")
    private String apiUrl;
    @Column(name="keyword_list")
    private String keywordList;
    @Column(name="sender_list")
    private String senderList;
    @Column(name="ip_list")
    private String ipList;
    @Column(name="is_check_ip")
    private boolean isCheckIp;
    @Column(name="is_send_sms")
    private boolean isSendSms;
    @Column(name="is_check_mobile")
    private boolean isCheckMobile;
    @Column(name="white_list_mobile")
    private String whiteListMobile;
    @Column(name="black_list_mobile")
    private String blackListMobile;
    @Column(name="status")
    private int status;
    @Column(name="from_date")
    private long fromdate;
    @Column(name="to_date")
    private long todate;
    @Column(name="is_encrypt_message")
    private boolean isEncrypt;
    @Column(name="is_check_sender")
    private boolean isCheckSender;
    @Column(name="is_check_template")
    private boolean isCheckTemp;
    @Column(name="is_check_duplicate")
    private boolean isCheckDuplicate;
    @Column(name="duplicate_type")
    private int duplicateType;
    @Column(name="duplicate_time")
    private int duplicateTime;

    @Override
    public String toString() {
        return "PartnerConfigQueryDto{" + "partnerId=" + partnerId + ", apiUser=" + apiUser + ", apiKey=" + apiKey + ", apiUrl=" + apiUrl + ", keywordList=" + keywordList + ", senderList=" + senderList + ", ipList=" + ipList + ", isCheckIp=" + isCheckIp + ", isSendSms=" + isSendSms + ", isCheckMobile=" + isCheckMobile + ", whiteListMobile=" + whiteListMobile + ", blackListMobile=" + blackListMobile + ", status=" + status + ", fromdate=" + fromdate + ", todate=" + todate + ", isEncrypt=" + isEncrypt + ", isCheckSender=" + isCheckSender + ", isCheckTemp=" + isCheckTemp + ", isCheckDuplicate=" + isCheckDuplicate + ", duplicateType=" + duplicateType + ", duplicateTime=" + duplicateTime + '}';
    }
    
}
