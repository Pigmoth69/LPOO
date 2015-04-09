package Elements;

public class Exit extends Element {
	
	
	public Exit(int x, int y){
		estado = 'C';
		this.x = x;
		this.y = y;
	}
	
	public void setOpen()
	{
		estado = 'O';
	}
}
