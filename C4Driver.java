import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class C4Driver extends Application{
   public void start(Stage primaryStage){
      ConnectFour pane = new ConnectFour(6, 7);
      Scene scene = new Scene(pane, 700, 700);
      primaryStage.setTitle("Connect Four"); // Set the stage title
      primaryStage.setScene(scene); // Place the scene in the stage
      primaryStage.setResizable(false);
      primaryStage.show(); // Display the stage
      pane.requestFocus();
   }
   public static void main(String[] args) {
      launch(args);
   }
}