
import processing.core.PApplet;

public class Enemy {
    PApplet canvas;
    private int speed = 3;
    private float charAngle = 0;
    private int selfX = 0;
    private int selfY = 0;
    public Enemy(int X, int Y, PApplet c) {
        this.canvas = c;
        this.selfX = X;
        this.selfY = Y;
    }

    public void display(){
        
    }
    public move(charX, charY) {
        charAngle = atan2(charX - selfX, charY - selfY);
        selfX += speed * cos(charAngle);
        selfY += speed * sin(charAngle);
        rect(selfX, selfY, 10, 40);
    }

}
        

