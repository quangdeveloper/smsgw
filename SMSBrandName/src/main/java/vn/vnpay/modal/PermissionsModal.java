package vn.vnpay.modal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionsModal {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="group_id")
    private String groupId;
    @Column(name="permissions_name")
    private String name;
    @Column(name="group_name")
    private String groupName;
    @Column(name="total")
    private int total;
}
