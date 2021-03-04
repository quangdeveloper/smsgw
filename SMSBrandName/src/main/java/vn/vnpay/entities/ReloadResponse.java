package vn.vnpay.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class ReloadResponse {
    private String token;
    private String respCode;
    private String description;
}
