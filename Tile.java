import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

//Cameron Schmidt
//Program description: 
//Jan 11, 2022

public class Tile
{
   protected int x, y, w, h;
   protected boolean flag, dug, bomb;
   
   public Tile()
   {
      this(0, 0, 25, 25);
   }
   
   public Tile(int x, int y, int w, int h)
   {
      this.x = x;
      this.y = y;
      this.w = w;
      this.h = h;
      flag = false;
      dug = false;
      bomb = false;
   }
   
   public void draw(Graphics2D g2, Color color, Color dugColor, int borderBombs, Image bombImage, Image flagImage)
   {
      g2.setColor(color);
      if(dug)
         g2.setColor(dugColor);
      g2.fillRect(x, y, w, h);
      g2.setColor(Color.BLACK);
      g2.setFont(new Font("Furtura", Font.BOLD, h/2));
      FontMetrics fm = g2.getFontMetrics();
      int width = fm.stringWidth(borderBombs + "");
      if(!bomb && dug && borderBombs>0)
         g2.drawString(borderBombs + "", x+width, y+fm.getHeight());
      if(flag)
         g2.drawImage(flagImage, x, y, null);
      if(dug && bomb)
         g2.drawImage(bombImage, x, y, null);
   }
   
   public int borderBomb(ArrayList<Tile> bordering)
   {
      int count = 0;
      for(Tile t : bordering)
         if(t.isBomb())
            count++;
      return count;
   }
   
   public int getX()
   {
      return x;
   }
   
   public void setX(int x)
   {
      this.x = x;
   }
   
   public int getY()
   {
      return y;
   }
   
   public void setY(int y)
   {
      this.y = y;
   }
   
   public int getW()
   {
      return w;
   }
   
   public void setW(int w)
   {
      this.w = w;
   }
   
   public int getH()
   {
      return h;
   }
   
   public void setH(int h)
   {
      this.h = h;
   }
   
   public boolean isFlagged()
   {
      return flag;
   }
   
   public void setFlag(boolean flag)
   {
      this.flag = flag;
   }
   
   public boolean isDug()
   {
      return dug;
   }
   
   public void setDug(boolean dug)
   {
      this.dug = dug;
   }
   
   public boolean isBomb()
   {
      return bomb;
   }
   
   public void setBomb(boolean bomb)
   {
      this.bomb = bomb;
   }
   
   public String toString()
   {
      return "Tile [x=" + x + ", y=" + y + ", w=" + w + ", h=" + h + ", bomb= " + bomb + ", flag=" + flag + ", dug=" + dug + "]";
   }
   
   
}
