package vn.vnpay.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class ProviderQueryDto {
    @Id
    @Column(name = "id")
    private String providerId;
    @Column(name = "short_name")
    private String providerName;
    @Column(name = "queue")
    private String routeName;
    @Column(name = "status")
    private int status;
    @Column(name = "is_mnp")
    private int isMnp;
    @Column(name = "provider_code_org")
    private String providerCodeOrg;

    @Override
    public String toString() {
        return "ProviderQueryDto{" + "providerId=" + providerId + ", providerName=" + providerName + ", routeName=" + routeName + ", status=" + status + ", isMnp=" + isMnp + ", providerCodeOrg=" + providerCodeOrg + '}';
    }


}
