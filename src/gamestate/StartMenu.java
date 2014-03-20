package gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.GamePanel;
import content.Background;
import content.Images;
import content.KeyHandler;

public class StartMenu extends GameState {

	private Background bg;

	private int currentChoice = 0;
	private final String[] options = { "Start", "Help", "Quit" };

	private Color titleColor;
	private Font titleFont;

	private Font maximilien;

	int image=0;
	
	public StartMenu(GameStateManager gsm) {
		this.gsm = gsm;

		try {

			bg = new Background("/Background/Background.png", 1, false, 0);
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
		// draw bg
		bg.draw(g);

		// draw title
		g.setFont(titleFont);

		g.setColor(Color.BLACK);
		g.drawString("Brawler Game", 75 + 2, 30 + 2);

		g.setColor(titleColor);
		g.drawString("Brawler Game", 75, 30);
		
		g.drawImage(Images.Button1, 95, 90, null);
		g.drawImage(Images.Button1, 95, 130, null);
		g.drawImage(Images.Button1, 95, 165, null);
		
		final Color clr = new Color(0.5f, 0f, 0.1f);
		// Draw menu square
		final Rectangle rect = new Rectangle((GamePanel.WIDTH / 2) - 20,
				105 + (currentChoice * 35), 46, 18);

		g.setColor(clr);
		g.draw(rect);

		

		// draw menu options
		g.setFont(maximilien);
		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice) {
				g.setColor(Color.BLACK);
			} else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], 145, 120 + (i * 35));
		}
	}

	@Override
	public void init() {

	}

	private void select() {
		if (currentChoice == 0) {
			gsm.setState(GameStateManager.CHARACTERSELECT);
		}
		if (currentChoice == 1) {
			gsm.setState(GameStateManager.HELP);
		}
		if (currentChoice == 2) {
			System.exit(0);
		}
	}

	@Override
	public void update() {

		if (KeyHandler.isPressed(KeyHandler.ENTER)) {
			select();
		}
		if (KeyHandler.isPressed(KeyHandler.UP)) {
			currentChoice--;
			if (currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if (KeyHandler.isPressed(KeyHandler.DOWN)) {
			currentChoice++;
			if (currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
}
