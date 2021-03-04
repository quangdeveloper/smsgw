package vn.vnpay.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class RoutingQueryDto {
	@Id
	@Column(name="id")
    private long routingId;
	@Column(name="name")
	private String routingName;
	@Column(name="provider_id")
    private String providerId;
	@Column(name="channel_id")
    private int channelId;
	@Column(name="partner_id")
    private String partnerId;
	@Column(name="start_time")
    private long startTime;
	@Column(name="sender")
	private String sender;
	@Column(name="queue")
	private String routeName;
	@Column(name="status")
    private int status;
	@Column(name="channel_name")
    private String channelName;
	@Column(name="keyword_name")
    private String keyword;

    @Override
    public String toString() {
        return "RoutingQueryDto{" + "routingId=" + routingId + ", routingName=" + routingName + ", providerId=" + providerId + ", channelId=" + channelId + ", partnerId=" + partnerId + ", startTime=" + startTime + ", sender=" + sender + ", routeName=" + routeName + ", status=" + status + ", channelName=" + channelName + ", keyword=" + keyword + '}';
    }

}
