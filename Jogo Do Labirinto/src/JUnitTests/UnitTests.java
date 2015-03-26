package JUnitTests;
import static org.junit.Assert.*;

import org.junit.Test;

import Logic.GameState;


public class UnitTests {
	
	GameState g;
	char[] swordPath = {'a','a','a','a','s','s','s'};
	char[] playerPathToDragon = {'a','a','a','a','w','w','w'};
	char[] swordToDragonPath = {'a','a','a','a','s','s','s','w','w','w','w','w','w'};
	char[] dragonToexitPath = {'w','d','d','d','d','d','d','d','s','s','s','s','d'};
	char[] exitPath = {'d','s','s','s','d','d','w','w','w','d'};
	
	
	
	public void GenerateTestField(){
		g = new GameState();
		g.SetMaze(0); 
		g.setDragonsSize(1);
		g.setDragonsType(0);
		g.AddDragons(1, 1);
		g.NewPlayer(5, 5);	
		g.PrintSword();
		g.PrintDragons();
	}

	@Test
	public void moveTestFreeCell() {
		GenerateTestField();
		assertEquals(g.movePlayer('a'), true);
		assertEquals(g.getPlayer().getX(),4);
		assertEquals(g.getPlayer().getY(),5);
		assertEquals(g.movePlayer('d'), true);
		assertEquals(g.getPlayer().getX(),5);
		assertEquals(g.getPlayer().getY(),5);
	}
	
	@Test
	public void moveAgainstWall() {
		GenerateTestField();
		assertEquals(g.movePlayer('w'), false);
		assertEquals(g.getPlayer().getX(),5);
		assertEquals(g.getPlayer().getY(),5);
		assertEquals(g.movePlayer('s'), false);
		assertEquals(g.getPlayer().getX(),5);
		assertEquals(g.getPlayer().getY(),5);
	}
	
	@Test
	public void catchSword() {
		GenerateTestField();
		
		assertEquals(g.getMaze().getBoard()[8][1],'E');
	
		assertEquals(g.getPlayer().getEstado(),'H');
		for(int i = 0; i <swordPath.length;i++ )
		{
			assertEquals(g.movePlayer(swordPath[i]), true);
		} 
		assertEquals(g.movePlayer('s'), false);
		assertEquals(g.getPlayer().getEstado(),'A');
	}
	
	
	
	@Test
	public void getKilledByDragon() {
		GenerateTestField();
		
		assertEquals(g.getMaze().getBoard()[1][1],'D');
		
		for(int i = 0; i <playerPathToDragon.length ; i++)
		{
			assertEquals(g.movePlayer(playerPathToDragon[i]), true);
		}
		assertEquals(g.checkDragonsColision(),true);
	}
	
	
	@Test
	public void killDragon() {
		GenerateTestField();
		
		assertEquals(g.getMaze().getExit().getEstado(),'X');
		for(int i = 0; i <swordToDragonPath.length ; i++)
		{
			assertEquals(g.movePlayer(swordToDragonPath[i]), true);
		}
		assertEquals(g.checkDragonsColision(),false);
		assertEquals(g.getDragonSize(),0);	
		g.PrintExit();
		assertEquals(g.getMaze().getExit().getEstado(),'S');
		assertEquals(g.checkPlayerWin(),false);
		for(int i = 0; i < dragonToexitPath.length;i++)
		{
			assertEquals(g.movePlayer(dragonToexitPath[i]), true);
		}
		assertEquals(g.checkPlayerWin(),true);
	}
	
	@Test
	public void directExit() {
		GenerateTestField();
		
		assertEquals(g.getMaze().getExit().getEstado(),'X');
		
		for(int i = 0; i <exitPath.length-1;i++ )
		{
			assertEquals(g.movePlayer(exitPath[i]), true);
		}
		
		assertEquals(g.movePlayer(exitPath[exitPath.length-1]), false);
		assertEquals(g.checkPlayerWin(),false);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
