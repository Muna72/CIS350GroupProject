/**
 * A class that tracks the lane of the intersection
 * @author Muna Gigowski
 * @version 1.0
 */
public class Location {

    //Declare all instance variables
    private int row;
    private int col;

    /**
     * Class constructor
     *
     * @param r row
     * @param c column
     */
    public Location(int r, int c) {
        row = r;
        col = c;
    }

    /**
     * Method to set row
     *
     * @param r row
     */
    public void setRow(int r) {
        r = row;
    }

    /**
     * Method to set column
     *
     * @param c column
     */
    public void setCol(int c) {
        c = col;
    }

    /**
     * Method to get row
     *
     * @return row for row
     */
    public int getRow() {
        return row;
    }

    /**
     * Method to get column
     *
     * @return col for column
     */
    public int getCol() {
        return col;
    }
}