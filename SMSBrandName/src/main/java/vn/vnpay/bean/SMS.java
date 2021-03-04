package vn.vnpay.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SMS {
    private String id;
    private String keyword;
    private int msgType;
    private String sender;
    private String destination;
    private String message;
    private String maskMessage;
    private String encryptMessage;
    private long requSeqno;
    private Timestamp requTime;
    private Timestamp sendTime;
    private Timestamp respTime;
    private int numOfChars;
    private int numOfBytes;
    private int cdrChars;
    private int cdrBytes;
    private String status;
    private int port;
    private String rcCode;
    private String rcCodeRef;
    private int tryCount;
    private String providerName;
    private String errMsg;
    private Partner partner;
    private Provider provider;
    private String orgProviderId;
    private ProviderPrefix providerPreFix;
    private int isMnp;
    private ProviderChannel providerChannel;
    private int isEncoding;
    private int isEncrypt;
    private int serverID;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String createdBy;
    private String updatedBy;
}
