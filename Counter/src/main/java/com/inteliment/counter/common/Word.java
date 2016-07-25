package com.inteliment.counter.common;

import java.io.Serializable;

public class Word implements Serializable{

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	private String value;
	private int count;
	
	public void incrementCountByValue(int countMatches) {
		this.count = this.count + countMatches;		
	}
	public Word(String value,int count){
		this.value=value;
		this.count=count;
	}
	
}
