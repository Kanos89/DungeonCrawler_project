import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GrassTrap extends Sprite {
    private static final int DAMAGE_PER_HALFSECOND = 1;  // Damage dealt every second
    private long lastDamageTime = 0;  // Time of last damage dealt

    public GrassTrap(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);  // Trap just needs to be a sprite with a hitbox
    }

    public Rectangle2D getHitBox() {

        return new Rectangle2D.Double(x,y,(double) width,(double) height);
    }

    // Apply damage if the hero is stepping on the trap
    public void applyDamage(DynamicSprite hero) {
        // Only deal damage every halff second
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastDamageTime >= 500) {  // 0.5 sec
            hero.takeDamage(DAMAGE_PER_HALFSECOND);
            lastDamageTime = currentTime;
        }
    }
}

