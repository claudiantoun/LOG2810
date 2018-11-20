import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Graph graph = new Graph();
		graph.readFromFile("./src/lexique1.txt");
		//TODO : interface avec le traitement des inputs du user
		//TODO : chaque fois qu'un mot est selectionné,
		//		 incrémenter nbTimeUsed du mot et mettre RecentlyUsed à true
		//		 ajouter ce mot à la queue de graph et vérifier si la taille
		//		 de la queue ne dépasse pas 5 dans un tel cas l'élément le moins
		//		 récent sera enlevé de la queue et RecentlyUsed du mot correspondant
		//		 sera remis à false
	}	
}
