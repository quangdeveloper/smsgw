package vn.vnpay.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class KeywordQueryDto {
	@Id
	@Column(name="id")
	private long keywordId;
	@Column(name="name")
	private String keyword;
	@Column(name="status")
	private int status;
	@Column(name="partner_id")
	private String partnerId;
	@Column(name="start_time")
	private long startTime;
}
