
import processing.core.PApplet;
import processing.core.PVector;

public class Enemy {
    private PVector position = new PVector(0, 0); // current pos.
    private PVector charXY; // pos of player
    private PApplet c; // main canvas
    private float speed = 1.5f;
    private float selfX = 0;
    private float selfY = 0;
    private int health = values(0);
    private int[] colors = { values(1), values(2), values(3) }; // color of enemies. should be static?

    public Enemy(int X, int Y, PApplet c) {
        this.c = c;
        this.selfX = X;
        this.selfY = Y;

    }

    // can't set different values in subclasses, so values are set here, 
    // and then variables assigned to functions calls
    public int values(int value) {
        switch (value) {
            // health
            case 0:
                return 1;
            // colors
            case 1:
                return 27;
            case 2:
                return 102;
            case 3:
                return 4;
            // need to return something
            default:
                return 0;

        }
    }

    public int getHealth() {
        return health;
    }

    // finds direction between the character and the enemy, then makes the enemy
    // move along that vector.
    public void move(int charX, int charY) {

        position = new PVector(selfX, selfY);

        charXY = new PVector(charX, charY);

        PVector direction = PVector.sub(charXY, position);
        direction.normalize();
        direction.mult(speed);
        position.add(direction);
        selfX = position.x;
        selfY = position.y;
        c.fill(colors[0], colors[1], colors[2]);
        c.rect(selfX, selfY, 10, 40);
        c.fill(255);
    }

    public void hit() {
        health--;
    }

    // gets pos of enemy to deal with in the App file.
    public int getPos(char axis) {
        if (axis == 'X') {
            return Math.round(selfX);
        } else if (axis == 'Y') {
            return Math.round(selfY);
        } else {
            return 0;
        }
    }

}
