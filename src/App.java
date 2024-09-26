import processing.core.PApplet;

public class App extends PApplet {

    int charX = 0;
    int charY = 0;
    int dir = 1;
    float speed = 10;
    color bg = color(255, 255, 255);
    float shootAngle = 0;
    int shootX = 0;
    int shootY = 0;
    float bulletSpeed = 100;
    

    public void setup() {
        charX = width / 2;
        charY = height / 2;
        background(bg);

    }

    public void settings() {
        size(750, 500);
    }

    public void draw() {
        background(bg);
        charAndWea();

    }

    public void charAndWea() {
    
        rect(charX, charY, 20, 50);
        ellipse(charX + 10, charY, 15, 15);
        // rect(charX + 5, charY + 20, 30 * dir, 5);
        float angle = atan2(mouseY - (charX + 5), mouseX - (charY + 20));
        translate((charX + 5), (charY + 20));
        rotate(angle);
        rect(0, 0, 30*dir, 5);
        shootAngle = atan2(((0 - 30)/ 2), ((0 + 30)/ 2));
        resetMatrix();
        shootX = charX + 5;
        shootY = charY + 20;
        // TODO fix weird drift
        
    }
    public void shoot() {
        fill(0);
        rect(shootX, shootY, 5, 5);

        fill(255);
        
    }

    public void keyPressed() {
        if (key == 'w') {
            charY -= speed;

        } else if (key == 's') {
            charY += speed;

        } else if (key == 'a') {
            charX -= speed;
            dir = -1;

        } else if (key == 'd') {
            charX += speed;
            dir = 1;

        }
    }
}
