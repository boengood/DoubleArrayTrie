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
	 private KeyWordMatch(String path , boolean isLoadCache){
		 trie = new DoubleArrayTrieStatic();
		 try {
			 trie.loadKeyWord();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
     /**
      * 查找文档中所有匹配的关键字
      * @param content
      * @return
      */
	public static  KeyWordResult matchKeyWord(String content){
		List<String>  keywords = KeyWordMatch.getInstance().trie.getAllKeyWord(content);
		KeyWordResult result = new KeyWordResult();
		result.setContain(keywords.size() > 0 ? true : false);
		result.setKeywords(keywords);
		return result;
	}

     /**
      * 是否含有敏感词或关键字，当含有一个则返回True，不含有则返回false
      * @param content
      * @return
      */
     public static boolean hasKeyWord(String content){
         return KeyWordMatch.getInstance().trie.hasKeyWord(content);
     }

     /**
      * 查看字符是否为敏感词/关键字
      * @param keyword
      * @return
      */
	public static  boolean iskeyWord(String keyword){
		return  KeyWordMatch.getInstance().trie.isKeyWord(keyword);
	}

     /**
      * 替换掉所有keyword
      * @param content
      * @param replacment
      * @return
      */
     public static String replaceAllKeyword(String content ,String replacment){
         return  KeyWordMatch.getInstance().trie.replaceAllKeyWord(content,replacment);
     }
	
	public static KeyWordMatch getInstance(){
		return keyWordMatch;
	}

     public static void init(){

     }
	
	public static void main(String[]a) {
		KeyWordResult result = KeyWordMatch.matchKeyWord("办证八九民办啥前途无量阿扁推翻代理办证办证前途无量");
		if (result.isContain) {
			for (String keyword : result.getKeywords()) {
				System.out.println("敏感词:" +keyword);
			}
		}

        String replacmentContent  = KeyWordMatch.replaceAllKeyword("办证代理办证办证八九民办啥前途无量阿扁推翻代理办证前途无量",null);
        System.out.println(replacmentContent);
	}
	
}
