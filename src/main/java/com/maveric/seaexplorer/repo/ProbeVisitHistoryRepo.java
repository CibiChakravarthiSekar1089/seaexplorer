package com.maveric.seaexplorer.repo;

import com.maveric.seaexplorer.dto.ProbeVisitHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProbeVisitHistoryRepo extends JpaRepository<ProbeVisitHistory, Long> {

}
