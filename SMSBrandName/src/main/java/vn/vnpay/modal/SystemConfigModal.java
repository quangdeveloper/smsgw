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
public class SystemConfigModal {
    @Id
    @Column(name="app_id")
    private String appId;
    @Column(name="key")
    private String key;
    @Column(name="value")
    private String value;
    @Column(name="total")
    private int total;
}
