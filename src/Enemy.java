
import processing.core.PApplet;
import processing.core.PVector;

public class Enemy {
    private PVector position = new PVector(0, 0); // current pos.
    private PVector charXY; // charpos
    private PApplet c; //discriptive enough?
    private float speed = 1.5f;
    private float selfX = 0; //need to ask if this is ok, its probaly not though. Feels simiplier than a function though
    private float selfY = 0;
    private int health = values(0);
    private int[] colors = {values(1), values(2), values(3)};
    
    public Enemy(int X, int Y, PApplet c) {
        this.c = c;
        this.selfX = X;
        this.selfY = Y;
        
    }
    

    public int values(int value) {
        switch (value) {
            case 0:
                return 1;

            case 1:
                return 27;
            case 2:
                return 102;
            case 3:
                return 4;


            default:
                return 0;
    
        }
    }

    public int getHealth() {
        return health;
    }
    public void move(int charX, int charY) {

        position = new PVector(selfX, selfY);

        charXY = new PVector(charX, charY);

        //finds direction between the character and the enemy, then makes the enemy move along that vector.
        PVector direction = PVector.sub(charXY, position);
        direction.normalize();
        direction.mult(speed);
        position.add(direction);
        selfX = position.x;
        selfY = position.y;
        c.fill(colors[0],colors[1],colors[2]);
        c.rect(selfX, selfY, 10, 40);
        c.fill(255);
    }

    public void hit() {
        health--;
    }
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
