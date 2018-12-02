import java.util.Vector;

public class Node 
{
	private Vector<Node> children_;
	private Mot mot_;
	private String identification_;
	
	public Node(String identification) 
	{
		children_ = new Vector<Node>();
		mot_ = null;
		identification_ = identification;
	}
	public Vector<Node> getChildren()
	{
		return children_;
	}
	public String getIdentification() 
	{
		return identification_;
	}
	
	public Mot getMot() 
	{
		return mot_;
	}
	
	public void setMot(Mot mot) 
	{
		mot_ = mot;
	}
	
	//ajoute le mot au noeud correspondant
	//si le noeud n'existe pas, l'ajoute à un nouveau noeud
	public Node addNode(String lettre, String mot) 
	{
		String newIdentification = identification_ + lettre;
		for(int i = 0; i < children_.size(); i++) 
		{
			if(children_.get(i).getIdentification().equals(newIdentification)) 
			{
				return children_.get(i);
			}
		}
		children_.add(new Node(newIdentification));
		return children_.get(children_.size() - 1);
	}
	
	//Se rend vers le prochain noeud s'il existe
	public Node getNextNode(String lettre, String mot) 
	{
		String newIdentification = identification_ + lettre;
		for(int i = 0; i < children_.size(); i++) 
		{
			if(children_.get(i).getIdentification().equals(newIdentification)) 
			{
				return children_.get(i);
			}
		}
		return null;
	}
	
	//ajoute au vecteur donné en paramètre tous les mots
	//qui sont dans le noeud présent ou sous celui-ci
	public void displayEachWord(Vector<Mot> words) 
	{
		if(mot_ != null) 
		{
			words.add(mot_);
		}
		for(int i = 0; i < children_.size(); i++) 
		{
    		children_.get(i).displayEachWord(words);
    	}
	}
	
	//trouve le mot passé en paramètre à partir du noeud passé en paramètre
	public Mot findWord(String mot, Node currentNode)
	{
		for(int i = 0; i < mot.length(); i++) 
		{
			String identification = mot.substring(0, i+1);
			for(int j = 0; j < currentNode.children_.size(); j++) 
			{
				if(currentNode.children_.get(j).getIdentification().equals(identification)) 
				{
					currentNode = currentNode.children_.get(j);
				}
			}
		}
		return currentNode.getMot();
	}
}
