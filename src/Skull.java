/**
 * the Skull class deals with the movements and rendering of the skull unit
 * 
 * by Nadia Mayangputri
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import java.lang.Math;

public class Skull extends Sprite implements Moveable{
	
	// stores the current direction of skull
	private String currentDirection = UP;
	
	// time passed in milliseconds
	private int timePassed = 0;
	
	// the time manually counted per second, as timePassed can have the same value in a row
	private int timeCount = 1;
		
	private float skullX = this.getX();
	private float skullY = this.getY();
	
	public Skull(String spriteType, float x, float y) {
		super(spriteType, x, y);	
	}
	
	/**
	 * Updates the movement and behavior of skull
	 * 
	 * @param input - Slick2d input key
	 * @param delta - time passed after last update
	 */
	
	public void update(Input input, int delta) {
		
		/* Checks if its surrounding is a blocked tile, 
		 * here, only the upper and lower tile is necessary since the skull only move in the y direction
		 */
		upperTileBlocked = Loader.isBlocked(skullX, skullY - App.TILE_SIZE);
		lowerTileBlocked = Loader.isBlocked(skullX, skullY + App.TILE_SIZE);
		
		/* To restrict movement, the directional speed is set to 0, 
		 * and to allow movement in a specific direction, the directional speed is set to 1
		 */
		if(upperTileBlocked) {
			upSpeed = 0;
		} else {
			upSpeed = 1;
		}
		
		if(lowerTileBlocked) {
			downSpeed = 0;
		} else {
			downSpeed = 1;
		}
		
		// If skull collides with player, restart current level
		if(Player.collidesWithPlayer(skullX, skullY)) {
			World.restart = true;
		}
		
		// count time passed every update
		timePassed += delta;
		
		/* Rounding timePassed to the nearest 100 since the milliseconds counted is almost never a multiply of 1000,
		 * then checks if timePassed is a multiply of 1000 and if timePassed in seconds equals to timeCount
		 */
		if((Math.round(timePassed/100) * 100 % 1000 == 0) && timeCount == timePassed / 1000) {
			
			move();
			// Increment timeCount, indicating the next second
			timeCount++;
		}
		
	}
	/**
	 * Moves the skull in the y direction
	 */
	public void move() {
		/* Keep moving the current direction,
		 * and change direction when a blocked tile is reached
		 */
		if(currentDirection.equals(DOWN)) {
			skullY += App.TILE_SIZE * downSpeed;
			if(downSpeed == 0) {
				currentDirection = UP;
			}
		} else if(currentDirection.equals(UP)) {
			skullY -= App.TILE_SIZE * upSpeed; 
			if(upSpeed == 0) {
				currentDirection = DOWN;
			}
		}
	}
	
    /** 
	 * Render skull
     * 
     * @param g The Slick graphics object
     */
	public void render(Graphics g) {
		// Render skull to specified location
		App.skullImage.draw(skullX,skullY);
	}

}