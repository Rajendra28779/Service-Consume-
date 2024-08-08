package com.bskyserviceconsume.repository;

import com.bskyserviceconsume.model.TblBskyStatisticsReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface TblBskyStatisticsReportRepository extends JpaRepository<TblBskyStatisticsReport, Long> {

    @Query("SELECT TSR FROM TblBskyStatisticsReport TSR WHERE FUNCTION('TRUNC', TSR.createdOn) = FUNCTION('TRUNC', :createdDate)")
    TblBskyStatisticsReport getTblBskyStatisticsReportByCreatedOn(@Param("createdDate") Date createdDate);

}