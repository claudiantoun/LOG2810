import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileReader;

public class graphElement extends vertex
{ 		
	// Constructeur par param�tres.
	public graphElement(int id, int hasRechargeStation) 
	{		
		super(id, hasRechargeStation);
	}
	
	// Cette m�thode permet de lire le fichier texte des centres CLSC.
	public static void main(String[] args) throws Exception 
	{ 
		System.out.println("Veuillez entrer le chemin complet du fichier � lire avec son extension (src/centresLocaux.txt, par exemple): ");
		
		Scanner scan = new Scanner(System.in);
		String userInputFileName = scan.nextLine();
		File file = new File(userInputFileName); 
		
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		ArrayList<vertex> vertexList = new ArrayList<vertex>();
		
		readVertex(br, vertexList);
		
		Double recharge = 0.0;
		int tempsRecharge = 0;
		int goToRechargeIndex = -1;
		
		displayMenu(vertexList, recharge, tempsRecharge, goToRechargeIndex);
	}
} 
