/**
 * Project skeleton for SWEN20003: Object Oriented Software Development 2017

 * by Eleanor McMurtry
 * Modified by Nadia Mayangputri (840854)
 */
//

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

/**
 * Main class for the game.
 * Handles initialisation, input and rendering.
 */
public class App extends BasicGame
{
 	/** screen width, in pixels */
    public static final int SCREEN_WIDTH = 800;
    /** screen height, in pixels */
    public static final int SCREEN_HEIGHT = 600;
    /** size of the tiles, in pixels */
    public static final int TILE_SIZE = 32;
    
    private World world;
    
    /**Image file of wall */
    public static Image wallImage;
    /**Image file of floor */
    public static Image floorImage;
    /**Image file of target */
    public static Image targetImage;
    /**Image file of stone */
    public static Image stoneImage;
    /**Image file of player */
    public static Image playerImage;
    /**Image file of ice */
    public static Image iceImage;
    /**Image file of door */
    public static Image doorImage;
    /**Image file of cracked wall */
    public static Image crackedImage;
    /**Image file of explosion */
    public static Image explosionImage;
    /**Image file of mage */
    public static Image mageImage;
    /**Image file of rogue */
    public static Image rogueImage;
    /**Image file of skull */
    public static Image skullImage;
    /**Image file of switch */
    public static Image switchImage;
    /**Image file of tnt */
    public static Image tntImage;

    public App()
    {    	
        super("Shadow Blocks");
    }

    @Override
    public void init(GameContainer gc)
    throws SlickException
    {
    	world = new World();
    	
    	// Initialize all the sprite images
    	wallImage = new Image("assets/wall.png");
    	floorImage = new Image("assets/floor.png");
    	targetImage = new Image("assets/target.png");
    	stoneImage = new Image("assets/stone.png");
    	playerImage = new Image("assets/player_left.png");
    	iceImage = new Image("assets/ice.png");
    	doorImage = new Image("assets/door.png");
    	crackedImage = new Image("assets/cracked_wall.png");
    	explosionImage = new Image("assets/explosion.png");
    	mageImage = new Image("assets/mage.png");
    	rogueImage = new Image("assets/rogue.png");
    	skullImage = new Image("assets/skull.png");
    	switchImage = new Image("assets/switch.png");
    	tntImage = new Image("assets/tnt.png");
    } 

    /** Update the game state for a frame.
     * @param gc The Slick game container object.
     * @param delta Time passed since last frame (milliseconds).
     */
    @Override
    public void update(GameContainer gc, int delta)
    throws SlickException
    {
        // Get data about the current input (keyboard state).
        Input input = gc.getInput();
        world.update(input, delta);
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param gc The Slick game container object.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(GameContainer gc, Graphics g)
    throws SlickException
    {
    	world.render(g);
    }

    /** Start-up method. Creates the game and runs it.
     * @param args Command-line arguments (ignored).
     */
    public static void main(String[] args)
    throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new App());
        app.setShowFPS(false);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        app.start();
    }
    
}