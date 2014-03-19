package gamestate;

import java.awt.GraphicsEnvironment;

import main.GamePanel;
import trivia.Print;
import content.CustomFont;

public class GameStateManager {

	private final GameState[] gameStates;
	private int currentState;

	public static final int NUMGAMESTATES = 5;

	public static final int DEATH = 0;
	public static final int STARTMENU = 1;
	public static final int CHARACTERSELECT = 2;
	public static final int HELP = 3;
	public static final int CHOOSEBG = 4;
	public static final int GAME = 5;

	public int character;

	public static final int HEART = 0;
	public static final int BRAIN = 1;
	public static final int PENIS = 2;

	public GameStateManager() {

		// Music.init();

		gameStates = new GameState[NUMGAMESTATES];

		CustomFont.maximilien = CustomFont.load("/Fonts/maximilien.TTF");
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		String[] fontFamilies = ge.getAvailableFontFamilyNames();
		for (String b : fontFamilies) {
			Print.say(b);
		}

		currentState = STARTMENU;
		loadState(currentState);

	}

	public void draw(java.awt.Graphics2D g) {
		if (gameStates[currentState] != null) {
			gameStates[currentState].draw(g);
		} else {
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}

	public int getCurrentState() {
		return currentState;
	}

	private void loadState(int state) {
		if (state == STARTMENU) {
			gameStates[state] = new StartMenu(this);
		} else if (state == CHARACTERSELECT) {
			gameStates[state] = new CharacterSelect(this);
		} else if (state == GAME) {
			gameStates[state] = new Game(this, character);
		}
	}

	public void setCharacter(int i) {
		character = i;
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

		if (gameStates[currentState] != null) {
			gameStates[currentState].update();
		}
	}

}