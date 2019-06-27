package org.dadeco.cu996.api.service.impl;

import java.util.List;

import org.dadeco.cu996.api.repository.DateDimRepository;
import org.dadeco.cu996.api.service.DateDimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DateDimServiceImpl implements DateDimService {
	@Autowired
	private DateDimRepository dateDimRepository = null;

	public List<String> selectDayInWeekEngSn() {
		return dateDimRepository.selectDayInWeekEngSn();
	}
}
