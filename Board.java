import java.awt.Graphics2D;
import java.util.ArrayList;

//Cameron Schmidt
//Program description: 
//May 11, 2022

public class Board
{
   private Tile[][] board;
   private int startX, startY;
   private Colorway colors;
   
   public Board()
   {
      this(0, 0, 25, 25, 0, 0, 0, new Colorway());
   }
   
   public Board(int rows, int cols, int tileW, int tileH, int startX, int startY, int bombs, Colorway colors)
   {
      board = new Tile[rows][cols];
      for(int r = 0; r < rows; r++)
         for(int c = 0; c < cols; c++)
            board[r][c] = new Tile(startX + c*tileW, startY + r*tileH, tileW, tileH);
      for(int i = bombs; i > 0; i--)
      {
         int r = (int)(Math.random()*rows);
         int c = (int)(Math.random()*cols);
         while(board[r][c].isBomb())
         {
            r = (int)(Math.random()*rows);
            c = (int)(Math.random()*cols);
         }
         board[r][c].setBomb(true);
      }
      this.colors = new Colorway(colors.getGrass1(), colors.getGrass2(), colors.getDug1(), colors.getDug2(), colors.getBomb(), colors.getFlag());
      this.startX = startX;
      this.startY = startY;
   }
   
   public void draw(Graphics2D g2)
   {
      for(int r = 0; r < board.length; r++)
         for(int c = 0; c < board[r].length; c++)
            if(r%2 == 0 && c%2 == 0)
               board[r][c].draw(g2, colors.getGrass1(), colors.getDug1(), board[r][c].borderBomb(getBordering(r, c)), colors.getBomb(), colors.getFlag());
            else if(r%2 == 0 && c%2 == 1)
               board[r][c].draw(g2, colors.getGrass2(), colors.getDug2(), board[r][c].borderBomb(getBordering(r, c)), colors.getBomb(), colors.getFlag());
            else if(r%2 == 1 && c%2 == 0)
               board[r][c].draw(g2, colors.getGrass2(), colors.getDug2(), board[r][c].borderBomb(getBordering(r, c)), colors.getBomb(), colors.getFlag());
            else
               board[r][c].draw(g2, colors.getGrass1(), colors.getDug1(), board[r][c].borderBomb(getBordering(r, c)), colors.getBomb(), colors.getFlag());
   }
   
   public ArrayList<Tile> getBordering(int row, int col)
   {
      ArrayList<Tile> bordering = new ArrayList<Tile>();
      if(row>0)
      {
         for(int r = row-1; r <= row+1; r++)
            if(col>0)
            {
               for(int c = col-1; c <= col+1; c++)
                  if(r < board.length && c < board[r].length)
                     bordering.add(board[r][c]);
            }
            else
               for(int c = col; c <= col+1; c++)
                  if(r < board.length && c < board[r].length)
                     bordering.add(board[r][c]);
      }
      else
         for(int r = row; r <= row+1; r++)
            if(col>0)
            {
               for(int c = col-1; c <= col+1; c++)
                  if(r < board.length && c < board[r].length)
                     bordering.add(board[r][c]);
            }
            else
               for(int c = col; c <= col+1; c++)
                  if(r < board.length && c < board[r].length)
                     bordering.add(board[r][c]);
      return bordering;
   }
   
   public void fullDig(int row, int col)
   {
      if(board[row][col].borderBomb(getBordering(row, col))==0)
         for(Tile t : getBordering(row, col))
         {
            if(!t.isDug())
            {
            t.setDug(true); 
            if(board[(t.getY()-startY)/t.getH()][(t.getX()-startX)/t.getW()].borderBomb(getBordering((t.getY()-startY)/t.getH(), (t.getX()-startX)/t.getW()))==0)
               fullDig((t.getY()-startY)/t.getH(), (t.getX()-startX)/t.getW());
            }
         }
   }
   
   public void open(int row, int col)
   {
         for(Tile t : getBordering(row, col))
         {
            if(!t.isBomb())
               t.setDug(true);
            if(board[(t.getY()-startY)/t.getH()][(t.getX()-startX)/t.getW()].borderBomb(getBordering((t.getY()-startY)/t.getH(), (t.getX()-startX)/t.getW()))==0)
               fullDig((t.getY()-startY)/t.getH(), (t.getX()-startX)/t.getW());
         }
   }
   
   public void revealBombs()
   {
      for(Tile[] r: board)
         for(Tile t : r)
            if(t.isBomb() && !t.isFlagged())
               t.setDug(true);
            else if(t.isFlagged() && !t.isBomb())
            {
               t.setDug(true);
               t.setFlag(false);
            }
   }
   
   public Tile[][] getBoard()
   {
      return board;
   }
   
   public void setColor(Colorway colors)
   {
      this.colors = colors;
   }
   
   public void printBoard()
   {
      for(int r = 0; r < board.length; r++)
      {
         for(int c = 0; c < board[r].length; c++)
            System.out.print(board[r][c].borderBomb(getBordering(r, c)));
         System.out.println();
      }
      System.out.println();
      System.out.println();
   }
}
