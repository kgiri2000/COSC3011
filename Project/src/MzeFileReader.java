
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * The default.mze file given by Professor Kim Buckner contains the maze image
 * to be displayed on each tile. This data is stored in byte format. The
 * GameWindow class reads the default.mze file into a byte array. This object
 * deciphers that byte array into a usable list of PieceData.
 * <p>
 * The default.mze file is list of integers and floats stored in byte format.
 * Each integer or float is 4 bytes. The first 4 bytes are the number of tiles.
 * Then the format goes: TileNumber NumberOfLines 4 coordinates for each line
 * <p>
 * This object reads the byte array passed in and creates a list of PieceData
 * objects. Each PieceData object contains the tile number, number of lines, and
 * the 4 coordinates for each line stored in a list.
 * 
 * @see GameWindow
 * @see PieceData
 * 
 * @author Sebastian Colwell
 * @version %I%, %G%
 * @since 3.0
 */
public class MzeFileReader {

  // Each data packet is stored as 4 bytes
  private static final int dataByteSize = 4;

  // How many tiles are in the .mze file
  private static int numTiles;

  // The next data packet (next 4 bytes)
  private static byte[] nextData;

  // The current byte index of the array
  private static int byteIndex;

  // Data to read
  private static byte[] mzeData;

  // List of deciphered data
  private static List<PieceData> pieceData = new ArrayList<PieceData>();

  /**
   * Initializer for the reader
   * <p>
   * Class method which initializes a new read. Set the current index to 0
   * (beginning), set the data, and read the number of tiles
   * 
   * @param data The byte date to be read
   * 
   * @since 4.0
   */
  private static void initReader(byte[] data) {
    byteIndex = 0;
    nextData = new byte[dataByteSize];
    mzeData = data;
    readNextFourBytes();
    numTiles = convertByteToInt(nextData);
    return;
  }

  /**
   * Read the .mze file data into a list of PieceData objects
   * <p>
   * Class method which returns the PieceData list. This initializes the reader
   * then reads the data into PieceData objects and returns the list of
   * PieceDatas
   * 
   * @param data The data to be read
   * 
   * @returns List<PieceData> The list of deciphered piece data from the byte
   *          array
   * 
   * @since 4.0
   */
  public static List<PieceData> readData(byte[] data) {
    initReader(data);
    readPieceData(numTiles);
    return pieceData;
  }

  /**
   * Converts a byte array to an integer.
   * <p>
   * This was taken from Kim Buckners Notes.pdf
   * 
   * @param array The byte array to convert to an integer
   * @returns The integer from the byte array
   * 
   * @since 3.0
   */
  private static int convertByteToInt(byte[] array) {
    ByteBuffer buffer = ByteBuffer.wrap(array);
    return buffer.getInt();
  }

  /**
   * Converts a byte array to a float
   * <p>
   * This was taken from Kim Buckners Notes.pdf
   * 
   * @param array The byte array to convert to a float
   * @returns The float from the byte array
   * 
   * @since 3.0
   */
  private static float convertByteToFloat(byte[] array) {
    ByteBuffer buffer = ByteBuffer.wrap(array);
    return buffer.getFloat();
  }

  /**
   * Read the Next 4 bytes into the nextData buffer
   * <p>
   * Since both an integer and a float are represented in 4 bytes This helper
   * function reads 4 bytes at a time. Populating the nextData list with the
   * correct bytes, and then incrementing the index
   * <p>
   * These are all class variables so there is no need for any return values
   * 
   * @since 3.0
   */
  private static void readNextFourBytes() {
    for (int i = byteIndex; i < byteIndex + dataByteSize; i++) {
      nextData[i - byteIndex] = mzeData[i];
    }
    byteIndex += dataByteSize;
    return;
  }

  /**
   * Read the bytes into PieceData objects (Recursive)
   * <p>
   * This is a recursive method that moves through the mzeData and reads the
   * bytes into the PieceData objects.
   * <p>
   * The first 8 bytes are the tile number and the number of lines. Then for
   * each line, read 16 bytes. These 16 bytes are the 4 coords for a given line.
   * That coordinate is added to the PieceData.
   * <p>
   * Once all line coordinates have been read, add the tile to the PieceData
   * list and recurse, decreasing the remainingTiles by 1. Once there are no
   * remaining tiles, return.
   */
  private static void readPieceData(int remainingTiles) {
    if (remainingTiles <= 0) {
      return;
    }
    readNextFourBytes();
    int tile = convertByteToInt(nextData);
    readNextFourBytes();
    int lines = convertByteToInt(nextData);
    PieceData newTile = new PieceData(tile, lines, 0);
    for (int i = 0; i < lines; i++) {
      for (int j = 0; j < 4; j++) {
        readNextFourBytes();
        int coord = (int) convertByteToFloat(nextData);
        newTile.getCoords().add(coord);
      }
    }
    pieceData.add(newTile);
    readPieceData(remainingTiles - 1);
  }

}