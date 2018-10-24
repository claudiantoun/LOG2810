import java.io.BufferedReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class vertex 
{ 	
	protected int id_;
	
	protected boolean hasRechargeStation_;
	
	protected static int[][] links;
	
	// Constructeur par paramètres.
	public vertex(int id, boolean hasRechargeStation) 
	{
		id_ = id;
		hasRechargeStation_ = hasRechargeStation;
	}
	
	// Cette méthode permet de lire récursivement chaque ligne du fichier et traiter l'information.
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
	
	// BLABLALBA
	public static void addVertexElement(ArrayList<vertex> vertex, String[] separated)
	{
		vertex.add(new vertex(Integer.parseInt(separated[0]), Boolean.parseBoolean(separated[1])));
	}
	
	// BLABLABLA
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
	
	// Cette méthode permet d'afficher le contenu de l'option 1 du menu.
	public static void lireGraphe(int[][] links, ArrayList<vertex> vertex)
	{
		for(int i = 0; i < vertex.size(); i++)
		{
			String result = "";
			int sommet = i+1;
			System.out.print("(Sommet "+sommet+",(");
			for(int j = 0; j < vertex.size(); j++)
			{
				if(links[i][j] != 0)
				{
					int time = links[i][j];
					int neighbor = j+1;
					result += "("+neighbor+","+time+"),";
				}
			}
			System.out.print(result.substring(0, result.length() - 1)+")"+"\n");
		}
	}
	
	// Cette méthode permet d'afficher le contenu de l'option 2 du menu.
	public static void plusCourtChemin(int startIndex, int endIndex, ArrayList<vertex> vertex, int[][] links)
	{
		
		ArrayList<vertexPath> vertexPathways = new ArrayList<vertexPath>();
		for(int i = 1; i <= vertex.size(); i++)
		{
			vertexPathways.add(new vertexPath(i, Integer.MAX_VALUE, Integer.toString(i), false));
		}
		for (int j = 0; j < vertexPathways.size(); j++)
		{
			if(links[startIndex - 1][j] != 0) 
			{
				vertexPathways.get(j).setTotalTime(links[startIndex - 1][j]); //check get(j - 1) if does not return right array
				vertexPathways.get(j).setActualPath(Integer.toString(startIndex)+","+Integer.toString(j + 1));
			}
		}
		vertexPathways.get(startIndex - 1).setVisited(true);
		
		while(vertexPathways.get(endIndex - 1).getVisited() != true) 
		{
			int shortestWay = -1;
			int minTime = Integer.MAX_VALUE;
			
			for(int i = 0; i < vertexPathways.size(); i++) 
			{
				if(vertexPathways.get(i).getTotalTime() < minTime && vertexPathways.get(i).getVisited() != true) 
				{
					minTime = vertexPathways.get(i).getTotalTime();
					shortestWay = vertexPathways.get(i).getId();
				}
			}
			for(int j = 0; j < vertexPathways.size(); j++) 
			{
				if(links[shortestWay - 1][j] != 0 && vertexPathways.get(j).getVisited() != true) {
					vertexPathways.get(j).setTotalTime(links[shortestWay - 1][j] + minTime); //check get(j - 1) if does not return right array
					vertexPathways.get(j).setActualPath(vertexPathways.get(shortestWay - 1).getActualPath()+","+Integer.toString(j + 1));
				}
			}
			vertexPathways.get(shortestWay - 1).setVisited(true);
			System.out.print(vertexPathways.get(shortestWay - 1).getActualPath()+"\n");
		}
	}
	
	// Cette méthode permet de gérer la réponse de l'usager lorsqu'il lui est demandé s'il veut saisir
	// une nouvelle option ou non.
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
	
	// Cette méthode permet de gérer les erreurs entrées par l'usager lors de l'option 2 du menu, plus
	// précisement lors de la saisie de l'index de départ. De plus, elle appelle la méthode displayEndIndex.
	public static void displayStartIndex(ArrayList<vertex> vertex, int[][] links)
	{
		System.out.println("\n"+"Veuillez saisir l'index de départ:");
		Scanner scanStartIndex = new Scanner(System.in);
		int userInputStartIndex = scanStartIndex.nextInt();
		
		if(userInputStartIndex > vertex.size() || userInputStartIndex < 0) 
		{
			System.out.println("\n"+"Option invalide! Veuillez saisir une option entre 1 et 29!");
			displayStartIndex(vertex, links);
		}
		else
		{
			displayEndIndex(userInputStartIndex, vertex, links);
		}
	}
	
	// Cette méthode permet de gérer les erreurs entrées par l'usager lors de l'option 2 du menu, plus
	// précisement lors de la saisie de l'index de fin. De plus, elle appelle la méthode plusCourtChemin.
	public static void displayEndIndex(int userInputStartIndex, ArrayList<vertex> vertex, int[][] links)
	{
		System.out.println("\n"+"Veuillez maintenant saisir l'index de fin:");
		Scanner scanEndIndex = new Scanner(System.in);
		int userInputEndIndex = scanEndIndex.nextInt();
		
		if(userInputEndIndex > vertex.size() || userInputEndIndex < 0) 
		{
			System.out.println("\n"+"Option invalide! Veuillez saisir une option entre 1 et 29!");
			displayEndIndex(userInputStartIndex, vertex, links);
		}
		else
		{
			plusCourtChemin(userInputStartIndex, userInputEndIndex, vertex, links);
		}
	}
	
	// Cette méthode affiche l'interface.
	public static void displayMenu(ArrayList<vertex> vertex)
	{
		System.out.println("------------------------------MENU------------------------------");
		System.out.println("|   1-Mettre à jour la carte                                   |");
		System.out.println("|   2-Déterminer le plus court chemin sécuritaire              |");
		System.out.println("|   3-Extraire un sous-graphe                                  |");
		System.out.println("|   4-Quitter                                                  |");
		System.out.println("----------------------------------------------------------------");
		System.out.println("Saisissez le numéro du menu que vous voulez exécuter:");
		
		boolean continueLoop = true;
		while(continueLoop) 
		{
			Scanner scan = new Scanner(System.in);
			String userInput = scan.nextLine();
			switch(userInput)
			{
				case "1":	
					lireGraphe(links, vertex);
					System.out.println("\n"+"Voulez-vous saisir une nouvelle option? (oui/non)");
					makeAnotherChoice();
					break;
				case "2":
					displayStartIndex(vertex, links);
					System.out.println("\n"+"Voulez-vous saisir une nouvelle option? (oui/non)");
					makeAnotherChoice();
					break;
				case "3": 
					System.out.println("Extraire un sous-graphe.");
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
