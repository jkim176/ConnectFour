import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.animation.PathTransition;
import javafx.util.Duration;
import javafx.scene.text.Text;

public class ConnectFour extends Pane{
   private int[][] values; // backend array - representation of GUI
   private int color = 1;
   private int row;
   private int col;
   private C4Circle[][] circleArray;   // array of Circles (subclass) that are clicked
   private PathTransition[][] pathArray;  // array of PathTransitions on which Tokens are drawn
   private C4Circle[][] tokenArray;   // array of Tokens that are drawn
   
   public ConnectFour(int row, int col){  // constructor 
      Rectangle background = new Rectangle();   // set background of pane
      background.setHeight(600);
      background.setWidth(700);
      background.setFill(Color.BLACK);
      getChildren().add(background);  // add background to pane
      
      values = new int[row][col];
      circleArray = new C4Circle[row][col];
      pathArray = new PathTransition[row][col];
      tokenArray = new C4Circle[row][col];
      
      Text msg = new Text(275, 640, "Player 1's turn...");
      getChildren().add(msg);
      
      for(int i = 0; i < values.length; i++){
         for(int j = 0; j < values[0].length; j++){
            C4Circle c = new C4Circle();  // fill circleArray... circles that are clicked
            c.setCenterX(j * 100 + 50);
            c.setCenterY(i * 100 + 50);
            c.setRadius(40);
            c.setFill(Color.WHITE);
            circleArray[i][j] = c;  // add circle to circleArray
            c.setRow(i);
            c.setCol(j);
            if(i == (values.length - 1))  // IF bottom row
               c.setStart(true);  // initialize bottom row to 'can put token' there
            getChildren().add(c);  // add circle to pane   
            
            Line l = new Line(j * 100 + 50, 0, j * 100 + 50, i * 100 + 50);   // lines that PathTransition will follow
            l.setStroke(new Color(0, 0, 0, 0) );
            getChildren().add(l);
            
            PathTransition pt = new PathTransition(); // fill pathArray 
            pt.setDuration(Duration.millis(1000));
            pt.setPath(l);
            pathArray[i][j] = pt;
            
            c.setOnMouseClicked(e -> handler1(c) );   // on circle click - event handler
         }
      }
   }
   public void handler1(C4Circle c){   // Event handler on circle click
      if(c.getStart() ){   // if circle clicked can be clicked/start               
         C4Circle x = new C4Circle();   // draw player token
         x.setCenterX(c.getCenterX() );
         x.setCenterY(0);
         x.setRadius(40);
         if(getColor() == 1){ // set color of circle based on backend values array
            x.setStroke(Color.RED);
            x.setFill(Color.RED);
         }
         else{ // set color of circle based on backend values array
            x.setStroke(Color.YELLOW);
            x.setFill(Color.YELLOW);
         }
         getChildren().add(x);   
         tokenArray[c.getRow()][c.getCol()] = x;   // fill tokenArray
         addToken(c.getRow(), c.getCol() );  // method for backend implementation
       
         pathArray[c.getRow()][c.getCol()].setNode(x);   // attach token to correct PathTransition
         pathArray[c.getRow()][c.getCol()].setCycleCount(1);
         pathArray[c.getRow()][c.getCol()].setAutoReverse(false);
         pathArray[c.getRow()][c.getCol()].play();
         
         c.setStart(false);   // this circle can no longer be clicked
         c.setOccupied(true); // this circle is occupied
         
         if(c.getRow() >= 1)  // does NOT allow row/col -1; restriction OutOfBounds
            circleArray[c.getRow() - 1][c.getCol()].setStart(true);  // allow above circle (row - 1) to be clicked    
              
         if(isConnectFour() == 1){
            changeColor();
            Rectangle eraser = new Rectangle(0, 600, 600, 100);   // draw over previous msg with white block
            eraser.setFill(Color.WHITE);
            getChildren().add(eraser);
            Text textP2Win = new Text(275, 640, "Player " + getColor() + " has won the game!"); // Win message
            getChildren().add(textP2Win);
            System.out.println("Player " + getColor() + " has won the game!");
            for(int i = 0; i < circleArray.length; i++){ // set all C4Circles as unclickable, when winner is found
               for(int j = 0; j < circleArray[0].length; j++){
                  circleArray[i][j].setStart(false);
               }
            }
         }
         else if(isConnectFour() == 2){
            changeColor();
            Rectangle eraser = new Rectangle(0, 600, 600, 100);
            eraser.setFill(Color.WHITE);
            getChildren().add(eraser);
            Text textP2Win = new Text(275, 640, "Player " + getColor() + " has won the game!");
            getChildren().add(textP2Win);
            System.out.println("Player " + getColor() + " has won the game!");
            for(int i = 0; i < circleArray.length; i++){ // set all C4Circles as unclickable, when winner is found
               for(int j = 0; j < circleArray[0].length; j++){
                  circleArray[i][j].setStart(false);
               }
            }
         }      
         else{
            Rectangle eraser = new Rectangle(0, 600, 600, 100);
            eraser.setFill(Color.WHITE);
            getChildren().add(eraser);
            Text textContinue = new Text(275, 640, "Player " + getColor() + "'s turn...");
            getChildren().add(textContinue);
            System.out.println("Continue.... ");
         }
         
         boolean draw = false;   // check for draw
         for(int i = 0; i < circleArray.length; i++){
            for(int j = 0; j < circleArray[0].length; j++){
               if(!circleArray[i][j].getOccupied() ){
                  draw = false;
                  break;
               }
               else{
                  draw = true;
               }
            }
            if(!draw)
               break;
         }
         if(draw){   // display draw
            Rectangle eraser = new Rectangle(0, 600, 600, 100);
            eraser.setFill(Color.WHITE);
            getChildren().add(eraser);
            Text textDraw = new Text(275, 640, "Draw!");
            getChildren().add(textDraw);
            System.out.println("Draw!");
         }
      }
   }   
   public int getRowSize(){
      return values.length;
   }
   public int getColSize(){
      return values[0].length;
   }
   public int getValue(int i, int j){
      return values[i][j];
   }
   public void setValue(int i, int j, int val){
      values[i][j] = val;
   }
   public int getColor(){
      return color;
   }
   // changes token color 1 or 2
   public void changeColor(){
      if(color == 1)
         color = 2;
      else
         color = 1;
   }
   
