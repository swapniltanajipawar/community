package com.jaspersoft.community.repository;

import com.jaspersoft.community.entity.MonthlyQaSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyQaSummaryRepository extends JpaRepository<MonthlyQaSummary, Integer> {
}
