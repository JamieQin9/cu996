package org.dadeco.cu996.api.service;

import java.util.List;

import org.dadeco.cu996.api.model.User;

public interface PersonalCapacityService {
	public List<List<Object>> getPersonalCapacityByMonth(User user, int startYearMonth, int endYearMonth);
}
