import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

// Features: game over/restart screen, diffrent waves, diff enemies, mrmr. moden black hole thing

public class App extends PApplet {

    int charX = 0;
    int charY = 0;
    int lr = 1; // is char facing left or right, 1 is right, -1 is left
    int ud = 1; // is char facing up or down, 1 is up, -1 is down
    float speed = 1; // speed of the player, adjusted to move diagonally
    float speedStat = 3; // raw speed of the player
    int bg = color(59, 47, 30); // background color
    float shootAngle = 0; // angle to shoot at
    int bulX = 0; // coords to make new bullets at
    int bulY = 0;
    float bulletSpeed = 5;
    ArrayList<Enemy> Enemies = new ArrayList<>(); // list of all enemies
    ArrayList<Bullet> Bullets = new ArrayList<>(); // list of all bullets
    int gameCode = 0; // what scene is the game on, 0 is menu, 1 is instructions, 2 is game, 3 is shop
    int hearts = 3; // how many lives the player starts with
    int lives = 3; // how many lives the player currently has
    int score = 0;
    int wave = 1;
    int waveSpawns = 0; // how many enemies were spawned this wave
    boolean moveXPos = false; // is the player moving in these directions
    boolean moveXNeg = false;
    boolean moveYPos = false;
    boolean moveYNeg = false;
    int highScore = 0;
    int shootSpeed = 30; // how many frames between shots
    int money = 0;
    int damage = 1;
    int shopCode = 1; // what part of the shop to show
    PImage wipBgImg; // background image for the unfinished shop.

    public void setup() {
        charX = width / 2; // sets player starting coords.
        charY = height / 2;
        background(bg);
        wipBgImg = loadImage("wipBg.jpg");

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

    int iFrames = 0; // frames since player lost a life.
    int shootFrames = 0; // frames since the player shot
    boolean lostLife = false; // has the player lost a life within 3 secs
    boolean shot = false; // is the gun on cooldown

    public void draw() {
        // decides which scene to show based on game code value.
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
                break;
        }
    }

