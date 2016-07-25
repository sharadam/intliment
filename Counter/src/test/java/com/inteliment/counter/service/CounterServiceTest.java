package com.inteliment.counter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.inteliment.counter.common.Word;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-counter-servlet.xml" })
public class CounterServiceTest {

	@Mock
	FrequencyCounter frequencyCounter;

	@Autowired
	@InjectMocks
	CounterService counterService;

	@Test
	public void testGetWordCounts() {
		counterService.setFilePath(new File("src/test/resources/test.txt").getPath());
		List<Word> wordCountList;
		try {
			wordCountList = counterService.getWordCounts(new ArrayList<String>(Arrays.asList("Duis", "Sed","Donec")));
			assertNotNull(wordCountList);
			assertEquals(3, wordCountList.size());
			assertEquals(11, wordCountList.get(0).getCount());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(expected = java.io.FileNotFoundException.class)
	public void testGetWordCountsFileNotFound() throws FileNotFoundException {
		counterService.setFilePath(new File("src/test/resources/test1.txt").getPath());
		counterService.getWordCounts(new ArrayList<String>(Arrays.asList("Sharada", "test")));		
	}
	
	@Test
	public void testGetTopCount() {
		counterService.setFilePath(new File("src/test/resources/test.txt").getPath());
		try {
			StringBuilder sb = new StringBuilder();
	    	sb.append("vel|17"+"\n");
	    	sb.append("eget|17"+"\n");	    	   
			String returnValue = counterService.getTopCount(2);
			//assertEquals(sb.toString(), returnValue);
			
		} catch (FileNotFoundException e) {
			
		}catch(IOException e){
			
		}

	}

	@Test(expected = java.io.IOException.class)
	public void testGetTopCountFileNotFound() throws FileNotFoundException,IOException {
		counterService.setFilePath(new File("src/test/resources/test1.txt").getPath());
		counterService.getTopCount(2);		
	}

}
