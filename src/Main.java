import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {
    JFrame displayZoneFrame;
    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    public Main() throws Exception {
        // Display the welcome screen before starting the game
        new WelcomeScreen(this::startGame);
    }

    private void startGame() {
        try {
            // Initialize the main window
            displayZoneFrame = new JFrame("Java Labs");
            displayZoneFrame.setSize(1920, 1080);
            displayZoneFrame.setTitle("Dungeon Crawler");
            displayZoneFrame.setLocationRelativeTo(null);
            displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Initialize the dynamic sprite (hero)
            DynamicSprite hero = new DynamicSprite(1000, 380,
                    ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50, 5);

            // Create the RenderEngine (which now also handles the health bar)
            renderEngine = new RenderEngine(displayZoneFrame, hero);  // Pass the hero to the RenderEngine

            // Initialize the game engine
            physicEngine = new PhysicEngine();
            Playground level = new Playground("./data/level1.txt");
            gameEngine = new GameEngine(hero,level);

            Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
            Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
            Timer physicTimer = new Timer(50, (time) -> physicEngine.update());

            renderTimer.start();
            gameTimer.start();
            physicTimer.start();

            displayZoneFrame.getContentPane().add(renderEngine);  // Add RenderEngine to the frame

            displayZoneFrame.setVisible(true);  // Make the main window visible


            renderEngine.addToRenderList(level.getSpriteList());  // Adds all level sprites
            renderEngine.addToRenderList(hero);  // Adds the hero sprite
            physicEngine.addToMovingSpriteList(hero);
            physicEngine.setEnvironment(level.getSolidSpriteList());

            displayZoneFrame.addKeyListener(gameEngine);  // Add key listener for game controls

            // Check for game over in the game engine and update render
            gameEngine.setGameOverListener(() -> {
                // If game is over, notify the RenderEngine to display the Game Over screen
                renderEngine.setGameOver(true);  // Set the game over flag
                renderEngine.repaint();  // Redraw the screen
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}

