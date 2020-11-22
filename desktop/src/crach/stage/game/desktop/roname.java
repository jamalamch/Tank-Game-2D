package crach.stage.game.desktop;

import java.io.File;

public class roname {
	     static String Filder = "G:\\CrachGame\\Effects";
         static File file = new File(Filder);
         public static void main(String[] args) {
		
		 if( file.isDirectory()) {
			 System.out.println("DErection");
			 for(File f :file.listFiles()) {
				 if(f.getName().contains("png.png.png")) {
					 String name = f.getName();
					 name =  name.replaceFirst("png.png.pngpng", "");
					 name = name.replaceFirst("_Effects_","");
					 System.out.println(name);
					// System.out.println(f.renameTo(new File(Filder+"\\"+name+"png")));
				 }
			 }
		 }
		}
}
