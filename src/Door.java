/**
 * the Door class deals with the rendering of the Door block
 * 
 * by Nadia Mayangputri
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Door extends Sprite{
	
	private float doorX = this.getX();
	private float doorY = this.getY();
	
	/**
	 * Indicates if door is open or not
	 * Set as public and static so that it can be accessed in class Switch
	 */
	public static boolean isOpen = false;
	
	public Door(String spriteType, float x, float y) {
		super(spriteType, x, y);	
	}
	
	/**
	 * Updates the behavior of door
	 * 
	 * @param input - Slick2d input key
	 * @param delta - time passed after last update
	 */
	
	public void update(Input input, int delta) {
		
		/* if door is open, set the door position in moveableBlocks array to a zero value
		 * else, keep updating door position in moveableBlocks array
		 */
		if(isOpen) {
			Loader.blockedTiles[Loader.doorBlockedIndex][0] = 0; 
			Loader.blockedTiles[Loader.doorBlockedIndex][0] = 0; 
		} else {
			Loader.blockedTiles[Loader.doorBlockedIndex][0] = doorX; 
			Loader.blockedTiles[Loader.doorBlockedIndex][1] = doorY; 
		}
	}
	
	/** 
	 * Render door
     * 
     * @param g The Slick graphics object
     */
	public void render(Graphics g) {
		// If door is not open, render door image to the specified location
		if(!isOpen) {
			App.doorImage.draw(doorX,doorY);
		}
	}

}