import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Graph graph = new Graph();
		graph.readFromFile("./src/lexique1.txt");
		//TODO : interface avec le traitement des inputs du user
		//TODO : chaque fois qu'un mot est selectionn�,
		//		 incr�menter nbTimeUsed du mot et mettre RecentlyUsed � true
		//		 ajouter ce mot � la queue de graph et v�rifier si la taille
		//		 de la queue ne d�passe pas 5 dans un tel cas l'�l�ment le moins
		//		 r�cent sera enlev� de la queue et RecentlyUsed du mot correspondant
		//		 sera remis � false
	}	
}
