package com.bskyserviceconsume.repository;

import com.bskyserviceconsume.model.APIServiceLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface APIServiceLogRepository extends JpaRepository<APIServiceLog, Integer> {
}