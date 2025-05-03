package com.jaspersoft.community.repository;

import com.jaspersoft.community.entity.QaContributorsList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QaContributorsListRepository extends JpaRepository<QaContributorsList, Long> {
}
