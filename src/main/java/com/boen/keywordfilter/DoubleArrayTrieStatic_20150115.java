package com.boen.keywordfilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 静态字典数构建
 *
 * <p>
 *  不支持动态增加节点，并且需要将首字相同的文字顺序排列提高效率.<br/>
 *  因为敏感词库比汉字数量少很多，使用Darts(unicode值为基数)会消耗较多内存,故写下此类减少内存占用。
 * </p>
 * <p> 未完成的功能: 敏感级别 </p>
 * <p>
 * 基本算法: <br/>
 * base[i] + c = next_sub;<br/>
 * check[i] = pre_sub;
 * </p>
 * ArrayTrie.java
 *
 * @auther Boen
 * @date 2015年1月12日
 * @update 上午9:51:42
 */
public class DoubleArrayTrieStatic_20150115 {
	
	int firstCharNum ;		// 首字数量,去重复
	int baseInit = 1114;	// 状态值初始值和总字数有关
	int HOPS_NUM = 15;  	// 碰撞时状态值跳跃数  default:15
	int pos = 0;
	int base[] = new int[2048];
	int check[] = new int[2048];
	
	Map<Character, Integer> seqMap = new HashMap<Character, Integer>();
	List<KeyWordDO> KeyWordDOList = new ArrayList<KeyWordDO>();
	List<Character> allChars = new ArrayList<Character>();
	
	private int getCharSeq(char c ,Boolean isFist){
		Integer cseq = seqMap.get(c);
		if ( cseq == null) {
			if (isFist) {
				firstCharNum ++;
			}
			cseq = seqMap.size() + 1;
			seqMap.put(c, cseq);
			allChars.add(c);
		}
		return cseq.intValue();
	}
	
	private void extendArray(){// 扩容
		base = Arrays.copyOf(base, base.length*2) ;
		check = Arrays.copyOf(check, check.length*2) ;
	}
	
	private void initCharSeq(String keyWord ){
		char[]  keyChars = keyWord.toCharArray();

		for (int i = 0; i < keyChars.length; i++) {
			getCharSeq(keyChars[i], i == 0 ? true : false);
		}
		char firstWord = keyChars[0] ;
		
		KeyWordDO keyWordDO = new KeyWordDO();
		keyWordDO.setFirstWord(firstWord);
		keyWordDO.setKeyWord(keyWord);
		KeyWordDOList.add(keyWordDO);
	}
	
