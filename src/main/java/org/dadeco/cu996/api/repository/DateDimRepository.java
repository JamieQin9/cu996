package org.dadeco.cu996.api.repository;

import java.util.List;

import org.dadeco.cu996.api.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DateDimRepository extends JpaRepository<Activity, Integer> {
	@Query(value = "select distinct d.day_in_week_eng_sn from cplanner.date_dim d order by d.day_in_week_id", nativeQuery = true)
	public List<String> selectDayInWeekEngSn();
}
