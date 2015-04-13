package Files;

import java.io.Serializable;
import java.util.ArrayList;

import Elements.Dart;
import Elements.Dragon;
import Elements.Exit;
import Elements.Fireball;
import Elements.Player;
import Elements.Shield;
import Elements.Sword;
import Interface.Maze;
import Logic.GameState;

public class GameStateCopy implements Serializable{

	public Maze labirinto;
	public int dragonsSize;
	public int dragonsType;
	public Sword sword;
	public Exit saida;
	public ArrayList<Dragon> dragons = new ArrayList<Dragon>();
	public ArrayList<Fireball> fireballs = new ArrayList<Fireball>();
	public Player player;
	public ArrayList<Dart> dardos = new ArrayList<Dart>();
	public int dardosJogador;
	public Shield shield;
	public boolean escudo;
	
	GameStateCopy(){
		this.labirinto = GameState.getMaze();
		this.dragonsSize = GameState.getDragonSize();
		this.dragonsType = GameState.getDragonsType();
		this.sword = GameState.getSword();
		this.saida = GameState.getExit();
		this.dragons = GameState.getDragons();
		this.fireballs = GameState.getFireballs();
		this.player = GameState.getPlayer();
		this.dardos = GameState.getDarts();
		this.dardosJogador = GameState.getDardosJogador();
		this.shield = GameState.getShield();
		this.escudo = GameState.hasShield();
	}
	
	public void LoadCopy(){
		GameState.setMaze(this.labirinto);
		GameState.setDragonsSize(this.dragonsSize);
		GameState.setDragonsType(this.dragonsType);
		GameState.setSword(this.sword);
		GameState.setExit(this.saida);
		GameState.setDragonsArray(this.dragons);
		GameState.setFireballs(this.fireballs);
		GameState.setPlayer(this.player);
		GameState.setDarts(this.dardos);
		GameState.AddDartsToPlayer(dardosJogador);
		GameState.SetShield(this.shield);
		GameState.SetShieldBol(this.escudo);
	}
	
}
