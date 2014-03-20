package gamestate;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import content.Background;
import content.Images;
import content.KeyHandler;
import entity.Entity;
import entity.Oponent;
import entity.Player;

public class Game extends GameState {
	
	Player p;
	Oponent op;
	
	public Game(GameStateManager gameStateManager, int character) {
		this.gsm = gameStateManager;
		bg = new Background("/Background/PlainBG.png", 1, false, 0);
		
		p = new Player();
		p.setPosition(50, 50);
		p.setWorld(this);
		
		op = new Oponent();
		op.setPosition(100, 75);

		setPlayerPosition();
	}

	private void setPlayerPosition() {
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		p.draw(g);
		op.draw(g);
		
		Rectangle rect = p.getRectangle();
		g.draw(rect);
		
		Rectangle rect2 = new Rectangle((int) p.getx()-(p.getWidth()/2) , (int) p.gety()-(p.getHeight()/2), p.getWidth(),
				p.getHeight());
		g.draw(rect2);
		
		//health1
		g.drawImage(Images.hud.getSubimage(0, 50, 150, 50),0,0,150,50, null); //150 -- to decrease health
		//health2
		g.drawImage(Images.hud.getSubimage(0, 50, Math.max(op.health,0) , 50),320,0,Math.min(-op.health,0),50, null); //150 -- to decrease health
		//health bar contour
		g.drawImage(Images.hud.getSubimage(0, 0, 320, 50),0,0,320,50, null);
		//shield
		g.drawImage(Images.hud.getSubimage(50, 100, 50, 50),(320/2)-25,0,50,50, null);


	}

	@Override
	public void init() {

	}

	@Override
	public void update() {
		p.update();
		handleInput();
		op.update();
	}
	
	public Entity getOponent(){
		return op;
	}
	
	public void handleInput() {
		// if(KeyHandler.isPressed(KeyHandler.ESCAPE)) gsm.setPaused(true);
//		if (p.getHealth() == 0)
//			return;
		p.setLeft(KeyHandler.keyState[KeyHandler.LEFT]);
		p.setRight(KeyHandler.keyState[KeyHandler.RIGHT]);
		p.setJumping(KeyHandler.keyState[KeyHandler.UP]);
		if (KeyHandler.isPressed(KeyHandler.SPACE))
			p.setAttacking();

	}

}
