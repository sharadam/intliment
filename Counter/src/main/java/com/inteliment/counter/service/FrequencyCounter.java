package com.inteliment.counter.service;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.inteliment.counter.common.Word;

/**
 * @author honey
 *
 */
@Component
public class FrequencyCounter implements IFrequencyCounter{

	/* (non-Javadoc)
	 * @see com.inteliment.counter.service.IFrequencyCounter#getWordFrequencyByWordValue(java.lang.String, java.util.List, java.util.Map)
	 */
	@Override
	public void getWordFrequencyByWordValue(String content, List<String> wordValues,Map<String,Word> wordCountsMap) {
		for(String searchText : wordValues){
			if(wordCountsMap.containsKey(searchText)){
				wordCountsMap.get(searchText).incrementCountByValue(StringUtils.countMatches(content.toLowerCase(), searchText.toLowerCase()));
		    }else {
		        wordCountsMap.put(searchText,new Word(searchText,StringUtils.countMatches(content.toLowerCase(), searchText.toLowerCase())));
		    }
		}			
	}

}
