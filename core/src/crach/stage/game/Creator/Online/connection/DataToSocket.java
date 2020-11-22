package crach.stage.game.Creator.Online.connection;


import crach.stage.game.Creator.Online.B2WorldCreatorOnline.StateEntity;

public class DataToSocket {
	public String id;
    public float x;
    public float y;
    public float r;	
    public int C;

    public StateEntity state;
    
    public  DataToSocket() {	
	}
    public DataToSocket(String id,StateEntity S) {
        this.id=id;
        this.state=S;
     }  
    public DataToSocket(String id,float x,float y,float r,StateEntity S) {
       this.id=id;
       this.x=x;
       this.y=y;
       this.r=r;
       this.state=S;
    }
    public DataToSocket(String id,float x,float y,float r,StateEntity S,int C) {
        this.id=id;
        this.x=x;
        this.y=y;
        this.r=r;
        this.state=S;
        this.C=C;
     }
    
    @Override
    public String toString() {
    	return " id: "+ id +" x: "+ x +" y: "+y+" r: "+ r +" C: "+ C +" State: "+state;
    }
}