/**
 * the Switch class deals with the rendering and effects of the switch block
 * 
 * by Nadia Mayangputri
 */
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Switch extends Sprite{

	private float switchX = this.getX();
	private float switchY = this.getY();
	
	public Switch(String spriteType, float x, float y) {
		super(spriteType, x, y);	
	}
	
	/**
	 * Updates the behavior of switch
	 * 
	 * @param input - Slick2d input key
	 * @param delta - time passed after last update
	 */
	public void update(Input input, int delta) {
		/* If a moveable block collides with switch or if a player collides wit switch, door is be open
		 * else, door is not open
		*/ 
		if(Loader.collidesWithMoveableBlock(switchX, switchY) || Player.collidesWithPlayer(switchX, switchY)) {
			Door.isOpen = true;
		} else {
			Door.isOpen = false;
		}
	}
	
	/** 
	 * Render switch
     * 
     * @param g The Slick graphics object
     */	
	public void render(Graphics g) {
				
		// Render switch to specified location
		App.switchImage.draw(switchX,switchY);
	}

}