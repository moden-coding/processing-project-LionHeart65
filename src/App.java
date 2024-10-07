import java.util.ArrayList;

import processing.core.PApplet;

//random freezing
// TODOs: high score system, game over/restart screen, diffrent waves, diff enemies,

public class App extends PApplet {

    int charX = 0;
    int charY = 0;
    int lr = 1;
    int ud = 1;
    float speed = 5;
    int bg = color(59, 47, 30);
    float shootAngle = 0;
    int bulX = 0;
    int bulY = 0;
    float bulletSpeed = 5;
    boolean isShoot = false;
    ArrayList<Enemy> Enemies = new ArrayList<>();
    ArrayList<Bullet> Bullets = new ArrayList<>();
    int gameCode = 0;
    int hearts = 3;
    int score = 0;
    int wave = 0;
    int waveSpawns = 0; // was getting out of hand too quickly
    boolean moveXPos = false;
    boolean moveXNeg = false;
    boolean moveYPos = false;
    boolean moveYNeg = false;
    int highScore = 0;
    int shootSpeed = 30;
    int money = 0;
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
    int shootFrames = 0;
    boolean lostLife = false;
    boolean shot = false;

    public void draw() {
        switch (gameCode) {
            case 0:
                menu();
                break;
            case 1:
                instr();
                break;
            case 2:
                play();
                break;
            case 3:
                shop();
        }
    }

    public void play() {

        background(bg);
        charAndWea();
        lives();
        fill(0);
        textSize(35);
        text("Score: " + score, 15, 35);
        text("Wave: " + wave, 15, 70);

        bulX = charX + 5;
        bulY = charY + 20;

        if (moveXPos) {// D
            charX += speed;
        }
        if (moveXNeg) {// A
            charX -= speed;
        }
        if (moveYPos) { // S
            charY += speed;
        }
        if (moveYNeg) { // W
            charY -= speed;
        }
        if (moveXNeg && moveYNeg || moveXNeg && moveYPos || moveXPos && moveYNeg || moveXPos && moveYPos
                || moveYNeg && moveXNeg || moveYNeg && moveXPos || moveYPos && moveXNeg || moveYPos && moveXPos) {
            speed = sqrt(8);
        } else {
            speed = 4;
        }
        if (frameCount % 180 == 0 && waveSpawns <= 5) {
            // makes new enemy every 3 seconds
            for (int i = 0; i < wave; i++) {
                Enemies.add(new Enemy(randX(), randY(), this));
            }
            waveSpawns++;

        }

        for (int i = 0; i < Enemies.size(); i++) {

            Enemies.get(i).move(charX, charY);

            // allows player to lose hearts
            if (dist(Enemies.get(i).selfX, Enemies.get(i).selfY, charX, charY) < 50 && !lostLife) {
                hearts--;
                lostLife = true;
            }
            // allows enemies to die

            for (Bullet bullet : Bullets) {
                if (dist(Enemies.get(i).selfX, Enemies.get(i).selfY + 20, bullet.bulX, bullet.bulY) < 25) {
                    Enemies.remove(Enemies.get(i));
                    // interesting, couldn't remove wirth bullet because couldn't accses index
                    bullet.remove();
                    money += 10;
                    score += 100;
                }
            }

        }
        // gives three seconds of invincablity after losing a life
        if (lostLife) {
            iFrames++;
        }
        if (shot) {
            shootFrames++;
        }
        if (iFrames >= 180) {
            lostLife = false;
            iFrames = 0;
        }
        if (shootFrames >= shootSpeed) {
            shot = false;
            shootFrames = 0;
        }
        for (int i = 0; i < Bullets.size(); i++) {
            Bullets.get(i).shoot();
            if (Bullets.get(i).bulX > width || Bullets.get(i).bulX < 0 || Bullets.get(i).bulY > height
                    || Bullets.get(i).bulY < 0 || Bullets.get(i).remove) {
                Bullets.remove(Bullets.get(i));
            }
        }
        if (score == 500 * wave) {
            wave++;
            waveSpawns = 0;
        }
    }

