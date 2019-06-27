package org.dadeco.cu996.api.repository;

import java.util.List;

import org.dadeco.cu996.api.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	Activity save(Activity productInfo);

	@Query(value = "select max(a.name) as name, max(a.role) as role, sum(a.daily_effort) as daily_effort, d.week_in_year, d.day_in_week_eng_sn from cplanner.date_dim d left join cplanner.activity a on d.day_id >= str_to_date(a.start,'%Y-%m-%d %H:%i:%s') and d.day_id <= str_to_date(a.end,'%Y-%m-%d %H:%i:%s') where a.userid = ?1 and d.yearmonth >= ?2 and d.yearmonth <= ?3 group by d.day_id, d.week_in_year, d.day_in_week_eng_sn order by d.day_id asc", nativeQuery = true)
	public List<List<Object>> findByUserIdAndStartAndEnd(Integer userId, int startYearMonth, int endYearMonth);

	@Query(value = "select max(a.name) as name, max(a.role) as role, sum(a.daily_effort) as daily_effort, d.week_in_year, d.day_in_week_eng_sn from cplanner.date_dim d left join cplanner.activity a on d.day_id >= str_to_date(a.start,'%Y-%m-%d %H:%i:%s') and d.day_id <= str_to_date(a.end,'%Y-%m-%d %H:%i:%s') where d.yearmonth >= ?1 and d.yearmonth <= ?2 group by d.day_id, d.week_in_year, d.day_in_week_eng_sn order by d.day_id asc", nativeQuery = true)
	public List<List<Object>> findByStartAndEnd(int startYearMonth, int endYearMonth);

	@Query(value = "select max(a.name) as name, max(a.role) as role, sum(a.daily_effort) as daily_effort, d.week_in_year, d.day_in_week_eng_sn from cplanner.date_dim d left join cplanner.activity a on d.day_id >= str_to_date(a.start,'%Y-%m-%d %H:%i:%s') and d.day_id <= str_to_date(a.end,'%Y-%m-%d %H:%i:%s') where a.userid = ?1 and d.yearmonth >= ?2 and d.yearmonth <= ?3 group by d.day_id, d.week_in_year, d.day_in_week_eng_sn order by d.day_id asc", nativeQuery = true)
	public List<List<Object>> findByUserIdAndStartAndEndForTeam(Integer userId, int startYearMonth, int endYearMonth);

	@Query(value = "select max(a.name) as name, max(a.role) as role, sum(a.daily_effort) as daily_effort, d.week_in_year, d.day_in_week_eng_sn from cplanner.date_dim d left join cplanner.activity a on d.day_id >= str_to_date(a.start,'%Y-%m-%d %H:%i:%s') and d.day_id <= str_to_date(a.end,'%Y-%m-%d %H:%i:%s') where d.yearmonth >= ?1 and d.yearmonth <= ?2 group by d.day_id, d.week_in_year, d.day_in_week_eng_sn order by d.day_id asc", nativeQuery = true)
	public List<List<Object>> findByStartAndEndForTeam(int startYearMonth, int endYearMonth);
}
