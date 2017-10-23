package mapareas;

/**
 * Created by Jawwad on 2/19/2017.
 */

public class MapAreaMeasure {

    public static enum Unit {pixels, meters}

    public double value;
    public Unit unit;

    public MapAreaMeasure(double value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

}
