package org.dadeco.cu996.api.repository;

import java.util.List;

import org.dadeco.cu996.api.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DateDimRepository extends JpaRepository<Activity, Integer> {
	@Query(value = "select d.day_in_week_eng_sn from cplanner.date_dim d group by d.day_in_week_eng_sn , d.day_in_week_id order by d.day_in_week_id", nativeQuery = true)
	public List<String> selectDayInWeekEngSn();

	@Query(value = "select d.week_start_time, d.week_end_time, d.week_in_year, , min(d.month_id) as month_id, dd.month_eng_sn from cplanner.date_dim d left join (select d.month_id, d.month_eng_sn from cplanner.date_dim d group by d.month_id, d.month_eng_sn) dd on d.month_id = dd.month_id where d.year_id = ?1 group by d.year_id, d.week_in_year order by d.week_in_year", nativeQuery = true)
	public List<List<Object>> selectYearWeeks(int year);

	@Query(value = "select min(d.week_start_time) as week_start_time from cplanner.date_dim d where d.yearmonth = ?1", nativeQuery = true)
	public Object selectWeekStartDate(String yearMonth);

	@Query(value = "select distinct d.week_end_time from cplanner.date_dim d where d.week_start_time <= str_to_date((select max(d.week_start_time) as week_start_time from cplanner.date_dim d where d.yearmonth = ?1),'%Y-%m-%d %H:%i:%s') and d.week_end_time >= str_to_date((select max(d.week_start_time) as week_start_time from cplanner.date_dim d where d.yearmonth = ?1),'%Y-%m-%d %H:%i:%s')", nativeQuery = true)
	public Object selectWeekEndDate(String yearMonth);
}
