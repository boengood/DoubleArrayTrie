package tmp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.boen.keywordfilter.DoubleArrayTrieStatic;

public class Test {

	public static void main(String[] args) {
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(DoubleArrayTrieStatic.class.getResourceAsStream("/keyword2.txt")));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] lineCols = line.split(";");
				System.out.println(lineCols.length);
			}
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
