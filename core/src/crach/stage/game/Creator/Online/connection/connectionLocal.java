package crach.stage.game.Creator.Online.connection;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.NetJavaServerSocketImpl;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.Json;

import crach.stage.game.Creator.Online.B2WorldCreatorOnline;
import crach.stage.game.Creator.Online.B2WorldCreatorOnline.StateEntity;
import crach.stage.game.entites.Entity;
import crach.stage.game.entites.Crach.CrachPlayer;



public class connectionLocal extends Connection{
    
    private NetJavaServerSocketImpl Server ;
    private java.net.Socket SocketClient;
    public B2WorldCreatorOnline creator;
    private Thread thread ;

	public connectionLocal(B2WorldCreatorOnline creator, CrachPlayer player, float update_time) {        
		super(creator , update_time);
		this.creator = creator;
	}
	
	@Override
	public void Connecte() {
		thread = new Thread(new Runnable(){
            @Override
            public void run() {
                ServerSocketHints serverSocketHint = new ServerSocketHints();
                serverSocketHint.acceptTimeout = 0;                
                Server = new NetJavaServerSocketImpl(Protocol.TCP, MyServerPort, serverSocketHint);
                while(true){                    
                    try {
                        Socket socket = Server.accept(null);  
                        int t =socket.getInputStream().read();
            	        byte code [] = new byte[t];
             	        socket.getInputStream().read(code);
            	        updateUsingData(getingData(code));
            	        creator.setOnLine(true);
            	        socket.dispose();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }); 
		thread.start();
        CreatConnection();
	}

    public void CreatConnection(){
        DataToSocket data = new DataToSocket(id
        		, player.getBody().getPosition().x
        		, player.getBody().getPosition().y
        		, player.getBody().getAngle()
        		,StateEntity.New_Player
        		,String.valueOf(player.getCodeCrach()).charAt(0));


        System.out.println(String.valueOf(player.getCodeCrach()).charAt(0));
        try {
    		SocketClient =  new java.net.Socket(ServerAdressIp , ServerPort);
    		SocketClient.setSoTimeout(0);
        	SocketClient.getOutputStream().write(sendingMessageData(data));
        	SocketClient.getOutputStream().flush();
        	SocketClient.close();
        	} catch (UnknownHostException e1){
        	//	creator.BeAdminisrationRoom(true);
    	    } catch (IOException e1) {
        	//	creator.BeAdminisrationRoom(true);
    	 }
    }
    
    public void SendDeConnection(){
        DataToSocket data = new DataToSocket(id,StateEntity.Destor_Player);
        sendingData(data);
    }
	public void updateServer(float dt) {
		times +=dt;
		if(times >= update_times && SocketClient != null && creator.isOnLine()) {
		if(player.isActive() ) {

			times =0;
                DataToSocket data = new DataToSocket(id
						, player.getBody().getPosition().x
                		, player.getBody().getPosition().y
                		, player.getBody().getAngle()
                		,StateEntity.Move_Player);
                sendingData(data);	
		}
		if(!creator.getEntityActive().isEmpty()) {
			String b = creator.getEntityActive().keySet().iterator().next();
			times =0;
			DataToSocket data;
			Entity E = creator.getEntiy(b);
			switch(creator.getEntityActive().get(b)){
			case New_Object:
	                 data = new DataToSocket(b, E.getBody().getPosition().x
	                		,E.getBody().getPosition().y
	                		,E.getBody().getAngle()
	                		,StateEntity.New_Object
	                        //,((Box) E).getLettercode()
	                        );
	                		
		            sendingData(data);
	                Gdx.app.log("Sokit.io", " sending data new Box  "+b);

			break;
			case Move_Object:
	                data = new DataToSocket(b, E.getBody().getPosition().x
	                		,E.getBody().getPosition().y
	                		,E.getBody().getAngle()
	                		,StateEntity.Move_Object);
	                sendingData(data);
	                Gdx.app.log("Sokit.io", " sending data Moveing Box  "+b);
			break;
			case Destor_Object:
	                data = new DataToSocket(b,StateEntity.Destor_Object);
	                sendingData(data);
					Gdx.app.log("Sokit.io", " sending data Remove Box  "+b);
			break;
			default : 
                Gdx.app.log(" null ", " defulse  ");
		}
			creator.getEntityActive().remove(b);
		}
	}
	}
	
    public void sendingData(DataToSocket Data) {
       try {
		SocketClient =  new java.net.Socket(ServerAdressIp , ServerPort);
		SocketClient.setSoTimeout(0);
    	SocketClient.getOutputStream().write(sendingMessageData(Data));
    	SocketClient.getOutputStream().flush();
    	SocketClient.close();
    	} catch (UnknownHostException e1){
    		creator.setOnLine(false);
	    } catch (IOException e1) {
    		creator.setOnLine(false);
	    }
    }

	public byte[] sendingMessageData(DataToSocket Data) {
		final Json json = new Json();
		return json.toJson(Data).getBytes();
	}
	public DataToSocket getingData(byte[] data) {
		final Json json = new Json();
		String Data = new String(DatatypeConverter.parseBase64Binary(DatatypeConverter.printBase64Binary(data)));
		return json.fromJson(DataToSocket.class,Data);
	}
	public void updateUsingData(DataToSocket Data) {
		switch(Data.state) {
		case New_Player:
			if(creator.isEntityExist(Data.id) ||  Data.id == id) 
				return;
            DataToSocket data = new DataToSocket(id
            		, player.getBody().getPosition().x
            		, player.getBody().getPosition().y
            		, player.getBody().getAngle()
            		,StateEntity.New_Player
            		,String.valueOf(player.getCodeCrach()).charAt(0));
            sendingData(data);	
            System.out.println(Integer.valueOf(Data.C));
			creator.putEntity(Data.state,Data.id, Data.x,Data.y,Data.r,Integer.valueOf(String.valueOf(Data.C)));
	//		creator.addAlltoActive();
            Gdx.app.log(" New player ", Data.toString());
			break;
		case Move_Player:			
            Gdx.app.log(" Move player ", Data.toString());
			if(Data.id != id)
			creator.updateEntityPosition(Data.id, Data.x,Data.y,Data.r);
			break;
		case Destor_Player:
		case Destor_Object:
		case Destor_Enimy:
		case Destor_Pickup:
            Gdx.app.log(" Destor_Entity ", Data.toString());
			creator.destoryEntity(Data.state,Data.id);
			break;
		case New_Object:
		case New_Enimy:
		case New_Pickup:
		case New_fire:
            Gdx.app.log(" New_Entity ", Data.toString());
			creator.putEntity(Data.state,Data.id, Data.x, Data.y,Data.r,Data.C);
			break;
		case Move_Object:
		case Move_Enimy:
            Gdx.app.log(" Move_Entity ", Data.toString());
			creator.updateEntityPosition(Data.id, Data.x, Data.y, Data.r);
			break;
		}	
	}
	public void SetServerAdress(String ipAdress,int ServerPort,int youServer) {
		this.ServerAdressIp = ipAdress;
		this.ServerPort = ServerPort;
		this.MyServerPort = youServer;
	}
	@SuppressWarnings("deprecation")
	@Override
	public void DeConnecte() {
		SendDeConnection();
		try {
			if(SocketClient != null)
			SocketClient.close();
			thread.stop();
			Server.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.DeConnecte();
	}
}