    public void menu() {
        background(bg);
        ellipse(width - 60, 45, 50, 50);
        fill(150);
        rect((width / 2) - 250, (height / 2) - 100, 500, 200);
        textSize(100);
        fill(0);
        text("Play", (width / 2) - 100, (height / 2) + 35);
        fill(100);
        rect((width / 2) - 200, (height / 2) + 125, 400, 75);
        fill(0);
        textSize(50);
        text("Shop", (width / 2) - 60, (height / 2) + 175);
        textSize(35);
        text("High Score: " + highScore, 15, 35);
        text("Money: " + money, 15, 70);
        textSize(50);
        text("i", width - 67, 63);
        fill(255);
        
        
    }

    public void instr() {
        background(bg);
        textSize(75);
        text("1. Move with WSAD", 50, 75);
        text("2. Shoot with the Space Bar", 50, 135);
        rect(width - 90, 20, 50, 50);
        fill(0);
        textSize(50);
        text("H", width - 80, 63);
        fill(255);

    }

    public void shop() {
        background(bg);
        rect(width - 90, 20, 50, 50);
        fill(0);
        text("H", width - 80, 63);
        fill(255);
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
            if (score > highScore) {
                highScore = score;
            }
            score = 0;
            wave = 1;
            hearts = 3;
            // intresting, remopving made less enemies, equlized at three, positions in
            // array list changed. was a cool fix, then i discorvered .clear
            Enemies.clear();
            iFrames = 0;
            shootFrames = 0;
            lostLife = false;
            shot = false;
            waveSpawns = 0;
            gameCode = 0;
            charX = width / 2;
            charY = height / 2;
        }
        fill(255);
    }

    public void keyPressed() {

        if (key == 'w') {
            // makes the char move, makes the gun shoot the right way, makes the char face
            // the right way. shoots if space is pressed
            moveYNeg = true;
            ud = 1;
            if (!isShoot) {
                shootAngle = (float) 4.71239;
            }
        } else if (key == 's') {

            moveYPos = true;
            ud = -1;
            if (!isShoot)

            {
                shootAngle = (float) 1.5708;
            }
        } else if (key == 'a') {
            moveXNeg = true;
            lr = -1;
            ud = 0;
            if (!isShoot)

            {
                shootAngle = (float) 3.14159;
            }
        } else if (key == 'd') {
            moveXPos = true;
            lr = 1;
            ud = 0;
            if (!isShoot) {
                shootAngle = 0;
            }

        } else if (key == ' ') {
            if (shot == false) {
                Bullets.add(new Bullet(bulX, bulY, shootAngle, this));
                shot = true;
            }
        }

    }

    public void keyReleased() {
        if (key == 'w') {

            moveYNeg = false;

        } else if (key == 's') {

            moveYPos = false;

        } else if (key == 'a') {
            moveXNeg = false;

        } else if (key == 'd') {
            moveXPos = false;
        }
    }

    public void mouseClicked() {
        if (gameCode == 0) {
            if (mouseX > 500 && mouseX < 1000 && mouseY > 400 && mouseY < 600) {
                gameCode = 2;
            } else if (mouseX > 550 && mouseX < 950 && mouseY > 625 && mouseY < 700) {
                gameCode = 3;
            } else if (dist(mouseX, mouseY, width - 67, 63) < 51) {
                gameCode = 1;
            }
        } else if (gameCode == 1) {
            if (mouseX > 1400 && mouseX < 1450 && mouseY > 20 && mouseY < 70) {
                gameCode = 0;
            }
        } else if (gameCode == 3) {
            if (mouseX > 1400 && mouseX < 1450 && mouseY > 20 && mouseY < 70) {
                gameCode = 0;
            }
        }

    }

    public void lives() {
        // displays the amount of lives as hearts in the upper left
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
