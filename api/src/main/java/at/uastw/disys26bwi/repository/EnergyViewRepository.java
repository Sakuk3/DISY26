package at.uastw.disys26bwi.repository;

import at.uastw.disys26bwi.entity.HourlyEnergyView;
import at.uastw.disys26bwi.entity.HourlyEnergyViewId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;


public interface EnergyViewRepository extends JpaRepository<HourlyEnergyView, HourlyEnergyViewId> {

    Optional<HourlyEnergyView> findTopByOrderByHourDesc();

    List<HourlyEnergyView> findByHourBetweenOrderByHourAsc(
            OffsetDateTime start,
            OffsetDateTime end
    );
}