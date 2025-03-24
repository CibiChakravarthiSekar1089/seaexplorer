package com.maveric.seaexplorer.repo;

import com.maveric.seaexplorer.dto.SeaObstacle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeaObstacleRepo extends JpaRepository<SeaObstacle, Long> {
}
