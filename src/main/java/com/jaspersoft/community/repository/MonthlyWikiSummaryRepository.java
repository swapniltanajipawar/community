package com.jaspersoft.community.repository;
import com.jaspersoft.community.entity.MonthlyWikiSummary;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyWikiSummaryRepository extends JpaRepository<MonthlyWikiSummary, Integer> {
	List<MonthlyWikiSummary> findByMonth(String month);

}
