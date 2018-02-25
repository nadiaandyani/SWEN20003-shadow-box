/**
 * The Sprite class is the parent of all the sprites in the game
 * and store common attributes and methods of the different sprites
 * 
 * by Eleanor McMurty
 * Modified by Nadia Mayangputri
 */

import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;

 
public class Sprite {
	
	private String spriteType;
	private float x;
	private float y;
	
	/** 
	 * Constant directions that the blocks and units has
	 * Its set as static since some directions in some sprite needs to be accessed statically in other classes
	 */
	public static final String RIGHT = "right";
	public static final String LEFT = "left";
	public static final String UP = "up";
	public static final String DOWN = "down";
	
	/**
	 * Stores the directional speed of each sprite
	 * mostly functions to indicate where the sprite should or should not move in a certain direction
	 */
	public int rightSpeed;
	public int leftSpeed;
	public int upSpeed;
	public int downSpeed;
	
	/**
	 * Indicates if a tile is the specific direction is blocked or not
	 */
	public boolean rightTileBlocked;
	public boolean leftTileBlocked;
	public boolean upperTileBlocked;
	public boolean lowerTileBlocked;
	
	public Sprite(String spriteType, float x, float y) {
		this.spriteType = spriteType;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Updates the movement and behavior of each sprite
	 * 
	 * @param input - Slick2d input key
	 * @param delta - time passed after last update
	 */
	public void update(Input input, int delta) {
	}
	
	/** 
	 * Render sprite
     * 
     * @param g The Slick graphics object
     */
	public void render(Graphics g) {
	}
	
	/**
	 * Gets value of spriteType
	 * 
	 * @return spriteType
	 */
	public String getSpriteType() {
		return spriteType;
	}
	
	/**
	 * Gets value of x
	 * 
	 * @return x
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Gets value of y
	 * @return y
	 */
	public float getY() {
		return y;
	}
	
	
}
