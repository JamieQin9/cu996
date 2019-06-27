package org.dadeco.cu996.api.service.impl;

import java.util.List;

import org.dadeco.cu996.api.model.User;
import org.dadeco.cu996.api.repository.ActivityRepository;
import org.dadeco.cu996.api.service.TeamCapacityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamCapacityServiceImpl implements TeamCapacityService {
	@Autowired
	private ActivityRepository activityRepository = null;

	public List<List<Object>> getTeamCapacityByMonth(User user, int startYearMonth, int endYearMonth) {
		List<List<Object>> activities = null;

		if (user != null) {
			activities = activityRepository.findByUserIdAndStartAndEnd(user.getId(), startYearMonth, endYearMonth);
		} else {
			activities = activityRepository.findByStartAndEnd(startYearMonth, endYearMonth);
		}

		return activities;
	}

}
