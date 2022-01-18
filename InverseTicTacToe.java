// Christine Nakano, lab section 3

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class InverseTicTacToe extends JFrame implements ActionListener {
  private Board board;
  private Color squareColor = Color.BLUE; 
  private Color	diamondColor = Color.RED;
  
  static final char BLANK = ' ', O='O', X='X';
  private char position[] = { 
    BLANK, BLANK, BLANK,
    BLANK, BLANK, BLANK,
    BLANK, BLANK, BLANK
    };

  // Start the game
  public static void main(String args[]) {
    new InverseTicTacToe();
  }

  // Initialize
  public InverseTicTacToe() {
    super("Inverse Tic Tac Toe");
    add(board=new Board(), BorderLayout.CENTER);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 400);
    setVisible(true);
  }

  // Plays and displays the game
  private class Board extends JPanel implements MouseListener {
    private Random random = new Random();
    // End points of the 8 rows 
    private int rows[][]={{0,2},{3,5},{6,8},{0,6},{1,7},{2,8},{0,8},{2,6}};
    
    /* 0, 1, 2
     * 3, 4, 5
     * 6, 7, 8
     */

    public Board() {
      addMouseListener(this);
    }

    // Redraw the board
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      int w = getWidth();
      int h = getHeight();

      // Draw the grid
      // Sets color of background
      g.setColor(Color.WHITE);
      // Sets color of grid lines
      g.setColor(Color.BLACK);
      // Draws grid lines
      g.drawLine(0, h/3, w, h/3);
      g.drawLine(0, h*2/3, w, h*2/3);
      g.drawLine(w/3, 0, w/3, h);
      g.drawLine(w*2/3, 0, w*2/3, h);

      // Draw the squares and diamonds
      for (int i=0; i<9; ++i) {
    	// position of shape in the grid
        int xpos = (int) ((i%3 + 0.5) * w/3.0);
        int ypos = (int) ((i/3 + 0.5) * h/3.0);
        // width and height of the shapes
        int xr = w/15;
        int yr = h/15;
        if (position[i]==O) {
          g.setColor(squareColor);
          g.drawLine(xpos+xr, ypos-yr, xpos+xr, ypos+yr);
          g.drawLine(xpos-xr, ypos-yr, xpos+xr, ypos-yr);
          g.drawLine(xpos-xr, ypos-yr, xpos-xr, ypos+yr);
          g.drawLine(xpos-xr, ypos+yr, xpos+xr, ypos+yr);
        }
        // couldnt get the diamond working
        else if (position[i]==X) {
          g.setColor(diamondColor);
          g.drawLine(xpos+xr, ypos-yr, xpos+xr, ypos+yr);
          g.drawLine(xpos-xr, ypos-yr, xpos+xr, ypos-yr);
          g.drawLine(xpos-xr, ypos-yr, xpos-xr, ypos+yr);
          g.drawLine(xpos-xr, ypos+yr, xpos+xr, ypos+yr);
        }
      }
    }

    // Draw where the mouse is clicked
    public void mouseClicked(MouseEvent e) {
      int xPos = e.getX()*3 / getWidth();
      int yPos = e.getY()*3 / getHeight();
      int pos = xPos + 3*yPos;
      if (pos>=0 && pos<9 && position[pos]==BLANK) {
        position[pos]=O;
        repaint();
        // Computer plays
        putX();  
        repaint();
      }
    }

    // Ignore other mouse events
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    // Computer plays X
    void putX() {
      
      // Check if game is over
      if (whoWon(O))
        newGame(O);
      else if (isDraw())
        newGame(BLANK);

      // Play X
      else {
        nextMove();
        if (whoWon(X))
          newGame(X);
        else if (isDraw())
          newGame(BLANK);
      }
    }

    // Return true if player has 3 in a row
    boolean whoWon(char player) {
      for (int i=0; i<8; ++i)
        if (testRow(player, rows[i][0], rows[i][1]))
          return true;
      return false;
    }

    // Has player placed 3 in a row in the row from position[a] to position[b]?
    boolean testRow(char player, int a, int b) {
      return position[a]==player && position[b]==player && position[(a+b)/2]==player;
    }

    // Play X in a random spot
    void nextMove() {
        int r;
		do {
          r = random.nextInt(9);
		} while (position[r]!=BLANK);
          position[r]=X;
    }

    // All 9 spots filled, but no 3 in a row
    boolean isDraw() {
      for (int i=0; i<9; ++i)
        if (position[i]==BLANK)
          return false;
      return true;
    }

    // Start a new game
    void newGame(char winner) {
      repaint();

      // Announce winner and ask if player wants to play again
      String result;
      if (winner==O) {
        result = "Computer Wins!";
      }
      else if (winner==X) {
        result = "You Win!";
      }
      else {
        result = "Draw";
      }
      if (JOptionPane.showConfirmDialog(null, "Play again?", result, JOptionPane.YES_NO_OPTION)
          !=JOptionPane.YES_OPTION) {
        System.exit(0);
      }

      // Clear board and start a new game
      for (int j=0; j<9; ++j) {
        position[j]=BLANK;
    }
	
}
  }

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	}
}
 