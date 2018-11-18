import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Graph {

	private Node root;
	
	public Graph() {
		root = new Node("");
	}
	
	public void readFromFile(String filePath) throws IOException{

		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		try {
		    String line = bufferedReader.readLine();
		    while (line != null) {
		    	Node currentNode = root;
		    	for(int i = 0; i < line.length(); i++) {
		    		String lettre = line.substring(i, i+1); 
		    		currentNode = currentNode.getNextNode(lettre, line);
		    	}	
		    	line = bufferedReader.readLine();
		    }
		} 
		finally {
			bufferedReader.close();
		}
	}
}

