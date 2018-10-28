import java.io.BufferedReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class vertex 
{ 	
	protected int id_;
	
	protected int hasRechargeStation_;
	
	protected static int[][] links;
	
	// Constructeur par paramètres.
	public vertex(int id, int hasRechargeStation) 
	{
		id_ = id;
		hasRechargeStation_ = hasRechargeStation;
	}
	
	// Cette méthode retourne un bool hasRechargeStation.
	public int getHasRechargeStation() 
	{
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
				vertex.add(new vertex(Integer.parseInt(separated[0]), Integer.parseInt(separated[1])));
			}
			else if(separated.length == 3) 
			{
				links = new int[vertex.size()][vertex.size()];
				readLinkElements(br, links, line);
			}
			readVertex(br, vertex);
		}
	}
	
	// Cette méthode permet de séparer le contenu lu dans chaque ligne du fichier en items individuels.
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
		System.out.print("\n");
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
	public static void plusCourtChemin(int startIndex, int endIndex, ArrayList<vertex> vertex, int[][] links, String vehicleType, String transportationRisk, Double recharge, int tempsRecharge, int goToRechargeIndex)
	{
		ArrayList<vertexPath> vertexPathways = new ArrayList<vertexPath>();
		Vehicle vehicle = new Vehicle(vehicleType, transportationRisk, 100.0);
		if(vehicleType == "NI-NH") 
		{
			switch(transportationRisk) 
			{
			case "a":	
				vehicle.setDurability(6.0);
				break;
			case "b":
				vehicle.setDurability(12.0);
				break;
			case "c": 
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
			case "a":	
				vehicle.setDurability(5.0);
				break;
			case "b":
				vehicle.setDurability(10.0);
				break;
			case "c": 
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
				vertexPathways.get(j).setTotalTime(links[startIndex - 1][j]);
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
					if(j == goToRechargeIndex)
						vertexPathways.get(j).setTotalTime(0);
					else
						vertexPathways.get(j).setTotalTime(links[shortestWay - 1][j] + minTime);
					vertexPathways.get(j).setActualPath(vertexPathways.get(shortestWay - 1).getActualPath()+","+Integer.toString(j + 1));
				}
			}
			if(vehicle.getBatteryPercentage() < 20) {
				String[] separated = vertexPathways.get(shortestWay - 1).getActualPath().split("\\,");
				for(int i = separated.length - 2; i > 0; i--) 
				{
					if(vertex.get(Integer.parseInt(separated[i])-1).getHasRechargeStation() == 1) 
					{
						tempsRecharge = 120;
						recharge += (vertexPathways.get(Integer.parseInt(separated[i])-1).getTotalTime()/vehicle.getDurability())*100;
						plusCourtChemin(startIndex, endIndex, vertex, links, vehicleType, transportationRisk, recharge, tempsRecharge, goToRechargeIndex);
						return;
					}
				}
				// Voir les voisins des Nodes du chemin.
				for(int i = separated.length - 3; i > 0; i--) 
				{
					for(int j = 0; j < vertex.size(); j++) 
					{
						if(links[Integer.parseInt(separated[i])-1][j] != 0 && vertex.get(j).getHasRechargeStation() == 1) 
						{
							tempsRecharge = 120 + vertexPathways.get(j).getTotalTime(); 
							recharge += (vertexPathways.get(j).getTotalTime()/vehicle.getDurability())*100;
							goToRechargeIndex = j;
							plusCourtChemin(startIndex, endIndex, vertex, links, vehicleType, transportationRisk, recharge, tempsRecharge, goToRechargeIndex);
							return;
						}
					}
				}
				System.out.println("\n"+"-----------------------------RÉSULTAT----------------------------");
				System.out.println("Désolé, le transport a été refusé!");
				System.out.println("-----------------------------------------------------------------");
				return;
			}
			vertexPathways.get(shortestWay - 1).setVisited(true);
		}
		// Afficher le chemin le plus court.
		System.out.println("\n"+"-----------------------------RÉSULTAT----------------------------");
		System.out.println("   1) Véhicule utilisé : " + vehicle.getVehicleType());
		System.out.println("   2) Batterie : "+Math.floor(vehicle.getBatteryPercentage())+" % restant");
		System.out.println("   3) Chemin : ("+vertexPathways.get(endIndex - 1).getActualPath()+")");
		System.out.println("   4) Temps de déplacement : "+(vertexPathways.get(endIndex - 1).getTotalTime()+tempsRecharge)+" minutes");
		System.out.println("-----------------------------------------------------------------");
	}
	
	// Cette méthode permet de gérer la réponse de l'usager lorsqu'il lui est demandé s'il veut saisir
	// une nouvelle option ou non.
	public static void makeAnotherChoice()
	{
		Scanner scanAnswer = new Scanner(System.in);
		String userAnswerInput = scanAnswer.nextLine();
		
		if(userAnswerInput.equalsIgnoreCase("oui"))
		{
			System.out.println("\n"+"------------------------------MENU------------------------------");
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
	public static void displayStartIndex(ArrayList<vertex> vertex, int[][] links, Double recharge, int tempsRecharge, int goToRechargeIndex)
	{
		System.out.println("\n"+"Veuillez saisir l'index de départ:");
		Scanner scanStartIndex = new Scanner(System.in);
		int userInputStartIndex = scanStartIndex.nextInt();
		
		if(userInputStartIndex > vertex.size() || userInputStartIndex < 0) 
		{
			System.out.println("\n"+"Option invalide! Veuillez saisir une option entre 1 et 29!");
			displayStartIndex(vertex, links, recharge, tempsRecharge, goToRechargeIndex);
		}
		else
		{
			displayEndIndex(userInputStartIndex, vertex, links, recharge, tempsRecharge, goToRechargeIndex);
		}
	}
	
	// Cette méthode permet de gérer les erreurs entrées par l'usager lors de l'option 2 du menu, plus
	// précisement lors de la saisie de l'index de fin. De plus, elle appelle la méthode vehicleChoice.
	public static void displayEndIndex(int userInputStartIndex, ArrayList<vertex> vertex, int[][] links, Double recharge, int tempsRecharge, int goToRechargeIndex)
	{
		System.out.println("\n"+"Veuillez maintenant saisir l'index de fin:");
		Scanner scanEndIndex = new Scanner(System.in);
		int userInputEndIndex = scanEndIndex.nextInt();
		
		if(userInputEndIndex > vertex.size() || userInputEndIndex < 0) 
		{
			System.out.println("\n"+"Option invalide! Veuillez saisir une option entre 1 et 29!");
			displayEndIndex(userInputStartIndex, vertex, links, recharge, tempsRecharge, goToRechargeIndex);
		}
		else
		{
			vehicleChoice(userInputStartIndex, userInputEndIndex, vertex, links, recharge, tempsRecharge, goToRechargeIndex);
		}
	}
	
	// Cette méthode permet de gérer les erreurs entrées par l'usager lors de l'option 2 du menu, plus
	// précisement lors de la saisie du choix du véhicule. De plus, elle appelle la méthode riskChoice.
	public static void vehicleChoice(int userInputStartIndex, int userInputEndIndex, ArrayList<vertex> vertex, int[][] links, Double recharge, int tempsRecharge, int goToRechargeIndex)
	{
		System.out.println("\n"+"Veuillez saisir la lettre qui correspond au type de véhicule désiré:");
		System.out.println("(a) Véhicule avec une batterie à NI-NH");
		System.out.println("(b) Véhicule avec une batterie à LI-ion");
		Scanner scanAnswer = new Scanner(System.in);
		String userInputVehicleType = scanAnswer.nextLine();
		
		if(userInputVehicleType.equalsIgnoreCase("a"))
		{
			riskChoice(userInputStartIndex, userInputEndIndex, "NI-NH", vertex, links, recharge, tempsRecharge, goToRechargeIndex);
		}
		else if(userInputVehicleType.equalsIgnoreCase("b"))
		{
			riskChoice(userInputStartIndex, userInputEndIndex, "LI-ion", vertex, links, recharge, tempsRecharge, goToRechargeIndex);
		}
		else
		{
			System.out.println("\n"+"Option invalide! Veuillez saisir (a) ou (b)!");
			vehicleChoice(userInputStartIndex, userInputEndIndex, vertex, links, recharge, tempsRecharge, goToRechargeIndex);
		}
	}
	
	// Cette méthode permet de gérer les erreurs entrées par l'usager lors de l'option 2 du menu, plus
	// précisement lors de la saisie du facteur de risque. De plus, elle appelle la méthode plusCourtChemin.
	public static void riskChoice(int userInputStartIndex, int userInputEndIndex, String userInputVehicleType, ArrayList<vertex> vertex, int[][] links, Double recharge, int tempsRecharge, int goToRechargeIndex)
	{
		System.out.println("\n"+"Veuillez saisir la lettre qui correspond au risque de transportation:");
		System.out.println("(a) Faible");
		System.out.println("(b) Moyen");
		System.out.println("(c) Élevé");
		Scanner scanAnswer = new Scanner(System.in);
		String userInputTransportationRisk = scanAnswer.nextLine();
		
		if(userInputTransportationRisk.equalsIgnoreCase("a") || userInputTransportationRisk.equalsIgnoreCase("b") || userInputTransportationRisk.equalsIgnoreCase("c"))
		{
			plusCourtChemin(userInputStartIndex, userInputEndIndex, vertex, links, userInputVehicleType, userInputTransportationRisk, recharge, tempsRecharge, goToRechargeIndex);
		}
		else
		{
			System.out.println("\n"+"Option invalide! Veuillez saisir (a), (b) ou (c)!");
			riskChoice(userInputStartIndex, userInputEndIndex, userInputVehicleType, vertex, links, recharge, tempsRecharge, goToRechargeIndex);
		}
	}
	
	// Cette méthode affiche l'interface.
	public static void displayMenu(ArrayList<vertex> vertex, Double recharge, int tempsRecharge, int goToRechargeIndex)
	{
		System.out.println("\n"+"------------------------------MENU------------------------------");
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
					displayStartIndex(vertex, links, recharge, tempsRecharge, goToRechargeIndex);
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
