import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

//Cam Schmidt
//Program description: 
//May 11, 2022

public class MinesweeperGUI extends JPanel implements KeyListener, MouseListener
{
   //variables
   private final int PREF_W = 650;
   private final int PREF_H = 500;

   private final Color[] FOREST = {new Color(150, 225, 36), new Color(119, 212, 5), new Color(234, 194, 153), new Color(219, 184, 148)};
   private final Color[] BEACH = {new Color(243, 232, 106), new Color(246, 215, 13), new Color(176, 213, 238), new Color(114, 186, 224)};
   private final Color[] VOLCANO = {new Color(255, 41, 0), new Color(253, 91, 0), new Color(244, 199, 0), new Color(248, 229, 0)};
   
   private final Image[] FOREST_PICS = {new ImageIcon(this.getClass().getResource("bomb.png")).getImage(), 
         new ImageIcon(this.getClass().getResource("flag.png")).getImage()};
   private final Image[] BEACH_PICS = {new ImageIcon(this.getClass().getResource("coconut.png")).getImage(),
         new ImageIcon(this.getClass().getResource("umbrella.png")).getImage()};
   private final Image[] VOLCANO_PICS = {new ImageIcon(this.getClass().getResource("meteor.png")).getImage(),
         new ImageIcon(this.getClass().getResource("caution.png")).getImage()};
   
   private Colorway forest = new Colorway(FOREST, FOREST_PICS);
   private Colorway beach = new Colorway(BEACH, BEACH_PICS);
   private Colorway volcano = new Colorway(VOLCANO, VOLCANO_PICS);
   
   private Board board;
   private boolean gameOver, ctrl, hideGOM, firstMove;
   private int flags;
   private Colorway activeColorway;
   
   //constructor
   public MinesweeperGUI()
   {
      this.addKeyListener(this);
      this.addMouseListener(this);
      this.setFocusable(true);
//      this.requestFocus();
      board = new Board(20, 24, 25, 25, 50, 0, 99, forest);
      gameOver = false;
      ctrl = false;
      flags = 99;
      firstMove = true;
      activeColorway = forest;
      hideGOM = false;
   }
   
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      
      g2.setFont(new Font("Furtura", Font.PLAIN, 25));
      FontMetrics fm = g2.getFontMetrics();
      int width = fm.stringWidth(flags+"");
      g2.drawString(flags+"", 25-width/2, 100);
      g2.drawImage(activeColorway.getFlag(), 13, 110, null);
      
      width = fm.stringWidth("F");
      g2.drawString("F", 25-width/2, 195);
      g2.drawImage(FOREST_PICS[0], 13, 205, null);
      
      width = fm.stringWidth("B");
      g2.drawString("B", 25-width/2, 280);
      g2.drawImage(BEACH_PICS[0], 13, 290, null);
      
      width = fm.stringWidth("V");
      g2.drawString("V", 25-width/2, 365);
      g2.drawImage(VOLCANO_PICS[0], 13, 375, null);
      
