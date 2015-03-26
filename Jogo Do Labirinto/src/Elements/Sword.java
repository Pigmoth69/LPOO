package Elements;

public class Sword extends Element{

	public Sword(int x, int y){
		estado = 'E';
		this.x = x;
		this.y = y;
	}
	
	
	public void removeSword()
	{
		estado = ' ';
	}
	public void swordDragon()
	{
		estado = 'F';
	}
	public void swordArmada()
	{
		estado = 'E';
	}	
	
}
