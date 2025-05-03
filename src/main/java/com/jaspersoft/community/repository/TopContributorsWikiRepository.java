package com.jaspersoft.community.repository;

import com.jaspersoft.community.entity.TopContributorsWiki;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopContributorsWikiRepository extends JpaRepository<TopContributorsWiki, Integer> {
}
