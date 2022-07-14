/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gridsolver;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author HI-TECH
 */
public class FXMLMainAppController implements Initializable {
    int currentIndexI,currentIndexJ;
    String sourceIndexI,sourceIndexJ;
    String targetIndexI1,targetIndexJ1,targetIndexI2,targetIndexJ2;
    ToggleGroup toggleGroup = new ToggleGroup();
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField height;
    @FXML
    private TextField width;
    @FXML
    private Button generateGridButton;
    @FXML
    private Label widthErrorLabel;
    @FXML
    private StackPane gridPane;
    @FXML
    private Label hightErrorLabel;
    @FXML
    private AnchorPane locatePane;
    @FXML
    private RadioButton sourceC;
    @FXML
    private RadioButton targetC;
    @FXML
    private RadioButton blockedC;
    @FXML
    private RadioButton unblockedC;
    @FXML
    private Button saveLocateButton;
    @FXML
    private Label locateErrorLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sourceC.setToggleGroup(toggleGroup);
        targetC.setToggleGroup(toggleGroup);
        blockedC.setToggleGroup(toggleGroup);
        unblockedC.setToggleGroup(toggleGroup);
        sourceIndexI="";
        sourceIndexJ="";
        targetIndexI1="";
        targetIndexJ1="";
        targetIndexI2="";
        targetIndexJ2="";
    }    

    @FXML
    private void actionButton(ActionEvent event) {
        if(event.getSource()==generateGridButton){
            GridPane grid = new GridPane();
            int counter=0;
            int max=Integer.parseInt(width.getText().trim());
            for(int h=0;h<Integer.parseInt(height.getText().trim());h++){
                for(int w=0;w<Integer.parseInt(width.getText().trim());w++){
                Button b=new Button();
                b.setId("u"+h+"_"+w);
                b.setStyle("-fx-background-color:#EAE2B7;-fx-border-color:black;-fx-border-width:2px;");
                if(Integer.parseInt(width.getText().trim())<Integer.parseInt(height.getText().trim())){
                    max=Integer.parseInt(height.getText().trim());
                }
                b.setMinWidth(580/max);
                b.setMinHeight(580/max);
                // when the cell clicked
                b.setOnAction((actionEvent) -> {
                    String []cellId=b.getId().split("_");
                    cellId[0] = cellId[0].substring(1, cellId[0].length() );// get the index from the id
                    if(sourceC.isSelected()){
                        if(b.getId().charAt(0)=='u' && sourceIndexI.equals("")){//if the cell is unblocked and no source chosed
                            sourceIndexI=cellId[0];
                            sourceIndexJ=cellId[1];
                            b.setId("s"+b.getId().substring(1,b.getId().length()));
                            b.setStyle("-fx-background-color:#4FF622;-fx-border-color:black;-fx-border-width:2px;");
                            locateErrorLabel.setVisible(false);
                        }
                        else{
                            locateErrorLabel.setText("*Error: You already chose the cell, please RESET the cell first.");
                            locateErrorLabel.setVisible(true);
                        }
                    }
                    else if(targetC.isSelected()){
                        if( b.getId().charAt(0)=='u' && (targetIndexI1.equals("") || targetIndexI2.equals(""))){//if the cell is unblocked and no target chosed
                            if(targetIndexI1.equals("")){
                                targetIndexI1=cellId[0];
                                targetIndexJ1=cellId[1];
                            }
                            else if(targetIndexI2.equals("")){
                                targetIndexI2=cellId[0];
                                targetIndexJ2=cellId[1];
                            }
                            b.setId("t"+b.getId().substring(1,b.getId().length()));
                            b.setStyle("-fx-background-color:#F62222;-fx-border-color:black;-fx-border-width:2px;");
                            locateErrorLabel.setVisible(false);
                        }
                        else{
                            locateErrorLabel.setText("*Error: You already chose the cell, please RESET the cell first.");
                            locateErrorLabel.setVisible(true);
                        }
                    }
                    else if(unblockedC.isSelected()){
                        if( b.getId().charAt(0)=='s' ){
                            
                            b.setId("u"+b.getId().substring(1,b.getId().length()));
                            b.setStyle("-fx-background-color:#EAE2B7;-fx-border-color:black;-fx-border-width:2px;");
                            locateErrorLabel.setVisible(false);
                        }
                        else{
                            locateErrorLabel.setText("*Error: You already chose the cell, please RESET the cell first.");
                            locateErrorLabel.setVisible(true);
                        }
                    }
                });
                grid.add(b, w, h);
                }
            }
            
            gridPane.getChildren().add(grid);
            grid.setStyle("-fx-border-color:#A12A31;-fx-border-width:3px;");
            gridPane.setPadding(new Insets((580-(580/max*Integer.parseInt(height.getText().trim())))/2, (580-(580/max*Integer.parseInt(width.getText().trim())))/2, (580-(580/max*Integer.parseInt(height.getText().trim())))/2, (580-(580/max*Integer.parseInt(width.getText().trim())))/2));
            mainPane.setVisible(false);
            gridPane.setVisible(true);
            locatePane.setVisible(true);
        }
    }
    
}
