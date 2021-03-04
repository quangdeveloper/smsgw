package vn.vnpay.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sender {

    private String sender;

    private String senderNew;

    private String description;

    private String status;

    private String createdBy;

    private String updatedBy;
}
