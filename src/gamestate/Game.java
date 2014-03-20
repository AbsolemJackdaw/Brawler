package gamestate;

import java.awt.Color;
import java.awt.Font;
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

	private Font maximilien;

	private boolean countdown = false;

	private String endResult = "";

	public Game(GameStateManager gameStateManager, int character) {
		this.gsm = gameStateManager;
		bg = new Background("/Background/PlainBG.png", 1, false, 0);

		maximilien = new Font("Maximilien", Font.PLAIN, 28);

		p = new Player();
		p.setPosition(50, 50);
		p.setWorld(this);
		p.setPlayerSkin(character);

		op = new Oponent();
		op.setPosition(100, 75);
		op.setOpponentSkin(character);
		op.setWorld(this);
		setPlayerPosition();
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		p.draw(g);
		op.draw(g);

		Rectangle rect = p.getRectangle();
		g.draw(rect);

		Rectangle rect2 = new Rectangle(p.getx() - (p.getWidth() / 2), p.gety()
				- (p.getHeight() / 2), p.getWidth(), p.getHeight());
		g.draw(rect2);

		// health1
		g.drawImage(Images.hud.getSubimage(0, 50, Math.max(p.health, 1), 50), 
				0, 0, Math.max(p.health, 1), 50, null);

		// health2
		g.drawImage(Images.hud.getSubimage(0, 50, Math.max(op.health, 1), 50),
				320, 0, Math.min(-op.health, 1), 50, null); 
		// health bar contour
		g.drawImage(Images.hud.getSubimage(0, 0, 320, 50), 0, 0, 320, 50, null);
		// shield
		g.drawImage(Images.hud.getSubimage(50, 100, 50, 50), (320 / 2) - 25, 0,
				50, 50, null);

		int xc = 70;
		int yx = 95;

		g.setFont(maximilien);
		g.setColor(Color.BLACK);
		g.drawString(endResult, xc + 2, yx + 2);
		g.setColor(Color.ORANGE);
		g.drawString(endResult, xc, yx);

		g.setColor(Color.BLACK);
	}

	public Entity getOponent() {
		return op;
	}

	public Entity getPlayer() {
		return p;
	}

	public void handleInput() {
		// if(KeyHandler.isPressed(KeyHandler.ESCAPE)) gsm.setPaused(true);
		if ((p.health <= 1) || (op.health <= 1) || (endResult.length() > 1)) {
			if (KeyHandler.isPressed(KeyHandler.ENTER)) {
				gsm.setState(GameStateManager.STARTMENU);
			}
			return;
		}

		p.setLeft(KeyHandler.keyState[KeyHandler.LEFT]);
		p.setRight(KeyHandler.keyState[KeyHandler.RIGHT]);
		p.setJumping(KeyHandler.keyState[KeyHandler.UP]);
		if (KeyHandler.isPressed(KeyHandler.SPACE)) {
			p.setAttacking();
		}

	}

	@Override
	public void init() {

	}

	private void setPlayerPosition() {
	}

	@Override
	public void update() {
		p.update();
		handleInput();
		op.update();

		if (op.health <= 1) {
			countdown = true;
			endResult = "Player1 Wins !";
			freeze();
		}else if(p.health <= 1){
			countdown = true;
			endResult = "You Lost !";
			freeze();
		}
		else {
			countdown = false;
			endResult = "";
		}
	}
	
	private void freeze(){
		p.setLeft(false);
		p.setRight(false);
		
		op.setLeft(false);
		op.setRight(false);
	}
}
