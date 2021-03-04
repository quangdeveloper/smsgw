package vn.vnpay.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class ProviderChannelSearch {
    private Integer id;
    private String name;
    private String providerId;
    private String queue;
    private Integer status;
    private String fromDate;
    private String toDate;
    private int pageSize;
    private int pageIndex;

}
