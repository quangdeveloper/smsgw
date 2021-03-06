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
public class ProviderPrefix {
    private String id;
    private String name;
    private String providerId;
    private String queue;
    private String length;
    private String status;
    private String createdBy;
    private String updatedBy;
}
