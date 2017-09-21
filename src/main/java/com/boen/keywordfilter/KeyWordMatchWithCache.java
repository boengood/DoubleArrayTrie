package com.boen.keywordfilter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
* KeyWordFilter.java
*
* @auther Boen
* @date 2015年1月15日
* @update 下午8:03:45
*/
public class KeyWordMatchWithCache {

   private DATrieStatic trie;

    public KeyWordMatchWithCache(String path){
        try
        {
            ObjectInputStream in =new ObjectInputStream( new FileInputStream(path + ".cache"));
            trie = (DATrieStatic)in.readObject();   //读取数据
            in.close();
        }catch(Exception e) {
            //e.printStackTrace();
        }

        if (null == trie){
            trie = new DATrieStatic();
            try {
                trie.loadKeyWord(path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try
            {
                ObjectOutputStream o = new ObjectOutputStream( new FileOutputStream(path + ".cache"));
                o.writeObject(trie);   //写入数据
                o.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

    }

    public KeyWordMatchWithCache(List<String> words){
        trie = new DATrieStatic();
        try {
            trie.loadKeyWord(words);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param content
     * @return
     * 查找文档中所有匹配的关键字
     */
   public   KeyWordResult matchKeyWord(String content){
       List<String>  keywords = trie.getAllKeyWord(content);
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
    public  boolean hasKeyWord(String content){
        return trie.hasKeyWord(content);
    }

    /**
     * 查看字符是否为敏感词/关键字
     * @param keyword
     * @return
     */
   public   boolean iskeyWord(String keyword){
       return  trie.isKeyWord(keyword);
   }

    /**
     * 替换掉所有keyword
     * @param content
     * @param replacment
     * @return
     */
    public  String replaceAllKeyword(String content ,String replacment){
        return trie.replaceAllKeyWord(content,replacment);
    }

    public String rpAllLongKeyword(String content ,String start,String end){
        return  trie.replaceAllKeyWord(content,start,end);
    }


   public static void main(String[]a) {
       KeyWordMatchWithCache cache = new KeyWordMatchWithCache("/Users/boen/workspace/bst/ai/AI/trunk/KnowledgeBase/default/allterms.txt");
      /* List<String> words = new ArrayList<String>();
       words.add("省委");
       words.add("省委门");
       KeyWordMatchWithCache cache = new KeyWordMatchWithCache(words);*/
       System.out.println(cache.rpAllLongKeyword("劳动合同期限与试用期期限的关系","[","]"));
       System.out.println(cache.rpAllLongKeyword("劳动合同期限与关系试用期期限的关系","[","]"));
       System.out.println(cache.replaceAllKeyword("劳动合同期限与试用期期限的关系","*"));
   }


}
