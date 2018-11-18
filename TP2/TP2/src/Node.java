import java.util.Vector;

public class Node {
	private Vector<Node> children_;
	private Vector<String> mots_;
	private String identification_;
	
	public Node(String identification) {
		children_ = new Vector<Node>();
		mots_ = new Vector<String>();
		identification_ = identification;
		
	}
	
	public Node(String identification, String mot) {
		children_ = new Vector<Node>();
		mots_ = new Vector<String>();
		mots_.add(mot);
		identification_ = identification;
		
	}
	
	public String getIdentification() {
		return identification_;
	}
	
	public void addWord(String mot) {
		mots_.addElement(mot);
	}
	
	public Node getNextNode(String lettre, String mot) {
		String newIdentification = identification_+lettre;
		for(int i = 0; i<children_.size(); i++) {
			if(children_.get(i).getIdentification().equals(newIdentification)) {
				children_.get(i).addWord(mot);
				return children_.get(i);
			}
		}
		children_.add(new Node(newIdentification, mot));
		return children_.get(children_.size()-1);
	}
}
