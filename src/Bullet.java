import processing.core.PApplet;

public class Bullet {
    PApplet c;
    float shootAngle = 0;
    int bulX = 0;
    int bulY = 0;
    float bulletSpeed = 5;
    
    public void Enemy(int X, int Y, PApplet c) {
        this.c = c;
        this.bulX = X;
        this.bulY = Y;
    }

    public void shoot() {

        c.fill(0);
        c.rect(bulX, bulY, 5, 5);
        bulX += bulletSpeed * PApplet.cos(shootAngle);
        bulY += bulletSpeed * PApplet.sin(shootAngle);
        c.fill(255);
        if (bulX > c.width || bulX < 0 || bulY > c.height || bulY < 0) {

        }

    }
}
