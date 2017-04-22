package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Loader {

	
	public static String load(MachineModel model, File file, int codeOffset, int memoryOffset){
		int codeSize=0;
		if(model==null||file==null)
			return null;
		try(Scanner input=new Scanner(file)){
			boolean incode=true;
			while(input.hasNextLine()){
				String line=input.nextLine();
				Scanner parser=new Scanner(line);
				if(parser.nextInt()==-1)
					incode=false;
				else if(parser.nextInt()!=-1){
					
					
					
				}
					
			}
		}
		catch(FileNotFoundException e){
			
		}
	}
}
