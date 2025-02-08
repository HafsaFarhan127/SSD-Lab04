import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CommandInfo {
    private final String userSelection;
    private Scene commandInfoScene;
    private Stage stage;
    private VBox commandInfoLayout;  // Declare VBox as a class member so that i can modify it from any function


    public CommandInfo(Stage primaryStage, String userSelection){
        this.stage = primaryStage;
        this.userSelection = userSelection;
    }

    public void initializeComponents() {
        commandInfoLayout = new VBox(10);
        //inside this vbox i will be injecting the info from  the db from the display function
        commandInfoLayout.setPadding(new Insets(10));
        Button backButton = new Button("Back");
        displayCommandInfo();


        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                UserSelection back = new UserSelection(stage); //primary stage gets replaced with stage
                back.initializeComponents();
            }
        });

        //here the order matters for adding all to command Layout cuz my displaydetails function also does the same ,this injection of data will show later the function one will show first.
        //i can add this from whereever i want only due to making vbox a class member as we associate the labels -> vbox & then vbox->layout
        commandInfoLayout.getChildren().add(backButton); //changed from addAll to add as only one label.

        //this will link Layout to scene
        commandInfoScene = new Scene(commandInfoLayout, 300, 200);
        stage.setTitle("Command Information");
        stage.setScene(commandInfoScene);
        stage.show();
    }

    private void displayCommandInfo(){
        Connection con = DBUtils.establishConnection();
        String query = "SELECT command,description FROM git_commands WHERE id=? ;";
        try{
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, userSelection);
            ResultSet rs = statement.executeQuery(); //this takes in no param it automatically runs the query cuz of code in line 52.

            if (rs.next()) {
                // Retrieve command and description from the result set
                String command = rs.getString("command"); //this gets the specific column value from the sql response ,
                                                                    // we specify the column we want here, it helps to format the data or segregate it for handling
                String description = rs.getString("description");

                // Create a label to display the information in the desired format
                String infoText = "Syntax: " + command + "\nDescription: " + description;
                Label commandInfoLabel = new Label(infoText);

                //addAll to add multiple labels and components.
                commandInfoLayout.getChildren().addAll(new Label("The command that you have chosen is below: "),commandInfoLabel); //here i am adding directly to the vbox now because i made it a class member (globally accessible)
            } else {
                showAlert("No Data", "No command found for the selected ID.");
            }

            DBUtils.closeConnection(con, statement);
        }catch(Exception e){
            e.printStackTrace();
            showAlert("Database Error", "Failed to connect to the database.");
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
