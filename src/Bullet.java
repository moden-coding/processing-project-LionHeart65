import processing.core.PApplet;

public class Bullet {
    private PApplet c;
    private float shootAngle = 0;
    int bulX = 0;
    private int bulY = 0;
    private float bulletSpeed = 5;
    private boolean remove = false;
    
    public Bullet(int X, int Y, float shootAngle, PApplet c) {
        this.c = c;
        this.bulX = X;
        this.bulY = Y;
        this.shootAngle = shootAngle;
    }

    public int getPos(char axis) {
        if (axis == 'X') {
            return Math.round(bulX);
        } else if (axis == 'Y') {
            return Math.round(bulY);
        } else {
            return 0;
        }
    }
    public boolean getRemove() {
        return remove;
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
