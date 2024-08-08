package com.bskyserviceconsume.repository;

import com.bskyserviceconsume.model.EdsDataLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EdsDataLogRepository extends JpaRepository<EdsDataLog, Long> {
}