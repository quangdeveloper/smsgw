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
public class RoutingModal {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="provider_channel_id")
    private Integer providerChannelId;
    @Column(name="channel_name")
    private String channelName;
    @Column(name="keyword_id")
    private Integer keywordId;
    @Column(name="keyword_name")
    private String keywordName;
    @Column(name="provider_id")
    private String providerId;
    @Column(name="provider_name")
    private String providerName;
    @Column(name="partner_id")
    private String partnerId;
    @Column(name="partner_name")
    private String partnerName;
    @Column(name="sender")
    private String sender;
    @Column(name="queue")
    private String queue;
    @Column(name="apply_from")
    private Integer applyFrom;
    @Column(name="apply_to")
    private Integer applyTo;
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
    @Column(name="description")
    private String description;
    @Column(name="total")
    private int total;
}
