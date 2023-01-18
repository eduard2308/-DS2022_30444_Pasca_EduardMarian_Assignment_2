package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.EnergyConsumption;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EnergyConsumptionRepository extends JpaRepository<EnergyConsumption, Integer> {
    @Query("select sum(c.consumption) from EnergyConsumption c where c.deviceId = :deviceId")
    Optional<Float> computeSumForDate(@Param("deviceId") int deviceId);

    @Query("select c from EnergyConsumption c join Device d on c.deviceId = d.id join User u on d.userId = :userId")
    List<EnergyConsumption> findAllByUser(@Param("userId") int userId);
}
