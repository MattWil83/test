package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Assembler2 {
	public static void assemble(File input, File output, ArrayList<String> errors){
		ArrayList<String> intext=new ArrayList<>();
		try (Scanner inp = new Scanner(input)) {
			while(inp.hasNextLine()){
				intext.add(inp.nextLine());}
		} catch (FileNotFoundException e) { 
			errors.add(0, "Error: Unable to open the input file"); 
			return;}

		for(String temp:intext){
			if (temp.trim().length()==0){
				if(temp.trim().length() > 0){
					errors.add("Error: line " + intext.indexOf(temp) + " is a blank line");}}
			if(temp.charAt(0)== ' ' ||temp.charAt(0)== '\t'){
				errors.add("Error: line " + intext.indexOf(temp) + " starts with white space");}
			if(temp.trim().toUpperCase().startsWith("--")){
				if(temp.trim().replace("-", "").length()==0){
					
				}
			}
			
			
			
		}

		

		

	}
}
