/**
 * the Player class deals with the movements and rendering of the player
 * 
 * by Nadia Mayangputri
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Player extends Sprite{
	
	/**
	 * An array consisting of the current x and y coordinate of player,
	 * this is done so that the players current position can be accessed statically in other classes
	 */
	public static float[] playerCurrentPos = new float[2];
	
	/**
	 * Stores the current direction of the player
	 * Static so that other classes, 
	 * mostly moveable blocks can move in the same direction as the player when it is pushed
	 */
	public static String direction;
	
	/**
	 * Counts the movements of the player
	 */
	public static int moveCount;
	
	/**
	 * Indicates whether player is moving or not, this is necessary for the movements of the rogue and the mage
	 */
	public static boolean isMoving;

	private float playerX = getX();
	private float playerY = getY();
	
	public static boolean collidesWithIce;
	public static boolean collidesWithStone;
	public static boolean collidesWithTnt;
	
	public Player(String spriteType, float x, float y) {
		super(spriteType, x, y);	
	}
	
	/**
	 * Updates the movement and behavior of player
	 * 
	 * @param input - Slick2d input key
	 * @param delta - time passed after last update
	 */
	public void update(Input input, int delta) {
		
		// Checks if its surrounding is a blocked tile
		rightTileBlocked = Loader.isBlocked(playerX + App.TILE_SIZE, playerY);
		leftTileBlocked = Loader.isBlocked(playerX - App.TILE_SIZE, playerY);
		upperTileBlocked = Loader.isBlocked(playerX, playerY - App.TILE_SIZE);
		lowerTileBlocked = Loader.isBlocked(playerX, playerY + App.TILE_SIZE);
		isMoving = false;
		
		/* To restrict movement, the directional speed is set to 0, 
		 * and to allow movement in a specific direction, the directional speed is set to 1
		 */
		if(rightTileBlocked) {
			
			rightSpeed = 0;
			
		} 
		// if the block next to it is a moveable block and the block next to the moveable block is blocked, set speed to 0
		else if(Loader.collidesWithMoveableBlock(playerX + App.TILE_SIZE, playerY) 
			&& Loader.isBlocked(playerX + (2 * App.TILE_SIZE), playerY)) {
			
			rightSpeed = 0;
			
		} 
		/* if the block next to it is a moveable block and the block next to the moveable block 
		 * is also a moveable block, set speed to 0
		 */
		else if(Loader.collidesWithMoveableBlock(playerX + App.TILE_SIZE, playerY) 
			&& Loader.collidesWithMoveableBlock(playerX + (2 * App.TILE_SIZE), playerY)) {
			
			rightSpeed = 0;
			
		} else {
			rightSpeed = 1;
		} 
		
		if(leftTileBlocked) {
			
			leftSpeed = 0;
		
		} 
		// if the block next to it is a moveable block and the block next to the moveable block is blocked, set speed to 0
		else if(Loader.collidesWithMoveableBlock(playerX - App.TILE_SIZE, playerY) 
				&& Loader.isBlocked(playerX - (2 * App.TILE_SIZE), playerY)) {
				
			leftSpeed = 0;
		}
		/* if the block next to it is a moveable block and the block next to the moveable block 
		 * is also a moveable block, set speed to 0
		 */
		else if(Loader.collidesWithMoveableBlock(playerX - App.TILE_SIZE, playerY) 
				&& Loader.collidesWithMoveableBlock(playerX - (2 * App.TILE_SIZE), playerY)) {
				
			leftSpeed = 0;
					
		} else {
			leftSpeed = 1;
		} 
		
		if(upperTileBlocked) {
			
			upSpeed = 0;
			
		} 
		// if the block next to it is a moveable block and the block next to the moveable block is blocked, set speed to 0
		else if(Loader.collidesWithMoveableBlock(playerX, playerY - App.TILE_SIZE) 
				&& Loader.isBlocked(playerX, playerY - (2 * App.TILE_SIZE))) {
				
			upSpeed = 0;
				
		}
		/* if the block next to it is a moveable block and the block next to the moveable block 
		 * is also a moveable block, set speed to 0
		 */
		else if(Loader.collidesWithMoveableBlock(playerX, playerY - App.TILE_SIZE) 
				&& Loader.collidesWithMoveableBlock(playerX, playerY - (2 * App.TILE_SIZE))) {
				
			upSpeed = 0;
				
		} else {
			upSpeed = 1;
		} 
		
		if(lowerTileBlocked) {
			
			downSpeed = 0;
			
		} 
		// if the block next to it is a moveable block and the block next to the moveable block is blocked, set speed to 0
		else if(Loader.collidesWithMoveableBlock(playerX, playerY + App.TILE_SIZE) 
				&& Loader.isBlocked(playerX, playerY + (2 * App.TILE_SIZE))) {
				
			downSpeed = 0;
				
		}
		/* if the block next to it is a moveable block and the block next to the moveable block 
		 * is also a moveable block, set speed to 0
		 */
		else if(Loader.collidesWithMoveableBlock(playerX, playerY + App.TILE_SIZE) 
				&& Loader.collidesWithMoveableBlock(playerX, playerY + (2 * App.TILE_SIZE))) {
				
			downSpeed = 0;
					
		} else {
			downSpeed = 1;
		}
		
		// Movement is done by incrementing and decrementing the x and y coordinates according to the key pressed
		if(input.isKeyPressed(Input.KEY_UP)) {
			isMoving = true;
			
			playerY -= App.TILE_SIZE * upSpeed;
			direction = UP;
			
			if(upSpeed != 0) {
				moveCount++;
			}
		}
		
		if(input.isKeyPressed(Input.KEY_DOWN)) {
			isMoving = true;
			
			playerY += App.TILE_SIZE * downSpeed;
			direction = DOWN;
			
			if(downSpeed != 0) {
				moveCount++;
			}
		}
		
		if(input.isKeyPressed(Input.KEY_RIGHT)) { 
			isMoving = true;
			
			playerX += App.TILE_SIZE * rightSpeed;
			direction = "right";
			
			if(rightSpeed != 0) {
				moveCount++;
			}
		}
		
		if(input.isKeyPressed(Input.KEY_LEFT)) {
			isMoving = true;
			
			playerX -= App.TILE_SIZE * leftSpeed;
			direction = LEFT;

			if(leftSpeed != 0) {
				moveCount++;
			}
		}
		
	}  
	
	/**
	 * Checks if a block collides with a player
	 * 
	 * @param x - the x coordinate of the sprite 
	 * @param y - the y coordinate of the sprite 
	 * @return true if that collision occurs, if not, return false
	 */
	public static boolean collidesWithPlayer(float x, float y) {
		if(x == playerCurrentPos[0] && y == playerCurrentPos[1]) {
			return true;
		}		
		return false;	
	}
	
	 /** 
	  * Render player
      * 
      * @param g The Slick graphics object
      */
	public void render(Graphics g) {
		
		// Store current position in an array so that it can be accessed statically in other classes
		playerCurrentPos[0] = playerX;
		playerCurrentPos[1] = playerY;
		
		// Render player to specified location
		App.playerImage.draw(playerX,playerY);
		
		// display moveCount in screen
		g.drawString("Moves = " + moveCount, 5, 0);
			
	}

}

