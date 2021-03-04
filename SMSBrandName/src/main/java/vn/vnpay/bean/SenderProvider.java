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
public class SenderProvider {
    private String sender;
    private String senderNew;
    private String createUser;
    private String providerId;
    private String providerIdNew;
    private String description;
    private String status;
    private String createdBy;
    private String updatedBy;
}
