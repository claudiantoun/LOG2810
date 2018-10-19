import java.io.*;
import java.util.*;

public class graphElement 
{ 	
	public static void main(String[] args) throws Exception 
	{ 
//		String fileName = "C:\\Users\\claud\\eclipse-workspace\\LOG2810-TP1\\src\\centresLocaux.txt";

		File file = new File("C:\\Users\\claud\\eclipse-workspace\\LOG2810-TP1\\src\\centresLocaux.txt"); 
	
		BufferedReader br = new BufferedReader(new FileReader(file)); 
	
		String line; 	
		while ((line = br.readLine()) != null) 
		{
			String[] separated = line.split("\\,");
			if(separated.length == 2)
				System.out.println(separated[0]); 
			else if(separated.length == 3)
				System.out.println(separated[2]); 
		}
	} 
} 
