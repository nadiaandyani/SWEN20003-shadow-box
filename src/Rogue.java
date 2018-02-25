/**
 * the Rogue class deals with the movements and rendering of the Rogue unit
 * 
 * by Nadia Mayangputri
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Rogue extends Sprite implements Moveable{
	
	/**
	 * An array consisting of the current x and y coordinate of rogue,
	 * this is done so that the rogues current position can be accessed statically in static methods
	 */
	public static float[] rogueCurrentPos = new float[2];
	
	/**
	 * Stores the current direction of rogue
	 * Set as static so that it can be accessed statically in other classes
	 */
	public static String currentDirection = LEFT;
		
	private float rogueX = this.getX();
	private float rogueY = this.getY();
	
	public Rogue(String spriteType, float x, float y) {
		super(spriteType, x, y);
	}
	
	/**
	 * Updates the movement and behavior of rogue
	 * 
	 * @param input - Slick2d input key
	 * @param delta - time passed after last update
	 */
	public void update(Input input, int delta) {
		
		/* Checks if its surrounding is a blocked tile, 
		 * here, only the right and left tile is necessary since the rogue only move in the x direction
		 */
		rightTileBlocked = Loader.isBlocked(rogueX + App.TILE_SIZE, rogueY);
		leftTileBlocked = Loader.isBlocked(rogueX - App.TILE_SIZE, rogueY);
		
		/* To restrict movement, the directional speed is set to 0, 
		 * and to allow movement in a specific direction, the directional speed is set to 1
		 */
		if(rightTileBlocked) {

			rightSpeed = 0;
		} 
		// if the block next to it is a moveable block and the block next to the moveable block is blocked, set speed to 0
		else if(Loader.collidesWithMoveableBlock(rogueX + App.TILE_SIZE, rogueY) 
				&& Loader.isBlocked(rogueX + (2 * App.TILE_SIZE), rogueY)) {
			
			rightSpeed = 0;
				
		}
		/* if the block next to it is a moveable block and the block next to the moveable block 
		 * is also a moveable block, set speed to 0
		 */
		else if(Loader.collidesWithMoveableBlock(rogueX + App.TILE_SIZE, rogueY) 
				&& Loader.collidesWithMoveableBlock(rogueX + (2 * App.TILE_SIZE), rogueY)) {
				
			rightSpeed = 0;
				
		} else {
			rightSpeed = 1;
		}
		

		if(leftTileBlocked) {

			leftSpeed = 0;
		}
		// if the block next to it is a moveable block and the block next to the moveable block is blocked, set speed to 0
		else if(Loader.collidesWithMoveableBlock(rogueX - App.TILE_SIZE, rogueY) 
				&& Loader.isBlocked(rogueX - (2 * App.TILE_SIZE), rogueY)) {

			leftSpeed = 0;
				
		}
		/* if the block next to it is a moveable block and the block next to the moveable block 
		 * is also a moveable block, set speed to 0
		 */
		else if(Loader.collidesWithMoveableBlock(rogueX - App.TILE_SIZE, rogueY) 
				&& Loader.collidesWithMoveableBlock(rogueX - (2 * App.TILE_SIZE), rogueY)) {

			leftSpeed = 0;
		} else { 
			leftSpeed = 1;
		}
		
		// If rogue collides with player, restart current level
		if(Player.collidesWithPlayer(rogueX, rogueY)) {
			World.restart = true;
		}
		
		// If player moves, rogue moves too
		if(Player.isMoving) {
			move();
		}
	}
	
	/**
	 * Moves the rogue in the x direction
	 */
	public void move() {
		/* Keep moving the current direction,
		 * and change direction when a blocked tile is reached
		 */
		if(currentDirection.equals(RIGHT)) {
			rogueX += App.TILE_SIZE * rightSpeed;
			if(rightSpeed == 0) {
				currentDirection = LEFT;
			}
		} else if(currentDirection.equals(LEFT)) {
			rogueX -= App.TILE_SIZE * leftSpeed; 
			if(leftSpeed == 0) {
				currentDirection = RIGHT;
			}
		}
	}
	
	/**
	 * Checks if a block collides with rogue
	 * 
	 * @param x - the x coordinate of the sprite 
	 * @param y - the y coordinate of the sprite 
	 * @return true if that collision occurs, if not, return false
	 */
	public static boolean collidesWithRogue(float x, float y) {
		if(x == rogueCurrentPos[0] && y == rogueCurrentPos[1]) {
			return true;
		}		
		return false;	
	}

	/** 
	  * Render rogue
      * 
      * @param g The Slick graphics object
      */
	public void render(Graphics g) {
		
		// Store current position in an array so that it can be accessed statically static methods
		rogueCurrentPos[0] = rogueX;
		rogueCurrentPos[1] = rogueY;
		
		// Render rogue to specified location
		App.rogueImage.draw(rogueX,rogueY);
	}

}