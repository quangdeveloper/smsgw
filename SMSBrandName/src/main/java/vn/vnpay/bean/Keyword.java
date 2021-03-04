package vn.vnpay.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Keyword {
    private String id;
    private String name;
    private String status;
    private String partnerId;
    private String createdBy;
    private String updatedBy;
}
