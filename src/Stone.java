/**
 * the Stone class deals with the movements and rendering of the stone block
 * 
 * by Nadia Mayangputri
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Stone extends Sprite implements Pushable{
	
	private float stoneX = getX();
	private float stoneY = getY();
	
	// the x and y position of stone before it is pushed
	private float prevStoneX;
	private float prevStoneY;

	public Stone(String spriteType, float x, float y) {
		super(spriteType, x, y);	
	}
	
	/**
	 * Updates the movement and behavior of stone
	 * 
	 * @param input - Slick2d input key
	 * @param delta - time passed after last update
	 */
	
	public void update(Input input, int delta) {
		
		// Checks if its surrounding is a blocked tile
		rightTileBlocked = Loader.isBlocked(stoneX + App.TILE_SIZE, stoneY);
		leftTileBlocked = Loader.isBlocked(stoneX - App.TILE_SIZE, stoneY);
		upperTileBlocked = Loader.isBlocked(stoneX, stoneY - App.TILE_SIZE);
		lowerTileBlocked = Loader.isBlocked(stoneX, stoneY + App.TILE_SIZE);

		// The directional speed is 0 if the block next to it is blocked of if it it is a moveable block
		if(rightTileBlocked || Loader.collidesWithMoveableBlock(stoneX + App.TILE_SIZE, stoneY)) {
			rightSpeed = 0;
		} else {
			rightSpeed = 1;
		} 
		
		if(leftTileBlocked || Loader.collidesWithMoveableBlock(stoneX - App.TILE_SIZE, stoneY)) {
			leftSpeed = 0;
		} else {
			leftSpeed = 1;
		} 
		
		if(upperTileBlocked || Loader.collidesWithMoveableBlock(stoneX, stoneY - App.TILE_SIZE)) {
			upSpeed = 0;
		} else {
			upSpeed = 1;
		} 
		
		if(lowerTileBlocked || Loader.collidesWithMoveableBlock(stoneX, stoneY + App.TILE_SIZE)) {
			downSpeed = 0;
		} else { 
			downSpeed = 1;
		} 
		
		push(); 
	} 
	
	/**
	 * Moves the stone
	 */
	public void push() {
		
		// If stone collides with player, it will be pushed to the same direction as the players movement
		if(Player.collidesWithPlayer(stoneX, stoneY)) {
			
			Player.collidesWithIce = false;
			Player.collidesWithStone = true;
			Player.collidesWithTnt = false;
			
			if(Player.direction.equals(DOWN)) {
				prevStoneX = stoneX;
				prevStoneY = stoneY;
				
				stoneY += App.TILE_SIZE * downSpeed;
				// If stone moves away from target, decrement targetBlocked
				if(Target.collidesWithTarget(stoneX, stoneY - App.TILE_SIZE)) {
					Target.targetBlocked--;
				} 
				// If stone collides with target, increment targetBlocked
				else if(Target.collidesWithTarget(stoneX, stoneY)) {
					Target.targetBlocked++;
				}
			}
			
			if(Player.direction.equals(UP)) {
				prevStoneX = stoneX;
				prevStoneY = stoneY;
				
				stoneY -= App.TILE_SIZE * upSpeed;
				// If stone moves away from target, decrement targetBlocked
				if(Target.collidesWithTarget(stoneX, stoneY + App.TILE_SIZE)) {
					Target.targetBlocked--;
				}
				// If stone collides with target, increment targetBlocked
				if(Target.collidesWithTarget(stoneX, stoneY)) {
					Target.targetBlocked++;
				}
			}
			
			if(Player.direction.equals(LEFT)) { 
				prevStoneX = stoneX;
				prevStoneY = stoneY;
				
				stoneX -= App.TILE_SIZE * leftSpeed;
				// If stone moves away from target, decrement targetBlocked
				if(Target.collidesWithTarget(stoneX + App.TILE_SIZE, stoneY )) {
					Target.targetBlocked--;
				}
				// If stone collides with target, increment targetBlocked
				if(Target.collidesWithTarget(stoneX, stoneY)) {
					Target.targetBlocked++;
				}
			}
			
			if(Player.direction.equals(RIGHT)) {
				prevStoneX = stoneX;
				prevStoneY = stoneY;
				
				stoneX += App.TILE_SIZE * rightSpeed;
				// If stone moves away from target, decrement targetBlocked
				if(Target.collidesWithTarget(stoneX - App.TILE_SIZE, stoneY)) {
					Target.targetBlocked--;
				}
				// If stone collides with target, increment targetBlocked
				if(Target.collidesWithTarget(stoneX, stoneY)) {
					Target.targetBlocked++;
				}
 			}
			
		}
		// If stone collides with rogue, it will be pushed to the same direction as the rogues movement
		else if(Rogue.collidesWithRogue(stoneX, stoneY)) { 
			
			if(Rogue.currentDirection.equals(LEFT)) { 
				prevStoneX = stoneX;
				prevStoneY = stoneY;
				
				stoneX -= App.TILE_SIZE * leftSpeed;
			}
			
			if(Rogue.currentDirection.equals(RIGHT)) {
				prevStoneX = stoneX;
				prevStoneY = stoneY;
				
				stoneX += App.TILE_SIZE * rightSpeed;
 			}
		}
		// Update position of stone in moveableBlocks array
		Loader.replace(prevStoneX, prevStoneY, stoneX, stoneY);
	}
	
	/** 
	 * Render stone(s)
     * 
     * @param g The Slick graphics object
     */
	public void render(Graphics g) {
		// Render stone to specified location
		App.stoneImage.draw(stoneX,stoneY);
	}
	

}