package com.jaspersoft.community.repository;

import com.jaspersoft.community.entity.Qa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QaRepository extends JpaRepository<Qa, Integer> {
    
    // ✅ Custom method to find QA records by month
    List<Qa> findByMonth(Integer month);
}
