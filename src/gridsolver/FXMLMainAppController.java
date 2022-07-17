/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gridsolver;

import java.io.IOException;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HI-TECH
 */
public class FXMLMainAppController implements Initializable {
    int currentIndexI,currentIndexJ;
    String sourceIndexI,sourceIndexJ;
    String targetIndexI1,targetIndexJ1,targetIndexI2,targetIndexJ2;
    String sourceStyle,targetStyle,blockedStyle,unblockedStyle;
    ToggleGroup toggleGroup = new ToggleGroup();
    ToggleGroup toggleGroup2 = new ToggleGroup();
    ArrayList <Button> cells=new ArrayList();
    int sorceIndexInArrayList;
    int gridWidth,gridHeight;
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
    @FXML
    private Button backButton;
    @FXML
    private AnchorPane heuristicPane;
    @FXML
    private RadioButton manhattan;
    @FXML
    private RadioButton euclidean;
    @FXML
    private Button solveButton;
    @FXML
    private Label heuristicErrorLabel;
    @FXML
    private Button backButton1;
    @FXML
    private TextField stepTime;
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
        
        manhattan.setToggleGroup(toggleGroup2);
        euclidean.setToggleGroup(toggleGroup2);
        
        sourceStyle="-fx-background-color:#4FF622;-fx-border-color:black;-fx-border-width:2px;";
        targetStyle="-fx-background-color:#F62222;-fx-border-color:black;-fx-border-width:2px;";
        blockedStyle="-fx-background-color:#78724F;-fx-border-color:black;-fx-border-width:2px;";
        unblockedStyle="-fx-background-color:#EAE2B7;-fx-border-color:black;-fx-border-width:2px;";
    }    

    @FXML
    private void actionButton(ActionEvent event) {
        if(event.getSource()==generateGridButton){
            sourceIndexI="";
            sourceIndexJ="";
            targetIndexI1="";
            targetIndexJ1="";
            targetIndexI2="";
            targetIndexJ2="";
            gridWidth=Integer.parseInt(width.getText().trim());
            gridHeight=Integer.parseInt(height.getText().trim());
            sourceC.setSelected(true);
            GridPane grid = new GridPane();
            int max=gridWidth;// to set the max width*height for the cell
            for(int h=0;h<gridHeight;h++){
                for(int w=0;w<gridWidth;w++){
                    Button b=new Button();
                    b.setId("u"+h+"_"+w);//type and index for the cell stored in the id ( u: unblocked | s: source | t: target | b: bloked )
                    b.setStyle(unblockedStyle);
                    if(gridWidth<gridHeight){//determine the max # of blocks to claculate the max w*h
                        max=gridHeight;
                    }
                    b.setMinWidth(580/max);
                    b.setMinHeight(580/max);
                    // when the cell clicked
                    b.setOnAction((actionEvent) -> {
                        String []cellId=b.getId().split("_");
                        cellId[0] = cellId[0].substring(1, cellId[0].length() );// get the index from the id
                        if(sourceC.isSelected()){
                            if(b.getId().charAt(0)=='u' && sourceIndexI.equals("")){//if the cell is unblocked and no source chosed
                                //save the index
                                sourceIndexI=cellId[0];
                                sourceIndexJ=cellId[1];
                                sorceIndexInArrayList=(Integer.parseInt(cellId[0])*gridWidth)+Integer.parseInt(cellId[1]);//save the index in the arraylist
                                b.setId("s"+b.getId().substring(1,b.getId().length()));//change the type from u to s
                                b.setStyle(sourceStyle);//change color
                                locateErrorLabel.setVisible(false);//hide error label
                                //System.out.println(b.getId());
                            }
                            else{
                                locateErrorLabel.setText("*Error: You already chose the cell, please RESET the cell first.");
                                locateErrorLabel.setVisible(true);
                            }
                        }
                        else if(targetC.isSelected()){
                            if( b.getId().charAt(0)=='u' && (targetIndexI1.equals("") || targetIndexI2.equals(""))){//if the cell is unblocked and no target chosed(1 || 2)
                                if(targetIndexI1.equals("")){
                                    targetIndexI1=cellId[0];
                                    targetIndexJ1=cellId[1];
                                }
                                else if(targetIndexI2.equals("")){
                                    targetIndexI2=cellId[0];
                                    targetIndexJ2=cellId[1];
                                }
                                b.setId("t"+b.getId().substring(1,b.getId().length()));
                                b.setStyle(targetStyle);
                                locateErrorLabel.setVisible(false);
                                //System.out.println(b.getId());
                            }
                            else{
                                locateErrorLabel.setText("*Error: You already chose the cell, please RESET the cell first.");
                                locateErrorLabel.setVisible(true);
                            }
                        }
                        else if(blockedC.isSelected()){
                            if(b.getId().charAt(0)=='u'){//if the cell is unblocked
                                b.setId("b"+b.getId().substring(1,b.getId().length()));
                                b.setStyle(blockedStyle);
                                locateErrorLabel.setVisible(false);
                                //System.out.println(b.getId());
                            }
                            else{
                                locateErrorLabel.setText("*Error: You already chose the cell, please RESET the cell first.");
                                locateErrorLabel.setVisible(true);
                            }
                        }
                        else if(unblockedC.isSelected()){
                            if( b.getId().charAt(0)=='s' ){//reset source index variables
                                sourceIndexI="";
                                sourceIndexJ="";
                                sorceIndexInArrayList=-1;//reset the index
                                
                            }
                            else if( b.getId().charAt(0)=='t'  &&  targetIndexI1.equals(cellId[0])  &&  targetIndexJ1.equals(cellId[1])){//reset target1 index variables
                                targetIndexI1="";
                                targetIndexJ1="";
                            }
                            else if( b.getId().charAt(0)=='t'  &&  targetIndexI2.equals(cellId[0])  &&  targetIndexJ2.equals(cellId[1])){//reset target2 index variables
                                targetIndexI2="";
                                targetIndexJ2="";
                            }
                            b.setId("u"+b.getId().substring(1,b.getId().length()));
                            b.setStyle(unblockedStyle);
                            locateErrorLabel.setVisible(false);
                            //System.out.println(b.getId());
                        }
                    });
                    cells.add(b);
                    grid.add(b, w, h);//add the button on the grid
                }
            }
            gridPane.getChildren().clear();
            gridPane.getChildren().add(grid);
            grid.setStyle("-fx-border-color:#A12A31;-fx-border-width:3px;");
            gridPane.setPadding(new Insets((580-(580/max*Integer.parseInt(height.getText().trim())))/2, (580-(580/max*Integer.parseInt(width.getText().trim())))/2, (580-(580/max*Integer.parseInt(height.getText().trim())))/2, (580-(580/max*Integer.parseInt(width.getText().trim())))/2));//center the grid 
            mainPane.setVisible(false);
            gridPane.setVisible(true);
            locatePane.setVisible(true);
        }
        if(event.getSource()==saveLocateButton){
            try{
            if((targetIndexI1.equals("") && targetIndexI2.equals("")) || sourceIndexI.equals("")){
                throw new Exception();
            }
            locatePane.setVisible(false);
            heuristicPane.setVisible(true);
            manhattan.setSelected(true);
            }
            catch(Exception E){
                
            }
        }
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws IOException {
        if(event.getSource()==backButton ){
            mainPane.setVisible(true);
            gridPane.setVisible(false);
            locatePane.setVisible(false);
            height.setText("");
            width.setText("");
        }
        if(event.getSource()==backButton1 ){
            mainPane.setVisible(false);
            gridPane.setVisible(true);
            locatePane.setVisible(true);
            heuristicPane.setVisible(false);
            manhattan.setDisable(false);
            euclidean.setDisable(false);
            stepTime.setDisable(false);
        }
    }

    @FXML
    private void solveAction(ActionEvent event) {
        if(event.getSource()==solveButton){
            
            manhattan.setDisable(true);
            euclidean.setDisable(true);
            stepTime.setDisable(true);
            
            
            
            if(numaric(stepTime.getText().trim())){
                
                findpath(sourceIndexI,sourceIndexJ,targetIndexI1,targetIndexJ1);
            }
            else{
                heuristicErrorLabel.setText("*Error: Please Enter only numbers for the Time");
            }
            
        }
    }
    private boolean numaric(String str){
        for(int i=0;i<str.length();i++){
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
    private double clacManhattan(String x1,String y1,Button node){
        String []nodeIndex=node.getId().split("_");
        nodeIndex[0] = nodeIndex[0].substring(1, nodeIndex[0].length() );
        int x2=Integer.parseInt(nodeIndex[0]);
        int y2=Integer.parseInt(nodeIndex[1]);
        return abs(Integer.parseInt(x1)-x2)+abs(Integer.parseInt(y1)-y2);
    }
    private double clacEuclidean(String x1,String y1,Button node){
        String []nodeIndex=node.getId().split("_");
        nodeIndex[0] = nodeIndex[0].substring(1, nodeIndex[0].length() );
        int x2=Integer.parseInt(nodeIndex[0]);
        int y2=Integer.parseInt(nodeIndex[1]);
        return sqrt(pow(abs(Integer.parseInt(x1)-x2),2)+pow(abs(Integer.parseInt(y1)-y2),2));
    }
    /*
                sourceIndexI="";
                sourceIndexJ="";
                targetIndexI1="";
                targetIndexJ1="";
                targetIndexI2="";
                targetIndexJ2="";
                */
    private double findpath(String sIndexI,String sIndexJ,String tIndexI1,String tIndexJ1){
        ArrayList <Button> closedList = new ArrayList();
        ArrayList <Button> openList = new ArrayList();
        Button n=new Button();
        double nh;
        openList.add(cells.get(sorceIndexInArrayList));
        do{
            if(openList.isEmpty())
                return -1;
            if(manhattan.isSelected())
                clacManhattan(tIndexI1,tIndexJ1,cells.get(sorceIndexInArrayList));
        } while(!openList.isEmpty());
        return 0;
    }
    
}
