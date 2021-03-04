package vn.vnpay.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Routing {
    private String id;
    private String name;
    private String providerId;
    private String partnerId;
    private String providerChannelId;
    private String sender;
    private String keywordId;
    private String queue;
    private String status;
    private Integer applyFrom;
    private Integer applyTo;
    private String createdBy;
    private String updatedBy;
    private String description;
}
