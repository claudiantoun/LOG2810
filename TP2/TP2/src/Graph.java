import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Graph {

	public Graph() {
		
	}
	
	public void readFromFile(String filePath) throws IOException{

		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		try {
		    String line = bufferedReader.readLine();
		    List<Vector<String>> mots = new ArrayList<Vector<String>>();
		    while (line != null) {
		    	Vector<String> lettres = new Vector<String>(); 
		    	for(int i = 0; i < line.length(); i++) {
		    		String lettre = line.substring(i, i+1); 
		    		lettres.add(lettre);
		    	}	
		    	mots.add(lettres);
		    	line = bufferedReader.readLine();
		    }
		} 
		finally {
			bufferedReader.close();
		}
	}
}

