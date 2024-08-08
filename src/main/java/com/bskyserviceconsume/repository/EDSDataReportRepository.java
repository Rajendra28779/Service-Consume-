package com.bskyserviceconsume.repository;

import com.bskyserviceconsume.model.EdsDataReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EDSDataReportRepository extends JpaRepository<EdsDataReport, Long> {
}