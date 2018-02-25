/**
 * the Floor class deals with the rendering of the floor block
 * 
 * by Nadia Mayangputri
 */

import org.newdawn.slick.Graphics;

public class Floor extends Sprite {
	
	private float floorX = this.getX();
	private float floorY = this.getY();
	
	public Floor(String spriteType, float x, float y) {
		super(spriteType, x, y);	
	}
	
	/** 
	 * Render floor
     * 
     * @param g The Slick graphics object
     */
	public void render(Graphics g) {
		// Render floor to specified location
		App.floorImage.draw(floorX,floorY);
	}
	
}