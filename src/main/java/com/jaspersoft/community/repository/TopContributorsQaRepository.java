package com.jaspersoft.community.repository;

import com.jaspersoft.community.entity.TopContributorsQa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopContributorsQaRepository extends JpaRepository<TopContributorsQa, Integer> {
}
