package vn.vnpay.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class PartnerQueryDto {
	@Id
	@Column(name="id")
	private String partnerId;
	@Column(name="short_name")
	private String partnerName;
	@Column(name="status")
	private int status;
}
