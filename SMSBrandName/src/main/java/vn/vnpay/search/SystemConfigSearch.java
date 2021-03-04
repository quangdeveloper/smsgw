package vn.vnpay.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SystemConfigSearch {
    private String appId;
    private String key;
    private int pageSize;
    private int pageIndex;
}
