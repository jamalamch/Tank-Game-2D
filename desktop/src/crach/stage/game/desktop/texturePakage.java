package crach.stage.game.desktop;


import java.io.File;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.tiledmappacker.TiledMapPacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

import crach.stage.game.windows.Bonus.MedalData;

import com.badlogic.gdx.tools.texturepacker.TextureUnpacker;

public class texturePakage {
	
	public static void main(String[] args) throws Exception {
//		   Settings S = new Settings();
//		   S.maxHeight =S.maxHeight*4;
//		   S.maxWidth  =S.maxWidth*4 ;
//		   S.edgePadding = false;
//		   S.duplicatePadding =false;
//		   S.combineSubdirectories = false;
//		   String Outpout = "C:\\Users\\jamal\\Downloads\\Compressed\\batter\\PNG\\Effects\\Nouveau dossier";
//		   String Input = "C:\\Users\\jamal\\Desktop\\NewTest\\android\\assets\\Sprite";
//		   String P[] = {"G:\\CrachGame\\Loding-packed\\pack.atlas"};
//		   TextureUnpacker.main(P);
//		Date N =new Date(System.currentTimeMillis());
//	    Date D =new Date(System.currentTimeMillis()-1569007290165L);
//	    Date T = new Date(1569007290165L);
//	    System.out.println(N.toString() +"\n" +T.toString()+"\n" +D.toString());
//	    System.out.println(D.getHours()*3600 +D.getMinutes()*60+D.getSeconds());
//	    System.out.println((System.currentTimeMillis()-1569007290165L)*0.001);
//	    System.out.println(123124234.1234123D);
//	    System.out.println(1231242341234123L);
		Json reader = new Json();
		MedalData data= new MedalData();
		JsonReader jsonReader = new JsonReader();
		JsonValue value = jsonReader.parse(new FileHandle("C:\\Users\\jamal\\Desktop\\NewTest\\android\\assets\\BonusValue.json"));
	//	data = reader.fromJson(MedalData.class,value.get("Medal").toJson(OutputType.json));
		data = reader.readValue("Medal", MedalData.class,MedalData.class, value);
		System.out.println(data.type);
//		data.medalIcon = "sdfsd";
//		data.mission ="sfsdf dfd dfs";
//		data.prize = 1000;
//		data.type =typeMedal.adsWatchd;
//		data.quantity =12;
//		
//		
//		String data1 =  reader.toJson(data, MedalData.class);
//		System.out.println(data1);
//	}
//	private static enum typeMedalE {
//		Coin,Diamound,Exper,bombs,Nive,adsWatchd,enimykill,matchWinne,nPlayMulti,nStar,hoursPlayed
//	}
//	public static class MedalData {
//		public String medalIcon;
//		public String mission;
//		public int prize;
//		
//		public typeMedalE type;
//		public int quantity;
//		
//		MedalData(){
//		}
	}
}
