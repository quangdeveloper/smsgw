package vn.vnpay.modal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProviderKeyModal {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="sender")
    private String sender;
    @Column(name="provider_id")
    private String providerId;
    @Column(name="provider_name")
    private String providerName;
    @Column(name="pri_key")
    private String priKey;
    @Column(name="priv_pkcs8")
    private String privPkcs8;
    @Column(name="pub_key")
    private String pubKey;
    @Column(name="secret_key")
    private String secretKey;
    @Column(name="start_time")
    private Integer startTime;
    @Column(name="end_time")
    private Integer endTime;
    @Column(name="description")
    private String description;
    @Column(name="status")
    private int status;
    @Column(name="total")
    private int total;
}
