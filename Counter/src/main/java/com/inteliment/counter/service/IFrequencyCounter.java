package com.inteliment.counter.service;

import java.util.List;
import java.util.Map;

import com.inteliment.counter.common.Word;

public interface IFrequencyCounter {
		/**
		 * The method checks for the occurances of search textValues 
		 * in the content and updates the same into the wordCountsMap
		 * @param content
		 * @param wordValues
		 * @param wordCountsMap
		 */
		void getWordFrequencyByWordValue(String content, List<String> wordValues, Map<String, Word> wordCountsMap);
}
