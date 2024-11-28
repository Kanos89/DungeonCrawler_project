import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {
    private ArrayList<Displayable> renderList;
    private DynamicSprite hero; // Add the hero here
    private BufferedImage heartImage; // Add the health bar image
    private boolean gameOver = false;  // Flag for game over

    // Health bar settings
    private final int HEART_LINE_WIDTH = 360;
    private final int HEART_LINE_HEIGHT = 72;

    public RenderEngine(JFrame jFrame, DynamicSprite hero) {
        this.hero = hero;  // Set the hero
        renderList = new ArrayList<>();

        try {
            heartImage = javax.imageio.ImageIO.read(new File("./img/hearts.png"));  // Load the heart image for health bar
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToRenderList(Displayable displayable) {
        if (!renderList.contains(displayable)) {
            renderList.add(displayable);
        }
    }

    public void addToRenderList(ArrayList<Displayable> displayables) {
        for (Displayable displayable : displayables) {
            if (!renderList.contains(displayable)) {
                renderList.add(displayable);
            }
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (gameOver) {
            //set a black background
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            // Draw Game Over message at the center of the screen
            String gameOverMessage = "GAME OVER";
            Font font = new Font("Arial", Font.BOLD, 50);
            g.setFont(font);
            g.setColor(Color.RED);
            FontMetrics metrics = g.getFontMetrics(font);
            int x = (getWidth() - metrics.stringWidth(gameOverMessage)) / 2;
            int y = getHeight() / 2;
            g.drawString(gameOverMessage, x, y);


        }
        else {
                // Draw all the render objects (e.g., level, sprites, etc.)
                for (Displayable renderObject : renderList) {
                    renderObject.draw(g);
                }
        }

        // Draw the health bar (heart image)
        drawHealthBar(g);
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;  // Set game over flag
    }

    public void drawHealthBar(Graphics g) {
        int x = 10;  // Horizontal position of the health bar
        int y = 10;  // Vertical position of the health bar

        // Draw the heart image corresponding to the HP (adjust Y position based on HP)
        if (hero.getHP()!=0) {
            g.drawImage(heartImage.getSubimage(0, (5 - hero.getHP()) * HEART_LINE_HEIGHT, HEART_LINE_WIDTH, HEART_LINE_HEIGHT),
                    x, y, null);
        }
    }

    @Override
    public void update() {
        this.repaint(); // Repaint the screen, updating the health bar
    }
}
