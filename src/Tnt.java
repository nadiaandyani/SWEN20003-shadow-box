/**
 * the Tnt class deals with the movements and rendering of the Tnt block
 * 
 * by Nadia Mayangputri
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Tnt extends Sprite implements Pushable{

	private float tntX = this.getX();
	private float tntY = this.getY();
	
	// the x and y position of tnt before it is pushed
	private float prevTntX;
	private float prevTntY;
	
	public Tnt(String spriteType, float x, float y) {
		super(spriteType, x, y);	
	}
	
	/**
	 * Updates the movement and behavior of tnt
	 * 
	 * @param input - Slick2d input key
	 * @param delta - time passed after last update
	 */
	
	public void update(Input input, int delta) {
		
		// Checks if its surrounding is a blocked tile
		rightTileBlocked = Loader.isBlocked(tntX + App.TILE_SIZE, tntY);
		leftTileBlocked = Loader.isBlocked(tntX - App.TILE_SIZE, tntY);
		upperTileBlocked = Loader.isBlocked(tntX, tntY - App.TILE_SIZE);
		lowerTileBlocked = Loader.isBlocked(tntX, tntY + App.TILE_SIZE);
		
		// The directional speed is 0 if the block next to it is blocked of if it it is a moveable block
		if(rightTileBlocked || Loader.collidesWithMoveableBlock(tntX + App.TILE_SIZE, tntY)) {
			rightSpeed = 0;
		} else {
			rightSpeed = 1;
		} 
		
		if(leftTileBlocked || Loader.collidesWithMoveableBlock(tntX - App.TILE_SIZE, tntY)) {
			leftSpeed = 0;
		} else {
			leftSpeed = 1;
		} 
		
		if(upperTileBlocked || Loader.collidesWithMoveableBlock(tntX, tntY - App.TILE_SIZE)) {
			upSpeed = 0;
		} else {
			upSpeed = 1;
		} 
		
		if(lowerTileBlocked || Loader.collidesWithMoveableBlock(tntX, tntY + App.TILE_SIZE)) {
			downSpeed = 0;
		} else { 
			downSpeed = 1;
		} 
		
		/* if cracked wall and tnt explodes, set the tnt position in moveableBlocks array to a zero value
		 * else, keep updating tnt position in moveableBlocks array
		 */
		if(CrackedWall.explode) {
			Loader.moveableBlocks[Loader.tntMoveableIndex][0] = 0; 
			Loader.moveableBlocks[Loader.tntMoveableIndex][0] = 0; 
		} else {
			Loader.moveableBlocks[Loader.tntMoveableIndex][0] = tntX; 
			Loader.moveableBlocks[Loader.tntMoveableIndex][1] = tntY; 
		}

		push();
		
	}
	
	/**
	 * Moves tnt tile
	 */
	public void push() {
		
		// If tnt collides with player, it will be pushed to the same direction as the players movement
		if(Player.collidesWithPlayer(tntX, tntY)) {
			
			Player.collidesWithIce = false;
			Player.collidesWithStone = false;
			Player.collidesWithTnt = true;
			
			if(Player.direction.equals(DOWN) ) {
				prevTntX = tntX;
				prevTntY = tntY;
				tntY += App.TILE_SIZE * downSpeed;
			}
			
			if(Player.direction.equals(UP)) {
				prevTntX = tntX;
				prevTntY = tntY;
				tntY -= App.TILE_SIZE * upSpeed;
			}
			
			if(Player.direction.equals(LEFT)) { 
				prevTntX = tntX;
				prevTntY = tntY;
				tntX -= App.TILE_SIZE * leftSpeed;
			}
			
			if(Player.direction.equals(RIGHT)) {
				prevTntX = tntX;
				prevTntY = tntY;
				tntX += App.TILE_SIZE * rightSpeed;
 			}
		}
		// If the surrounding of tnt is a cracked wall tnt and cracked wall should explode
		if(CrackedWall.collidesWithCracked(tntX, tntY + App.TILE_SIZE) || 
		   CrackedWall.collidesWithCracked(tntX, tntY - App.TILE_SIZE) ||
		   CrackedWall.collidesWithCracked(tntX - App.TILE_SIZE, tntY) ||
		   CrackedWall.collidesWithCracked(tntX + App.TILE_SIZE, tntY)) {
			
			CrackedWall.explode = true;
		}	
		
		// Update position of tnt in moveableBlocks array
		Loader.replace(prevTntX, prevTntY, tntX, tntY); 
	}
	
	/** 
     * Render tnt
     * 
     * @param g The Slick graphics object
     */
	public void render(Graphics g) {
		
		// Render tnt to specified location if tnt and cracked wall haven't explode
		if(!CrackedWall.explode) {
			App.tntImage.draw(tntX,tntY);
		}
	}

}
