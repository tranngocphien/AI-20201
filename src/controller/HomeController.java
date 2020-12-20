package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomeController {
	@FXML
	private BorderPane borderPane;
	@FXML 
	private Button btn3;
	@FXML
	private Button btn4;
	@FXML
	private Button btn5;
	
	private int tileAmount;
	
	public void handle(ActionEvent event) throws IOException {
		
		if(event.getSource() == btn3) {
			tileAmount = 3;
		}
		if(event.getSource() == btn4) {
			tileAmount = 4;
		}
		if(event.getSource() == btn5) {
			tileAmount = 5;
		}

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/MainView2.fxml"));
		
		Pane rootParent =(Pane) loader.load();		
		MainController mainController = loader.getController();
		mainController.setPane(tileAmount);
		
		borderPane.setCenter(rootParent);
//			
//		Scene scene = new Scene(rootParent,800,600);
//		Stage stage = new Stage();
//		stage.setScene(scene);
//
//		stage.show();
	}

}
