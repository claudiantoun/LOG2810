import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileReader;

public class graphElement extends vertex
{ 	
	private static String fileName_ = "src/centresLocaux.txt";
	
	// Constructeur par paramètres.
	public graphElement(int id, int hasRechargeStation) 
	{		
		super(id, hasRechargeStation);
	}
	
	// Cette méthode permet de lire le fichier texte des centres CLSC.
	public static void main(String[] args) throws Exception 
	{ 
		File file = new File(fileName_); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		ArrayList<vertex> vertexList = new ArrayList<vertex>();
		readVertex(br, vertexList);
		int[][] vertexLinks = links;
		Double recharge = 0.0;
		int tempsRecharge = 0;
		int goToRechargeIndex = -1;
		displayMenu(vertexList, recharge, tempsRecharge, goToRechargeIndex);
	}
} 