    // main game function, where the playing happens. 1
    public void play() {

        background(bg);
        charAndWea();
        lives();
        fill(0);
        textSize(35);
        text("Score: " + score, 15, 35);
        text("Wave: " + wave, 15, 70);

        // sets the position to make new bullets
        bulX = charX + 5;
        bulY = charY + 20;

        // moves the player
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
        // prevents the player from going super fast if moving diagonally.
        if (moveXNeg && moveYNeg || moveXNeg && moveYPos || moveXPos && moveYNeg || moveXPos && moveYPos
                || moveYNeg && moveXNeg || moveYNeg && moveXPos || moveYPos && moveXNeg || moveYPos && moveXPos) {
            speed = speedStat / sqrt(2);
        } else {
            speed = speedStat;
        }
        // makes new enemy every 3 seconds, but only if it hasn't spawned more than 5
        // times each wave
        if (frameCount % 180 == 0 && waveSpawns <= 5) {
            // spawns more enemies depending on the wave
            for (int i = 0; i < wave; i++) {
                // chooses if a new spawn should be strong or normal
                if (random(1, 5) > 4) {
                    Enemies.add(new StrongEnemy(randX(), randY(), this));
                } else {
                    Enemies.add(new Enemy(randX(), randY(), this));
                }
            }
            waveSpawns++;

        }

        // handles all things enemy related
        for (int i = 0; i < Enemies.size(); i++) {

            Enemies.get(i).move(charX, charY);

            // allows player to lose hearts, if player is within 50 pixels and isn't
            // invincible
            if (dist(Enemies.get(i).getPos('X'), Enemies.get(i).getPos('Y'), charX, charY) < 50 && !lostLife) {
                lives--;
                lostLife = true;
            }

            // allows enemies to die, raises score and money when an enemy dies
            for (Bullet bullet : Bullets) {
                // checks if each bullet is within 25 pixels of the enemy
                if (dist(Enemies.get(i).getPos('X'), Enemies.get(i).getPos('Y') + 20, bullet.getPos('X'),
                        bullet.getPos('Y')) < 25) {
                    Enemies.get(i).hit();
                    if (Enemies.get(i).getHealth() == 0) {
                        Enemies.remove(Enemies.get(i));
                    }

                    bullet.remove();
                    money += 10;
                    score += 100;
                }
            }

        }
        // next two if statments gives three seconds of invincibility after losing a
        // life

        // if the player is invincable, make a timer increase
        if (lostLife) {
            iFrames++;
        }
        // if it has been 3 seconds (180 frames) since the player has lost a life,
        // resets the timer and makes it vulnerable
        if (iFrames >= 180) {
            lostLife = false;
            iFrames = 0;
        }

        // next two if statments make a delay between shooting

        // timer if the gun has been shot
        if (shot) {
            shootFrames++;
        }

        // after a certian amount of frames, stops the timer and allows to shoot again
        if (shootFrames >= shootSpeed) {
            shot = false;
            shootFrames = 0;
        }
        // removes bullets if they leave the map to save memory
        for (int i = 0; i < Bullets.size(); i++) {
            Bullets.get(i).shoot();
            if (Bullets.get(i).getPos('X') > width || Bullets.get(i).getPos('X') < 0
                    || Bullets.get(i).getPos('Y') > height
                    || Bullets.get(i).getPos('Y') < 0 || Bullets.get(i).getRemove()) {
                Bullets.remove(Bullets.get(i));
            }
        }
        // if all enemies on the wave have been killed, go to the next wave.
        if ((score) == waveScore(wave)) {
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
        // guns, stats, equipment, ammo? power ups???
        background(bg);
        rect(width - 90, 20, 50, 50);
        fill(0);
        text("H", width - 80, 63);
        textSize(35);
        text("Money: " + money, 15, 35);
        textSize(50);
        fill(100);
        rect(100, 100, 1300, 800);
        fill(150);
        strokeWeight(5);
        rect(100, 100, 433, 100);
        rect(533, 100, 433, 100);
        rect(966, 100, 433, 100);
        fill(0);
        text("Weapons", 225, 170);
        text("Stats", 680, 170);
        text("Equipment", 1050, 170);
        strokeWeight(1);
        fill(255);
        // chooses which part of the shop to show
        switch (shopCode) {
            case 0:
                wipBg();
                break;

            case 1:
                fill(75);
                rect(150, 225, 1200, 100);
                rect(150, 335, 1200, 100);
                rect(150, 445, 1200, 100);
                rect(150, 555, 1200, 100);

                // shows a bar for how many upgrades have been purchased
                upgradeBar(Float.valueOf(hearts - 3), 450, 240, 15, color(191, 6, 6));
                upgradeBar(speedStat - 1, 450, 350, 7, color(255));
                fill(0);
                text("More hearts", 160, 290);
                // shows current price of a new heart, or if it is maxed out
                if (hearts < 18) {
                    text((hearts * 200) - 400 + "$", 1225, 290);
                } else {
                    text("Max", 1255, 290);
                }

                text("More speed", 160, 400);
                // shows current price of more speed, or if it is maxed out

                if (speedStat <= 8) {
                    text(Math.round(((speedStat * 10) * 50)) + "$", 1225, 400);
                } else {
                    text("Max", 1225, 400);
                }
                text("More hearts", 160, 510);
                text(hearts * 200 + "$", 1225, 510);
                text("More hearts", 160, 620);
                text(hearts * 200 + "$", 1225, 620);

                fill(255);

                break;
            case 2:
                wipBg();
                break;
        }
        fill(255);

    }

    public void wipBg() {
        image(wipBgImg, 100, 200, 1300, 700);
        fill(150);
        rect(450, 400, 700, 200);
        fill(0);
        textSize(100);
        text("Coming Soon TM", 450, 550);
        fill(255);
        textSize(50);
    }

    // creates the char and the gun on the screen
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
        }

