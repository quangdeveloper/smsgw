package vn.vnpay.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class PrefixQueryDto {
    @Id
    @Column(name="id")
    private long prefixId;
    @Column(name="name")
    private String prefix;
    @Column(name="provider_id")
    private String providerId;
    @Column(name="provider_name")
    private String providerName;
    @Column(name="queue")
    private String routeName;
    @Column(name="length")
    private int length;
    @Column(name="status")
    private int status;

    @Override
    public String toString() {
        return "PrefixQueryDto{" + "prefixId=" + prefixId + ", prefix=" + prefix + ", providerId=" + providerId + ", providerName=" + providerName + ", routeName=" + routeName + ", length=" + length + ", status=" + status + '}';
    }


}
