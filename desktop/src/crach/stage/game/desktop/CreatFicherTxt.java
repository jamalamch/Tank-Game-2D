package crach.stage.game.desktop;

import java.io.File;
import java.util.ArrayList;

public class CreatFicherTxt {
	
	String nameficher="test.txt";
    static String Filder = "G:\\CrachGame\\Skin\\Skin\\controle";
    static File file = new File(Filder);
	static ArrayList<String> filename = new ArrayList<String>(); 
    public static void main(String[] args) {
		 if( file.isDirectory()) {
		 for(File f :file.listFiles()) {
			 int i = f.getName().indexOf("_");
			 System.out.println(i);
			 if(i>1) {
				 String name =  f.getName().substring(0, i);
				 if(!filename.contains(name))
	             filename.add(name);
			 }
		 }
		 for(String S : filename)
			 System.out.println("\t"+S +": { up: controle/"+S+"_1 ,down: controle/"+S+"_2 }");
    }
}
}
