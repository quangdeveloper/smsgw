package vn.vnpay.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SystemConfigQueryDto {
	private String appId;
	private String key;	
	private String value;
}
