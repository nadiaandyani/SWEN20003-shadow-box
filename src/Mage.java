/**
 * the Mage class deals with the movements and rendering of the mage unit
 * 
 * by Nadia Mayangputri
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Mage extends Sprite implements Moveable{
	
	// x and y distance between player and mage
	private float distX;
	private float distY;
	
	// signs of the x and y distance
	private int sgnX;
	private int sgnY;
	
	private float mageX = this.getX();
	private float mageY = this.getY();
	
	public Mage(String spriteType, float x, float y) {
		super(spriteType, x, y);	
	}
	
	/**
	 * Updates the movement and behavior of mage
	 * 
	 * @param input - Slick2d input key
	 * @param delta - time passed after last update
	 */
	
	public void update(Input input, int delta) {
	
		// Checks if its surrounding is a blocked tile
		rightTileBlocked = Loader.isBlocked(mageX + App.TILE_SIZE, mageY);
		leftTileBlocked = Loader.isBlocked(mageX - App.TILE_SIZE, mageY);
		upperTileBlocked = Loader.isBlocked(mageX, mageY - App.TILE_SIZE);
		lowerTileBlocked = Loader.isBlocked(mageX, mageY + App.TILE_SIZE);
		
		// Calculate distance between player and mage
		distX = Player.playerCurrentPos[0] - mageX;
		distY = Player.playerCurrentPos[1] - mageY;
		
		// Determine the signs of the x and y distance
		if(distX < 0) {
			sgnX = -1;
		} else {
			sgnX = 1;
		}
		
		if(distY < 0) {
			sgnY = -1;
		} else {
			sgnY = 1;
		}
		
		/* To restrict movement, the directional speed is set to 0, 
		 * and to allow movement in a specific direction, the directional speed is set to 1
		 */
		if(rightTileBlocked || Loader.collidesWithMoveableBlock(mageX + App.TILE_SIZE, mageY)) {
			rightSpeed = 0;
		} else {
			rightSpeed = 1;
		} 
		
		if(leftTileBlocked || Loader.collidesWithMoveableBlock(mageX - App.TILE_SIZE, mageY)) {
			leftSpeed = 0;
		} else {
			leftSpeed = 1;
		} 
		
		if(upperTileBlocked || Loader.collidesWithMoveableBlock(mageX, mageY - App.TILE_SIZE)) {
			upSpeed = 0;
		} else {
			upSpeed = 1;
		} 
		
		if(lowerTileBlocked || Loader.collidesWithMoveableBlock(mageX, mageY + App.TILE_SIZE)) {
			downSpeed = 0;					
		} else {
			downSpeed = 1;
		}
		
		// If player moves, mage moves too
		if(Player.isMoving) {
			move();
		}
		
		// If mage collides with player, restart current level
		if(Player.collidesWithPlayer(mageX, mageY)) {
			World.restart = true;
		}
		
	}
	
	/**
	 * Moves the mage with specified algorithm attempting get closer to the player
	 */
	public void move() { 
		
		/* If distX is greater than distY, 
		 * mage should move to right if sign x is positive, 
		 * or left if sign x is negative
		 */
		if(Math.abs(distX) > Math.abs(distY)) {
			if(sgnX < 0 && leftSpeed != 0) {
				mageX -= App.TILE_SIZE;
			} else if(sgnX > 0 && rightSpeed != 0) {
				mageX += App.TILE_SIZE;
			}
			
		} 
		/* If distY is greater than distX, 
		 * mage should move downwards if sign y is positive, 
		 * or upwards if sign x is negative
		 */
		else if(Math.abs(distX) < Math.abs(distY)) {
			if(sgnY < 0 && upSpeed != 0) {
				mageY -= App.TILE_SIZE;
			} else if(sgnY > 0 && downSpeed != 0) {
				mageY += App.TILE_SIZE;
			}
		}
	}  
	
	/** 
	 * Render player
     * 
     * @param g The Slick graphics object
     */
	public void render(Graphics g) {
		// Render mage to specified location
		App.mageImage.draw(mageX,mageY);
	}

}