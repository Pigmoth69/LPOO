package JUnitTests;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import Interface.StaticMaze;
import Logic.GameState;


public class UnitTests {
	 

	
	char[] swordPath = {'a','a','a','a','s','s','s'};
	char[] playerPathToDragon = {'a','a','a','a','w','w','w'};
	char[] swordToDragonPath = {'a','a','a','a','s','s','s','w','w','w','w','w','w'};
	char[] dragonToexitPath = {'w','d','d','d','d','d','d','d','s','s','s','s','d'};
	char[] exitPath = {'d','s','s','s','d','d','w','w','w','d'};
	char[] burnPath =  {'a','a','a','a','w'};
	char[] pathToDartKill = {'d','w','w','w','w'};
	char[] toSafeSpot = {'d','s','s','s','d','d'}; 
	
	
	
	public void GenerateTestFieldStatic(){
		GameState.restartDragons();
		GameState.restartDarts();
		GameState.restartFireballs();
		
		GameState.SetMaze(0); 
		GameState.setDragonsSize(1);
		GameState.setDragonsType(0);
		
		GameState.AddDragons(1, 1);
		GameState.NewPlayer(5, 5);	
		GameState.PrintSword();
		GameState.PrintDragons();
		GameState.SetShield(6, 5);
	}
	
	
	public void GenerateTestFieldsRandomMaze(int tamanhoR, int dragonsR){
		GameState.restartDragons();
		GameState.restartDarts();
		GameState.restartFireballs();
		
		GameState.SetRandomMaze(tamanhoR); 
		GameState.setDragonsSize(dragonsR);
		GameState.setDragonsType(0);
		
		GameState.GenerateDragons();
		GameState.RefreshElements();
		
		GameState.GeneratePlayer();
		GameState.RefreshElements();
		
		GameState.GenerateDarts();
		GameState.RefreshElements();
		
		GameState.GenerateShield();
		GameState.RefreshElements();
	}
	

	@Test
	public void moveTestFreeCell() {
		GenerateTestFieldStatic();
				
		assertEquals(GameState.movePlayer('a'), true);
		assertEquals(GameState.getPlayer().getX(),4);
		assertEquals(GameState.getPlayer().getY(),5);
		assertEquals(GameState.movePlayer('d'), true);
		assertEquals(GameState.getPlayer().getX(),5);
		assertEquals(GameState.getPlayer().getY(),5);
	}
	
	@Test
	public void moveAgainstWall() {
		GenerateTestFieldStatic();
		assertEquals(GameState.movePlayer('w'), false);
		assertEquals(GameState.getPlayer().getX(),5);
		assertEquals(GameState.getPlayer().getY(),5);
		assertEquals(GameState.movePlayer('s'), false);
		assertEquals(GameState.getPlayer().getX(),5);
		assertEquals(GameState.getPlayer().getY(),5);
	}
	
	@Test
	public void catchSword() {
		GenerateTestFieldStatic();
		
		assertEquals(GameState.getMaze().getBoard()[8][1],'E');
	
		assertEquals(GameState.getPlayer().getEstado(),'H');
		for(int i = 0; i <swordPath.length;i++ )
		{
			assertEquals(GameState.movePlayer(swordPath[i]), true);
		} 
		assertEquals(GameState.movePlayer('s'), false);
		assertEquals(GameState.getPlayer().getEstado(),'A');
	}
	
	@Test
	public void getKilledByDragon() {
		GenerateTestFieldStatic();
		
		assertEquals(GameState.getMaze().getBoard()[1][1],'D');
		
		for(int i = 0; i <playerPathToDragon.length ; i++)
		{
			assertEquals(GameState.movePlayer(playerPathToDragon[i]), true);
		}
		assertEquals(GameState.checkDragonsColision(),true);
	}
	
	@Test
	public void killDragon() {
		GenerateTestFieldStatic();
		
		GameState.activateEscudo();
		assertEquals(GameState.getMaze().getExit().getEstado(),'C');
		
		for(int i = 0; i <swordToDragonPath.length ; i++)
		{
			assertEquals(GameState.Jogar(swordToDragonPath[i]), 0);
			GameState.RefreshElements();
			//GameState.PrintLab();
		}
		assertEquals(GameState.checkDragonsColision(),false);
		assertEquals(GameState.getDragonSize(),0);	
		assertEquals(GameState.getMaze().getExit().getEstado(),'O');
		assertEquals(GameState.checkPlayerWin(),false);
		for(int i = 0; i < dragonToexitPath.length - 1;i++)
		{
			assertEquals(GameState.Jogar(dragonToexitPath[i]), 0);
			
		}
		
		assertEquals(GameState.Jogar('d'), 2);
	}
	
	@Test
	public void directExit() {
		GenerateTestFieldStatic();
		
		assertEquals(GameState.getMaze().getExit().getEstado(),'C');
		
		for(int i = 0; i <exitPath.length-1;i++ )
		{
			assertEquals(GameState.movePlayer(exitPath[i]), true);
		}
		
		assertEquals(GameState.movePlayer(exitPath[exitPath.length-1]), false);
		assertEquals(GameState.checkPlayerWin(),false);
	} 
	
