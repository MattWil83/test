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
			while(inp.hasNextLine()){
				intext.add(inp.nextLine());}
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
				if(temp2.trim().replace("-", "").length()!=0){
					errors.add("Error: line " + (intext.indexOf(temp2)+1) + " has a badly formatted data separator");
				}
			}}
		if(errors.size()<1){
			int dash=0;
			while(!intext.get(dash).startsWith("--")){
				code.add(intext.get(dash));
				dash++;}
			for(int i=dash+1;i<intext.size();i++){
				data.add(intext.get(i));
			}

			ArrayList<String>outtext=new ArrayList<>();

			for(String line:code){
				String [] parts = line.trim().split("\\s+");
				int lvl=0;
				if(InstructionMap.sourceCodes.contains(parts[0].toUpperCase())&&!InstructionMap.sourceCodes.contains(parts[0])){
					errors.add("Error: line " + (intext.indexOf(line)+1) + " does not have the instruction mnemonic in upper case");}

				else if(InstructionMap.noArgument.contains(parts[0])&&parts.length!=1)
					errors.add("Error: line " + (intext.indexOf(line)+1) + " has an illegal argument");

				else if(!InstructionMap.noArgument.contains(parts[0])&&parts.length==1)
					errors.add( "Error: line " + (intext.indexOf(line)+1) + " is missing an argument");

				else if(parts.length>=3)
					errors.add( "Error: line " + (intext.indexOf(line)+1) + " has more than one argument");

				else if(parts.length==2&&parts[1].startsWith("[")&&!InstructionMap.indirectOK.contains(parts[0])){
					errors.add("Error: line " + (intext.indexOf(line)+1) + " has an illegal argument");}

				else if(parts.length==2&&parts[1].startsWith("[")&&InstructionMap.indirectOK.contains(parts[0])){
					if(!parts[1].endsWith("]"))
						errors.add("Error: line " + (intext.indexOf(line)+1) + " Does not end with ]");
					else if(parts[0].endsWith("]")){
						parts[0].substring(1, parts[0].length()-1);
						lvl=2;}}

//				else if(parts.length!=2)
//					errors.add("Error: line " + (intext.indexOf(line)+1) + " Instruction missing argument");

				System.out.println(parts[0]);
				int arg = 0; 
//							try {
//								arg = Integer.parseInt(parts[1],16);
//							} catch (NumberFormatException e) {
//								errors.add("Error: line " + (intext.indexOf(line)+1) + " does not have a numeric argument");
//							}
			
			}
			for(String line:code){
				String[] parts = line.trim().split("\\s+");
				int lvl=0;
				if(parts.length==2){
					lvl=1;
					if(parts[1].startsWith("[")){
						lvl=2;
						parts[1]=parts[1].substring(1, parts[1].length()-1);}}
				if(parts[0].endsWith("I")){
					lvl=0;}
				else if(parts[0].endsWith("A"))
					lvl=3;

				int opcode = InstructionMap.opcode.get(parts[0]);
				if(parts.length==1)
					outtext.add(Integer.toHexString(opcode).toUpperCase() + " 1 0");
				if(parts.length==2)
					outtext.add(Integer.toHexString(opcode).toUpperCase() + " " + lvl + " " + parts[1]);}
			
			
			System.out.println(errors.toString());
			outtext.add("-1");
			outtext.addAll(data);
			
			if(errors.size()<1){
				try (PrintWriter out = new PrintWriter(output)){
					for(String s : outtext) out.println(s);
				} catch (FileNotFoundException e) {
					errors.add("Cannot create output file");}}}
		
		System.out.println(data.toString());}
	
	public static void main(String[] args) {
		ArrayList<String> errors = new ArrayList<>();
		assemble(new File("in.pasm"), new File("out.pexe"), errors);}


}