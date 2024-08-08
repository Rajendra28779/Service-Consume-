package com.bskyserviceconsume.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 28/04/2023 - 01:01 PM
 */

@Getter
@Setter
@ToString
@Embeddable
public class TxntransactiondetailId implements Serializable {
    private static final long serialVersionUID = -3773701523595152472L;
    @Column(name = "TRANSACTIONDETAILSID", nullable = false)
    private Long transactiondetailsid;

    @Column(name = "ID", nullable = false)
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TxntransactiondetailId entity = (TxntransactiondetailId) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.transactiondetailsid, entity.transactiondetailsid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactiondetailsid);
    }

}