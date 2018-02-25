/**
 * the CrackedWall class deals with the rendering and effects of the CrackedWall block
 * 
 * by Nadia Mayangputri
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class CrackedWall extends Sprite{
		
	private float crackedX = this.getX();
	private float crackedY = this.getY();
	
	/** An array consisting of the x and y coordinate of cracked wall,
	 *  this is done so that the cracked walls' position can be accessed statically in class Tnt
	 */
	public static float[] crackedWallPos= new float[2];
	
	/**
	 * Indicates if explosion have occurred or not
	 */
	public static boolean explode;
	
	// time passed after the explosion in milliseconds
	private int timePassedAfterExplosion = 0;
		
	private boolean showExplosion = false;
	
	public CrackedWall(String spriteType, float x, float y) {
		super(spriteType, x, y);	
	}
	
	/**
	 * Updates the behavior of cracked wall
	 * 
	 * @param input - Slick2d input key
	 * @param delta - time passed after last update
	 */
	public void update(Input input, int delta) {
		
		/* if cracked wall and tnt explodes, set the cracked wall position in moveableBlocks array to a zero value
		 * else, keep updating tnt position in moveableBlocks array
		 */
		if(explode) {
			// Start timing of explosion
			timePassedAfterExplosion += delta;
			Loader.blockedTiles[Loader.crackedBlockedIndex][0] = 0; 
			Loader.blockedTiles[Loader.crackedBlockedIndex][0] = 0; 
			
		} else {
			Loader.blockedTiles[Loader.crackedBlockedIndex][0] = crackedX; 
			Loader.blockedTiles[Loader.crackedBlockedIndex][1] = crackedY; 
		}
		
		// If the time after the explosion is not yet 0.4 seconds, keep showing explosion
		if(Math.round(timePassedAfterExplosion/100) * 100 <= 400) {
			showExplosion = true;
		} else {
			showExplosion = false;
		}
		
	}

	/** 
	  * Render cracked wall
      * 
      * @param g The Slick graphics object
      */
	public void render(Graphics g) {
		
		// Store position in an array so that it can be accessed statically in other classes
		crackedWallPos[0] = crackedX;
		crackedWallPos[1] = crackedY;
		
		// If cracked wall haven't explode, render cracked wall to specified location
		if(!explode) {	
			App.crackedImage.draw(crackedX,crackedY);

		}
		// If cracked wall exploded and the time for showing explosion hasn't run out, render explosion image
		else if(explode && showExplosion) {
			App.explosionImage.draw(crackedX - App.TILE_SIZE,crackedY - App.TILE_SIZE);
		}
		
	}
	
	/**
	 * Checks if a block collides with cracked wall
	 * 
	 * @param x - the x coordinate of the sprite 
	 * @param y - the y coordinate of the sprite 
	 * @return true if collision occurs, if not, return false
	 */
	public static boolean collidesWithCracked(float x, float y) {
		if(x == crackedWallPos[0] &&
		   y == crackedWallPos[1]) {
				return true;
		} 
		return false;
	}
	
}
