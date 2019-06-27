package org.dadeco.cu996.api.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.dadeco.cu996.api.error.BusinessException;
import org.dadeco.cu996.api.model.User;
import org.dadeco.cu996.api.service.DateDimService;
import org.dadeco.cu996.api.service.PersonalCapacityService;
import org.dadeco.cu996.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller("capacity")
@RequestMapping("/capacity")
@CrossOrigin(origins = { "*" }, allowCredentials = "true")
public class CapacityController extends BaseController {

	@Autowired
	private PersonalCapacityService personalCapacityService = null;

	@Autowired
	private DateDimService dateDimService = null;

	@RequestMapping(value = "/getPersonalMonthlyCapacity", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public String getPersonalMonthlyCapacity(@RequestParam(value = "startMonth", required = true) String startMonth,
			@RequestParam(value = "endMonth", required = true) String endMonth)
			throws BusinessException, ParseException, JSONException {

		JSONObject result = new JSONObject();

		if (!CommonUtil.isEmptyString(startMonth) && !CommonUtil.isEmptyString(endMonth)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			Calendar startMonthC = Calendar.getInstance();
			startMonthC.setTime(df.parse(startMonth));

			Calendar endMonthC = Calendar.getInstance();
			endMonthC.setTime(df.parse(endMonth));

			DateFormat df2 = new SimpleDateFormat("yyyyMM");

			List<List<Object>> activities = personalCapacityService.getPersonalCapacityByMonth(null,
					Integer.parseInt(df2.format(startMonthC.getTime())),
					Integer.parseInt(df2.format(endMonthC.getTime())));

			DateFormat df3 = new SimpleDateFormat("yyyy-MM");
			startMonth = df3.format(startMonthC.getTime());
			endMonth = df3.format(endMonthC.getTime());

			if (!CommonUtil.isEmptyList(activities)) {
				System.out.println(activities);

				JSONObject root = new JSONObject();

				JSONObject chart = new JSONObject();
				chart.put("caption", "Top Smartphone Ratings");
				chart.put("subcaption", startMonth + "~" + endMonth);
				chart.put("xAxisName", "Day");
				chart.put("yAxisName", "Week");
				chart.put("showplotborder", "0");
				chart.put("showValues", "1");
				chart.put("xAxisLabelsOnTop", "0");
				chart.put("baseFontColor", "#333333");
				chart.put("toolTipBorderRadius", "2");
				chart.put("toolTipPadding", "5");
				chart.put("theme", "fusion");

				root.put("chart", chart);

				JSONObject columns = new JSONObject();
				JSONArray columnsArray = new JSONArray();

				List<String> dayInWeekEngSnList = dateDimService.selectDayInWeekEngSn();

				System.out.println(dayInWeekEngSnList);

				for (int i = 0; i < dayInWeekEngSnList.size(); i++) {
					String str = dayInWeekEngSnList.get(i);

					JSONObject column = new JSONObject();
					column.put("id", str);
					column.put("label", str);

					columnsArray.put(column);
				}

				columns.put("column", columnsArray);

				root.put("columns", columns);

				JSONObject colorrange = new JSONObject();
				colorrange.put("gradient", "0");
				colorrange.put("minvalue", "-1");
				colorrange.put("code", "#000000");

				JSONArray colorArray = new JSONArray();

				JSONObject columnNotWorking = new JSONObject();
				columnNotWorking.put("code", "#aaaaaa");
				columnNotWorking.put("minvalue", "-1");
				columnNotWorking.put("maxvalue", "0");
				columnNotWorking.put("label", "Not Working");

				colorArray.put(columnNotWorking);

				JSONObject columnLower = new JSONObject();
				columnLower.put("code", "#f6bc33");
				columnLower.put("minvalue", "0");
				columnLower.put("maxvalue", "4");
				columnLower.put("label", "Lower");

				colorArray.put(columnLower);

				JSONObject columnGood = new JSONObject();
				columnGood.put("code", "#6da81e");
				columnGood.put("minvalue", "4");
				columnGood.put("maxvalue", "9");
				columnGood.put("label", "Good");

				colorArray.put(columnGood);

				JSONObject columnOverload = new JSONObject();
				columnOverload.put("code", "#e24b1a");
				columnOverload.put("minvalue", "9");
				columnOverload.put("maxvalue", "9999");
				columnOverload.put("label", "Overload");

				colorArray.put(columnOverload);

				colorrange.put("color", colorArray);

				root.put("colorrange", colorrange);

				// Data Area
				JSONArray dataset = new JSONArray();
				JSONObject data = new JSONObject();
				JSONArray dataArray = new JSONArray();

				for (int i = 0; i < activities.size(); i++) {
					List<Object> recordList = activities.get(i);

					System.out.println(recordList);

					if (!CommonUtil.isEmptyList(recordList)) {
						String name = recordList.get(0) == null ? "" : (String) recordList.get(0);
						String role = recordList.get(1) == null ? "" : (String) recordList.get(1);
						int dailyEffort = recordList.get(2) == null ? 0
								: (int) ((BigDecimal) recordList.get(2)).doubleValue();
						Integer weekInYear = (Integer) recordList.get(3);
						String dayInWeekEngSn = (String) recordList.get(4);

						JSONObject capacity = new JSONObject();
						capacity.put("rowid", "W" + weekInYear.intValue());
						capacity.put("columnid", dayInWeekEngSn);
						capacity.put("value", dailyEffort);
						capacity.put("tllabel", name);
						capacity.put("trlabel", role);

						dataArray.put(capacity);
					}
				}

				data.put("data", dataArray);

				dataset.put(data);

				root.put("dataset", dataset);

				System.out.println(root);

				result.put("status", "success");
				result.put("data", root);
			} else {
				result.put("status", "fail");
				result.put("data", null);
			}
		} else {
			result.put("status", "fail");
			result.put("data", null);
		}

		return result.toString();
	}

	@RequestMapping(value = "/getTeamMonthlyCapacity", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public String getTeamMonthlyCapacity(@RequestParam(value = "startMonth", required = true) String startMonth,
			@RequestParam(value = "endMonth", required = true) String endMonth)
			throws BusinessException, ParseException, JSONException {
		JSONObject result = new JSONObject();

		if (!CommonUtil.isEmptyString(startMonth) && !CommonUtil.isEmptyString(endMonth)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat df2 = new SimpleDateFormat("yyyyMM");
			DateFormat df3 = new SimpleDateFormat("dd/MM/yyyy");

			Calendar startMonthC = Calendar.getInstance();
			startMonthC.setTime(df.parse(startMonth));

			Calendar endMonthC = Calendar.getInstance();
			endMonthC.setTime(df.parse(endMonth));

			List<List<Object>> activities = personalCapacityService.getPersonalCapacityByMonth(null,
					Integer.parseInt(df2.format(startMonthC.getTime())),
					Integer.parseInt(df2.format(endMonthC.getTime())));

			if (!CommonUtil.isEmptyList(activities)) {
				System.out.println(activities);

				JSONObject root = new JSONObject();

				JSONObject chart = new JSONObject();
				chart.put("caption", "Resource Capacity Overview");
				chart.put("subcaption", "DAD-AP & PJ-ECO");
				chart.put("dateformat", "dd/mm/yyyy");
				chart.put("plottooltext", "Status for period <b>$start - $end</b> is <b>$label</b>");
				chart.put("theme", "fusion");

				root.put("chart", chart);

				JSONObject columns = new JSONObject();
				JSONArray columnsArray = new JSONArray();

				List<List<Object>> yearWeeks = dateDimService.selectYearWeeks(startMonthC.get(Calendar.YEAR));

				System.out.println(yearWeeks);

				JSONArray categorysArray = new JSONArray();

				JSONObject categoryMonth = new JSONObject();
				JSONArray categoryArrayMonth = new JSONArray();

				for (int i = 0; i < yearWeeks.size(); i++) {
					List<Object> weekList = yearWeeks.get(i);

					System.out.println(weekList);

					if (!CommonUtil.isEmptyList(weekList)) {
						Date weekStartTime = weekList.get(0) == null ? null
								: new Date(((java.sql.Date) weekList.get(0)).getTime());
						Date weekEndTime = weekList.get(1) == null ? null
								: new Date(((java.sql.Date) weekList.get(1)).getTime());
						String monthEngSn = (String) weekList.get(5);

						JSONObject month = new JSONObject();
						month.put("start", df3.format(weekStartTime));
						month.put("end", df3.format(weekEndTime));
						month.put("label", monthEngSn + ". " + startMonthC.get(Calendar.YEAR));

						categoryArrayMonth.put(month);
					}
				}

				categoryMonth.put("category", categoryArrayMonth);
				categorysArray.put(categoryMonth);

				JSONObject categoryWeek = new JSONObject();
				JSONArray categoryArrayWeek = new JSONArray();

				for (int i = 0; i < yearWeeks.size(); i++) {
					List<Object> weekList = yearWeeks.get(i);

					System.out.println(weekList);

					if (!CommonUtil.isEmptyList(weekList)) {
						Date weekStartTime = weekList.get(0) == null ? null
								: new Date(((java.sql.Date) weekList.get(0)).getTime());
						Date weekEndTime = weekList.get(1) == null ? null
								: new Date(((java.sql.Date) weekList.get(1)).getTime());
						Integer weekInYear = (Integer) weekList.get(2);

						JSONObject week = new JSONObject();
						week.put("start", df3.format(weekStartTime));
						week.put("end", df3.format(weekEndTime));
						week.put("label", weekInYear.intValue());

						categoryArrayWeek.put(week);
					}
				}
				categoryWeek.put("bgalpha", "0");
				categoryWeek.put("category", categoryArrayWeek);
				categorysArray.put(categoryWeek);

				root.put("categories", categorysArray);

				columns.put("column", columnsArray);

				root.put("columns", columns);

				JSONObject colorrange = new JSONObject();
				colorrange.put("gradient", "0");
				colorrange.put("minvalue", "-1");
				colorrange.put("code", "#000000");

				JSONArray colorArray = new JSONArray();

				JSONObject columnNotWorking = new JSONObject();
				columnNotWorking.put("code", "#aaaaaa");
				columnNotWorking.put("minvalue", "-1");
				columnNotWorking.put("maxvalue", "0");
				columnNotWorking.put("label", "Not Working");

				colorArray.put(columnNotWorking);

				JSONObject columnLower = new JSONObject();
				columnLower.put("code", "#f6bc33");
				columnLower.put("minvalue", "0");
				columnLower.put("maxvalue", "4");
				columnLower.put("label", "Lower");

				colorArray.put(columnLower);

				JSONObject columnGood = new JSONObject();
				columnGood.put("code", "#6da81e");
				columnGood.put("minvalue", "4");
				columnGood.put("maxvalue", "9");
				columnGood.put("label", "Good");

				colorArray.put(columnGood);

				JSONObject columnOverload = new JSONObject();
				columnOverload.put("code", "#e24b1a");
				columnOverload.put("minvalue", "9");
				columnOverload.put("maxvalue", "9999");
				columnOverload.put("label", "Overload");

				colorArray.put(columnOverload);

				colorrange.put("color", colorArray);

				root.put("colorrange", colorrange);

				// Data Area
				JSONArray dataset = new JSONArray();
				JSONObject data = new JSONObject();
				JSONArray dataArray = new JSONArray();

				for (int i = 0; i < activities.size(); i++) {
					List<Object> recordList = activities.get(i);

					System.out.println(recordList);

					if (!CommonUtil.isEmptyList(recordList)) {
						String name = recordList.get(0) == null ? "" : (String) recordList.get(0);
						String role = recordList.get(1) == null ? "" : (String) recordList.get(1);
						int dailyEffort = recordList.get(2) == null ? 0
								: (int) ((BigDecimal) recordList.get(2)).doubleValue();
						Integer weekInYear = (Integer) recordList.get(3);
						String dayInWeekEngSn = (String) recordList.get(4);

						JSONObject capacity = new JSONObject();
						capacity.put("rowid", "W" + weekInYear.intValue());
						capacity.put("columnid", dayInWeekEngSn);
						capacity.put("value", dailyEffort);
						capacity.put("tllabel", name);
						capacity.put("trlabel", role);

						dataArray.put(capacity);
					}
				}

				data.put("data", dataArray);

				dataset.put(data);

				root.put("dataset", dataset);

				System.out.println(root);

				result.put("status", "success");
				result.put("data", root);
			} else {
				result.put("status", "fail");
				result.put("data", null);
			}
		} else {
			result.put("status", "fail");
			result.put("data", null);
		}

		return result.toString();
	}
}