	@Test
	public void testFireColision(){
		GenerateTestFieldStatic();
		for(int i = 0; i < burnPath.length-1;i++ )
		{
			assertEquals(GameState.Jogar(burnPath[i]), 0);
			GameState.RefreshElements();
			//GameState.PrintLab();
		}
		
		assertEquals(GameState.Jogar(burnPath[burnPath.length-1]), 3);
		GameState.RefreshElements();
	}
	
	@Test
	public void testShieldAndFireColision(){
		
		GenerateTestFieldStatic();
		GameState.activateEscudo();
		for(int i = 0; i < burnPath.length;i++ )
		{
			assertEquals(GameState.Jogar(burnPath[i]), 0);
		}
	}
	
	@Test
	public void TestRandomGeneratedElements()
	{
		
		Random rand = new Random();
		int tamanhoR = rand.nextInt(10) + 7;
		if (tamanhoR % 2 == 0){
			tamanhoR++;
		}
		int dragonsR = rand.nextInt(5) + 1;
		
		GenerateTestFieldsRandomMaze(tamanhoR, dragonsR);
		int dragonNum=0;
		int dartsNum=0;
		boolean shieldExists=false;
		
		
		int var = GameState.getMaze().getSize();
		
		//GameState.PrintLab();
		
//		System.out.println("Escudo(X,Y):" + GameState.getShield().getX() + "," + GameState.getShield().getY());
//		
//		for(int i = 0; i < GameState.getDarts().size(); i++){
//			System.out.println("Dardo " + i + " (X,Y):" + GameState.getDarts().get(i).getX() + "," + GameState.getDarts().get(i).getY());
//		}
//		
//		for(int i = 0; i < GameState.getDragonSize(); i++){
//			System.out.println("Dragon " + i + " (X,Y):" + GameState.getDragons().get(i).getX() + "," + GameState.getDragons().get(i).getY());
//		}
		
		for(int i = 0; i < var;i++)
		{
			for(int x = 0; x < var;x++)
			{
				if(GameState.getMaze().getBoard()[x][i] == 'd')
					dartsNum++;
				else if(GameState.getMaze().getBoard()[x][i] == '0')
					shieldExists=true;
				else if(GameState.getMaze().getBoard()[x][i] == 'D')
					dragonNum++;
			}
		}	
		assertEquals(GameState.getDragonSize(), dragonNum);
		assertEquals(shieldExists, true);
		assertEquals(GameState.getNumDardos(), dartsNum);
		
		
		
	}
	
	@Test
	public void KillDragonWithDart(){
		GenerateTestFieldStatic();
		
		GameState.activateEscudo();
		assertEquals(GameState.getDardosJogador(), 0);
		assertEquals(GameState.ShootDarts('d'), false);
		assertEquals(GameState.ShootDarts('e'), false);
		assertEquals(GameState.AddDartsToPlayer(3), 3);
		assertEquals(GameState.ShootDarts('s'), false);
		assertEquals(GameState.getDardosJogador(), 2);
		
		
		for(int i = 0; i <pathToDartKill.length ; i++)
		{
			assertEquals(GameState.Jogar(pathToDartKill[i]), 0);
			GameState.RefreshElements();
		}
		assertEquals(GameState.checkDragonsColision(),false);
		
		assertEquals(GameState.ShootDarts('a'), true);
		assertEquals(GameState.getDardosJogador(), 1);
		GameState.RefreshElements();
		assertEquals(GameState.getDragonSize(),0);
		
		GameState.AddDragons(8, 1);
		GameState.AddDragons(6, 8);
		GameState.setDragonsSize(GameState.getDragons().size());
		GameState.RefreshElements();
		assertEquals(GameState.getDragonSize(),2);
		assertEquals(GameState.AddDartsToPlayer(1), 2);
		assertEquals(GameState.ShootDarts('d'), true);
		assertEquals(GameState.getDardosJogador(), 1);
		assertEquals(GameState.getDragonSize(),1);
		assertEquals(GameState.ShootDarts('s'), true);
		assertEquals(GameState.getDardosJogador(), 0);
		assertEquals(GameState.getDragonSize(),0);
		GameState.RefreshElements();
	}
	
	@Test
	public void MoveDragonTest(){
		GenerateTestFieldStatic();
		int x = GameState.getDragons().get(0).getX();
		int y = GameState.getDragons().get(0).getY();
		
		for(int i = 0; i <toSafeSpot.length ; i++)
		{
			assertEquals(GameState.Jogar(toSafeSpot[i]), 0);
			GameState.RefreshElements();
			//GameState.PrintLab();
		}
		
		assertEquals(GameState.getDragonSize(), 1);
		
		GameState.RefreshElements();
		GameState.setDragonsType(1);
		GameState.setAllDragonsType();
		//GameState.PrintLab();
		for(int i = 0; i < 15; i++){
			assertEquals(GameState.moveDragon(0), true);
			if (GameState.getDragonSize() == 0)
				break;
		}
		
	}
	
	
	
	
	
	
	
}
