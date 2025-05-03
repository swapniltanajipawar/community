package com.jaspersoft.community.repository;

import com.jaspersoft.community.entity.Wiki;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WikiRepository extends JpaRepository<Wiki, Integer> {
    List<Wiki> findByMonth(String month);
}
