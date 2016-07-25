package com.inteliment.counter.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;

import com.inteliment.counter.service.CounterService;
import java.util.List;
import com.inteliment.counter.common.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-counter-servlet.xml","classpath:test-security-context.xml"})
public class CounterControllerTest {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Mock
	CounterService counterService;
	
	@Autowired
	@InjectMocks
	private CounterController counterController;
	
	private MockMvc mockMvc;
	
	@Autowired
	private FilterChainProxy springSecurityFilterChain;
 
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(counterController)//.addFilters(this.springSecurityFilterChain)
				.build();
		MockitoAnnotations.initMocks(this);
		
	}
	
	private List<Word> buildMockData(){
		List<Word> wordCountList = new ArrayList<Word>();
		wordCountList.add(new Word("test",3));
		wordCountList.add(new Word("abc",0));
		return wordCountList;
	}
	
    @Test
    public void testSearchTextSuccessCase() throws Exception {    
    	Mockito.when(counterService.getWordCounts(any(ArrayList.class))).thenReturn(buildMockData());
    	
    	UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken("inteliment", "pass123");
    	
    	  	this.mockMvc
		.perform(get("/search").param("searchText", "test,abc")
				.contentType(APPLICATION_JSON_UTF8).principal(principal))
		.andExpect(content().contentType("application/json"))
		.andExpect(status().is(200))
		.andExpect(jsonPath("$.counts").exists())
		.andExpect(jsonPath("$.counts.test").exists())
		.andExpect(jsonPath("$.counts.test").value(3))
		.andExpect(jsonPath("$.counts.abc").value(0));    	
    }    
    
    @Test
    public void testSearchText404Error() throws Exception {
    	this.mockMvc
		.perform(post("/xyz/").contentType(APPLICATION_JSON_UTF8))
		.andExpect(status().is(404));    	
    }
    
    @Test
    public void testTopListSuccessCase() throws Exception {
    	String mockDataStr = buildTopCountMockData().toString();

    	UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken("inteliment", "pass123");
		Mockito.when(counterService.getTopCount(anyInt())).thenReturn(mockDataStr);
    	 this.mockMvc
		.perform(post("/top/2").contentType(APPLICATION_JSON_UTF8).header("Authorization","aW50ZWxpbWVudDpwYXNzMTIz"))
		.andExpect(status().is(200))
		.andExpect(content().contentType("text/csv"))
    	.andExpect(content().string(mockDataStr));    	
    }

	private StringBuilder buildTopCountMockData() {
		StringBuilder sb = new StringBuilder();
    	sb.append("sharadasharada" + "|"+"1"+  "\n");
    	sb.append("honey" +"|"+ "1" + "\n");
    	sb.append("Bharath" +"|"+ "1" + "\n");
    	sb.append("sharada" +"|"+ "1" + "\n");    	
    	sb.append("sharadahoney" + "|"+"1"+ "\n");
		return sb;
	}
    
    @Test
    public void testTopList404Error() throws Exception {    	
    	this.mockMvc
		.perform(post("/xyz/5").contentType(APPLICATION_JSON_UTF8))
		.andExpect(status().is(404));
    	
    }
}
