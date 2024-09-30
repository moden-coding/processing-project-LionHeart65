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

    public void setup() {
        charX = width / 2;
        charY = height / 2;
        background(bg);

    }

    public void settings() {
        size(750, 500);
    }
    public void enemieCreate() {

    }
    int count;
    
    public void draw() {

        background(bg);
        charAndWea();
        if (!isShoot) {
            bulX = charX + 5;
            bulY = charY + 20;
        } else {
            shoot();
        }
        Enemy e = new Enemy(5,5,this);
        e.move(charX, charY);
        
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
        System.out.println(shootAngle);
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
