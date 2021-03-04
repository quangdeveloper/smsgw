package vn.vnpay.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MnpQueryDto {

    private long mnpId;
    private String providerId;
    private String ogiProviderId;
    private long startTime;
    private int status;
    private String destination;
    private String providerName;
}
