package vn.vnpay.entities;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class ReloadRequest {
    
    private String token;
    private String commandCode;
    private String apiuser;
    private String apikey;
}
