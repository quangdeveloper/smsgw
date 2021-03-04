package vn.vnpay.modal;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class UserEntities {

    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "user_name")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "role_id")
    private String roleId;
    @Column(name = "role_name")
    private String roleName;

    @Column(name = "full_name")
    private String fullName;
}
