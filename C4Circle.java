import javafx.scene.shape.Circle;

public class C4Circle extends Circle{
   private boolean isStart = false; // true = clickable, false = not clickable
   private boolean isWinner = false;   // indicates winner (4 winning circles)... Only implemented on token circles
   private boolean isOccupied = false; // true if clicked
   private int row;
   private int col;

   public int getRow(){
      return row;
   }
   public void setRow(int x){
      row = x;
   }
   public int getCol(){
      return col;
   }
   public void setCol(int x){
      col = x;
   }
   public boolean getStart(){
      return isStart;
   }
   public boolean getWinner(){
      return isWinner;
   }
   public boolean getOccupied(){
      return isOccupied;
   }
   public void setStart(boolean x){
      isStart = x;
   }
   public void setWinner(boolean x){
      isWinner = x;
   }
   public void setOccupied(boolean x){
      isOccupied = x;
   }
}