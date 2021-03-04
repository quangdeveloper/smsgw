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
public class ProviderModal {
    @Id
    @Column(name="id")
    private String id;
    @Column(name="short_name")
    private String shortName;
    @Column(name="full_name")
    private String fullName;
    @Column(name="description")
    private String description;
    @Column(name="prefix")
    private String prefix;
    @Column(name="logo")
    private String logo;
    @Column(name="queue")
    private String queue;
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
