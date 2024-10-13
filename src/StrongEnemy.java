import processing.core.PApplet;
@SuppressWarnings("unused") // gets rid of incorrect warnings

public class StrongEnemy extends Enemy {

    private int health = values(0);
    private int[] colors = { values(1), values(2), values(3) }; // color of enemies. should be static?

    public StrongEnemy(int X, int Y, PApplet c) {
        super(X, Y, c);

    }

    // can't set different values in subclasses, so values are set here,
    // and then variables assigned to functions calls
    @Override
    public int values(int value) {
        switch (value) {
            // health
            case 0:
                return 2;
            // colors
            case 1:
                return 245;
            case 2:
                return 235;
            case 3:
                return 54;

            // need to return something
            default:
                return 0;

        }
    }

}
