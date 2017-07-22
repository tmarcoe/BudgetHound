package com.rest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Categories;
import com.entity.Household;
import com.service.HouseholdService;
import com.service.RegisterService;

@RestController
@RequestMapping(value = "/data-service", method = RequestMethod.GET)
public class ChartController {
	
	@Autowired
	RegisterService registerService;
	
	@Autowired
	HouseholdService householdService;
	
	@RequestMapping("/{parent}/budget")
	public String getBudget(@PathVariable("parent") String parent, Principal principal) throws JSONException {
		
		Household household = householdService.retrieve(principal.getName());
		List<Categories> cat = registerService.budgetBreakdown(household.getHousehold_id(), parent);
		
	
		JSONObject json = new JSONObject();
		JSONArray datasets = new JSONArray();
		JSONObject data = new JSONObject();
		JSONObject d1 = new JSONObject();
		JSONObject options = new JSONObject();
		
		json.put("type", "pie");
		json.put("data", data);
		data.put("labels", getLabels(cat));
		data.put("datasets", datasets);
		datasets.put(d1);
		d1.put("label", "Budget Breakdown By Category");
		d1.put("backgroundColor", getColorPalette(cat.size(), 20));
		d1.put("data", getDataPoints(cat));
		json.put("options", options);
		options.put("responsive", false);
		options.put("display", true);
		options.put("text", "Budget Breakdown By Category");

		return json.toString();
		
	}
	
	private String [] getLabels(List<Categories> bp) {
		List<String> labels = new ArrayList<String>();
		
		for (Categories budget : bp) {
			if (budget.getPercentage() > 0) {
				labels.add(String.format("%s (%.2f%%)", budget.getCategory(), budget.getPercentage()));
			}
		}
		
		return labels.stream().toArray(String[]::new);
	}
	
	private Double[] getDataPoints(List<Categories> bp){
		List<Double> dataPoints = new ArrayList<Double>();
		
		for(Categories budget : bp) {
			if (budget.getPercentage() > 0) {
				dataPoints.add(budget.getPercentage());
			}
		}
		
		return dataPoints.stream().toArray(Double[]::new);
	}
	
	private String[] getColorPalette(int count, int seperation) {
		List<String> colors = new ArrayList<String>();
		int r = 200;
		int g = 200;
		int b = 200;
		Random rand = new Random();
		
		for (int i=0; i < count; i++) {
			r = rand.nextInt() % 255;
			g = rand.nextInt() % 255;
			b = rand.nextInt() % 255;
			colors.add(String.format("rgba(%d, %d, %d, 0.7)", r,g,b));
		}
		
		return colors.stream().toArray(String[]::new);
	}
}
