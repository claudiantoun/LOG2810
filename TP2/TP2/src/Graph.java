import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Vector;

public class Graph {

	private Node root;
	private Queue<String> fiveRecentlyUsed;
	
	public Graph() {
		root = new Node("");
		fiveRecentlyUsed = new ArrayDeque<String>();
	}
	
	public void readFromFile(String filePath) throws IOException{

		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		try {
		    String line = bufferedReader.readLine();
		    while (line != null) {
		    	Node currentNode = root;
		    	for(int i = 0; i < line.length(); i++) {
		    		String lettre = line.substring(i, i+1); 
		    		currentNode = currentNode.addNode(lettre, line);
		    	}
		    	currentNode.setMot(new Mot(line));
		    	line = bufferedReader.readLine();
		    }
		} 
		finally {
			bufferedReader.close();
		}
	}
	
	public Vector<Mot> displayWords(String input) {
		Node currentNode = root;
		Vector<Mot> words = new Vector<Mot>();
		for(int i = 0; i < input.length(); i++) {
    		String lettre = input.substring(i, i+1);
    		if(currentNode.getNextNode(lettre, input) == null)
    			return null;
    		currentNode = currentNode.getNextNode(lettre, input);
    	}
		currentNode.displayEachWord(words);
		return words;
	}
	
	public void addToQueue(String input) {
		if(root.findWord(input, root) == null)
			return;
		Mot motChoisi = root.findWord(input, root);
    	if(fiveRecentlyUsed.contains(input)) {
    		int size = fiveRecentlyUsed.size();
    		for(int i = 0; i<size; i++) {
    			if(!(fiveRecentlyUsed.peek().equals(input))) {
    				fiveRecentlyUsed.add(fiveRecentlyUsed.peek());
    			}
    			fiveRecentlyUsed.poll();
    		}
    	}
    	fiveRecentlyUsed.add(input);
    	motChoisi.incrementNbTimeUsed();
    	motChoisi.setRecentlyUsed(true);
		if(fiveRecentlyUsed.size() > 5) {
			root.findWord(fiveRecentlyUsed.peek(), root).setRecentlyUsed(false);		
			fiveRecentlyUsed.poll();
		}
	}
}

