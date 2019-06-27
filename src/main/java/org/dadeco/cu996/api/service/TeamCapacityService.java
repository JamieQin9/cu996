package org.dadeco.cu996.api.service;

import java.util.List;

public interface TeamCapacityService {
	public List<List<Object>> getTeamCapacityByMonth(String startDate, String endDate);
}
