/**
 * Class World is responsible for the rendering and update of the game as a whole
 * 
 * by Eleanor McMurty
 * Modified by Nadia Mayangputri
 */

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class World {
	
	// Stores the level files
	private String[] levelFile = new String[] {"file/0.lvl", "file/1.lvl", "file/2.lvl", 
													"file/3.lvl", "file/4.lvl", "file/5.lvl"};
	/**
	 * Stores all the Sprite objects in the game
	 */
	public static ArrayList<Sprite> sprites;
	public static ArrayList<Sprite> prevSprites;
	
	private int currentLevel = 0;
	
	/**
	 * Indicates if the level should restart, this is changed in other classes
	 * e.g when an enemy collides with player
	 */
	public static boolean restart;
	
	public World() {
		// Loads the first level
		loadLevel(currentLevel);
	}
	
	// Loads the level and resets all the significant attributes
	private void loadLevel(int level) {
		Target.targetBlocked = 0;
		Target.allTargetBlocked = false;
		CrackedWall.explode = false;
		Player.moveCount = 0;
		restart = false;
		sprites = Loader.loadSprites(levelFile[level]);
		sprites.trimToSize();
	}
	
	/**
	 * Updates the game as a whole
	 * 
	 * @param input - Slick2d input key
	 * @param delta - time passed after last update
	 */
	 
	public void update(Input input, int delta) {

		for(int i = 0; i < sprites.size(); i++) {
			if(sprites.get(i) != null) {
				sprites.get(i).update(input, delta);
			}
		}
		
		if(Target.allTargetBlocked) {
			currentLevel++;
			loadLevel(currentLevel);
		}
		
		if(input.isKeyPressed(Input.KEY_R) || restart) {
			loadLevel(currentLevel);
		}		

	}
	
	/** 
	  * Render the game as a whole
      * 
      * @param g The Slick graphics object
      */
	public void render(Graphics g) {
		for(int i = 0; i < sprites.size(); i++) {
			if(sprites.get(i) != null) {
				sprites.get(i).render(g);
			}
		}
	}
	
}