      board.draw(g2);
      if(gameOver)
         showGameOverMessage(g2);
   }
   
   public void showGameOverMessage(Graphics2D g2)
   {
      if(!hideGOM)
      {
         g2.setColor(new Color(255, 255, 255, 200));
         g2.fillRect(0, 0, PREF_W, PREF_H);
         
         String message;
         if(isBoardClear())
            message = "You Win!";
         else
            message = "You Lose!";
         g2.setColor(Color.BLACK);
         g2.setFont(new Font("Furtura", Font.PLAIN, 100));
         FontMetrics fm = g2.getFontMetrics();
         int width = fm.stringWidth(message);
         g2.drawString(message, PREF_W/2 - width/2+25, PREF_H/2 - 50);
         
         g2.setFont(new Font("Furtura", Font.PLAIN, 50));
         fm = g2.getFontMetrics();
         width = fm.stringWidth("Press <R> to Play Again");
         g2.drawString("Press <R> to Play Again", PREF_W/2 - width/2+25, PREF_H/2+75);
         
         g2.setFont(new Font("Furtura", Font.PLAIN, 25));
         fm = g2.getFontMetrics();
         width = fm.stringWidth("Press <SPACE> to Show Board");
         g2.drawString("Press <SPACE> to Show Board", PREF_W/2 - width/2+25, PREF_H/2+150);
      }
   }

   public boolean isBoardClear()
   {
      for(Tile[] r : board.getBoard())
         for(Tile t : r)
            if(t.isBomb() && !t.isFlagged())
               return false;
      return true;
   }
   
   public void endGame()
   {
      gameOver = true;
      board.revealBombs();
   }
   
   public void restart()
   {
      board = new Board(20, 24, 25, 25, 50, 0, 99, activeColorway);
      gameOver = false;
      flags = 99;
      firstMove = true;
      repaint();
   }
   
   @Override
   public void keyPressed(KeyEvent e)
   {
      if(e.getKeyCode() == KeyEvent.VK_CONTROL)
         ctrl = true;
      if(e.getKeyCode() == KeyEvent.VK_R && gameOver)
         restart();
      
      if(e.getKeyCode() == KeyEvent.VK_F)
         activeColorway = forest;
      if(e.getKeyCode() == KeyEvent.VK_B)
         activeColorway = beach;
      if(e.getKeyCode() == KeyEvent.VK_V)
         activeColorway = volcano;
      board.setColor(activeColorway);
      if(e.getKeyCode() == KeyEvent.VK_SPACE)
         hideGOM = true;
      repaint();
   }

 

   @Override
   public void keyReleased(KeyEvent e)
   {
      int key = e.getKeyCode();
      ctrl = false;
      if(gameOver && key == KeyEvent.VK_SPACE)
         hideGOM = false;
      repaint();
   }
   
   
   @Override
   public void keyTyped(KeyEvent e){}

   @Override
   public void mouseClicked(MouseEvent e)
   {
   }


   @Override
   public void mousePressed(MouseEvent e)
   {
      int x = e.getX();
      int y = e.getY();
      if(x > 50 && !gameOver)
      {
         int r = y/25;
         int c = (x-50)/25;
         if(firstMove)
         {
            firstMove = false;
            board.open(r, c);
         }
         if(!ctrl && !board.getBoard()[r][c].isFlagged())
         {
            board.getBoard()[r][c].setDug(true);
//            System.out.println(board.getBoard()[r][c]);
            if(board.getBoard()[r][c].isBomb())
               endGame();
            else
               board.fullDig(r, c);
         }
         else if(!board.getBoard()[r][c].isFlagged() && flags >= 1 && !board.getBoard()[r][c].isDug())
         {
            board.getBoard()[r][c].setFlag(true);
            flags--;
         }
         else if(board.getBoard()[r][c].isFlagged() && ctrl)
         {
            board.getBoard()[r][c].setFlag(false);
            flags++;
         }
      }
      if(isBoardClear())
         endGame();
//      board.printBoard();
      repaint();
   }


   @Override
   public void mouseReleased(MouseEvent e)
   {
   }


   @Override
   public void mouseEntered(MouseEvent e)
   {
   }


   @Override
   public void mouseExited(MouseEvent e)
   {
   }
   
   //************ CODE TO CREATE THE FRAME FOR THE PANEL ************\\
   
   public Dimension getPreferredSize()
   {
      return new Dimension(PREF_W, PREF_H);
   }
   
   public static void createAndShowGUI()
   {
      JFrame frame = new JFrame("Minesweeper"); //Insert title
      JPanel gamePanel = new MinesweeperGUI();
      
      frame.getContentPane().add(gamePanel);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      frame.setResizable(false); //SET DEPENDING ON GAME
   }
   
   public static void main(String[] args)
   {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGUI();
         }
      });
   }
}
