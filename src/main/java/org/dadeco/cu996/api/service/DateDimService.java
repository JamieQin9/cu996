package org.dadeco.cu996.api.service;

import java.util.List;

public interface DateDimService {
	public List<String> selectDayInWeekEngSn();

	public List<List<Object>> selectYearWeeks(int year);

	public Object selectWeekStartDate(String yearMonth);

	public Object selectWeekEndDate(String yearMonth);
}
