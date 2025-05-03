package com.jaspersoft.community.repository;
import com.jaspersoft.community.entity.MonthlyQaSummary;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyQaSummaryRepository extends JpaRepository<MonthlyQaSummary, Integer> {
	
	 List<MonthlyQaSummary> findByMonth(String month);
}
