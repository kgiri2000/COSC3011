
import java.util.ArrayList;
import java.util.List;

/**
 * PieceData is a dataclass for the coordinates of the lines for the Piece. The
 * default.mze file defines all coordinates of all lines for all pieces. These
 * lines are used to make parts of a maze, each piece will have its own maze
 * part.
 * <p>
 * The default.mze file gives the tile number and the number of lines. Then for
 * each line there is a set of 4 coordinates.
 * <p>
 * This class stores all the data for an individual tile. The coordinates are
 * stored in a single dimension list, since we know there are 4 coordinates per
 * line there is no need to have a dynamic 2D list
 * 
 * @author Sebastian Colwell
 * @version %I%, %G%
 * @since 3.0
 */
public class PieceData implements Cloneable {
  private int tileNum;
  private int numLines;
  private int rotation = 3;
  private List<Integer> coords = new ArrayList<Integer>();

  /**
   * The constructor requires the tile number and number of lines The
   * coordinates are set as the data is read, so they are not required in the
   * constructor
   * 
   * @param tile   The Original tile number ie 0 -15
   * @param lines  The Number of lines in the image
   * @param rotate The number of rotations for the coordinates
   * 
   * @see MzeReader
   * @since 3.0
   */
  public PieceData(int tile, int lines, int rotate) {
    tileNum = tile;
    numLines = lines;
    rotation = rotate;
    return;
  }

  /**
   * Getter for the tile number
   * 
   * @return tileNum
   * 
   * @since 4.0
   */
  public int getTileNum() {
    return tileNum;
  }

  /**
   * Getter for the Number of Lines
   * 
   * @return numLines
   * 
   * @since 4.0
   */
  public int getLineNum() {
    return numLines;
  }

  /**
   * Setter for the number of rotations
   * 
   * @param r The number of rotations needed for this image
   * 
   * @since 4.0
   */
  public void setRotation(int r) {
    rotation = r;
  }

  /**
   * Getter for the number of rotations
   * 
   * @return rotation
   * 
   * @since 4.0
   */
  public int getRotation() {
    return rotation;
  }

  /**
   * Getter for the line coordinates list
   * 
   * @return coords
   */
  public List<Integer> getCoords() {
    return coords;
  }

  /**
   * Rotates the coordinates 90 degrees clockwise
   * <p>
   * Every 4 integers in the coordinates represents a line. The 4 ints represent
   * x1, y1, x2, y2 respectively.
   * <p>
   * This rotates each point around the center of a piece (50,50). To rotate 90
   * degrees clockwise (x,y) -> (y, -x) but since the coordinates are never
   * negative they must be "centered" for instance (0,0) is really (-50, 50)
   * since (0,0) is the top left corner.
   * <p>
   * Note that this only performs 90 degree rotations about a point where x == y
   * the math was able to be reduced.
   * 
   * @since 4.0
   */
  public void rotate() {
    for (int i = 0; i < coords.size(); i += 4) {
      int x1 = coords.get(i);
      int y1 = coords.get(i + 1);
      int x2 = coords.get(i + 2);
      int y2 = coords.get(i + 3);

      int nx1 = (-1 * y1) + 100;
      int ny1 = x1;
      int nx2 = (-1 * y2) + 100;
      int ny2 = x2;

      coords.set(i, nx1);
      coords.set(i + 1, ny1);
      coords.set(i + 2, nx2);
      coords.set(i + 3, ny2);
    }
    return;
  }

  /**
   * Clones a PieceData Object (used to store initial data)
   * <p>
   * The PieceData objects are stored in lists each object corresponds to a
   * single piece. In order to store the initial data, the lists of PieceData
   * objects cannot be copied by Collections or by a loop. Instead a clone
   * method is implemented to create a deep copy of the object.
   * <p>
   * The coordinates of the data is a mutable object, so it must be manually
   * copied outside of the super.clone call. Each coordinate is an Integer
   * object so it must be cast to a primitive in order to clone it.
   * 
   * @since 4.0
   */
  @Override
  public PieceData clone() {
    PieceData cpy = null;
    try {
      cpy = (PieceData) super.clone();
      List<Integer> cpy_coords = new ArrayList<Integer>();
      for (Integer i : this.coords) {
        cpy_coords.add(Integer.valueOf(i));
      }
      cpy.coords = cpy_coords;
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return cpy;
  }
}