package com.boen.keywordfilter;

import java.util.List;

public class KeyWordResult {
	boolean isContain;
	List<String> keywords ;
	
	public boolean isContain() {
		return isContain;
	}
	public void setContain(boolean isContain) {
		this.isContain = isContain;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	
}
