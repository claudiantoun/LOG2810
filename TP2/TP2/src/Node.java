import java.util.Vector;

public class Node {
	private Vector<Node> children_;
	private Mot mot_;
	private String identification_;
	
	public Node(String identification) {
		children_ = new Vector<Node>();
		mot_ = null;
		identification_ = identification;
		
	}
	
	public String getIdentification() {
		return identification_;
	}
	
	public Mot getMot() {
		return mot_;
	}
	
	public void setMot(Mot mot) {
		mot_ = mot;
	}
	
	public Node getNextNode(String lettre, String mot) {
		String newIdentification = identification_+lettre;
		for(int i = 0; i<children_.size(); i++) {
			if(children_.get(i).getIdentification().equals(newIdentification)) {
				return children_.get(i);
			}
		}
		children_.add(new Node(newIdentification));
		return children_.get(children_.size()-1);
	}
	
	public void displayEachWord() {
		if(mot_ != null) {
			//TODO : affichage du mot
		}
		for(int i = 0; i < children_.size(); i++) {
    		children_.get(i).displayEachWord();
    	}
	}
}
