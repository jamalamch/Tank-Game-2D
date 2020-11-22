package crach.stage.game.desktop;

import com.badlogic.gdx.math.MathUtils;

public class test {
    public static void main(String[] args) throws Exception{
    	float x=0;
			do{
				System.out.println("cos("+x+")="+Math.cos(x)+"   sin("+x+") = "+Math.sin(x)+"   tan("+x+")= "+Math.tan(x));
				x+=0.1;
			}while(x<10);
    	}
}
