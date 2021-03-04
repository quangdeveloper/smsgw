package vn.vnpay.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConfigPartnerSearch {
    private String partnerId;
    private String keyword;
    private String apiKey;
    private String sender;
    private Integer status;
    private String fromDate;
    private String toDate;
    private int pageSize;
    private int pageIndex;
}
