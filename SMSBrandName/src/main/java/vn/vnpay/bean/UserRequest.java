package vn.vnpay.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRequest {
    private String id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String roleId;
}
