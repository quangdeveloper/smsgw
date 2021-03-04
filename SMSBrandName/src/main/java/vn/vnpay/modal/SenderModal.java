package vn.vnpay.modal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SenderModal {
    @Id
    @Column(name="sender")
    private String sender;
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
