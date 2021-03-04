package vn.vnpay.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlackListMobile {
    private long id;
    private String destination;
    private Partner partner;
    private String description;
    private Timestamp applyFrom;
    private Timestamp applyTo;
    private int status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String createdBy;
    private String updatedBy;
}
