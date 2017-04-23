package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Assembler2 {
	public static void assemble(File input, File output, ArrayList<String> errors){
		ArrayList<String> code=new ArrayList<>();
		ArrayList<String> data=new ArrayList<>();
		ArrayList<String> intext=new ArrayList<>();
		String temp;
		try (Scanner inp = new Scanner(input)) {
			temp = inp.nextLine();
			while(inp.hasNextLine()){
				intext.add(temp);
				temp = inp.nextLine();}
		} catch (FileNotFoundException e) { 
			errors.add(0, "Error: Unable to open the input file"); 
			return;}

		for(String temp2:intext){
			if (temp2.trim().length()==0){
				if(temp2.trim().length() > 0){
					errors.add("Error: line " + (intext.indexOf(temp2)+1) + " is a blank line");}}

			if(temp2.charAt(0)== ' ' ||temp2.charAt(0)== '\t'){
				errors.add("Error: line " + (intext.indexOf(temp2)+1) + " starts with white space");}

			if(temp2.trim().toUpperCase().startsWith("--")){
				if(temp2.trim().replace("-", "").length()==0){

				}
			}}

		for(String line : intext){
			if(!line.startsWith("--")){
				code.add(line);}
			else{
				data.add(line);}
		}
		ArrayList<String>outtext=new ArrayList<>();

		for(String line:code){
			String [] parts = line.trim().split("\\s+");
			int lvl=0;
			if(InstructionMap.sourceCodes.contains(parts[0].toUpperCase())&&!InstructionMap.sourceCodes.contains(parts[0])){
				errors.add("Error: line " + (intext.indexOf(line)+1) + " does not have the instruction mnemonic in upper case");}

			else if(InstructionMap.noArgument.contains(parts[0])&&parts.length==1)
				errors.add("Error: line " + (intext.indexOf(line)+1) + " has an illegal argument");

			if(!InstructionMap.noArgument.contains(parts[0])&&parts.length==1)
				errors.add( "Error: line " + (intext.indexOf(line)+1) + " is missing an argument");

			else if(parts.length>=3)
				errors.add( "Error: line " + (intext.indexOf(line)+1) + " has more than one argument");

			if(parts.length==2&&parts[1].startsWith("[")&&!InstructionMap.indirectOK.contains(parts[0])){
				errors.add("Error: line " + (intext.indexOf(line)+1) + " has an illegal argument");}

			else if(parts.length==2&&parts[1].startsWith("[")&&InstructionMap.indirectOK.contains(parts[0])){
				if(!parts[0].endsWith("]"))
					errors.add("Error: line " + (intext.indexOf(line)+1) + " Does not end with ]");}

			
			else if(parts[0].endsWith("A"))
				lvl=3;

			int opcode = InstructionMap.opcode.get(parts[0]);
			if(parts.length==1)
				outtext.add(Integer.toHexString(opcode).toUpperCase() + " 1 0");
			if(parts.length==2)
				outtext.add(Integer.toHexString(opcode).toUpperCase() + " " + lvl + " " + parts[1]);

		}
		outtext.add("-1");
		outtext.addAll(data);

		try (PrintWriter out = new PrintWriter(output)){
			for(String s : outtext) out.println(s);
		} catch (FileNotFoundException e) {
			errors.add("Cannot create output file");
		}

	}
	public static void main(String[] args) {
		ArrayList<String> errors = new ArrayList<>();
		assemble(new File("in.pasm"), new File("out.pexe"), errors);		
	}


}