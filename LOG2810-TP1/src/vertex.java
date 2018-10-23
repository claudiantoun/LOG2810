import java.io.BufferedReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class vertex 
{ 	
	protected int id_;
	
	protected boolean hasRechargeStation_;
	
	protected static int[][] links;
	
	public vertex(int id, boolean hasRechargeStation) 
	{
		id_ = id;
		hasRechargeStation_ = hasRechargeStation;
	}
	
	//Méthode pour lire récursivement chaque ligne du fichier et traiter l'info
	public static void readVertex(BufferedReader br, ArrayList<vertex> vertex) throws IOException 
	{   
		String line;
		
		while ((line = br.readLine()) != null) 
		{
			String[] separated = line.split("\\,");
			if(separated.length == 2) 
			{
				addVertexElement(vertex, separated);
			}
			else if(separated.length == 3) 
			{
				links = new int[vertex.size()][vertex.size()];
				readLinkElements(br, links, line);
			}
			readVertex(br, vertex);
		}
	}
	
	public static void addVertexElement(ArrayList<vertex> vertex, String[] separated)
	{
		vertex.add(new vertex(Integer.parseInt(separated[0]), Boolean.parseBoolean(separated[1])));
	}
	
	public static void readLinkElements(BufferedReader br, int[][] links, String line) throws IOException 
	{
		while (line != null) 
		{
			String[] separated = line.split("\\,");

			links[Integer.parseInt(separated[0])-1][Integer.parseInt(separated[1])-1] = Integer.parseInt(separated[2]);
			links[Integer.parseInt(separated[1])-1][Integer.parseInt(separated[0])-1] = Integer.parseInt(separated[2]);
		
			line = br.readLine();
			readLinkElements(br, links, line);
		}
	}
	
	public static void readGraph(int userInputReadGraph, int[][] links, ArrayList<vertex> vertex)
	{
		System.out.print("(Sommet "+userInputReadGraph+",(");
		for(int j = 0; j < vertex.size(); j++)
		{
			if(links[userInputReadGraph-1][j] != 0)
			{
				int time = links[userInputReadGraph-1][j];
				int neighbor = j+1;
				System.out.print("("+neighbor+","+time+"),");
			}
		}
		System.out.print(")"+"\n");
	}
	
	public static void makeAnotherChoice()
	{
		Scanner scanAnswer = new Scanner(System.in);
		String userAnswerInput = scanAnswer.nextLine();
		
		if(userAnswerInput.equalsIgnoreCase("oui"))
		{
			System.out.println("------------------------------MENU------------------------------");
			System.out.println("|   1-Mettre à jour la carte                                   |");
			System.out.println("|   2-Déterminer le plus court chemin sécuritaire              |");
			System.out.println("|   3-Extraire un sous-graphe                                  |");
			System.out.println("|   4-Quitter                                                  |");
			System.out.println("----------------------------------------------------------------");
			System.out.println("Saisissez à nouveau un numéro du menu que vous voulez exécuter:");
		}
		else if(userAnswerInput.equalsIgnoreCase("non"))
		{
			System.out.println("\n"+"Vous avez quitté le programme!");
			System.exit(0);
		}
		else
		{
			System.out.println("\n"+"Option invalide! Veuillez saisir oui ou non!");
			makeAnotherChoice();
		}
	}
	
	public static void displayGraph(ArrayList<vertex> vertex)
	{
		Scanner scanReadGraph = new Scanner(System.in);
		int userInputReadGraph = scanReadGraph.nextInt();
		if(userInputReadGraph > vertex.size() || userInputReadGraph < 0) 
		{
			System.out.println("\n"+"Option invalide! Veuillez saisir une option entre 1 et 29!");
			displayGraph(vertex);
		}
		readGraph(userInputReadGraph, links, vertex);
	}
	
	public static void displayMenu(ArrayList<vertex> vertex)
	{
		System.out.println("------------------------------MENU------------------------------");
		System.out.println("|   1-Mettre à jour la carte                                   |");
		System.out.println("|   2-Déterminer le plus court chemin sécuritaire              |");
		System.out.println("|   3-Extraire un sous-graphe                                  |");
		System.out.println("|   4-Quitter                                                  |");
		System.out.println("----------------------------------------------------------------");
		System.out.println("Saisissez le numéro du menu que vous voulez exécuter:       ");
		
		boolean continueLoop = true;
		while(continueLoop) 
		{
			Scanner scan = new Scanner(System.in);
			String userInput = scan.nextLine();
			switch(userInput)
			{
				case "1":	
					System.out.println("Mettre à jour la carte");
					System.out.println("\n"+"Voulez-vous saisir une nouvelle option? (oui/non)");
					makeAnotherChoice();
					break;
				case "2": 
					System.out.println("Déterminer le plus court chemin sécuritaire");
					System.out.println("\n"+"Voulez-vous saisir une nouvelle option? (oui/non)");
					makeAnotherChoice();
					break;
				case "3": 
					System.out.println("\n"+"Entrer le sommet pour lequel vous voulez afficher le graphe (1 à 29):");
					displayGraph(vertex);
					System.out.println("\n"+"Voulez-vous saisir une nouvelle option? (oui/non)");
					makeAnotherChoice();
					break;
				case "4": 
					continueLoop = false;
					System.out.println("\n"+"Vous avez quitté le programme!");
					System.exit(0);
					break;
				default:
					System.out.println("\n"+"Option invalide! Veuillez saisir une option entre 1 et 4!");
		           	break;
			}
		}
	}
} 
