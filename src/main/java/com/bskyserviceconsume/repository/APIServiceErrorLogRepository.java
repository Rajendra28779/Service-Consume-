package com.bskyserviceconsume.repository;


import com.bskyserviceconsume.model.APIServiceErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface APIServiceErrorLogRepository extends JpaRepository<APIServiceErrorLog, Long> {
}