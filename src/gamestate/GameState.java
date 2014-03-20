package gamestate;

import content.Background;

public abstract class GameState {

	public Background bg;

	public GameStateManager gsm;

	public abstract void draw(java.awt.Graphics2D g);

	public abstract void init();

	public abstract void update();

}