        // what to do when player runs out of hearts; resets all stats, the char pos,
        // and removes all enemies and bullets
        if (lives <= 0) {
            if (score > highScore) {
                highScore = score;
            }
            score = 0;
            wave = 1;
            Enemies.clear();
            iFrames = 0;
            shootFrames = 0;
            lostLife = false;
            shot = false;
            waveSpawns = 0;
            gameCode = 0;
            charX = width / 2;
            charY = height / 2;
            lives = hearts;
        }
        fill(255);
    }

    // handles all keyboard inputs
    public void keyPressed() {

        // next 4 if statments make the char move, the gun shoot the right way, and the
        // char and gun face
        // the right way.
        if (key == 'w') {

            moveYNeg = true;
            ud = 1;

            shootAngle = (float) 4.71239;

        } else if (key == 's') {

            moveYPos = true;
            ud = -1;

            shootAngle = (float) 1.5708;

        } else if (key == 'a') {
            moveXNeg = true;
            lr = -1;
            ud = 0;

            shootAngle = (float) 3.14159;

        } else if (key == 'd') {
            moveXPos = true;
            lr = 1;
            ud = 0;

            shootAngle = 0;

            // shoots if the spacebar is pressed
        } else if (key == ' ') {
            if (shot == false) {
                Bullets.add(new Bullet(bulX, bulY, shootAngle, this));
                shot = true;
            }
        }

    }

    // when movement keys are lifted, stops the char from moving
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

    // handles when the mouse is clicked
    public void mouseClicked() {
        // different actions depending on what scene is active
        if (gameCode == 0) {
            if (mouseX > 500 && mouseX < 1000 && mouseY > 400 && mouseY < 600) {
                gameCode = 2; // starts game
            } else if (mouseX > 550 && mouseX < 950 && mouseY > 625 && mouseY < 700) {
                gameCode = 3; // goes to shop
            } else if (dist(mouseX, mouseY, width - 67, 63) < 51) {
                gameCode = 1; // goes to instructions
            }
        } else if (gameCode == 1) {
            if (mouseX > 1400 && mouseX < 1450 && mouseY > 20 && mouseY < 70) {
                gameCode = 0; // goes home
            }
        } else if (gameCode == 3) {
            if (mouseX > 1400 && mouseX < 1450 && mouseY > 20 && mouseY < 70) {
                gameCode = 0; // goes home
            } else if (mouseX > 100 && mouseX < 533 && mouseY > 100 && mouseY < 200) {
                shopCode = 0; // goes to weapons section
            } else if (mouseX > 533 && mouseX < 966 && mouseY > 100 && mouseY < 200) {
                shopCode = 1; // goes to stats section
            } else if (mouseX > 966 && mouseX < 1300 && mouseY > 100 && mouseY < 200) {
                shopCode = 2; // goes to equipment section
            }
            // buys upgrades based of shop section
            if (shopCode == 1) {
                // buys more hearts
                if (mouseX > 150 && mouseX < 436 && mouseY > 225 && mouseY < 322) {
                    if (money >= (hearts * 200) - 400 && hearts <= 17) {
                        money -= (hearts * 200) - 400;
                        hearts += 1;
                        lives = hearts;
                    }
                    // buys more speed
                } else if (mouseX > 150 && mouseX < 436 && mouseY > 335 && mouseY < 432) {
                    if (money >= Math.round((speedStat * 10) * 50) && speed <= 8) {
                        money -= Math.round((speedStat * 10) * 50);
                        speedStat += 0.1;
                        speed = speedStat;
                    }
                }
            }

        }

    }

    // displays the amount of lives as hearts in the upper left
    public void lives() {

        fill(191, 6, 6);
        switch (lives) {
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
            default:
                text(lives + "X ", width - 100, 55);
                rect(width - 33, 35, 25, 25);
                break;
        }
        fill(255);

    }

    // calculates what score is needed to move to the next level
    public int waveScore(int wave) {
        int score = 0;
        for (int i = 1; i <= wave; i++) {
            score += 500 * i;
        }
        return score;
    }

    // creates bars to show how many of an upgrade have been bought
    public void upgradeBar(float stat, int X, int Y, int max, int color) {
        fill(150);
        rect(X, Y, 490, 70);
        fill(color);
        rect(X, Y, (500 / max) * stat, 70);
        System.out.println(String.format("Max: %f, Stat: %f,", Float.valueOf(max), stat));
        fill(255);
    }

    public static void main(String[] args) {
        PApplet.main("App");
    }

}
