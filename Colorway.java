import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

//Cameron Schmidt
//Program description: 
//May 16, 2022

public class Colorway
{
   private Color grass1, grass2, dug1, dug2;
   private Image bomb, flag;
   
   public Colorway()
   {
      this(Color.WHITE, Color.BLACK, Color.LIGHT_GRAY, Color.DARK_GRAY, null, null);
   }
   
   public Colorway(Color grass1, Color grass2, Color dug1, Color dug2, Image bomb, Image flag)
   {
      this.grass1 = grass1;
      this.grass2 = grass2;
      this.dug1 = dug1;
      this.dug2 = dug2;
      this.bomb = bomb;
      this.flag = flag;
   }
   
   public Colorway(Color[] colors, Image[] images)
   {
      this(colors[0], colors[1], colors[2], colors[3], images[0], images[1]);
   }
   
   public Colorway(ArrayList<Color> colors, ArrayList<Image> images)
   {
      this(colors.get(0), colors.get(1), colors.get(2), colors.get(3), images.get(0), images.get(1));
   }

   public Color getGrass1()
   {
      return grass1;
   }

   public void setGrass1(Color grass1)
   {
      this.grass1 = grass1;
   }

   public Color getGrass2()
   {
      return grass2;
   }

   public void setGrass2(Color grass2)
   {
      this.grass2 = grass2;
   }

   public Color getDug1()
   {
      return dug1;
   }

   public void setDug1(Color dug1)
   {
      this.dug1 = dug1;
   }

   public Color getDug2()
   {
      return dug2;
   }

   public void setDug2(Color dug2)
   {
      this.dug2 = dug2;
   }

   public Image getBomb()
   {
      return bomb;
   }

   public void setBomb(Image bomb)
   {
      this.bomb = bomb;
   }

   public Image getFlag()
   {
      return flag;
   }

   public void setFlag(Image flag)
   {
      this.flag = flag;
   }
}
