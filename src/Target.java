/**
 * the Target class deals with the rendering and collision detection of the Target block
 * 
 * by Nadia Mayangputri
 */
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Target extends Sprite {
	
	/**
	 * Counts the number of targets blocked
	 */
	public static int targetBlocked;
	
	/**
	 * allTargetsBlocked indicates that a all targets are blocked so that the next level can be loaded
	 */
	public static boolean allTargetBlocked;
	
	private float targetX = this.getX();
	private float targetY = this.getY();
	
	public Target(String spriteType, float x, float y) {
		super(spriteType, x, y);	
	}

	/**
	 * Checks if a block collides with target
	 * 
	 * @param x - the x coordinate of the sprite 
	 * @param y - the y coordinate of the sprite 
	 * @return true if collision occurs, if not, return false
	 */
	public static boolean collidesWithTarget(float x, float y) {
		
		/* Loop through array isATarget which stores the x and coordinate of tiles that are targets,
		 * if the parameter x and y exist in the array, the tile must be a target, hence return true 
		 */
		for(int i = 0; i < Loader.targetNum; i++) {	
			if(x == Loader.isATarget[i][0] &&
			   y == Loader.isATarget[i][1]) {
				return true;
			}
		}
		return false;		
	}
	
	/**
	 * Updates the behavior of target
	 * 
	 * @param input - Slick2d input key
	 * @param delta - time passed after last update
	 */
	
	public void update(Input input, int delta) {
		// Set allTargetBlocked to true if all targets are blocked
		if(targetBlocked == Loader.targetNum) {
			allTargetBlocked = true;
		}
	}
	
    /** 
	 * Render target(s)
     * 
     * @param g The Slick graphics object
     */
	public void render(Graphics g) {
		// Render target to specified location
		App.targetImage.draw(targetX,targetY);
	}

}