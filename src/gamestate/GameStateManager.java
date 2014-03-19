package gamestate;

import main.GamePanel;

public class GameStateManager {

	private final GameState[] gameStates;
	private int currentState;

	public static final int NUMGAMESTATES = 4;

	public static final int DEATH = 0;
	public static final int STARTMENU = 1;
	public static final int CHARACTERSELECT = 2;
	public static final int GAME = 3;

	public GameStateManager() {

		//Music.init();

		gameStates = new GameState[NUMGAMESTATES];

		currentState = STARTMENU;
		loadState(currentState);

	}

	public void draw(java.awt.Graphics2D g) {
		if (gameStates[currentState] != null)
			gameStates[currentState].draw(g);
		else {
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}

	public int getCurrentState() {
		return currentState;
	}

	private void loadState(int state) {
	//	if (state == MENUSTATE)
			//gameStates[state] = new STATENAME(this);
		//else if (state == LEVEL1STATE)
			//gameStates[state] = new STATENAME(this);
		/*etc etc*/
	}

	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public void update() {

		if (gameStates[currentState] != null)
			gameStates[currentState].update();
	}

}