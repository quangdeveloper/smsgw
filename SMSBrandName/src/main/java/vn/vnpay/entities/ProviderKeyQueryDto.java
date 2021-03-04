package vn.vnpay.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProviderKeyQueryDto {
    private long id;
    private String sender;
    private String providerId;
    private String priKey;
    private String priPKCS8Key;
    private String pubKey;
    private String secretKey;
    private long startTime;
    private long endTime;
    private int status;
    private String description;

    @Override
    public String toString() {
        return "ProviderKeyQueryDto{" + "id=" + id + ", sender=" + sender + ", providerId=" + providerId + ", priKey=" + priKey + ", priPKCS8Key=" + priPKCS8Key + ", pubKey=" + pubKey + ", secretKey=" + secretKey + ", startTime=" + startTime + ", endTime=" + endTime + ", status=" + status + ", description=" + description + '}';
    }

}
