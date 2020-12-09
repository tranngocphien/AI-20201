package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartController {
	@FXML 
	private Button btn3;
	@FXML
	private Button btn4;
	@FXML
	private Button btn5;
	@FXML
	private Button btn6;
	
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
		if(event.getSource() == btn6) {
			tileAmount = 6;
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/MainView2.fxml"));
		
		Parent rootParent = loader.load();		
		MainController mainController = loader.getController();
		mainController.setPane(tileAmount);
			
		Scene scene = new Scene(rootParent,800,600);
		Stage stage = new Stage();
		stage.setScene(scene);

		stage.show();
		
		
		
	}
	

}
