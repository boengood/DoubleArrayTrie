# DoubleArrayTrie
双数组字典树的实现，可用于敏感词过滤，关键字提取等。


词语查询的时间复杂度为 O(n)


本双数组实现字符串查找的基本算法是


next_sub = base[cur_sub] + next_var_seq


check[cur_sub] = pre_sub


主要是字符之间的状态传递实现


使用方法\\
KeyWordResult result = KeyWordMatch.matchKeyWord("代理办证前途无量");\\


if (result.isContain) {


	for (String keyword : result.getKeywords()) {
	
	
		System.out.println(keyword);
		
		
	}
	
	
}



首字数量383     ------------  初始加载时


总字数1278      ------------  初始加载时


8192            ------------  初始加载时


8192            ------------  初始加载时


敏感词:办证     ------------  文本敏感词



未完成功能：


1 词级别控制


新增：
	1 新增替换文本接口，如关键字是 关键字  ， 文本为 某某关键字的应用 ，通过[]符号替换后为  某某[关键字]的应用


	
