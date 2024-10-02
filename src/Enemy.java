
import processing.core.PApplet;
import processing.core.PVector;

public class Enemy {
    PVector position = new PVector(0, 0); // current pos.
    PVector charXY; // charpos
    PApplet c; //discriptive enough?
    private float speed = 1.5f;
    public float selfX = 0; //need to ask if this is ok, its probaly not though. Feels simiplier than a function though
    public float selfY = 0;

    public Enemy(int X, int Y, PApplet c) {
        this.c = c;
        this.selfX = X;
        this.selfY = Y;
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
        c.fill(27, 102, 4);
        c.rect(selfX, selfY, 10, 40);
        c.fill(255);
    }


}
