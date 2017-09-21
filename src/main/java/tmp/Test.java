package tmp;

import com.boen.keywordfilter.KeyWordMatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {

	public static void main(String[] args) {
		
		try {
            KeyWordMatch.getInstance();
			BufferedReader reader = new BufferedReader(new InputStreamReader(Test.class.getResourceAsStream("/keyword.txt")));
            //BufferedReader in2 = new BufferedReader(new InputStreamReader(Test.class.getResourceAsStream("/keyword2.txt")));
           // BufferedReader in3 = new BufferedReader(new InputStreamReader(Test.class.getResourceAsStream("/keyword3.txt")));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] lineCols = line.split(";");
                if (KeyWordMatch.iskeyWord(lineCols[0]) != true){
                    System.err.println(lineCols[0]);
                }
			}
           /* while ((line = in2.readLine()) != null) {
                String[] lineCols = line    .split(";");
                for (String keyWord : lineCols) {
                    if (keyWord.length() > 1){
                        if (KeyWordMatch.iskeyWord(keyWord) != true){
                            System.err.println(lineCols[0]);
                        }
                    }

                }
            }
            line = null;
            while ((line = in3.readLine()) != null) {
                String[] lineCols = line.split("=");
                String keyWord = lineCols[0];
                if (KeyWordMatch.iskeyWord(keyWord) != true){
                    System.err.println(lineCols[0]);
                }
            }*/
            System.out.println(KeyWordMatch.iskeyWord("好人刘"));
            reader.close();
           // in2.close();
            //in3.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
