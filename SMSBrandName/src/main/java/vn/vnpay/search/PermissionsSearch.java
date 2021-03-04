package vn.vnpay.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PermissionsSearch {
    private Integer groupId;
    private int pageSize;
    private int pageIndex;
}
