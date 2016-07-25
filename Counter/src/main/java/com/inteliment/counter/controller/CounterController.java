package com.inteliment.counter.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inteliment.counter.model.SearchListResponse;
import com.inteliment.counter.service.CounterService;

@RestController
public class CounterController {

	@Autowired
	CounterService counterService;

	static final Logger logger = Logger.getLogger(CounterController.class);

	/**Controller method invoked for search text
	 * @param searchText
	 * @return
	 */
	
	
	@RequestMapping(value = "/search", method = { RequestMethod.GET,
			RequestMethod.POST }, headers = "Accept=application/json")
	public SearchListResponse getCounts(@RequestParam String searchText) {
		logger.debug("Entering getCounts controller method");
		SearchListResponse response = new SearchListResponse();
		try {
			response.setCounts(counterService.getWordCounts(Arrays.asList(searchText.split(","))));
			logger.debug("The return response from getCounts: " + response.toString());
		} catch (FileNotFoundException e) {
			
		}
		return response;
	}

	/**
	 * Controller method for fetching top {topsize} element occurances to be returned
	 * @param topList
	 * @return
	 */
	@RequestMapping(value = "/top/{topList}", method = { RequestMethod.GET,
			RequestMethod.POST }, headers = "Accept=text/csv")
	public String getTopCount(@PathVariable int topList) {
		logger.debug("Entering getTopCount controller method");
		String topCountListStr = StringUtils.EMPTY;
		try{
			topCountListStr = counterService.getTopCount(topList);
			logger.debug("The return response from getTopCount : " + topCountListStr);
		}catch(FileNotFoundException e){
			
		}catch(IOException e){
			
		}		
		return topCountListStr;
	}
}
