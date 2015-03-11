package Elements;
import java.util.Random;


public class Dragon extends Element {
	
	int type; //tipo de dragao
	int numPlaysSleeping; //numero de jogadas que o dragão está a dormir
	
	public Dragon(int x,int y, int type)
	{
		estado = 'D';
		this.x = x;
		this.y = y;
		this.type = type;
		numPlaysSleeping = 0;
	}
	
	public void setType(int type)
	{
		this.type=type;
	}
	
	public void setNormalDragon()
	{
		estado = 'D';
	}
	
	public void setSleep()
	{
		estado = 'S';
	}
	public void setSleepTime(int numSleep)
	{
		numPlaysSleeping = numSleep;
	}
	
	public int getNumPlaysSleeping()
	{
		return numPlaysSleeping;
	}
	
	public int getType()
	{
		return type;
	}
	
	public void setDead()
	{
		estado = ' ';
	}
	
	public void moveUP()
	{
		y--;
	}
	
	public void moveDOWN()
	{
		y++;
	}
	
	public void moveLEFT()
	{
		x--;
	}
	
	public void moveRIGHT()
	{
		x++;
	}
}
