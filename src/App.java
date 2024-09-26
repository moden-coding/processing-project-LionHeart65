import processing.core.PApplet;

public class App extends PApplet {
    int charX = 0;
    int charY = 0;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        charX = width / 2;
        charY = height / 2;
        background(255);

    }

    public void settings() {
        size(750, 500);
    }

    public void draw() {
        charAndWea();

    }

    public void charAndWea() {
        rect(charX, charY, 20, 50);
        ellipse(charX + 10, charY, 15, 15);
        rect(charX + 5, charY + 25, -30, 5);

    }
}
