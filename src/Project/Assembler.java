package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Assembler {
	public static void assemble(File input, File output, ArrayList<String> errors){
		ArrayList<String> text=new ArrayList<>();
		try (Scanner inp = new Scanner(input)) {
			while(inp.hasNextLine()) {
				text.add(inp.nextLine()); 
			}
		} catch (FileNotFoundException e) { 
			errors.add(0, "Error: Unable to open the input file"); 
			return;
		}
		
	}
}
