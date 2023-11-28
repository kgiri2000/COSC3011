
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.SecureRandom;

import javax.swing.*;

/**
 * Main GamePlay Controller for the Maze Game
 * <p>
 * The GameController object is the main controller for the Game. It handles all
 * piece movement. As well as button presses.
 * <p>
 * This object creates and places the 2 HoldingArea objects and the board. When
 * the HoldingArea objects are created they need to be populated with the
 * correct pieces. The PieceData list from the MzeFileReader must be passed in
 * to the GameController upon construction so the pieces can be created
 * correctly.
 * <p>
 * GameController creates a boardFrame object to encompass the board. This is
 * purely for visual reasons. This is a lighter gray than the GameArea
 * background so it makes the board stand out rather than having 2 dark colors
 * on top of each other.
 * <p>
 * This object implements the MouseListener. This is how the piece movement is
 * handled. This is a point/click type of movement. Click the piece in a holding
 * area then click a square on the board to move that piece to that square. The
 * user can also click a piece on the board, then click an open space in any
 * holding area to move it back. Click a piece on the board and then an open
 * spot on the board to move the selected piece.
 * 
 * @see GameWindow
 * @see HoldingArea
 * @see Menu
 * @see Board
 * 
 * @author Sebastian Colwell
 * @author Zach Kingsmore
 * @author Michael Stoll
 * 
 * @version %I%, %G%
 * @since 2.0
 * 
 */
