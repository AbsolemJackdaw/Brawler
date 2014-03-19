package gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.GamePanel;
import trivia.Print;
import content.Background;
import content.Images;
import content.KeyHandler;

public class CharacterSelect extends GameState {

	private Background bg;

	private int currentChoice = 0;
	private final String[] options = { "The Heart", "The Brain" };

	private Color titleColor;
	private Font titleFont;

	private Font font;
	private Font maximilien;

	private static final int dist = 150;

	// private BufferedImage[] spriteHeart;
	// private BufferedImage[] spriteBrain;
	// private Animation animation;

	public CharacterSelect(GameStateManager gsm) {
		this.gsm = gsm;

		try {

			bg = new Background("/Background/bg_2.png", 1, false, 0);
			bg.setVector(-0.1, 0);

			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Maximilien", Font.PLAIN, 28);

			maximilien = new Font("Maximilien", Font.PLAIN, 15);

		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void draw(Graphics2D g) {

		int xVx = 25;

		// draw bg
		bg.draw(g);

		// draw title
		g.setFont(titleFont);

		g.setColor(Color.BLACK);
		g.drawString("Brawler Game", 75 + 2, 30 + 2);

		g.setColor(titleColor);
		g.drawString("Brawler Game", 75, 30);

		g.setFont(maximilien);

		g.setColor(Color.BLACK);
		g.drawString("Choose your character", 100 + 1, 47 + 1);

		g.setColor(Color.ORANGE);
		g.drawString("Choose your character", 100, 47);

		g.drawImage(Images.Heart, 65, 90, null);
		g.drawImage(Images.Brain, 65 + dist, 90, null);

		final Color clr = new Color(0.5f, 0f, 0.1f);
		// Draw menu square
		final Rectangle rect = new Rectangle(((GamePanel.WIDTH / 2) - 110)
				+ (currentChoice * dist), 150 + xVx, 80, 16);
		Rectangle selectRect = new Rectangle(((GamePanel.WIDTH / 2) - 120)
				+ (currentChoice * dist), 40 + xVx, 100, 100);

		g.setColor(clr);
		g.draw(rect);
		// g.setColor(clr);
		g.draw(selectRect);

		// draw menu options
		g.setFont(font);
		for (int i = 0; i < 2; i++) {
			if (i == currentChoice) {
				g.setColor(Color.BLACK);
			} else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], 55 + (i * dist), 164 + xVx);
		}
	}

	@Override
	public void init() {

	}

	private void select() {
		if (currentChoice == 0) {
			gsm.setState(GameStateManager.GAME);
			gsm.setCharacter(GameStateManager.HEART);
			Print.say("Choosing heart");
		}
		if (currentChoice == 1) {
			gsm.setState(GameStateManager.GAME);
			gsm.setCharacter(GameStateManager.BRAIN);
			Print.say("Choosing Brain");
		}
		// if (currentChoice == 2) {
		// System.exit(0);
		// }
	}

	@Override
	public void update() {

		if (KeyHandler.isPressed(KeyHandler.ENTER)) {
			select();
		}
		if (KeyHandler.isPressed(KeyHandler.LEFT)) {
			currentChoice--;
			if (currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if (KeyHandler.isPressed(KeyHandler.RIGHT)) {
			currentChoice++;
			if (currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
}
