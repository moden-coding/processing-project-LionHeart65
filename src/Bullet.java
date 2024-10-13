import processing.core.PApplet;

public class Bullet {
    private PApplet c; // main canvas
    private float shootAngle = 0; // angle the bullet should be shot at
    private int bulX = 0;
    private int bulY = 0;
    private float bulletSpeed = 5;
    private boolean remove = false; // should the bullet get removed this frame?

    public Bullet(int X, int Y, float shootAngle, PApplet c) {
        this.c = c;
        this.bulX = X;
        this.bulY = Y;
        this.shootAngle = shootAngle;
    }
    
    // gets pos of bullet to deal with in the App file.
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

    // moves the bullet by bullet speed at the shootAngle
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
