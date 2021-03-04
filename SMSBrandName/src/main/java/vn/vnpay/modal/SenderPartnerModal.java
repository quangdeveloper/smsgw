package vn.vnpay.modal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SenderPartnerModal {
    @Id
    @Column(name="id")
    private Integer id;
    @Column(name="sender")
    private String sender;
    @Column(name="partner_id")
    private String partnerId;
    @Column(name="partner_name")
    private String partnerName;
    @Column(name="description")
    private String description;
    @Column(name="status")
    private int status;
    @Column(name="created_at")
    private Timestamp createdAt;
    @Column(name="updated_at")
    private Timestamp updatedAt;
    @Column(name="created_by")
    private String createdBy;
    @Column(name="updated_by")
    private String updatedBy;
    @Column(name="total")
    private int total;
}
