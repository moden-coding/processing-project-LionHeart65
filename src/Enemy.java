
import processing.core.PApplet;
import processing.core.PVector;

public class Enemy {
    PVector position = new PVector(0, 0); // current pos.
    PVector charXY; // charpos
    PApplet c; //discriptive enough?
    private int speed = 1;
    private float selfX = 0;
    private float selfY = 0;

    public Enemy(int X, int Y, PApplet c) {
        this.c = c;
        this.selfX = X;
        this.selfY = Y;
    }

    public void display() {
        c.fill(0);
        c.rect(100, 100, 50, 50);
        c.fill(255);
    }

    public void move(int charX, int charY) {

        position = new PVector(selfX, selfY);

        charXY = new PVector(charX, charY);

        PVector direction = PVector.sub(charXY, position);
        c.print(direction);
        direction.normalize();
        c.print("Normalized: " + direction);
        direction.mult(speed);
        c.print("Speeded: " + direction);
        position.add(direction);
        c.print("New pos: " + position);
        selfX = position.x;
        selfY = position.y;
        c.rect(selfX, selfY, 10, 40);
    }


}