	/**
	 * 生成树
	 */
	private int tried() {
		int _pos = pos;
		int _error = 0;
		
		List<KeyWordDO> tmpList = new ArrayList<KeyWordDO>();
		
		while (_pos < KeyWordDOList.size()) {
			char firstChar = KeyWordDOList.get(_pos).firstWord;
			tmpList.add(KeyWordDOList.get(_pos));
			int max_word_num = KeyWordDOList.get(_pos).keyWord.length();
			int _pos_c1 = _pos + 1;
			while (_pos_c1 < KeyWordDOList.size()) {
				if (firstChar == KeyWordDOList.get(_pos_c1).firstWord) {
					KeyWordDO keyWordDO = KeyWordDOList.get(_pos_c1);
					int keyWordLenth = keyWordDO.getKeyWord().length();
					max_word_num = max_word_num >  keyWordLenth ? max_word_num : keyWordLenth;
					tmpList.add(keyWordDO);
					_pos_c1++;
				} else {
					break;
				}
			}
			_pos = _pos_c1 ;
			
			char[][] tempChars = new char[tmpList.size()][max_word_num];
			// 将相同首字符传人双数组
			for (int i = 0; i < tmpList.size(); i++) {
				char [] tmpChars_1 = tmpList.get(i).getKeyWord().toCharArray();
				for (int j = 0; j < tmpChars_1.length; j++) {
					tempChars[i][j] = tmpChars_1[j];
				}
			}
			
			for (int i = 0; i < tempChars.length; i++) {
				int pre_ValueState = 0; //Pre State
				int cur_Sub ;  // Subscript
				int pre_Sub = 0; //Pre sub
				for (int j = 0; j < tempChars[i].length; j++) {
					boolean isEndChar = false; // 是否词尾
					char curChar = tempChars[i][j];
					/*if (curChar == '轮') {
						System.out.println("---------");
					}*/
					if (curChar == 0) {
						break;
					}
					if ((j+1 < tempChars[i].length && tempChars[i][j+1] == 0) || j == tempChars[i].length -1) {
						isEndChar = true;
					}
					int curCharSeq = getCharSeq(curChar, false);
					int baseValue = baseInit;
					cur_Sub = pre_ValueState + curCharSeq; // 即 base下标 等于上一个base的value + 当前字符的seq
					boolean isThisEnd = false;
					boolean isMeet = true;
					
					if (cur_Sub > base.length -1) {
						extendArray();
					}
					
					if (base[cur_Sub] == 0) {// 当前没有处理过时
						while(!isThisEnd){;
							isMeet =true;
							baseValue += HOPS_NUM;
							Map<Integer,Integer> tmpMap = new HashMap<Integer,Integer>();
							Lable_1:for (int k = i; k < tempChars.length; k++) {
									boolean isMatch = true;//前缀是否相等
									int f_1 = -1;
									for (int f = 0; f < j+1; f++) {
										if ( tempChars[k][f] == 0 || tempChars[k][f] != tempChars[i][f]) {
											isMatch = false;
										}else if (tempChars[k][f] == tempChars[i][f] && isMatch) {
											f_1 = f;
										}
									}
									if (isMatch) {
										// 前缀匹配，需要判断该base的value值计算后是否该节点的下一级都有对应空间可使用
										if (j < max_word_num -1 && tempChars[k][j+1] !=0) {
											int tmpSub = baseValue + getCharSeq(tempChars[k][j+1], false);
											if (tmpSub > base.length - 1) {
												extendArray();
											}
											if (base[tmpSub] != 0 || tmpSub == cur_Sub) {// 防止两词重叠
												/*if (base[tmpSub] != 0 || (tmpMap.get(tmpSub) != null && tmpMap.get(tmpSub).intValue() != j+1)) {*/
												isMeet = false;
												break Lable_1;
											}else {
												/*if (tempChars[k][j+1] == '在') {
													System.out.println("zai");
												}*/
												tmpMap.put(tmpSub, i);
											}
										}
									}else {
										
									}
									// 防止当前下标导致之前已经匹配的项无法使用
									if (f_1 > -1  && f_1 != j && k != i) {
										//说明至少有匹配项
										if (tempChars[k][f_1+1]!= 0 ) {
											int pre_sub_f_1 = 0;
											int pre_state = 0;
											for (int l = 0; l < f_1 + 2; l++) {
												int cur_sub_f_1 = pre_state + getCharSeq(tempChars[k][l], false);
												pre_sub_f_1 = cur_sub_f_1;
												if (cur_sub_f_1 > base.length - 1) {
													extendArray();
												}
												pre_state = Math.abs(base[cur_sub_f_1]);
											}
											/*if (j < max_word_num -1 && tempChars[i][j+1] !=0) {
												int tmpSub = baseValue + getCharSeq(tempChars[i][j+1], false);*/
												if (tmpMap.get(pre_sub_f_1) != null) {
													isMeet = false;
													break Lable_1;
												}

										}
									}
									
							}
							
							if (isMeet) {
								// 此时表示所有该节点的子节点都可找到对应位置
								if (isEndChar) {
									base[cur_Sub] = -baseValue;									
								}else {
									base[cur_Sub] = baseValue;
								}
								/*if (cur_Sub == 1532) {
									System.out.println("--------");
								}*/
								check[cur_Sub] = pre_Sub;
								pre_ValueState = Math.abs(base[cur_Sub]);
								pre_Sub = cur_Sub;
								isThisEnd = true;
							}
							
						}
					}else {
						// 之前已经将其纳入， 默认信任之前数据
						if (check[cur_Sub] != pre_Sub) {
							System.out.println("ERROR :  数据结构出现异常" + curChar);;
						}
						if (isEndChar && base[cur_Sub] > 0) {
							base[cur_Sub] = -base[cur_Sub];
						}
						
						pre_ValueState = Math.abs(base[cur_Sub]);
						pre_Sub = cur_Sub;
					}
					if (isEndChar) {
						 break;
					}
				}
			}

			tmpList.clear();
		}// While end
		
		System.out.println(base.length);
		System.out.println(check.length);
		return _error;
	}
	
