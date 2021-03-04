package vn.vnpay.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RoutingSearch {
    private String name;
    private String providerChannelId;
    private String keywordId;
    private String providerId;
    private String partnerId;
    private String sender;
    private Integer status;
    private String fromDate;
    private String toDate;
    private int pageSize;
    private int pageIndex;
}
