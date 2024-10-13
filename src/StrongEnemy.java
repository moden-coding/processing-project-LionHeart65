import processing.core.PApplet;

public class StrongEnemy extends Enemy {

    private int health = values(0);
    private int[] colors = {values(1), values(2), values(3)};

    public StrongEnemy(int X, int Y, PApplet c) {
        super(X, Y, c);
        
    }
    @Override public int values(int value) {
        switch (value) {
            case 0:
                return 2;

            case 1:
                return 245;
            case 2:
                return 235;
            case 3:
                return 54;


            default:
                return 0;
    
        }
    }
    
}
