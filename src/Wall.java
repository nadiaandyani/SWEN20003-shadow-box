/**
 * the Wall class deals with the rendering of the Wall block
 * 
 * by Nadia Mayangputri
 */

import org.newdawn.slick.Graphics;

public class Wall extends Sprite {
	private float wallX = this.getX();
	private float wallY = this.getY();
	
	public Wall(String spriteType, float x, float y) {
		super(spriteType, x, y);	
	}
	

	/** 
	 * Render wall
     * 
     * @param g The Slick graphics object
     */
	public void render(Graphics g) {
		// Render wall to specified location
		App.wallImage.draw(wallX,wallY);
	}

}
