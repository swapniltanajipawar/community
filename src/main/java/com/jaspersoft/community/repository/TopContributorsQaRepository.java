package com.jaspersoft.community.repository;
import com.jaspersoft.community.entity.TopContributorsQa;
import com.jaspersoft.community.entity.TopContributorsWiki;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopContributorsQaRepository extends JpaRepository<TopContributorsQa, Integer> {
	List<TopContributorsQa> findByMonth(String month);
}
