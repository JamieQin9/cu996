package org.dadeco.cu996.api.service.impl;

import java.util.List;

import org.dadeco.cu996.api.repository.ActivityRepository;
import org.dadeco.cu996.api.service.TeamCapacityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamCapacityServiceImpl implements TeamCapacityService {
	@Autowired
	private ActivityRepository activityRepository = null;

	public List<List<Object>> getTeamCapacityByMonth(String startDate, String endDate) {

		List<List<Object>> activities = activityRepository.findByStartAndEndForTeam(startDate, endDate);

		return activities;
	}
}
