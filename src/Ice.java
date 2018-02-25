/**
 * the Ice class deals with the movements and rendering of the ice block
 * 
 * by Nadia Mayangputri
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Ice extends Sprite implements Pushable{

	private float iceX = this.getX();
	private float iceY = this.getY();	
	
	// the x and y position of ice before it is pushed
	private float iceXBeforePush;
	private float iceYBeforePush;
	
	// time passed in milliseconds
	private int timePassed = 0;	
	private int roundedTime;
	
	// the last time the ice is moving
	private int prevMovementTime;
	
	private boolean isPushed = false;
	private String direction;
	
	public static boolean undo = false;
	
	public Ice(String spriteType, float x, float y) {
		super(spriteType, x, y);	
	}
	
	/**
	 * Updates the movement and behavior of ice
	 * 
	 * @param input - Slick2d input key
	 * @param delta - time passed after last update
	 */
	
	public void update(Input input, int delta) {
		
		// Checks if its surrounding is a blocked tile
		rightTileBlocked = Loader.isBlocked(iceX + App.TILE_SIZE, iceY);
		leftTileBlocked = Loader.isBlocked(iceX - App.TILE_SIZE, iceY);
		upperTileBlocked = Loader.isBlocked(iceX, iceY - App.TILE_SIZE);
		lowerTileBlocked = Loader.isBlocked(iceX, iceY + App.TILE_SIZE);

		// The directional speed is 0 if the block next to it is blocked of if it it is a moveable block
		if(rightTileBlocked || Loader.collidesWithMoveableBlock(iceX + App.TILE_SIZE, iceY)) {
			rightSpeed = 0;
		} else {
			rightSpeed = 1;
		} 
		
		if(leftTileBlocked || Loader.collidesWithMoveableBlock(iceX - App.TILE_SIZE, iceY)) {
			leftSpeed = 0;
		} else {
			leftSpeed = 1;
		} 
		
		if(upperTileBlocked || Loader.collidesWithMoveableBlock(iceX, iceY - App.TILE_SIZE)) {
			upSpeed = 0;
		} else {
			upSpeed = 1;
		} 
		
		if(lowerTileBlocked || Loader.collidesWithMoveableBlock(iceX, iceY + App.TILE_SIZE)) {
			downSpeed = 0;
		} else { 
			downSpeed = 1;
		} 
		
		// if ice collides with player, it will be pushed to the same direction as the players movement
		if(Player.collidesWithPlayer(iceX, iceY)) {
			isPushed = true;
			direction = Player.direction;
			
			Player.collidesWithIce = true;
			Player.collidesWithStone = false;
			Player.collidesWithTnt = false;
			
			// Stores the position of ice before it is pushed
			iceXBeforePush = iceX;
			iceYBeforePush = iceY;
		}
		
		// count time passed
		timePassed += delta;
		
		// Round time to the nearest 50 so that its easier to check if its reached a multiple 250 milliseconds
		roundedTime = Math.round(timePassed/50) * 50;
		
		/* if ice is pushed by player, the ice will move one tile every 250 milliseconds or 0.25 seconds 
		 * in the direction of the players movement.
		 * prevMovementTime is recorded to make sure that the movement happens every 0.25 seconds 
		 * rather than a multiple of time in that 0.25 seconds window
		 */
		if(roundedTime % 250 == 0 && isPushed && prevMovementTime != roundedTime) {	
			prevMovementTime = roundedTime;
			push();
		}
		
		/*if(undo) {
			if(iceXBeforePush != 0 && iceYBeforePush != 0) {
				//Loader.replace(iceX, iceY, iceXBeforePush, iceYBeforePush);
				undo();
			}
			undo = false;
		}*/

	}
	
	/**
	 * Moves the ice in the same direction as the player when its pushed
	 */
	public void push() {
		
		/*
		 * ice is moved in the same direction as the direction of the players movement
		 * and if it reaches a stop, its checked if it hits a target and the ice position is updated in moveableBlocks array
		 */
		if(direction.equals(DOWN)) {
			iceY += App.TILE_SIZE * downSpeed;
			if(downSpeed == 0) {
				isPushed = false;
				
				if(Target.collidesWithTarget(iceX, iceY - App.TILE_SIZE)) {
					Target.targetBlocked--;
				} else if(Target.collidesWithTarget(iceX, iceY)) {
					Target.targetBlocked++;
				} 
				Loader.replace(iceXBeforePush, iceYBeforePush, iceX, iceY);
			}	
		}
			
		if(direction.equals(UP)) {
			iceY -= App.TILE_SIZE * upSpeed;
			if(upSpeed == 0) {
				isPushed = false;
				
				if(Target.collidesWithTarget(iceX, iceY + App.TILE_SIZE)) {
					Target.targetBlocked--;
				}
				if(Target.collidesWithTarget(iceX, iceY)) {
					Target.targetBlocked++;
				}
				Loader.replace(iceXBeforePush, iceYBeforePush, iceX, iceY);
			}	
		}
		
		if(direction.equals(LEFT)) { 
			iceX -= App.TILE_SIZE * leftSpeed;
			if(leftSpeed == 0) {
				isPushed = false;
				
				if(Target.collidesWithTarget(iceX + App.TILE_SIZE, iceY )) {
					Target.targetBlocked--;
				}
				if(Target.collidesWithTarget(iceX, iceY)) {
					Target.targetBlocked++;
				}
				Loader.replace(iceXBeforePush, iceYBeforePush, iceX, iceY);
			}

		}
		if(direction.equals(RIGHT)) {
			iceX += App.TILE_SIZE * rightSpeed;
			if(rightSpeed == 0) {
				isPushed = false;
				
				if(Target.collidesWithTarget(iceX - App.TILE_SIZE, iceY)) {
					Target.targetBlocked--;
				}
				if(Target.collidesWithTarget(iceX, iceY)) {
					Target.targetBlocked++;
				}
				Loader.replace(iceXBeforePush, iceYBeforePush, iceX, iceY);
			}
 		}
	}
	
	public void undo() {
		iceX = iceXBeforePush;
		iceY = iceYBeforePush;
	}
	
	/** 
	 * Render ice
     * 
     * @param g The Slick graphics object
     */
	public void render(Graphics g) {
		// Render target to specified location
		App.iceImage.draw(iceX,iceY);
	}
}
