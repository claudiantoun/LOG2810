import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileReader;

public class graphElement extends vertex
{ 		
	public graphElement(int id, boolean hasRechargeStation) 
	{		
		//Eclipse autocompleted this constructor but we could use it to stack the vertex/links info.
		super(id, hasRechargeStation);
		// TODO Auto-generated constructor stub
	}

	private static String fileName_ = "src/centresLocaux.txt";
	
	// Cette méthode permet de lire le fichier texte des centres CLSC.
	public static void main(String[] args) throws Exception 
	{ 
		File file = new File(fileName_); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		ArrayList<vertex> vertex = new ArrayList<vertex>();
		//ArrayList<Integer> links = new ArrayList<Integer>();
		readVertex(br, vertex);
		int[][] vertexLinks = links;
		
		displayMenu(vertex);
	}
} 
