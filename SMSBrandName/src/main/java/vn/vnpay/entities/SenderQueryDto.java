package vn.vnpay.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class SenderQueryDto {
    @Id
    @Column(name="sender")
    private String sender;
    @Column(name="status")
    private int status;
    @Column(name="description")
    private String description;
    @Column(name="start_time")
    private long startTime;

    @Override
    public String toString() {
        return "SenderQueryDto{" + "sender=" + sender + ", status=" + status + ", description=" + description + ", startTime=" + startTime + '}';
    }


}
