package Elements;

public class Shield extends Element{

	public Shield(int x, int y){
		estado = '0';
		this.x = x;
		this.y = y;
	}
	
	public void removeShield()
	{
		estado = ' ';
	}
}
