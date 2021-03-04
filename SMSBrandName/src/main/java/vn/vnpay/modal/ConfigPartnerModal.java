package vn.vnpay.modal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigPartnerModal {
    @Id
    @Column(name="partner_id")
    private String partnerId;
    @Column(name="partner_name")
    private String partnerName;
    @Column(name="api_user")
    private String apiUser;
    @Column(name="api_key")
    private String apiKey;
    @Column(name="api_url")
    private String apiUrl;
    @Column(name="keyword_list")
    private String keywordList;
    @Column(name="ip_list")
    private String ipList;
    @Column(name="is_check_ip")
    private Integer isCheckIp;
    @Column(name="is_send_sms")
    private Integer isSendSMS;
    @Column(name="is_check_mobile")
    private Integer isCheckMobile;
    @Column(name="is_encrypt_message")
    private Integer isEncryptMessage;
    @Column(name="is_check_sender")
    private Integer isCheckSender;
    @Column(name="is_check_template")
    private Integer isCheckTemplate;
    @Column(name="is_check_duplicate")
    private Integer isCheckDuplicate;
    @Column(name="duplicate_type")
    private Integer duplicateType;
    @Column(name="duplicate_time")
    private Integer duplicateTime;
    @Column(name="d_from_date")
    private Timestamp dFromDate;
    @Column(name="d_to_date")
    private Timestamp dToDate;
    @Column(name="n_from_date")
    private Long nFromDate;
    @Column(name="n_to_date")
    private Long nToDate;
    @Column(name="is_check_unicode")
    private Integer isCheckUnicode;
    @Column(name="white_list_mobile")
    private String whiteListMobile;
    @Column(name="black_list_mobile")
    private String blackListMobile;
    @Column(name="sender")
    private String sender;
    @Column(name="status")
    private int status;
    @Column(name="created_at")
    private Timestamp createdAt;
    @Column(name="updated_at")
    private Timestamp updatedAt;
    @Column(name="created_by")
    private String createdBy;
    @Column(name="updated_by")
    private String updatedBy;
    @Column(name="total")
    private int total;
}
