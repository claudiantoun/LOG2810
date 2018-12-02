import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Vector;

public class Graph 
{
	private Vector<Mot> lexicon;
	private Node root;
	private Queue<String> fiveRecentlyUsed;
	
	//constructeur de l'arbre des mots (initialisation)
	public Graph() 
	{
		root = new Node("");
		fiveRecentlyUsed = new ArrayDeque<String>();
		lexicon = new Vector<Mot>();
	}
	
	public Vector<Mot> getLexiconWords()
	{
		return lexicon;
	}
	
	public Queue<String> getFiveRecentlyUsed()
	{
		return fiveRecentlyUsed;
	}
	
	//lit le lexique dont le nom est passé en paramètre 
	//et construit l'arbre à partir de celui-ci
	public void readFromFile(String filePath) throws IOException
	{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		try 
		{
		    String line = bufferedReader.readLine();
		    while (line != null) 
		    {
		    	Node currentNode = root;
		    	for(int i = 0; i < line.length(); i++) 
		    	{
		    		String lettre = line.substring(i, i+1); 
		    		currentNode = currentNode.addNode(lettre, line);
		    	}
		    	currentNode.setMot(new Mot(line));
		    	lexicon.add(new Mot(line));
		    	line = bufferedReader.readLine();
		    }
		} 
		finally 
		{
			bufferedReader.close();
		}
	}
	
	//Se rend jusqu'au noeud dont l'identifiant est égal au input
	//et affiche tous les mots des noeuds sous ce noeud
	public Vector<Mot> displayWords(String input) 
	{
		Node currentNode = root;
		Vector<Mot> words = new Vector<Mot>();
		for(int i = 0; i < input.length(); i++) 
		{
    		String lettre = input.substring(i, i+1);
    		if(currentNode.getNextNode(lettre, input) == null)
    		{
    			return null;
    		}
    		currentNode = currentNode.getNextNode(lettre, input);
    	}
		currentNode.displayEachWord(words);
		return words;
	}
	
	/*Lorsqu'un mot est sélectionné, l'ajoute à la queue des mots 
	 *récemment utilisés et modifie les attributs de ce mot
	 *gère aussi la queue pour ne pas que sa taille dépasse 5 
	 *et que les mots n'y soit pas répétés
	 */
	public void addToQueue(String input) 
	{
		if(root.findWord(input, root) == null)
		{
			return;
		}
		
    	if(fiveRecentlyUsed.contains(input)) 
    	{
    		int size = fiveRecentlyUsed.size();
    		for(int i = 0; i<size; i++) 
    		{
    			if(!(fiveRecentlyUsed.peek().equals(input))) 
    			{
    				fiveRecentlyUsed.add(fiveRecentlyUsed.peek());
    			}
    			fiveRecentlyUsed.poll();
    		}
    	}
    	fiveRecentlyUsed.add(input);
    	Mot motChoisi = root.findWord(input, root);
    	for (int j = 0; j < lexicon.size(); j++)
    	{
    		if (lexicon.get(j).getNom() == motChoisi.getNom())
    		{
    			lexicon.get(j).incrementNbTimeUsed();
    			lexicon.get(j).setRecentlyUsed(true);
    		}
    	}
    	
    	if(fiveRecentlyUsed.size() > 5) 
		{
    		Mot leastRecentlyUsedWord = root.findWord(fiveRecentlyUsed.peek(), root);
    		
    		for (int j = 0; j < lexicon.size(); j++)
        	{
        		if (lexicon.get(j).getNom() == leastRecentlyUsedWord.getNom())
        		{
        			lexicon.get(j).setRecentlyUsed(false);
        		}
        	}
    		
			fiveRecentlyUsed.poll();
		}
	}
}

