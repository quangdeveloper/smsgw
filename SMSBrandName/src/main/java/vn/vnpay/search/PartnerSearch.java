package vn.vnpay.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PartnerSearch {
    private String shortName;
    private Integer status;
    private String fromDate;
    private String toDate;
    private int pageSize;
    private int pageIndex;
}
