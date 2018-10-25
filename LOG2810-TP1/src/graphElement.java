import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileReader;

public class graphElement extends vertex
{ 		
	public graphElement(int id, int hasRechargeStation) 
	{		
		//Eclipse autocompleted this constructor but we could use it to stack the vertex/links info.
		super(id, hasRechargeStation);
		// TODO Auto-generated constructor stub
	}

	private static String fileName_ = "textFile/centresLocaux.txt";
	
	// Cette m�thode permet de lire le fichier texte des centres CLSC.
	public static void main(String[] args) throws Exception 
	{ 
		File file = new File(fileName_); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		ArrayList<vertex> vertexList = new ArrayList<vertex>();
		//ArrayList<Integer> links = new ArrayList<Integer>();
		readVertex(br, vertexList);
		int[][] vertexLinks = links;
		Double recharge = 0.0;
		int tempsRecharge = 0;
		int goToRechargeIndex = -1;
		displayMenu(vertexList, recharge, tempsRecharge, goToRechargeIndex);
	}
} 
