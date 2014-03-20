package entity;

import gamestate.Game;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import content.Animation;

public class Entity {

	// position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;

	// dimensions
	protected int width;
	protected int height;

	// collision box
	protected int cwidth;
	protected int cheight;

	// collision
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	// protected boolean topLeft;
	// protected boolean topRight;
	// protected boolean bottomLeft;
	// protected boolean bottomRight;

	// animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;

	protected boolean facingRight;

	// movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;

	// movement attributes
	public double moveSpeed;
	public double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;

	protected boolean isFlightEnabled = false;

	public static boolean showBox = false;

	public int health;

	public boolean death;

	public Game world;

	// constructor
	public Entity() {
		health = 150;
	}


	public void checkTileMapCollision() {

		xdest = x + dx;
		ydest = y + dy;

		xtemp = x;
		ytemp = y;

		// calculateCorners(x, ydest);
		if (dy < 0) {
			if (y == 0) {
				dy = 0;

			} else {
				ytemp += dy;
			}
		}
		if (dy > 0) {
			if (y >= 200) {
				dy = 0;
				falling = false;
			} else {
				ytemp += dy;
			}
		}

		// calculateCorners(xdest, y);
		if (dx < 0) {
			if (x <= (0 + (width / 2))) {
				dx = 0;
			} else {
				xtemp += dx;
			}
		}
		if (dx > 0) {
			if (x >= (320 - (width / 2))) {
				dx = 0;
			} else {
				xtemp += dx;
			}
		}

		if (!falling) {
			if (y < 200) {
				falling = true;
			}
		}
	}

	public void damageEntity(int damage) {
		health -= damage;
	}

	public void draw(Graphics2D g) {
		drawSprite(g, animation);
	}

	private void drawSprite(Graphics2D g, Animation am) {

		if (showBox) {

		}

		// draw

		if (facingRight) {
			g.drawImage(animation.getImage(), (int) ((x) - (width / 2)),
					(int) ((y) - (height / 2)), null);
		} else {
			g.drawImage(animation.getImage(),
					(int) ((x) - (width / 2)) + width,
					(int) ((y) - (height / 2)), -width, height, null);
		}
	}

	public int getCHeight() {
		return cheight;
	}

	public int getCWidth() {
		return cwidth;
	}

	public int getHeight() {
		return height;
	}

	public void getNextPosition() {

		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		}

		if (falling && !isFlightEnabled) {
			dy += fallSpeed;
		}
	}

	public Rectangle getRectangle() {
		return new Rectangle((int) x - (cwidth / 2), (int) y - (cheight / 2),
				cwidth, cheight);
	}

	public int getWidth() {
		return width;
	}

	public Game getWorld() {
		return world;
	}

	public int getx() {
		return (int) x;
	}

	public int gety() {
		return (int) y;
	}

	public boolean intersects(Entity o) {
		final Rectangle r1 = getRectangle();
		final Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}

	public void setDown(boolean b) {
		down = b;
	}


	public void setJumping(boolean b) {
		jumping = b;
	}

	public void setLeft(boolean b) {
		left = b;
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setRight(boolean b) {
		right = b;
	}

	public void setUp(boolean b) {
		up = b;
	}

	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void setWorld(Game world) {
		this.world = world;
	}
	
	
	public void backOff(Entity other) {
		
		if (other.getx() > getx()) {
			x -= 3;
		} else {
			x += 3;
		}
	}
}
