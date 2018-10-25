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
	
	public boolean getHasRechargeStation() {
		return hasRechargeStation_;
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
	public static void plusCourtChemin(int startIndex, int endIndex, ArrayList<vertex> vertex, int[][] links, String vehicleType, String transportationRisk)
	{
		ArrayList<vertexPath> vertexPathways = new ArrayList<vertexPath>();
		Vehicle vehicle = new Vehicle(vehicleType, transportationRisk, 100.0);
		Double recharge = 0.0;
		if(vehicle.getVehicleType() == "NI-NH") 
		{
			switch(transportationRisk) 
			{
			case "faible":	
				vehicle.setDurability(6.0);
				break;
			case "moyen":
				vehicle.setDurability(12.0);
				break;
			case "élevé": 
				vehicle.setDurability(48.0);
				break;
			default:
	           	break;
			}
		}
		else
		{
			switch(transportationRisk) 
			{
			case "faible":	
				vehicle.setDurability(5.0);
				break;
			case "moyen":
				vehicle.setDurability(10.0);
				break;
			case "élevé": 
				vehicle.setDurability(30.0);
				break;
			default:
	           	break;
			}
		}
		
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
		vertexPathways.get(startIndex - 1).setTotalTime(0);
		while(vertexPathways.get(endIndex - 1).getVisited() != true) 
		{
			int shortestWay = 0;
			int minTime = Integer.MAX_VALUE;
			
			for(int i = 0; i < vertexPathways.size(); i++) 
			{
				if(vertexPathways.get(i).getTotalTime() < minTime && vertexPathways.get(i).getVisited() != true) 
				{
					minTime = vertexPathways.get(i).getTotalTime();
					shortestWay = vertexPathways.get(i).getId();
				}
			}
			Double battery = (((1-(vertexPathways.get(shortestWay - 1).getTotalTime()/vehicle.getDurability()))*100.0)+recharge);
			vehicle.setBatteryPercentage(battery);
			for(int j = 0; j < vertexPathways.size(); j++) 
			{
				if(links[shortestWay - 1][j] != 0 && vertexPathways.get(j).getVisited() != true && vertexPathways.get(j).getTotalTime() > links[shortestWay - 1][j] + minTime) {
					vertexPathways.get(j).setTotalTime(links[shortestWay - 1][j] + minTime); //check get(j - 1) if does not return right array
					vertexPathways.get(j).setActualPath(vertexPathways.get(shortestWay - 1).getActualPath()+","+Integer.toString(j + 1));
				}
			}
			if(vehicle.getBatteryPercentage() < 20) {
				String[] separated = vertexPathways.get(shortestWay - 1).getActualPath().split("\\,");
				for(int i = separated.length - 2; i > 0; i--) 
				{
					if(vertex.get(Integer.parseInt(separated[i])-1).getHasRechargeStation() == true) 
					{
						plusCourtChemin(startIndex, Integer.parseInt(separated[i]), vertex, links, vehicleType, transportationRisk);
						plusCourtChemin(Integer.parseInt(separated[i]), endIndex, vertex, links, vehicleType, transportationRisk);
						recharge = (vertexPathways.get(Integer.parseInt(separated[i])-1).getTotalTime()/vehicle.getDurability())*100;
					}
				}
			}
			vertexPathways.get(shortestWay - 1).setVisited(true);
		}
		System.out.print(vertexPathways.get(endIndex - 1).getActualPath()+"\n");
		System.out.print(vertexPathways.get(endIndex - 1).getTotalTime()+"\n");

		System.out.print(Math.floor(vehicle.getBatteryPercentage())+"\n");
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
	// précisement lors de la saisie de l'index de fin. De plus, elle appelle la méthode vehicleChoice.
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
			vehicleChoice(userInputStartIndex, userInputEndIndex, vertex, links);
		}
	}
	
	// Cette méthode permet de gérer les erreurs entrées par l'usager lors de l'option 2 du menu, plus
	// précisement lors de la saisie du choix du véhicule. De plus, elle appelle la méthode riskChoice.
	public static void vehicleChoice(int userInputStartIndex, int userInputEndIndex, ArrayList<vertex> vertex, int[][] links)
	{
		System.out.println("\n"+"Veuillez saisir le type du véhicule désiré (NI-NH ou LI-ion):");
		Scanner scanAnswer = new Scanner(System.in);
		String userInputVehicleType = scanAnswer.nextLine();
		
		if(userInputVehicleType.equalsIgnoreCase("NI-NH") || userInputVehicleType.equalsIgnoreCase("LI-ion") || userInputVehicleType.equalsIgnoreCase("li-ion") || userInputVehicleType.equalsIgnoreCase("ni-nh"))
		{
			riskChoice(userInputStartIndex, userInputEndIndex, userInputVehicleType, vertex, links);
		}
		else
		{
			System.out.println("\n"+"Option invalide! Veuillez saisir NI-NH ou LI-ion!");
			vehicleChoice(userInputStartIndex, userInputEndIndex, vertex, links);
		}
	}
	
	// Cette méthode permet de gérer les erreurs entrées par l'usager lors de l'option 2 du menu, plus
	// précisement lors de la saisie du facteur de risque. De plus, elle appelle la méthode plusCourtChemin.
	public static void riskChoice(int userInputStartIndex, int userInputEndIndex, String userInputVehicleType, ArrayList<vertex> vertex, int[][] links)
	{
		System.out.println("\n"+"Veuillez saisir le risque de transportation (faible, moyen ou élevé):");
		Scanner scanAnswer = new Scanner(System.in);
		String userInputTransportationRisk = scanAnswer.nextLine();
		
		if(userInputTransportationRisk.equalsIgnoreCase("faible") || userInputTransportationRisk.equalsIgnoreCase("moyen") || userInputTransportationRisk.equalsIgnoreCase("élevé") || userInputTransportationRisk.equalsIgnoreCase("eleve"))
		{
			plusCourtChemin(userInputStartIndex, userInputEndIndex, vertex, links, userInputVehicleType, userInputTransportationRisk);
		}
		else
		{
			System.out.println("\n"+"Option invalide! Veuillez saisir faible, moyen ou élevé!");
			riskChoice(userInputStartIndex, userInputEndIndex, userInputVehicleType, vertex, links);
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
