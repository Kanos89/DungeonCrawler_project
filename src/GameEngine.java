import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener {
    private DynamicSprite hero;
    private Playground playground;
    private boolean gameOver = false;
    private Runnable gameOverListener;

    public GameEngine(DynamicSprite hero, Playground playground) {
        this.hero = hero;
        this.playground = playground;
    }

    @Override
    public void update() {
        if (hero.getHP() <= 0) {
            // If HP is 0 or less, stop the game
            gameOver = true;
            if (gameOverListener != null) {
                gameOverListener.run();  // Notify the listener (RenderEngine)
            }
            return;  // Stop the game update
        }
        // Check for traps and apply damage
        for (Displayable d : playground.getSpriteList()) {  // Iterate over Displayable objects
            if (d instanceof Sprite) {  // Check if the Displayable is actually a Sprite
                Sprite s = (Sprite) d;  // Cast to Sprite

                // Check if the sprite is a GrassTrap and apply damage if hero is on it
                if (s instanceof GrassTrap) {
                    GrassTrap trap = (GrassTrap) s;
                    if (hero.getHitBox().intersects(trap.getHitBox())) {
                        trap.applyDamage(hero);
                    }
                }
            }
        }
    }

    public void setGameOverListener(Runnable listener) {
        this.gameOverListener = listener;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // This method doesn't need to do anything for now
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Change hero direction based on key input
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
                hero.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_DOWN:
                hero.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:
                hero.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                hero.setDirection(Direction.EAST);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Optional, handle key releases if necessary
    }
}
