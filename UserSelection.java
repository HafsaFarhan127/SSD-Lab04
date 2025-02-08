import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserSelection {
    private Scene selectionScene;
    private TextField selectionField = new TextField();
    private Stage stage;

    public UserSelection(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void initializeComponents() {
        VBox loginLayout = new VBox(5); //reduing thisc number made both the new label and button show.
        loginLayout.setPadding(new Insets(10));
        //User Input Label
        Label userInput=new Label("Enter a command number from 1-5 to showcase that command's syntax and info:");
        label.setWrapText(true); // Enable text wrapping
        Button detailsButton = new Button("Show me the deets");


        detailsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //here ill initialize the usersignup class and set that as scene as soon as signup button created
                CommandInfo commandInfo = new CommandInfo(stage, selectionField.getText());
                commandInfo.initializeComponents();

            }
        });
        loginLayout.getChildren().addAll(new Label("Enter a command number from 1-5 to showcase that command's syntax and info:"), selectionField,
                detailsButton);  //here i added a label to show OR and then i associated the sign up button i created.

        selectionScene = new Scene(loginLayout, 300, 200);
        stage.setTitle("User Selection");
        stage.setScene(selectionScene);
        stage.show();
    }
}
