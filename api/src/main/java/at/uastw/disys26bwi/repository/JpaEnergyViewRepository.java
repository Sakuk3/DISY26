package at.uastw.disys26bwi.repository;

import at.uastw.disys26bwi.entity.HourlyEnergyView;
import at.uastw.disys26bwi.entity.HourlyEnergyViewId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;


//This is the database access layer. It can read the newest hourly row and all rows between start and end.
public interface JpaEnergyViewRepository extends JpaRepository<HourlyEnergyView, HourlyEnergyViewId> {

    Optional<HourlyEnergyView> findTopByOrderByHourDesc();

    List<HourlyEnergyView> findByHourBetweenOrderByHourAsc(
            OffsetDateTime start,
            OffsetDateTime end
    );
}