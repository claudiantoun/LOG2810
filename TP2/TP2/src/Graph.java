import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Graph {

	public Graph() {
		
	}
	
	public void readFromFile(String filePath) throws IOException{

		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		try {
		    String line = bufferedReader.readLine();

		    while (line != null) {
		    	//ajout des mots dans un conteneur
		        line = bufferedReader.readLine();
		    }
		} 
		finally {
			bufferedReader.close();
		}
	}
}

