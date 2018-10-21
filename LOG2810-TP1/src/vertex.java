import java.io.BufferedReader;

import java.io.IOException;
import java.util.ArrayList;

public class vertex 
{ 	
	protected int id_;
	
	protected boolean hasRechargeStation_;
	
	protected static int[][] links;
	
	public vertex(int id, boolean hasRechargeStation) {
		id_ = id;
		hasRechargeStation_ = hasRechargeStation;
	}
	
	
	//Méthode pour lire récursivement chaque ligne du fichier et traiter l'info
	public static void readVertex(BufferedReader br, ArrayList<vertex> vertex) throws IOException {   
		String line;
		
		while ((line = br.readLine()) != null) 
		{
			String[] separated = line.split("\\,");
			if(separated.length == 2) {
				addVertexElement(vertex, separated);
			}
			else if(separated.length == 3) {
				links = new int[vertex.size()][vertex.size()];
				readLinkElements(br, links, line);
			}
			readVertex(br, vertex);
		}

	}
	public static void addVertexElement(ArrayList<vertex> vertex, String[] separated){
		
		vertex.add(new vertex(Integer.parseInt(separated[0]), Boolean.parseBoolean(separated[1])));
	}
	
	
	public static int[][] readLinkElements(BufferedReader br, int[][] links, String line) throws IOException {
		
	
		while (line != null) 
		{
			String[] separated = line.split("\\,");

			links[Integer.parseInt(separated[0])-1][Integer.parseInt(separated[1])-1] = Integer.parseInt(separated[2]);
			links[Integer.parseInt(separated[1])-1][Integer.parseInt(separated[0])-1] = Integer.parseInt(separated[2]);
		
		
			line = br.readLine();
			readLinkElements(br, links, line);
		}
		return links;
	}
} 
