package com.inteliment.counter.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.inteliment.counter.common.Word;


public class SearchListResponse {

	private List<Word> counts = new ArrayList<Word>();

	/*public List<Word> getCounts() { 
		return counts;
	}*/
	
	public Map<String,Integer> getCounts(){
		Map<String,Integer> wordCountsMap = new LinkedHashMap<String,Integer>();
		for(Word word:counts){
			wordCountsMap.put(word.getValue(), word.getCount());
		}	
		return wordCountsMap;
	}

	public void setCounts(List<Word> counts) {
		this.counts = counts;
	}
	
	public String toString(){
		return getCounts().toString();
		
	}
	
}
