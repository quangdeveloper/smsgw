package vn.vnpay.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class ChannelQueryDto {
	@Id
	@Column(name="id")
	private long channelId;
	@Column(name="provider_id")
	private String providerId;
	@Column(name="status")
	private int status;
	@Column(name="route_name")
	private String routeName;
}
