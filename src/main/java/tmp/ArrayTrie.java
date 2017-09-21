package tmp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ArrayTrie.java
 * 
 * @auther Boen
 * @date 2015年1月12日
 * @update 上午9:51:42
 */
public class ArrayTrie {
	
	int baseInit = 322;
	int pos = 0;

	int base[] = new int[1024];
	int check[] = new int[1024];
	
	Map<Character, Integer> seqMap = new HashMap<Character, Integer>();
	List<KeyWordDO> KeyWordDOList = new ArrayList<KeyWordDO>();
	
	int firstCharNum ;
	
	private int getCharSeq(char c ,Boolean isFist){
		Integer cseq = seqMap.get(c);
		if ( cseq == null) {
			if (isFist) {
				firstCharNum ++;
			}
			cseq = seqMap.size() + 1;
			seqMap.put(c, cseq);
		}
		return cseq.intValue();
	}
	
	private void extendArray(){
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
			System.out.println(_pos);
			if (_pos == 14) {
				System.out.println(_pos);
				
			}
			char firstChar = KeyWordDOList.get(_pos).firstWord;
			tmpList.add(KeyWordDOList.get(_pos));
			//int firstCharCode = getCharSeq(firstChar, false);
			int _pos_c1 = _pos + 1;

			//System.out.println(_pos +" --- " + firstChar);
		/*	if (_pos == 1060) {
				System.out.println(_pos +" --- " + firstChar);
				
			}*/
			while (_pos_c1 < KeyWordDOList.size()) {
				
				if (firstChar == KeyWordDOList.get(_pos_c1).firstWord) {
					tmpList.add(KeyWordDOList.get(_pos_c1));
					_pos_c1++;
				} else {
					break;
				}
			}
			
			_pos = _pos_c1 ;
		
			int baseValue = baseInit;

			// int oldCur = getCharSeq(fristChar, true);
			// 循环到当前所有的子节点都可找到新的
			// base[getCharSeq(fristChar, false)]= 1;
			
			boolean isMeet = true; // 是否符合指定的参数皆为空
			boolean isThisEnd = true;
			while (isThisEnd) {
				baseValue ++;
				isMeet = true;
				int[] tmpBase = Arrays.copyOf(base, base.length);
				int[] tmpCheck = Arrays.copyOf(check, check.length);
				
				//tmpBase[firstCharCode] = baseValue;
				//System.out.println(base[firstCharCode]);;
				Lable_1:for (int i = 0; i < tmpList.size(); i++) {
					KeyWordDO dotmp = tmpList.get(i);
					System.out.println(dotmp.getKeyWord());
					char keyWordChars[] = dotmp.getKeyWord().toCharArray();

					int preValueState = baseValue; //Pre State
					int tmpCurSub;  // Subscript
					int preSub = 0; //Pre state
					for (int j = 0; j < keyWordChars.length; j++) {
						char c = keyWordChars[j];
						int depth = j*1000;
						int curCharCode = getCharSeq(c, false);
						System.out.println(c + " == " + curCharCode);
						if (j == 0) {
							// preValueState = cur;
							tmpCurSub = curCharCode;
						}else {
							tmpCurSub = curCharCode + depth + preValueState;
						}
						if (tmpCurSub > tmpBase.length -1) {
							tmpBase = Arrays.copyOf(tmpBase, tmpBase.length*2);
							tmpCheck = Arrays.copyOf(tmpCheck, tmpCheck.length*2);
							extendArray();
						}
						
						//base[tmpCur] = cur;
						if (j == keyWordChars.length - 1 ) {
							if (tmpBase[tmpCurSub] == 0 ) {
								tmpBase[tmpCurSub] = -tmpCurSub;
								preValueState = tmpCurSub;
							}else if (tmpBase[tmpCurSub] != 0 && tmpCheck[tmpCurSub] == preSub) {
								isMeet = false;
								_error = 1;// 词语位置不一致/重复
								return _error;
							}else if (tmpBase[tmpCurSub] != 0 && tmpCheck[tmpCurSub] != preSub) {
								isMeet = false;
								break Lable_1;
							}
							
						}else {
							if (tmpBase[tmpCurSub] == 0) {
								tmpBase[tmpCurSub] = baseValue;//TODO 需要调整保持原始状态
								preValueState = baseValue;
							}else if (tmpBase[tmpCurSub] != 0 && tmpCheck[tmpCurSub] == preSub) {
								preValueState = Math.abs(tmpBase[tmpCurSub]);
							}else if (tmpBase[tmpCurSub] != 0 && tmpCheck[tmpCurSub] != preSub) {
								isMeet = false;
								break Lable_1;
							}

						}
						tmpCheck[tmpCurSub] = preSub;
						preSub = tmpCurSub;
					}
				}
				//break tmplabl_2;
				if (isMeet) {
					for (int i = 0; i < tmpList.size(); i++) {
						KeyWordDO dotmp = tmpList.get(i);
						char keyWordChars[] = dotmp.getKeyWord().toCharArray();

						int preValueState = baseValue; //Pre State
						int tmpCurSub;  // Subscript
						int preSub = 0; //Pre state
						for (int j = 0; j < keyWordChars.length; j++) {
							char c = keyWordChars[j];
							int depth = j*1000;
							int curCharCode = getCharSeq(c, false);
							if (j == 0) {
								// preValueState = cur;
								tmpCurSub = curCharCode;
							}else {
								tmpCurSub = curCharCode+depth + preValueState;
							}
							
							//base[tmpCur] = cur;
							if (j == keyWordChars.length - 1 ) {
								if (base[tmpCurSub] == 0 ) {
									base[tmpCurSub] = -tmpCurSub;
									preValueState = tmpCurSub;
								}else if (base[tmpCurSub] != 0 && check[tmpCurSub] == preSub) {
									_error = 1;// 词语位置不一致
									return _error;
								}else if (base[tmpCurSub] != 0 && check[tmpCurSub] != preSub) {
									_error = 1;// 词语位置不一致
									/*isMeet = false;*/
									return _error;
								}
								
							}else {
								if (base[tmpCurSub] == 0) {
									base[tmpCurSub] = baseValue;//TODO 需要调整保持原始状态
									preValueState = baseValue;
								}else if (base[tmpCurSub] != 0 && check[tmpCurSub] == preSub) {
									preValueState = Math.abs(base[tmpCurSub]);
								}else if (base[tmpCurSub] != 0 && check[tmpCurSub] != preSub) {
									//isMeet = false;
								}

							}
							check[tmpCurSub] = preSub;
							preSub = tmpCurSub;
						}
					}
					isThisEnd = false;
				}
				
			/*	tmplabl_1: for (int i = 0; i < tmpList.size(); i++) {
					KeyWordDO dotmp = tmpList.get(i);
					//System.out.println(dotmp.getKeyWord());
					char keyWordChars[] = dotmp.getKeyWord().toCharArray();

					int value = cur;
					int tmpCurSub = cur;
					for (int j = 0; j < keyWordChars.length; j++) {
						
						char c = keyWordChars[j];
						int curCharCode = getCharSeq(c, false);
						if (j == 0) {
							tmpCurSub = curCharCode + 1;
						}else {
							tmpCurSub = curCharCode + value;
						}
						if (tmpCurSub > base.length -1) {
							extendArray();
						}
						if (base[tmpCurSub] != 0) {
							isMeet = false;
							break tmplabl_1;
						}
						if (j == keyWordChars.length - 1 ) {
							//base[tmpCurSub] = -tmpCurSub;
							value = tmpCurSub;
						}else {
							value = cur;
							//base[tmpCurSub] = cur;
						}
						//System.out.println( ++count );
					}
				}*/
				
				/*if (isMeet) {
					base[firstCharCode] = cur;
					for (int i = 0; i < tmpList.size(); i++) {
						KeyWordDO dotmp = tmpList.get(i);
						char keyWordChars[] = dotmp.getKeyWord().toCharArray();

						int value = cur;
						int tmpCurSub = cur;
						int preSub = firstCharCode;
						for (int j = 1; j < keyWordChars.length; j++) {
							char c = keyWordChars[j];
							int curCharCode = getCharSeq(c, false);
							tmpCurSub = curCharCode + value;
							//base[tmpCur] = cur;
							if (j == keyWordChars.length - 1 ) {
								base[tmpCurSub] = -tmpCurSub;
								value = tmpCurSub;
							}else {
								base[tmpCurSub] = cur;
								value = cur;
							}
							check[tmpCurSub] = preSub;
							preSub = tmpCurSub;
							
						}
					}
					break tmplabl_2;
				}*/
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
				/*System.out.println(keyWord);
				System.out.println("base < 0 " + base[curSub]);
				System.out.println("check == 上一个，说明匹配" + check[curSub]);
				System.out.println(++match);*/
			}
			
			preSub = curSub;
			valueCur = Math.abs(base[curSub]);
		}
		return false;
	}
	
	public void loadKeyWord() throws IOException{
		List<String> keyWordList = new ArrayList<String>();
		/*URL dic = ArrayTrie.class.getResource("keyword.txt");*/
		BufferedReader in = new BufferedReader(new FileReader("D:\\workspace\\server\\Test\\bin\\keyword.txt"));
		String line = null;
		while ((line = in.readLine()) != null) {
			//String keyWord = line.substring(0, line.length() - 2);
			String[] lineCols = line.split("\\|");
			String keyWord = lineCols[0];
			//System.out.println(keyWord);
			keyWordList.add(keyWord);
		}
		
		for (String keyWord : keyWordList) {
			initCharSeq(keyWord);
		}
		
		System.out.println("首字数量" + firstCharNum);
		System.out.println("总字数" + seqMap.size());
		
		tried();
		
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
		ArrayTrie trie = new ArrayTrie();
		try {
			trie.loadKeyWord();
			/*trie.isKeyWord("卧槽");
			trie.isKeyWord("安眠药");
			trie.isKeyWord("宝在甘肃修");*/
			System.out.println(trie.isKeyWord("阿扁推翻"));
			System.out.println(trie.isKeyWord("安街逆"));
			System.out.println(trie.isKeyWord("安街逆"));
			System.out.println(trie.isKeyWord("安街逆"));
			System.out.println(trie.isKeyWord("安街逆"));
			
			BufferedReader in = new BufferedReader(new FileReader("D:\\workspace\\server\\Test\\bin\\keyword.txt"));
			String line = null;
			int kecount = 0;
			while ((line = in.readLine()) != null) {
				//String keyWord = line.substring(0, line.length() - 2);
				String[] lineCols = line.split("\\|");
				String keyWord = lineCols[0];
				if (!trie.isKeyWord(keyWord)) {
					System.out.println(keyWord);
				};
				++ kecount;
			}
			System.out.println(kecount);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
