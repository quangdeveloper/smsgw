package vn.vnpay.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProviderKey {
    private String id;
    private String sender;
    private String providerId;
    private String priKey;
    private String privPkcs8;
    private String pubKey;
    private String secretKey;
    private String status;
    private String description;
}