   // Backend implementation method - ADD TOKEN METHOD @Param (row, col) 
   public void addToken(int row, int col){   // backend - changes int array
      values[row][col] = getColor();
      changeColor();
   }
   public int isConnectFour(){  // returns 0 if values[i][j] == 0(empty), returns 1/2 if 4 consecutive matches of 1/2
      // preliminary check
      if(values.length < 4 && values[0].length < 4)
         return 0;
      // horizontal check
      for(int i = 0; i < values.length; i++){
         for(int j = 0; j < values[0].length - 3; j++){  // only check to 4th last column
            if(values[i][j] == 0)
               continue;
            else if(values[i][j] == 1 && values[i][j] == values[i][j + 1] && values[i][j] == values[i][j + 2] && values[i][j] == values[i][j + 3] ){  // check for 4 consecutive 1 matches
               tokenArray[i][j].setWinner(true);   // set 4 tokens as winners
               tokenArray[i][j + 1].setWinner(true);
               tokenArray[i][j + 2].setWinner(true);
               tokenArray[i][j + 3].setWinner(true);
               return 1;
            }
            else if(values[i][j] == 2 && values[i][j] == values[i][j + 1] && values[i][j] == values[i][j + 2] && values[i][j] == values[i][j + 3] ){  // check for 4 consecutive 2 matches
               tokenArray[i][j].setWinner(true);
               tokenArray[i][j + 1].setWinner(true);
               tokenArray[i][j + 2].setWinner(true);
               tokenArray[i][j + 3].setWinner(true);
               return 2;
            }
         }
      }
      // vertical check
      for(int i = 0; i < values.length - 3; i++){  // only check to 4th last row
         for(int j = 0; j < values[0].length; j++){
            if(values[i][j] == 0)
               continue;
            else if(values[i][j] == 1 && values[i][j] == values[i + 1][j] && values[i][j] == values[i + 2][j] && values[i][j] == values[i + 3][j] ){  // check for 4 consecutive 1 matches
               tokenArray[i][j].setWinner(true);
               tokenArray[i + 1][j].setWinner(true);
               tokenArray[i + 2][j].setWinner(true);
               tokenArray[i + 3][j].setWinner(true);
               return 1;
            }
            else if(values[i][j] == 2 && values[i][j] == values[i + 1][j] && values[i][j] == values[i + 2][j] && values[i][j] == values[i + 3][j] ){  // check for 4 consecutive 2 matches
               tokenArray[i][j].setWinner(true);
               tokenArray[i + 1][j].setWinner(true);
               tokenArray[i + 2][j].setWinner(true);
               tokenArray[i + 3][j].setWinner(true);
               return 2;
            }
         }
      }
      // diagonal check (top-left to bottom-right)
      for(int i = 0; i < values.length - 3; i++){  // only check to 4th last row
         for(int j = 0; j < values[0].length - 3; j++){  // only check to 4th last column
            if(values[i][j] == 0)
               continue;
            else if(values[i][j] == 1 && values[i][j] == values[i + 1][j + 1] && values[i][j] == values[i + 2][j + 2] && values[i][j] == values[i + 3][j + 3] ){ // check for 4 consecutive 1 matches
               tokenArray[i][j].setWinner(true);
               tokenArray[i + 1][j + 1].setWinner(true);
               tokenArray[i + 2][j + 2].setWinner(true);
               tokenArray[i + 3][j + 3].setWinner(true);
               return 1;
            }
            else if(values[i][j] == 2 && values[i][j] == values[i + 1][j + 1] && values[i][j] == values[i + 2][j + 2] && values[i][j] == values[i + 3][j + 3] ){ // check for 4 consecutive 2 matches
               tokenArray[i][j].setWinner(true);
               tokenArray[i + 1][j + 1].setWinner(true);
               tokenArray[i + 2][j + 2].setWinner(true);
               tokenArray[i + 3][j + 3].setWinner(true);
               return 2;
            }
         }
      }
      // diagonal check (top-right to bottom-left)
      for(int i = 0; i < values.length - 3; i++){
         for(int j = 3; j < values[0].length; j++){
            if(values[i][j] == 0)
               continue;
            else if(values[i][j] == 1 && values[i][j] == values[i + 1][j - 1] && values[i][j] == values[i + 2][j - 2] && values[i][j] == values[i + 3][j - 3] ){ // check for 4 consecutive 1 matches
               tokenArray[i][j].setWinner(true);
               tokenArray[i + 1][j - 1].setWinner(true);
               tokenArray[i + 2][j - 2].setWinner(true);
               tokenArray[i + 3][j - 3].setWinner(true);
               return 1;
            }
            else if(values[i][j] == 2 && values[i][j] == values[i + 1][j - 1] && values[i][j] == values[i + 2][j - 2] && values[i][j] == values[i + 3][j - 3] ){ // check for 4 consecutive 2 matches
               tokenArray[i][j].setWinner(true);
               tokenArray[i + 1][j - 1].setWinner(true);
               tokenArray[i + 2][j - 2].setWinner(true);
               tokenArray[i + 3][j - 3].setWinner(true);
               return 2;
            }
         }
      }
      return 0;
   }
}