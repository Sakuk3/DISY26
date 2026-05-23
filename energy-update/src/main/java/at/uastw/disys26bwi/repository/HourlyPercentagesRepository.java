package at.uastw.disys26bwi.repository;

import at.uastw.disys26bwi.entity.HourlyPercentagesEntity;
import at.uastw.disys26bwi.entity.HourlyPercentagesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HourlyPercentagesRepository
  extends JpaRepository<HourlyPercentagesEntity, HourlyPercentagesId> {
}
