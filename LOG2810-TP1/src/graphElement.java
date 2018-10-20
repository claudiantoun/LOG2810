import java.io.*;
import java.util.*;

public class graphElement 
{ 	
	private static String fileName_ = "src/centresLocaux.txt";
	
	private int id_;
	
	private boolean hasRechargeStation_;
	
	public graphElement(int id, boolean hasRechargeStation) {
		id_ = id;
		hasRechargeStation_ = hasRechargeStation;
	}
	
	//Méthode pour lire le fichier texte des centres CLSC
	public static void main(String[] args) throws Exception 
	{ 
		File file = new File(fileName_); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 
	
		ArrayList<graphElement> vertex = new ArrayList<graphElement>();
		readVertex(br, vertex);
		
	}
	
	//Méthode pour lire récursivement chaque ligne du fichier et traiter l'info
	public static void readVertex(BufferedReader br, ArrayList<graphElement> vertex) throws IOException {
		String line; 
		while ((line = br.readLine()) != null) 
		{
			String[] separated = line.split("\\,");
			if(separated.length == 2) {
				vertex.add(new graphElement(Integer.parseInt(separated[0]), Boolean.parseBoolean(separated[1])));
			}
			else if(separated.length == 3) {
				int[][] links = new int[vertex.size()][vertex.size()];
				
				
				readLink(br, vertex, links, line);
			}
			readVertex(br, vertex);
		}
		
	}
	public static void readLink(BufferedReader br, ArrayList<graphElement> vertex, int[][] links, String line) throws IOException {
		 
		while (line != null) 
		{
			String[] separated = line.split("\\,");

			links[Integer.parseInt(separated[0])-1][Integer.parseInt(separated[1])-1] = Integer.parseInt(separated[2]);
			links[Integer.parseInt(separated[1])-1][Integer.parseInt(separated[0])-1] = Integer.parseInt(separated[2]);
			
			
			line = br.readLine();
			readLink(br, vertex, links, line);
		}
		
	}
} 
