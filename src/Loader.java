/**
 * the Loader class is responsible for reading the level file
 * and also storing the coordinates of the sprites thats are blocked, moveable and a target
 * 
 * by Eleanor McMurty
 * Modified by Nadia Mayangputri
 */

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Loader {
	
	private static float mapWidth;
	private static float mapHeight;
	
	private static float tiles_x;
	private static float tiles_y;

	private static float mapZeroCoordinate_x;
	private static float mapZeroCoordinate_y;
	
	/**
	 * The number of sprites in the level
	 */
	public static int inputCount;
	
	/**
	 * A 2D array that stores Blocked tiles
	 */
	public static float[][] blockedTiles;
	public static int blockedTilesNum;
	
	// the index of door in blockedTiles array
	public static int doorBlockedIndex;
	
	// the index of door in blockedTiles array
	public static int crackedBlockedIndex;
	
	/**
	 * An 2D array that stores the target(s)
	 */
	public static float[][] isATarget;
	public static int targetNum;
	
	/**
	 * An 2D array that stores moveable block(s)
	 */
	public static float[][] moveableBlocks;
	public static int moveableBlocksNum;
	
	// the index of tnt in moveableBlocks array
	public static int tntMoveableIndex;
	
	/**
	 * Checks if a block is a blocked tile
	 * 
	 * @param x - the x coordinate of the sprite 
	 * @param y - the y coordinate of the sprite 
	 * @return true if that coordinate is a blocked tile, if not, return false
	 */
	public static boolean isBlocked(float x, float y) {
		
		// Loop through array blockedTiles which stores the x and coordinate of tiles that are blocked,
		// if the parameter x and y exist in the array, the tile must be blocked, hence return true
		for(int i = 0; i < blockedTilesNum; i++) {	
			if(x == blockedTiles[i][0] &&
			   y == blockedTiles[i][1]) {
				return true;
			}
		}
		return false;		
	}
	
	/**
	 * Replaces the old coordinates in array moveableBlocks to the updated coordinate
	 * this method is called on every change of position a moveable block make,
	 * so we can keep track of the positions of the blocks
	 *
	 * @param oldX - the old x coordinate
	 * @param oldY - the old y coordinate
	 * @param newX - the new x coordinate
	 * @param newY - the new y coordinate
	 */
	public static void replace(float oldX, float oldY, float newX, float newY) {
		/* loop through moveableBlocks array and 
		 * find the matching coordinates for the old values of x and y
		 * note that each block has a unique position
		 */
		for(int i = 0; i < moveableBlocksNum; i++) {	
			if(oldX == moveableBlocks[i][0] &&
			   oldY == moveableBlocks[i][1]) {
			   
			   // Updates the x and y position
			   moveableBlocks[i][0] = newX;
			   moveableBlocks[i][1] = newY;
			}
		}
	}
	
	/**
	 * Checks if a block or unit collides with a moveable block
	 * 
	 * @param x - the x coordinate of the sprite 
	 * @param y - the y coordinate of the sprite 
	 * @return true if that cllision occurs, if not, return false
	 */
	public static boolean collidesWithMoveableBlock(float x, float y) {
		for(int i = 0; i < moveableBlocksNum; i++) {	
			if(x == moveableBlocks[i][0] &&
			   y == moveableBlocks[i][1]) {
				return true;
			}
		} 
		return false;
	}
	
	

	/**
	 * Reads the csv file excluding the first line as the first line is not a sprite
	 * 
	 * @param filename - the level file
	 * @return the number of sprites in the file
	 */
	public static int countInput(String filename) {
		int inputCount = 0;
		
		// Reads csv file and counts every line read
		try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
			while((br.readLine()) != null) {
				inputCount++;
			}
		}  catch(IOException e) {
			e.printStackTrace();
		}
		// Excludes first line
		return inputCount - 1;
	}
	
	/**
	 * Loads the sprites from a given file.
	 * 
	 * @param filename
	 * @return an array of object Sprite
	 */
	public static ArrayList<Sprite> loadSprites(String filename) {
	
		inputCount = countInput(filename);
		ArrayList<Sprite> sprites = new ArrayList<>();
		String line;
		int coordinateNum = 2;
		
		int indexOfType = 0;
		int indexOfX = 1;
		int indexOfY = 2;
		
		String type;
		float typeX;
		float typeY;
			
		float xPos;
		float yPos;
			
		blockedTiles = new float[inputCount][coordinateNum];
		blockedTilesNum = 0;

		isATarget = new float[inputCount][coordinateNum];
		targetNum = 0;
			
		moveableBlocks = new float[inputCount][coordinateNum];
		moveableBlocksNum = 0;
			
		boolean firstLine = true;

		// Reads csv file, copies each line to an array of stings separated by ",",		
		try(BufferedReader br = new BufferedReader(new FileReader(filename))) {

			while((line = br.readLine()) != null) {
				String[] tiles = line.split(",");
				
				// Number of tiles that fits into the screen
				tiles_x = App.SCREEN_WIDTH / App.TILE_SIZE;
				tiles_y = App.SCREEN_HEIGHT / App.TILE_SIZE;
			
				/* Calculate the 'zero coordinate' of the map
				 * This is the coordinate where the map starts
				 */
				mapZeroCoordinate_x = (tiles_x - Loader.mapWidth) / 2; 
				mapZeroCoordinate_y = (tiles_y - Loader.mapHeight) / 2;

				
				/* If it is not the first line, a new Sprite object is created from the information provided.
				 * and store it in an ArrayList of type Sprite.
				 */
				if(!firstLine) {
					
					type = tiles[indexOfType];
					typeX = Float.parseFloat(tiles[indexOfX]);
					typeY = Float.parseFloat(tiles[indexOfY]);
					
					xPos = (typeX + mapZeroCoordinate_x) * App.TILE_SIZE;
					yPos = (typeY + mapZeroCoordinate_y) * App.TILE_SIZE;
					
					if(type.equals("wall")) {
						sprites.add(new Wall(type, xPos , yPos));
						
						blockedTiles[blockedTilesNum][0] = xPos;
						blockedTiles[blockedTilesNum][1] = yPos;
						blockedTilesNum++;
					} 
					else if(type.equals("player")) {
						sprites.add(new Player(type, xPos , yPos));
					} 
					else if(type.equals("stone")) {
						sprites.add(new Stone(type, xPos , yPos));
						
						moveableBlocks[moveableBlocksNum][0] = xPos;
						moveableBlocks[moveableBlocksNum][1] = yPos;
						moveableBlocksNum++;
						
					}
					else if(type.equals("floor")) {
						sprites.add(new Floor(type, xPos , yPos));
					}
					else if(type.equals("target")) {
						sprites.add(new Target(type, xPos , yPos));
						
						isATarget[targetNum][0] = xPos;
						isATarget[targetNum][1] = yPos;
						targetNum++;
					}
					else if(type.equals("ice")) {
						sprites.add(new Ice(type, xPos , yPos));
						
						moveableBlocks[moveableBlocksNum][0] = xPos;
						moveableBlocks[moveableBlocksNum][1] = yPos;
						moveableBlocksNum++;
					}
					else if(type.equals("cracked")) {
						sprites.add(new CrackedWall(type, xPos , yPos)); 
						blockedTiles[blockedTilesNum][0] = xPos;
						blockedTiles[blockedTilesNum][1] = yPos;
						
						crackedBlockedIndex = blockedTilesNum;
						blockedTilesNum++;
					}
					else if(type.equals("door")) {
						sprites.add(new Door(type, xPos , yPos));
						blockedTiles[blockedTilesNum][0] = xPos;
						blockedTiles[blockedTilesNum][1] = yPos;
						
						doorBlockedIndex = blockedTilesNum;
						blockedTilesNum++;
					}
					else if(type.equals("mage")) {
						sprites.add(new Mage(type, xPos , yPos));
					}
					else if(type.equals("rogue")) {
						sprites.add(new Rogue(type, xPos , yPos));
					}
					else if(type.equals("skeleton")) {
						sprites.add(new Skull(type, xPos , yPos));
					}
					else if(type.equals("switch")) {
						sprites.add(new Switch(type, xPos , yPos));
					}
					else if(type.equals("tnt")) {
						sprites.add(new Tnt(type, xPos , yPos));
						
						moveableBlocks[moveableBlocksNum][0] = xPos;
						moveableBlocks[moveableBlocksNum][1] = yPos;
						
						tntMoveableIndex = moveableBlocksNum;
						moveableBlocksNum++;
					}
				
			    // if it is the first line, assign the values as the width and height of the map
				} else if(firstLine) {
					int IndexSizeX = 0;
					int indexSizeY = 1;
					
					mapWidth = Float.parseFloat(tiles[IndexSizeX]);
					mapHeight = Float.parseFloat(tiles[indexSizeY]);
					firstLine = false;
				}
				
			}			
		
		} catch(IOException e) {
			e.printStackTrace();
		}
		return sprites;
	}
	
}
