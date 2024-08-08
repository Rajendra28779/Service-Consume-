package com.bskyserviceconsume.repository;

import com.bskyserviceconsume.model.Txntransactiondetail;
import com.bskyserviceconsume.model.TxntransactiondetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxntransactiondetailRepository extends JpaRepository<Txntransactiondetail, TxntransactiondetailId> {
}