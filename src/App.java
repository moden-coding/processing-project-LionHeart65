import java.util.ArrayList;

import processing.core.PApplet;

// start screen and instructions.
// TODOs: fix down shoot flying left, multiple shots, janky movement,, the bad bullet thing, high score system, game over/restart screen, diffrent waves, diff enemies, more bullets

public class App extends PApplet {

    int charX = 0;
    int charY = 0;
    int lr = 1;
    int ud = 1;
    float speed = 10;
    int bg = color(59, 47, 30);
    float shootAngle = 0;
    int bulX = 0;
    int bulY = 0;
    float bulletSpeed = 5;
    boolean isShoot = false;
    ArrayList<Enemy> Enemies = new ArrayList<>();
    ArrayList<Bullet> Bullets = new ArrayList<>();

    int hearts = 3;
    int score = 0;

    public void setup() {
        charX = width / 2;
        charY = height / 2;
        background(bg);

    }

    public void settings() {
        size(1500, 1000);
    }

    // following methods get random (X, Y) values to spawn Enemies in random
    // locations.
    public int randX() {
        if (random(1) > 0.5) {
            float X = random(Float.valueOf(0), Float.valueOf(50));

            return Math.round(X);
        } else {
            float X = random(Float.valueOf(width - 50), Float.valueOf(width));
            return Math.round(X);
        }
    }

    public int randY() {
        if (random(1) > 0.5) {
            float Y = random(Float.valueOf(0), Float.valueOf(50));

            return Math.round(Y);
        } else {
            float Y = random(Float.valueOf(height - 50), Float.valueOf(height));
            return Math.round(Y);
        }

    }

    int iFrames = 0;
    boolean lostLife = false;

    public void draw() {

        background(bg);
        charAndWea();
        lives();
        fill(0);
        textSize(35);
        text("Score: " + score, 15, 35);

        if (!isShoot) {
            bulX = charX + 5;
            bulY = charY + 20;

        } else {
            shoot();
        }
        if (frameCount % 180 == 0) {
            // makes new enemy every 3 seconds
            
            Enemies.add(new Enemy(randX(), randY(), this));
                
            
        }

        for (int i = 0; i < Enemies.size(); i++) {

            Enemies.get(i).move(charX, charY);

            if (isShoot && dist(Enemies.get(i).selfX, Enemies.get(i).selfY, bulX, bulY) < 25) {
                Enemies.remove(Enemies.get(i));
                isShoot = false;
                // allows enemies to die
                score += 100;
            }
            if (dist(Enemies.get(i).selfX, Enemies.get(i).selfY, charX, charY) < 50 && !lostLife) {
                hearts--;
                lostLife = true;
                // allows player to lose hearts
            }

        }
        // gives three seconds of invincablity after losing a life
        if (lostLife) {
            iFrames++;
        }
        if (iFrames >= 180) {
            lostLife = false;
            iFrames = 0;
        }

    }

    public void charAndWea() {
        fill(145, 125, 80);
        rect(charX, charY, 20, 50);
        fill(181, 167, 91);
        ellipse(charX + 10, charY, 15, 15);
        // makes the character and gun face the right ways
        fill(117, 112, 99);
        if (ud == 1) {
            rect(charX + 5, charY + 20, 5, -30);
        } else if (ud == -1) {
            rect(charX + 5, charY + 20, 5, 30);

        } else if (ud == 0) {
            rect(charX + 5, charY + 20, 30 * lr, 5);
        } else
            System.out.println("Cry");
        // System.exit(0);
        if (hearts <= 0) {
            // what to do when player runs out of hearts;
            System.out.println("Game over");
            System.exit(0);
        }
        fill(255);
    }

    public void shoot() {

        fill(0);
        rect(bulX, bulY, 5, 5);
        bulX += bulletSpeed * cos(shootAngle);
        bulY += bulletSpeed * sin(shootAngle);
        fill(255);
        if (bulX > width || bulX < 0 || bulY > height || bulY < 0) {
            isShoot = false;
            // if bullet leaves the map, stop shooting.
        }
        for (int i = 0; i < Bullets.size(); i++) {
            
        }

    }

    public void keyPressed() {
        if (key == 'w') {
            // makes the char move, makes the gun shoot the right way, makes the char face
            // the right way. shoots if space is pressed
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

    public void lives() {
        //displays the amount of lives as hearts in the upper left
        fill(191, 6, 6);
        switch (hearts) {
            case (1):
                rect(width - 100, 35, 25, 25);
                break;
            case (2):
                rect(width - 100, 35, 25, 25);
                rect(width - 66, 35, 25, 25);
                break;
            case (3):
                rect(width - 100, 35, 25, 25);
                rect(width - 66, 35, 25, 25);
                rect(width - 33, 35, 25, 25);
                break;
        }
        fill(255);


    }

    public static void main(String[] args) {
        PApplet.main("App");
    }

}