	int match = 0;
	public boolean isKeyWord(String keyWord){
		char[] keyChars = keyWord.toCharArray();
		int preSub = 0;
		int curSub = 0;
		int valueCur = baseInit + 1;
		for (int i = 0; i < keyChars.length; i++) {
			char c = keyChars[i];
			if (i == 0) {
				curSub = Math.abs(getCharSeq(c, true));
			}else {
				curSub =  Math.abs(getCharSeq(c, true)) + valueCur;
			}
			if (curSub > base.length -1) {
				return false;
			}
			if (base[curSub] < 0 && check[curSub] == preSub) {
				return true;
			}
			preSub = curSub;
			valueCur = Math.abs(base[curSub]);
		}
		return false;
	}
	
	public void loadKeyWord() throws IOException{
		List<String> keyWordList = new ArrayList<String>();/*URL dic = ArrayTrie.class.getResource("keyword.txt");*/
		BufferedReader in = new BufferedReader(new FileReader("D:\\workspace\\server\\Test\\bin\\keyword.txt"));
		String line = null;
		while ((line = in.readLine()) != null) {
			String[] lineCols = line.split("\\|");
			String keyWord = lineCols[0];
			keyWordList.add(keyWord);
		}
		
		for (String keyWord : keyWordList) {
			initCharSeq(keyWord.trim());
		}
		
		System.out.println("首字数量" + firstCharNum);
		System.out.println("总字数" + seqMap.size());
		if (seqMap.size() > baseInit) {
			baseInit = seqMap.size();
		}
		tried();// 构建树
	}
	
	class KeyWordDO{
		char firstWord;
		String keyWord;
		public char getFirstWord() {
			return firstWord;
		}
		public void setFirstWord(char firstWord) {
			this.firstWord = firstWord;
		}
		public String getKeyWord() {
			return keyWord;
		}
		public void setKeyWord(String keyWord) {
			this.keyWord = keyWord;
		}
	}
	public static void main(String []a) {
		DoubleArrayTrieStatic_20150115 trie = new DoubleArrayTrieStatic_20150115();
		try {
			long start = System.nanoTime();
			trie.loadKeyWord();
			System.out.println(System.nanoTime() - start);
			System.out.println(trie.isKeyWord("我掉你老母i"));
			System.out.println(trie.isKeyWord("办理本科"));
			System.out.println(trie.isKeyWord("办理各种"));
			System.out.println(trie.isKeyWord("办理票据"));
			System.out.println(trie.isKeyWord("办理文凭"));
			System.out.println(trie.isKeyWord("办理真实"));
			System.out.println(trie.isKeyWord("办理证书2"));
			BufferedReader in = new BufferedReader(new FileReader("D:\\workspace\\server\\Test\\bin\\keyword.txt"));
			String line = null;
			int kecount = 0;
			while ((line = in.readLine()) != null) {
				String[] lineCols = line.split("\\|");
				String keyWord = lineCols[0];
				if (!trie.isKeyWord(keyWord.trim())) {
					System.out.println(keyWord);
					++ kecount;
				};
				
			}
			System.out.println(kecount);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
