package crach.stage.game.creator.Online.connection;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import crach.stage.game.creator.B2WorldCreator;
import crach.stage.game.entity.Crach.CrachPlayer;

public  class Connection {
	    float update_times;
	    float times;
	    CrachPlayer player;
	    String id;

		String ServerAdressIp ;
		int ServerPort;
		int MyServerPort;



	   public Connection(B2WorldCreator creator2, float update_time) {
		   this.player=creator2.getPlayer();
		   this.update_times=update_time;
		   id = String.valueOf((float)Math.random());
	    }
	   public void updateServer(float dt) {
		   return;
	   }
	   public void Connecte() {
		   return;
	   }
	   public void DeConnecte() {
		   return;
	   }

	public static List<String> getNetworkInterfaces() {
		List<String> addresses = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

			for(NetworkInterface ni : Collections.list(interfaces)){
				for(InetAddress address : Collections.list(ni.getInetAddresses()))
				{
					if(address instanceof Inet4Address){
						addresses.add(address.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		for(String str:addresses)
		{
			System.out.println(str);
		}
		return addresses;
	}
	public void SendsrequetsToall(){
		List<String> LesAdress = getNetworkInterfaces();
	}
}
