package content;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import trivia.Print;

public class Images {

	public static BufferedImage[] HeartIcon = load("/Characters/Heart.png", 50,
			0, 0);
	public static BufferedImage[] BrainIcon = load("/Characters/Brain.png", 50,
			0, 0);

	public static BufferedImage Heart = loadSingle("/Characters/Heart.png");
	public static BufferedImage Brain = loadSingle("/Characters/Brain.png");

	public static BufferedImage[] load(String s, int x, int y, int subImages) {
		BufferedImage[] ret;
		try {
			final BufferedImage spritesheet = ImageIO.read(Images.class
					.getResourceAsStream(s));

			ret = new BufferedImage[subImages];

			for (int i = 0; i < subImages; i++) {
				ret[i] = spritesheet.getSubimage(i * x, y, x, x);
			}
			return ret;
		} catch (final Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics.");
			System.exit(0);
		}
		return null;
	}

	public static BufferedImage loadSingle(String s) {

		BufferedImage img;

		try {
			img = ImageIO.read(Images.class.getResourceAsStream(s));
			return img;

		} catch (IOException e) {
			e.printStackTrace();
			Print.say("Error Loading single image");
		}

		return null;

	}

}
