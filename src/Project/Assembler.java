package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Assembler {
	public static void assemble(File input, File output, ArrayList<String> errors){
		ArrayList<String> code=new ArrayList<>();
		ArrayList<String> data=new ArrayList<>();
		try (Scanner inp = new Scanner(input)) {
			while(inp.hasNextLine()){
				while(inp.nextLine()!="--"){
					code.add(inp.nextLine());}
				data.add(inp.nextLine());}
		} catch (FileNotFoundException e) { 
			errors.add(0, "Error: Unable to open the input file"); 
			return;}
		ArrayList<String>temp=new ArrayList<>();
		ArrayList<String>outtext=new ArrayList<>();
		for(String line:code){
			String[] parts = line.trim().split("\\s+");
			





		}
	}
}
