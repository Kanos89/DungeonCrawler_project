import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite {
    private Direction direction = Direction.EAST;
    private double speed = 5;
    private double timeBetweenFrame = 250;
    private boolean isWalking = true;
    private final int spriteSheetNumberOfColumn = 10;
    protected int hp;
    private int maxHp;


    public DynamicSprite(double x, double y, Image image, double width, double height, int maxHP) {
        super(x, y, image, width, height);
        this.maxHp = maxHP;
        this.hp = maxHp;
    }

    @Override
    public Rectangle2D getHitBox() {
        // Adjust the hitbox to be the bottom 30% of the sprite's height
        double hitBoxHeight = super.getHitBox().getHeight() * 0.3;  // 30% of the total height
        double hitBoxY = super.getHitBox().getY() + super.getHitBox().getHeight() * 0.7;  // Starts at 70% down

        return new Rectangle2D.Double(x, hitBoxY, width, hitBoxHeight);
    }


    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
        }
    }

    public int getHP() {
        return hp;
    }



    // Check if the sprite can move
    private boolean isMovingPossible(ArrayList<Sprite> environment) {
            // Create a moved rectangle (hitbox for movement detection)
            Rectangle2D.Double moved = new Rectangle2D.Double();

            // Adjust hitbox to be 0.3 of the sprite's height
            double hitBoxHeight = super.getHitBox().getHeight() * 0.3;
            double hitBoxY = super.getHitBox().getY() + super.getHitBox().getHeight() * 0.7; // Lower the hitbox by 1/4

            // Update the moved rectangle based on the direction and new hitbox dimensions
            switch (direction) {
                case EAST:
                    moved.setRect(super.getHitBox().getX() + speed, hitBoxY,
                            super.getHitBox().getWidth(), hitBoxHeight);
                    break;
                case WEST:
                    moved.setRect(super.getHitBox().getX() - speed, hitBoxY,
                            super.getHitBox().getWidth(), hitBoxHeight);
                    break;
                case NORTH:
                    moved.setRect(super.getHitBox().getX(), hitBoxY - speed,
                            super.getHitBox().getWidth(), hitBoxHeight);
                    break;
                case SOUTH:
                    moved.setRect(super.getHitBox().getX(), hitBoxY + speed,
                            super.getHitBox().getWidth(), hitBoxHeight);
                    break;
            }

        // Check for collisions with other solid sprites
        for (Sprite s : environment) {
            if ((s instanceof SolidSprite) && (s != this)) {
                if (((SolidSprite) s).intersect(moved)) {
                    return false; // Can't move if there's a collision
                }
            }
        }

        return true; // Can move if no collision detected
    }


    // Set the direction of movement
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    // Move the sprite
    private void move() {
        switch (direction) {
            case NORTH -> this.y -= speed;
            case SOUTH -> this.y += speed;
            case EAST -> this.x += speed;
            case WEST -> this.x -= speed;
        }
    }

    // Move if possible (check for collisions)
    public void moveIfPossible(ArrayList<Sprite> environment) {
        if (isMovingPossible(environment)) {
            move();
        }
    }

    @Override
    public void draw(Graphics g) {
        int index = (int) (System.currentTimeMillis() / timeBetweenFrame % spriteSheetNumberOfColumn);
        g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                (int) (index * this.width), (int) (direction.getFrameLineNumber() * height),
                (int) ((index + 1) * this.width), (int) ((direction.getFrameLineNumber() + 1) * this.height), null);
    }
}