public class GameController extends JPanel
    implements ActionListener, MouseListener {

  // Default serialVersion (required for extension of JPanel)
  private static final long serialVersionUID = 1L;

  // Dark Grey background used for the background of the controller
  private static final Color dgreyBackground = Color.decode("#393939");

  // Light Grey color used for the "container" frames for Holding area & board
  private static final Color lgreyBackground = Color.decode("#4A4A4A");

  // The currently selected piece (player clicked this piece)
  private Piece selectedPiece = null;

  // GUI Components instantiated within this JPanel
  private Menu gameMenu = new Menu();
  private Board gameBoard = new Board();
  private HoldingArea LHoldingArea = new HoldingArea();
  private HoldingArea RHoldingArea = new HoldingArea();

  // The current data being displayed on the GUI
  private List<PieceData> pieceData = new ArrayList<PieceData>();

  // Initial data from the start of this game
  private List<PieceData> initialData = new ArrayList<PieceData>();

  /**
   * Class Constructor
   * <p>
   * The constructor takes in the PieceData to construct the pieces during the
   * setup. The background is set to be a dark grey and a GridBagLayout is used
   * to position the menu, board, and holding areas.
   * <p>
   * The constructor calls the setupController method.
   * 
   * @param PieceData The data read in from the default.mze file used to create
   *                  the maze graphics
   * 
   * @see MzeFileReader
   * 
   * @since 4.0
   */
  public GameController(List<PieceData> PieceData) {
    pieceData = PieceData;
    this.setBackground(dgreyBackground);
    this.setLayout(new GridBagLayout());
    setupController();
    return;
  }

  /**
   * Setup method for the controller
   * <p>
   * Randomizes the pieces, then copies that into the initial data. Then the
   * menu, board, holding areas, and pieces are added to the controller
   * 
   * @since 4.0
   */
  private void setupController() {
    randomizePositions();
    randomizeRotations();
    copyData(initialData, pieceData);
    addMenu();
    addBoard();
    addHoldingAreas();
    setPieces();
    return;
  }

  /**
   * Randomly shuffle the positions of the pieces
   * <p>
   * This utilizes the SecureRandom package to get as close to true random as
   * possible.
   * <p>
   * The list shuffle is in-place.
   * 
   * @since 4.0
   */
  private void randomizePositions() {
    SecureRandom secureRandom = new SecureRandom();
    secureRandom.setSeed(System.currentTimeMillis());
    for (int i = 0; i < pieceData.size(); i++) {
      int randIndex = secureRandom.nextInt(pieceData.size());
      PieceData tmp = pieceData.get(randIndex);
      pieceData.set(randIndex, pieceData.get(i));
      pieceData.set(i, tmp);
    }
    return;
  }

  /**
   * Randomize the rotations of each PieceData object.
   * <p>
   * The rotations are as follows: 0 = 90 deg 1 = 180 deg 2 = 270 deg 3 = 360
   * deg (No rotation)
   * <p>
   * Only 4 pieces can have no rotation, and at least 1 piece must be 90, 180,
   * and 270 degrees rotated.
   * <p>
   * SecureRandom is utilized to get as close to true random as possible
   * 
   * @since 4.0
   */
  private void randomizeRotations() {
    SecureRandom secureRandom = new SecureRandom();
    secureRandom.setSeed(System.currentTimeMillis());
    int r0count = 0;
    int r90 = secureRandom.nextInt(pieceData.size());
    int r180 = secureRandom.nextInt(pieceData.size());
    int r270 = secureRandom.nextInt(pieceData.size());
    for (int i = 0; i < pieceData.size(); i++) {
      int rotation = secureRandom.nextInt(4);
      rotation = i == r90 ? 0 : rotation;
      rotation = i == r180 ? 1 : rotation;
      rotation = i == r270 ? 2 : rotation;
      if (rotation == 3 && r0count == 4) {
        rotation = secureRandom.nextInt(3);
      } else if (rotation == 3) {
        r0count++;
      }
      pieceData.get(i).setRotation(rotation);
    }
    return;
  }

  /**
   * Create a copy of a piece data list
   * <p>
   * The PieceData list requires a special copy method to ensure a deep-copy
   * Each PieceData object must be cloned into the copy list
   * <p>
   * This is used to store the initial data
   * 
   * @param dst Destination Piece Data list (copy of source)
   * @param src Original piece data list to be copied
   * 
   * @since 4.0
   */
  private void copyData(List<PieceData> dst, List<PieceData> src) {
    dst.clear();
    for (PieceData p : src) {
      dst.add(p.clone());
    }
    return;
  }

  /**
   * Add the Menu Object to the main Controller JPanel
   * <p>
   * The Menu object creates itself upon construction, no colors or sizes need
   * to be set. This method places the menu in the top-center of the GUI.
   * <p>
   * Since this class implements ActionListener all 3 of the buttons get this
   * controller added as the listener.
   * 
   * @see Menu
   * 
   * @since 4.0
   */
  private void addMenu() {
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 1;
    c.weighty = 0.20;
    c.gridy = 0;
    this.add(gameMenu, c);
    gameMenu.getNewGame().addActionListener(this);
    gameMenu.getReset().addActionListener(this);
    gameMenu.getQuit().addActionListener(this);
    return;
  }

  /**
   * Add The board object to the main Controller JPanel
   * <p>
   * The Board object has a container frame created for it (purely visual) With
   * a 15px inset. This makes a nice container for the board rather than having
   * a bright blue grid sit directly on a dark grey JPanel.
   * <p>
   * Similar to the Menu, all colors for the board and sizes are controlled
   * within the Board object. This method creates the frame and places the board
   * within the frame, then places the frame within the controller
   * <p>
   * The frame is never used again so no need for an instance or class variable
   * 
   * @see Board
   * 
   * @since 4.0
   */
  private void addBoard() {
    JPanel frame = new JPanel();
    frame.setLayout(new GridBagLayout());
    frame.setBackground(lgreyBackground);
    GridBagConstraints boardConstraints = new GridBagConstraints();
    boardConstraints.insets = new Insets(15, 15, 15, 15);
    frame.add(gameBoard, boardConstraints);
    GridBagConstraints frameConstraints = new GridBagConstraints();
    frameConstraints.anchor = GridBagConstraints.NORTH;
    frameConstraints.weighty = 0.5;
    frameConstraints.weightx = 0.5;
    frameConstraints.gridx = 1;
    frameConstraints.gridy = 1;
    this.add(frame, frameConstraints);
    gameBoard.addMouseListener(this);
    return;
  }

  /**
   * Adds Both holding areas to the Controller JPanel
   * <p>
   * 2 seperate HoldingArea objects are created and placed on either side of the
   * board (horizontally). They are set to take up both rows in the grid.
   * <p>
   * The gridx is changed inbetween adding the holding areas since the board
   * occupies gridx = 1, the left holding area goes into slot 0 and the right in
   * slot 2.
   * 
   * @see HoldingArea
   * 
   * @since 4.0
   */
  private void addHoldingAreas() {
    GridBagConstraints c = new GridBagConstraints();
    c.gridy = 0;
    c.gridheight = 2;
    c.weightx = 0.25;
    c.gridx = 0;
    this.add(LHoldingArea, c);
    c.gridx = 2;
    this.add(RHoldingArea, c);
    return;
  }

  /**
   * Reset the pieces inside the holding area to what's inside the pieceData
   * list
   * <p>
   * This method first clears the list of pieces inside the holding areas. Then
   * populates that list with new pieces. Each piece will get a PieceData object
   * that defines the lines to draw on the piece.
   * <p>
   * Each piece added to the holding area will have a mouse listener added to
   * it, and a pointer to which holding area it is being assigned to.
   * 
   * @see Piece
   * @see HoldingArea
   * 
   * @since 4.0
   */
  private void setPieces() {
    LHoldingArea.clearPieces();
    RHoldingArea.clearPieces();
    for (int i = 0; i < 16; i++) {
      Piece piece = new Piece();
      piece.drawImage(pieceData.get(i));
      piece.addMouseListener(this);
      if (i < 8) {
        piece.setHoldingArea(LHoldingArea);
        LHoldingArea.addPiece(piece);

      } else {
        piece.setHoldingArea(RHoldingArea);
        RHoldingArea.addPiece(piece);
      }
    }
    return;
  }

  /**
   * Handle the Piece selection
   * <p>
   * If there is already a selected piece, de-select it.
   * <p>
   * If there is already a piece selected, and another piece is clicked A Red
   * border on the selected piece flashes to alert the user
   * <p>
   * Otherwise, select the piece
   * <p>
   * The Piece.select() & Piece.deselect() just change the color to make it
   * apparent which piece is currently selected.
   * 
   * @param p The piece that was clicked by the user
   * 
   * @see Piece
   * 
   * @since 4.0
   */
  private void selectPiece(Piece p) {
    if (selectedPiece == p) {
      selectedPiece.deselect();
      selectedPiece = null;
    } else if (selectedPiece != null) {
      Timer timer = new Timer(80, selectedPiece);
      timer.setInitialDelay(0);
      timer.start();
    } else {
      selectedPiece = p;
      p.select();
    }
    return;
  }

  /**
   * Move the selected piece onto the board
   * <p>
   * Given an x and y coordinate from the mouse click, the selected piece can be
   * moved to that coordinate on the grid.
   * <p>
   * A Blank piece is created to "swap" with the selected piece inside the
   * holding area. For example, a piece inside the holding area is clicked, then
   * a square on the board is clicked. The piece is moved to the board at the
   * specified coordinates, then a blank fills the missing spot.
   * <p>
   * The blank piece uses a different constructor which takes a color to make
   * the piece appear invisible, it will also get a blue border to make it
   * appear like an empty slot.
   * <p>
   * The selcted piece is then deselected and set to null (now no piece is
   * selected)
   * 
   * @param x The integer cooresponding to the relative x pixel on the board.
   * @param y The integer cooresponding to the relative y pixel on the board.
   * 
   * @see HoldingArea
   * @see Board
   * @see Piece
   * 
   * @since 4.0
   */
  private void moveToBoard(int x, int y) {
    if (selectedPiece != null) {
      int xcoord = x / 100;
      int ycoord = y / 100;
      Piece blank = new Piece(lgreyBackground);
      blank.addMouseListener(this);
      selectedPiece.getHoldingArea().removePiece(selectedPiece, blank);
      gameBoard.addPiece(selectedPiece, xcoord, ycoord);
      selectedPiece.deselect();
      selectedPiece = null;
    }
    return;
  }

  /**
   * Moves a piece to the holding area
   * <p>
   * This handles movement from 1 holding area to the other. Movement from a
   * holding area to the board and back, and movement within a holding area.
   * <p>
   * If a piece is moving within holding area(s) a blank must be used to fill
   * the spot
   * 
   * @param piece The placeholder piece that was clicked. This will be replaced
   *              by the selected piece
   * 
   * @see HoldingArea
   * @see Board
   * 
   * @since 4.0
   */
  private void moveToHolding(Piece piece) {
    if (selectedPiece != null) {
      gameBoard.removePiece(selectedPiece);
      if (selectedPiece.getParent() == selectedPiece.getHoldingArea()) {
        Piece blank = new Piece(lgreyBackground);
        blank.addMouseListener(this);
        selectedPiece.getHoldingArea().removePiece(selectedPiece, blank);
      }
      piece.getHoldingArea().replace(piece, selectedPiece);
      selectedPiece.deselect();
      selectedPiece = null;
    }
    return;
  }

  /**
   * This method defines the functionality for the menu buttons.
   * <p>
   * The new_game button will randomize the PieceData and reset the game
   * <p>
   * The reset button will clear the board and reset the holding areas according
   * to the PieceData
   * <p>
   * The quit button simply exits the program.
   * 
   * @param e The click event (what button was clicked)
   * 
   * @see Board
   *
   * @since 4.0
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == gameMenu.getNewGame()) {
      randomizePositions();
      randomizeRotations();
      gameBoard.clearBoard();
      setPieces();
    } else if (e.getSource() == gameMenu.getReset()) {
      copyData(pieceData, initialData);
      gameBoard.clearBoard();
      setPieces();
    } else if (e.getSource() == gameMenu.getQuit()) {
      System.exit(0);
    }
    return;
  }

  /**
   * This method defines the piece movement and selection.
   * <p>
   * If a piece is clicked select it if it is not a placeholder. If a
   * placeholder was clicked swap the placeholder and the selected piece. If the
   * board is clicked move the selected piece to the board where the user
   * clicked.
   * <p>
   * If a piece is right clicked, rotate the piece.
   * 
   * @param e The mouse event object for a click
   * 
   * @since 4.0
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    List<Piece> allPieces = new ArrayList<Piece>(LHoldingArea.getPieces());
    allPieces.addAll(RHoldingArea.getPieces());
    allPieces.addAll(gameBoard.getPositions());
    for (Piece p : allPieces) {
      if (e.getSource() == p) {
        if (e.getButton() == MouseEvent.BUTTON3) {
          p.rotate();
        } else {
          if (p.isBlank()) {
            moveToHolding(p);
          } else {
            selectPiece(p);
          }
        }
      }
    }
    if (e.getSource() == gameBoard) {
      moveToBoard(e.getX(), e.getY());
    }
    return;
  }

  /**
   * Unused method
   * 
   * @param e The mouse event object for a Press
   * 
   * @since 4.0
   */
  @Override
  public void mousePressed(MouseEvent e) {
    return;
  }

  /**
   * Unused method
   * 
   * @param e The mouse event object for a Release
   * 
   * @since 4.0
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    return;
  }

  /**
   * Unused method
   * 
   * @param e The mouse event object for an Entered
   * 
   * @since 4.0
   */
  @Override
  public void mouseEntered(MouseEvent e) {
    return;
  }

  /**
   * Unused method
   * 
   * @param e The mouse event object for a Exited
   * 
   * @since 4.0
   */
  @Override
  public void mouseExited(MouseEvent e) {
    return;
  }

}