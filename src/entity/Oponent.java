package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import content.Animation;

public class Oponent extends Entity {

	private boolean attacking;

	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
			// for every animation, add the number of frames here
			// sample : 2,5,8,4 (2 for idle 0, 5 for walking 1, etc.
			2, 2, 2, 2, 2 };

	// animation action
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int ATTACKING = 4;

	int aiCntdwn = 100;

	Random rand = new Random();

	public boolean follows;

	public Oponent() {
		super();

		width = 50;
		height = 50;

		cwidth = 20;
		cheight = 20;

		moveSpeed = 0.3; // inital walking speed. you speed up as you walk
		maxSpeed = 0.3; // change to jump farther and walk faster
		stopSpeed = 1.0;
		fallSpeed = 0.15; // affects falling and jumping
		maxFallSpeed = 5.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;

		facingRight = true;

		health = 150;

	}

	private void AImovements() {
		int i = rand.nextInt(10);
		cwidth += 30;
		if (this.intersects(getWorld().getPlayer())) {
			follows = false;
			if (i < ((getWorld().getDiff() * 2) + 1)) {
				cwidth = 30;
				attack();
			}
			if (i == 9) {
				backOff();
				backOff();
			}
		} else {
			follows = true;
		}
		cwidth = 30;
	}

	private void attack() {
		cwidth += 20;
		if (this.intersects(getWorld().getPlayer())) {
			attacking = true;
			getWorld().getPlayer().damageEntity(10);
		}
		cwidth -= 20;
	}

	public void backOff() {
		Player p = getWorld().getPlayer();

		if (getx() < p.getx()) {
			x -= 3;
		} else {
			x += 3;
		}
	}

	private void followPlayer() {
		Player p = getWorld().getPlayer();

		// Print.say(xtemp + " " + p.xtemp);
		if (getx() < p.getx()) {
			setLeft(false);
			setRight(true);
		} else {
			setRight(false);
			setLeft(true);
		}
	}

	@Override
	public void getNextPosition() {

		// movement
		if (left && (x > 25)) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if (right && (x < 295)) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		} else if (dx > 0) {
			dx -= stopSpeed;
			if (dx < 0) {
				dx = 0;
			}
		} else if (dx < 0) {
			dx += stopSpeed;
			if (dx > 0) {
				dx = 0;
			}
		}

		// cannot move while attacking, except in air
		if ((currentAction == ATTACKING) && !(jumping || falling)) {
			dx = 0;
		}

		// jumping
		if (jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}

		// falling
		if (falling) {
			dy += fallSpeed;

			if (dy > 0) {
				jumping = false;
			}
			if ((dy < 0) && !jumping) {
				dy += stopJumpSpeed;
			}

			if (dy > maxFallSpeed) {
				dy = maxFallSpeed;
			}

		}

	}

	public void setOpponentSkin(int character) {

		String s;

		s = character == 0 ? "/Characters/TestChar.png"
				: "/Characters/TestChar2.png";

		// load sprites
		try {

			final BufferedImage spritesheet = ImageIO.read(getClass()
					.getResourceAsStream(s));

			sprites = new ArrayList<BufferedImage[]>();
			for (int i = 0; i < numFrames.length; i++) {

				final BufferedImage[] bi = new BufferedImage[numFrames[i]];

				for (int j = 0; j < numFrames[i]; j++) {
					bi[j] = spritesheet.getSubimage(j * width, i * height,
							width, height);
				}
				sprites.add(bi);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(100);
	}

	public void update() {

		if (health <= 1) {
			death = true;
		}

		// update position
		if (!death) {
			getNextPosition();
			checkTileMapCollision();
			setPosition(xtemp, ytemp);
		}

		// check attack to stop
		if (currentAction == ATTACKING) {
			if (animation.hasPlayedOnce()) {
				attacking = false;
			}
		}

		// set animation
		if (attacking) {
			if (currentAction != ATTACKING) {
				currentAction = ATTACKING;
				animation.setFrames(sprites.get(ATTACKING));
				animation.setDelay(75);
				width = 50;
			}
		} else if (dy > 0) {
			if (currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 50;
			}
		} else if (dy < 0) {
			if (currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 50;
			}
		} else if (left || right) {
			if (currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				width = 50;
			}
		} else if (currentAction != IDLE) {
			currentAction = IDLE;
			animation.setFrames(sprites.get(IDLE));
			animation.setDelay(100);
			width = 50;
		}

		animation.update();

		// set direction
		if (currentAction != ATTACKING) {
			if (right) {
				facingRight = true;
			}
			if (left) {
				facingRight = false;
			}
		}

		if (!death) {
			aiCntdwn--;
			if (aiCntdwn <= 0) {
				AImovements();
				aiCntdwn = 40;
			}
		}
		if (follows) {
			followPlayer();
		}
	}
}
