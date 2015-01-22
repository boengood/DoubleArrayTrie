package com.boen.keywordfilter;

import java.io.IOException;
import java.util.List;

/**
 * KeyWordFilter.java
 * 
 * @auther Boen
 * @date 2015年1月15日
 * @update 下午8:03:45
 */
public class KeyWordMatch {
	static KeyWordMatch keyWordMatch;
	
	static{
		keyWordMatch = new KeyWordMatch();
	}
	private DoubleArrayTrieStatic trie;
	private KeyWordMatch(){
		trie = new DoubleArrayTrieStatic();
		try {
			trie.loadKeyWord();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static  KeyWordResult matchKeyWord(String content){
		List<String>  keywords = KeyWordMatch.getInstance().trie.getAllKeyWord(content);
		KeyWordResult result = new KeyWordResult();
		result.setContain(keywords.size() > 0 ? true : false);
		result.setKeywords(keywords);
		return result;
	}
	
	public static  boolean iskeyWord(String keyword){
		return  KeyWordMatch.getInstance().trie.isKeyWord(keyword);
	}
	
	public static KeyWordMatch getInstance(){
		return keyWordMatch;
	}
	
	
	public static void main(String[]a) {
		KeyWordResult result = KeyWordMatch.matchKeyWord("我催情药草你老母的陈水扁啊日啊.avi 成人电但是防守对方的手狗日的3级片我打飞机啊藏春阁法轮草。。。你妈逼");
		if (result.isContain) {
			for (String keyword : result.getKeywords()) {
				System.out.println(keyword);
			}
		}
	}
	
}
