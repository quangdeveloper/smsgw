package vn.vnpay.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BusinessSearch {
    private Integer id;
    private String name;
    private Integer status;
    private String fromDate;
    private String toDate;
    private int pageSize;
    private int pageIndex;
}
