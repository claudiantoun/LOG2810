import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args) throws IOException 
	{	
		System.out.println("Veuillez entrer le chemin complet du fichier à lire avec son extension");
		System.out.println("(./src/lexique6.txt, par exemple):");

		Scanner scan = new Scanner(System.in);
		String userInputFileName = scan.nextLine();
		
		try
		{
			Graph graph = new Graph();
			graph.readFromFile(userInputFileName);
			Interface frameInterface = new Interface(graph);
		}
		catch (FileNotFoundException exception)
		{
			System.out.println("\n" + "Le chemin entré n'est pas valide!" + "\n");
			main(args);
		}
		
		System.out.println("L'interface s'affichera sous peu!");
	}	
}
