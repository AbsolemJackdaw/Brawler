package content;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

import trivia.Print;

public class CustomFont {

	public static Font maximilien;

	public static Font load(String s) {
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		String[] fontFamilies = ge.getAvailableFontFamilyNames();

		Print.say(fontFamilies.toString());

		Font f;

		try {
			f = Font.createFont(Font.TRUETYPE_FONT,
					CustomFont.class.getResourceAsStream(s));
			ge.registerFont(f);

			return f;
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Print.say("Oh noes, font error D: !");
		}
		return null;
	}

}
