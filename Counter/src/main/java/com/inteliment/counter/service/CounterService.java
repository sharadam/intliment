package com.inteliment.counter.service;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.inteliment.counter.service.FrequencyCounter;
import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import com.inteliment.counter.common.Word;
import org.apache.log4j.Logger;

/**
 * @author honey
 *
 */
@Service
public class CounterService {

	//TODO:To be moved to separate constants file
	private static final String DATA_SPLIT_REGULAR_EXPRESSION = "\\s|\\.|\\,";

	@Autowired
	FrequencyCounter frequencyCounter;

	@Value("${input.file.path}")
	private String filePath;

	//@VisibleForTesting //TODO: part of Guava 
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	static final Logger logger = Logger.getLogger(CounterService.class);

	/**
	 * The method searches for the string occurances in the configured file
	 * @param searchStringList
	 * @return
	 * @throws FileNotFoundException 
	 */
	public List<Word> getWordCounts(List<String> searchStringList) throws FileNotFoundException {
		Map<String, Word> wordCountsMap = new LinkedHashMap<String, Word>();
		Scanner scanner = null;
		try {
			logger.info("Entering getWordCounts method");
			scanner = new Scanner(new File(filePath));
			while (scanner.hasNextLine()) {
				String strLine = scanner.nextLine();
				frequencyCounter.getWordFrequencyByWordValue(strLine, searchStringList, wordCountsMap);
			}
		} catch (FileNotFoundException e) {
			logger.error("The Resource file counting the occurance is not found : " + e.getLocalizedMessage());
			throw e;
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		return new ArrayList<Word>(wordCountsMap.values());
	}

	/**
	 * The method returns the top most frequently used words from the file
	 * @param topSize
	 * @return
	 */
	public String getTopCount(int topSize)  throws FileNotFoundException,IOException{
		logger.info("Entering getTopCount method");
		try {
			String data = String.join(" ", Files.readAllLines(Paths.get(filePath)));

			Map<String, List<String>> map = Arrays.asList(data.split(DATA_SPLIT_REGULAR_EXPRESSION)).stream()
					.collect(Collectors.groupingBy(str -> StringUtils.lowerCase(str)));
			
			/*for(String key : map.keySet()){
				if(key.equals("")|| key.length()==0){
					map.remove(key);
					break;
				}
			}*/

			Map<String, List<String>> sorted = map.entrySet().stream()
					.sorted(comparingInt(e -> ((List<String>) ((Entry) e).getValue()).size()).reversed())
					.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> {
						throw new AssertionError();
					} , LinkedHashMap::new));

			// LinkedHashMap<String,Integer> subMap = new
			// LinkedHashMap<String,Integer>();
			StringBuilder builder = new StringBuilder();
			for (List<String> l : Collections.list(Collections.enumeration(sorted.values())).subList(0, sorted.values().size()>topSize?topSize:sorted.values().size())) {
				builder.append(l.get(0) + "|" + l.size() + "\n");
			}
			return builder.toString();
		} catch (FileNotFoundException e) {
			logger.error("The Resource file counting the occurance is not found : "+ e.getLocalizedMessage());
			throw e;
		}catch(IOException e){
			logger.error("Error while reading from file :" + e.getLocalizedMessage());
			throw e;
		}finally {
			
		}
	}

}
