import processing.core.PApplet;

public class Bullet {
    PApplet c;
    float shootAngle = 0;
    int bulX = 0;
    int bulY = 0;
    float bulletSpeed = 5;
    boolean remove = false;
    
    public Bullet(int X, int Y, float shootAngle, PApplet c) {
        this.c = c;
        this.bulX = X;
        this.bulY = Y;
        this.shootAngle = shootAngle;
    }

    public void shoot() {

        c.fill(0);
        c.rect(bulX, bulY, 5, 5);
        bulX += bulletSpeed * PApplet.cos(shootAngle);
        bulY += bulletSpeed * PApplet.sin(shootAngle);
        c.fill(255);
    }
    public void remove() {
        remove = true;
    }
}
