import java.util.ArrayList;

import processing.core.PApplet;



public class App extends PApplet {

    int charX = 0;
    int charY = 0;
    int lr = 1;
    int ud = 1;
    float speed = 10;
    int bg = color(255, 255, 255);
    float shootAngle = 0;
    int bulX = 0;
    int bulY = 0;
    float bulletSpeed = 5;
    boolean isShoot = false;
    ArrayList<Enemy> Enemies = new ArrayList<>();

    public void setup() {
        charX = width / 2;
        charY = height / 2;
        background(bg);

    }

    public void settings() {
        size(1500, 1000);
    }
    
    public int randX() {
        if (random(1) > 0.5) {
            float X = random(Float.valueOf(0), Float.valueOf(50));

            return Math.round(X);
        } else {
            float X = random(Float.valueOf(width-50), Float.valueOf(width));
            return Math.round(X);
        }
    }
    public int randY() {
        if (random(1) > 0.5) {
            float Y = random(Float.valueOf(0), Float.valueOf(50));

            return Math.round(Y);
        } else {
            float Y = random(Float.valueOf(height-50), Float.valueOf(height));
            return Math.round(Y);
        }
        
    }

    public void draw() {

        background(bg);
        charAndWea();
        if (!isShoot) {
            bulX = charX + 5;
            bulY = charY + 20;
        } else {
            shoot();
        }
        if (frameCount % 180 == 0) {
            
            Enemies.add(new Enemy(randX(),randY(),this));
        }
        for (Enemy enemy : Enemies) {
            enemy.move(charX, charY);
            // enemy.display();

        }
        
    }

    public void charAndWea() {

        rect(charX, charY, 20, 50);
        ellipse(charX + 10, charY, 15, 15);
        if (ud == 1) {
            rect(charX + 5, charY + 20, 5, -30);
        } else if (ud == -1) {
            rect(charX + 5, charY + 20, 5, 30);

        } else if (ud == 0) {
            rect(charX + 5, charY + 20, 30 * lr, 5);
        } else
            System.out.println("Cry");
        // System.exit(0);
    }


    public void shoot() {

        fill(0);
        rect(bulX, bulY, 5, 5);
        bulX += bulletSpeed * cos(shootAngle);
        bulY += bulletSpeed * sin(shootAngle);
        fill(255);
        if (bulX > width || bulX < 0 || bulY > height || bulY < 0) {
            isShoot = false;
        }
        // fix down shoot flying left

    }

    public void keyPressed() {
        if (key == 'w') {
            charY -= speed;
            ud = 1;
            if (!isShoot) {
                shootAngle = (float) 4.71239;
            }
        } else if (key == 's') {
            charY += speed;
            ud = -1;
            if (!isShoot)

            {
                shootAngle = (float) 1.5708;
            }
        } else if (key == 'a') {
            charX -= speed;
            lr = -1;
            ud = 0;
            if (!isShoot)

            {
                shootAngle = (float) 3.14159;
            }
        } else if (key == 'd') {
            charX += speed;
            lr = 1;
            ud = 0;
            if (!isShoot) {
                shootAngle = 0;
            }

        } else if (key == ' ') {
            isShoot = true;
        }
    }

    public static void main(String[] args) {
        PApplet.main("App");
    }

}
