package vn.vnpay.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProviderKeySearch {
    private String sender;
    private String providerId;
    private Integer status;
    private int pageSize;
    private int pageIndex;
}
