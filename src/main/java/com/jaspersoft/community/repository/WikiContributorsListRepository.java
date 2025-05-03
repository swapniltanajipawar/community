package com.jaspersoft.community.repository;

import com.jaspersoft.community.entity.WikiContributorsList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WikiContributorsListRepository extends JpaRepository<WikiContributorsList, Long> {
}
