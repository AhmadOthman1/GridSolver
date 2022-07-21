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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author HI-TECH
 */
public class FXMLMainAppController implements Initializable {
    int currentIndexI,currentIndexJ;
    String sourceIndexI,sourceIndexJ;
    String targetIndexI1,targetIndexJ1,targetIndexI2,targetIndexJ2;
    String sourceStyle,targetStyle,blockedStyle,unblockedStyle,stepStyle,PathStyle;
    ToggleGroup toggleGroup = new ToggleGroup();
    ToggleGroup toggleGroup2 = new ToggleGroup();
    ArrayList <Button> cells=new ArrayList();
    int sorceIndexInArrayList;
    int gridWidth,gridHeight;
    static int pathIndex=1;
    static BNode target,secTarget;
    boolean  SolutionFlag;
    ArrayList <BNode> closedList = new ArrayList();
    ArrayList <BNode> openList = new ArrayList();
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
    @FXML
    private AnchorPane resultPane;
    @FXML
    private Label resultErrorLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //radio button group
        sourceC.setToggleGroup(toggleGroup);
        targetC.setToggleGroup(toggleGroup);
        blockedC.setToggleGroup(toggleGroup);
        unblockedC.setToggleGroup(toggleGroup);
        
        manhattan.setToggleGroup(toggleGroup2);
        euclidean.setToggleGroup(toggleGroup2);
        
        //buttons style strings for css 
        sourceStyle="-fx-background-color:#4FF622;-fx-border-color:black;-fx-border-width:2px;";
        targetStyle="-fx-background-color:#F62222;-fx-border-color:black;-fx-border-width:2px;";
        blockedStyle="-fx-background-color:#78724F;-fx-border-color:black;-fx-border-width:2px;";
        unblockedStyle="-fx-background-color:#EAE2B7;-fx-border-color:black;-fx-border-width:2px;";
        stepStyle="-fx-background-color:rgba(66, 255, 0, 0.3);-fx-border-color:black;-fx-border-width:2px;-fx-text-fill:white;-fx-font-size: 8px;";
        PathStyle="-fx-background-color:#0013B6;-fx-border-color:black;-fx-border-width:2px;";
    }    

    @FXML
    private void actionButton(ActionEvent event) {
        if(event.getSource()==generateGridButton){
            //clear all variables
            sourceIndexI="";
            sourceIndexJ="";
            targetIndexI1="";
            targetIndexJ1="";
            targetIndexI2="";
            targetIndexJ2="";
            //check if w*h correct
            if(!height.getText().trim().equals("") && !width.getText().trim().equals("") && numaric(width.getText().trim())&& numaric(height.getText().trim()) && Integer.parseInt(width.getText().trim())>1 && Integer.parseInt(height.getText().trim())>1){
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
                        if(gridWidth<gridHeight){//determine the max # of blocks in (h or w) to claculate the max w*h for the button (cell)
                            max=gridHeight;
                        }
                        b.setMinWidth(580/max);
                        b.setMinHeight(580/max);
                        // when the cell clicked
                        b.setOnAction((actionEvent) -> {//if button clecked change the type to what is selected
                            String []cellId=b.getId().split("_");
                            cellId[0] = cellId[0].substring(1, cellId[0].length() );// get the index from the id
                            if(locatePane.isVisible()){//if the button is clecked in stage that locatePane is visible only (the user can choose in this stage)
                                if(sourceC.isSelected()){
                                    if(b.getId().charAt(0)=='u' && sourceIndexI.equals("")){//if the cell is unblocked and no source chosed
                                        //save the index
                                        sourceIndexI=cellId[0];
                                        sourceIndexJ=cellId[1];
                                        sorceIndexInArrayList=(Integer.parseInt(cellId[0])*gridWidth)+Integer.parseInt(cellId[1]);//save the index in the arraylist
                                        b.setId("s"+b.getId().substring(1,b.getId().length()));//change the type from u to s
                                        b.setStyle(sourceStyle);//change color
                                        locateErrorLabel.setVisible(false);//hide error label
                                    }
                                    else{
                                        locateErrorLabel.setText("*Error: You already chose the cell, please RESET the cell first.");
                                        locateErrorLabel.setVisible(true);
                                    }
                                }
                                else if(targetC.isSelected()){//if the cell is unblocked and no target chosed
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
                                    }
                                    else{
                                        locateErrorLabel.setText("*Error: You already chose the cell, please RESET the cell first.");
                                        locateErrorLabel.setVisible(true);
                                    }
                                }
                                else if(blockedC.isSelected()){//if the cell is unblocked and blocked choice is selected
                                    if(b.getId().charAt(0)=='u'){//if the cell is unblocked
                                        b.setId("b"+b.getId().substring(1,b.getId().length()));
                                        b.setStyle(blockedStyle);
                                        locateErrorLabel.setVisible(false);
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
                                }
                        }
                        });
                        cells.add(b);//add the button on the arraylist so we can reach later
                        grid.add(b, w, h);//add the button on the grid
                    }
                }
                gridPane.getChildren().clear();
                gridPane.getChildren().add(grid);
                grid.setStyle("-fx-border-color:#A12A31;-fx-border-width:1px;");
                //center the grid 
                gridPane.setPadding(new Insets((580-(580/max*Integer.parseInt(height.getText().trim())))/2, (580-(580/max*Integer.parseInt(width.getText().trim())))/2, (580-(580/max*Integer.parseInt(height.getText().trim())))/2, (580-(580/max*Integer.parseInt(width.getText().trim())))/2));
                //change the visible pane
                mainPane.setVisible(false);
                gridPane.setVisible(true);
                locatePane.setVisible(true);
        }
        }
        if(event.getSource()==saveLocateButton){
            try{
            if((targetIndexI1.equals("") && targetIndexI2.equals("")) || sourceIndexI.equals("")){//if there is no target or source
                throw new Exception();
            }//else
            locatePane.setVisible(false);
            heuristicPane.setVisible(true);
            manhattan.setSelected(true);
            locateErrorLabel.setVisible(false);
            }
            catch(Exception E){
                locateErrorLabel.setText("*Error: Please choose one source and one/two target First.");
                locateErrorLabel.setVisible(true);
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
            stepTime.setText("");
        }
    }
    @FXML
    private void solveAction(ActionEvent event) {
        if(event.getSource()==solveButton){
            mainPane.setVisible(false);
            gridPane.setVisible(true);
            locatePane.setVisible(false);
            heuristicPane.setVisible(false);
            resultPane.setVisible(true);
            resultErrorLabel.setVisible(false);
            SolutionFlag=true;
            try {
                secTarget=(!targetIndexI2.equals("")) ? findpath(sourceIndexI,sourceIndexJ,targetIndexI2,targetIndexJ2) : null;//if there is target1
                ArrayList <BNode> closedList2=closedList;//store closed list for 1
                target=(!targetIndexI1.equals("")) ? findpath(sourceIndexI,sourceIndexJ,targetIndexI1,targetIndexJ1) : null;//if there is target2
                if(target !=null){
                    if(secTarget!=null){
                        if(target.finalH>secTarget.finalH){
                            target=secTarget;
                            closedList=closedList2;
                        }
                    }
                }
                else if(secTarget!=null){
                    target=secTarget;
                    closedList=closedList2;
                }
                else{
                    resultErrorLabel.setText("There is no solution for the grid");
                    resultErrorLabel.setVisible(true);
                    SolutionFlag=false;
                }
                        
                if(!stepTime.getText().trim().equals("") && numaric(stepTime.getText().trim()) && Integer.parseInt(stepTime.getText())!=0){//find if time textfield is a number
                    Timeline timeline;
                    timeline = new Timeline(
                            new KeyFrame(Duration.millis(Integer.parseInt(stepTime.getText().trim())), new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {//show the steps
                                    if(closedList.size()>pathIndex){
                                        closedList.get(pathIndex).currentButton.setStyle(stepStyle);
                                        closedList.get(pathIndex).currentButton.setText(String.valueOf(closedList.get(pathIndex).finalH) +"/"+pathIndex);
                                        closedList.get(pathIndex).currentButton.setTooltip(new Tooltip(closedList.get(pathIndex).finalH-closedList.get(pathIndex).cost +"+"+closedList.get(pathIndex).cost+"/"+pathIndex));
                                        pathIndex++;
                                    }
                                    else{
                                        if(SolutionFlag && target!=null ){//show the shortest path
                                            target=target.ParentNode;
                                            while(target!=null && target.ParentNode!=null){
                                                target.currentButton.setStyle(PathStyle);
                                                target=target.ParentNode;
                                            }
                                        }
                                    }
                                }
                            })
                    );
                        timeline.setCycleCount(Timeline.INDEFINITE);
                        timeline.play();
                
                }
                else if(stepTime.getText().trim().equals("") || (numaric(stepTime.getText().trim()) && Integer.parseInt(stepTime.getText())==0)){//if time is 0
                    while(target!=null && target.ParentNode!=null){//show the shortest path
                        target.currentButton.setStyle(PathStyle);
                        target=target.ParentNode;
                    }
                }
                else{
                    heuristicErrorLabel.setText("*Error: Please Enter only numbers for the Time");
                }
            } catch (Exception ex) {
                    Logger.getLogger(FXMLMainAppController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    private BNode findpath(String sIndexI,String sIndexJ,String tIndexI,String tIndexJ) throws InterruptedException{//find the shortest path fun
        closedList = new ArrayList();
        openList = new ArrayList();
        openList.add(new BNode(0,0,cells.get(sorceIndexInArrayList),null));// add the source
        do{
            if(openList.isEmpty())
                return null;
            
            if(openList.get(0).i==Integer.parseInt(tIndexI)  &&  openList.get(0).j==Integer.parseInt(tIndexJ)){//if goul
                return openList.get(0);
            }
            //check for the neighbours according to rules
            int parentIndexi=openList.get(0).i;
            int parentIndexj=openList.get(0).j;            
            if(parentIndexi+1<gridHeight && cells.get(findIndexInArr(parentIndexi+1,parentIndexj)).getId().charAt(0)!='b' ){//down 
                    checkNeighbours( tIndexI, tIndexJ, parentIndexi+1, parentIndexj);
            }
            if(parentIndexi-1>=0 && cells.get(findIndexInArr(parentIndexi-1,parentIndexj)).getId().charAt(0)!='b'){//Up 
                checkNeighbours( tIndexI, tIndexJ, parentIndexi-1, parentIndexj);
            }
            if(parentIndexj+1<gridWidth && cells.get(findIndexInArr(parentIndexi,parentIndexj+1)).getId().charAt(0)!='b'){//right 
                checkNeighbours( tIndexI, tIndexJ, parentIndexi, parentIndexj+1);
            }
            if(parentIndexj-1>=0 && cells.get(findIndexInArr(parentIndexi,parentIndexj-1)).getId().charAt(0)!='b'){//left 
                checkNeighbours( tIndexI, tIndexJ, parentIndexi, parentIndexj-1);
            }
            closedList.add(openList.get(0));
            openList.remove(0);  //delete the parent
        } while(!openList.isEmpty());
        return null;
    }
    private void checkNeighbours(String tIndexI,String tIndexJ,int Indexi,int Indexj){//cheak for neighbour
        int parentCost=openList.get(0).cost;
        double heuristic=0;
        if(manhattan.isSelected()){ heuristic=clacManhattan(tIndexI,tIndexJ,Indexi,Indexj); }//if manhattan is selected calc the heuristic
        if(euclidean.isSelected()){ heuristic=clacEuclidean(tIndexI,tIndexJ,Indexi,Indexj); }//if euclidean is selected calc the heuristic
                BNode n=new BNode(parentCost+1,parentCost+1+heuristic,cells.get(findIndexInArr(Indexi,Indexj)),openList.get(0));//create the node
                boolean flag=true;
                for(int listIndex =0;listIndex<closedList.size();listIndex++){//loop on closed list
                    if(closedList.get(listIndex).currentButton.equals(n.currentButton)){//if the nde in closed list
                        if(closedList.get(listIndex).finalH >n.finalH)//if the fNode < fStored in CL replace it
                            closedList.set(listIndex, n);
                        flag=false;//dont check the OL
                        break;
                    }
                }
                if(flag){//if node not on CL
                    boolean notInOpenFlag=true;
                    for(int listIndex =1;listIndex<openList.size();listIndex++){//check for the open list
                        if(openList.get(listIndex).currentButton.equals(n.currentButton)  ){//if the node in OL
                            if(openList.get(listIndex).finalH == n.finalH){//if f() is equals check for h (h=f-g)
                                if(openList.get(listIndex).finalH-openList.get(listIndex).cost > n.finalH-n.cost){
                                    openList.remove(listIndex);
                                    notInOpenFlag=true;//to add the node again with diffrent path
                                    break;
                                }
                            }
                            else if(openList.get(listIndex).finalH >n.finalH){//if f() OL > f()Node thin Noode is better
                                openList.remove(listIndex);
                                notInOpenFlag=true;//to add the node again with diffrent path
                                break;
                            }
                            notInOpenFlag=false;//else : f()OL < f()Node so we dont remove it and dont add it again
                            break;
                            }
                    }
                    if(notInOpenFlag){
                        boolean endFlag=true;
                        for(int arrindex=1;arrindex<openList.size();arrindex++ ){//store in open List 
                            if(openList.get(arrindex).finalH==n.finalH){
                                if(openList.get(arrindex).finalH-openList.get(arrindex).cost >n.finalH-n.cost ){
                                    openList.add(arrindex, n);
                                    endFlag=false; 
                                    break;
                                }
                            }
                            else if(openList.get(arrindex).finalH>n.finalH){
                            openList.add(arrindex, n);
                            endFlag=false; 
                            break;
                            }
                        }
                        if(endFlag){
                            openList.add(n);
                        }
                    }
                }
    }
    private boolean numaric(String str){//to find if the string is a number
        for(int i=0;i<str.length();i++)
            if(!Character.isDigit(str.charAt(i)) )
                return false;
        return true;
    }
    private double clacManhattan(String x1,String y1,int i,int j){//calculate Manhattan
        return abs(Integer.parseInt(x1)-i)+abs(Integer.parseInt(y1)-j);
    }
    private double clacEuclidean(String x1,String y1,int i, int j){//calculate clidean
        return sqrt(pow(abs(Integer.parseInt(x1)-i),2)+pow(abs(Integer.parseInt(y1)-j),2));
    }
    private int findIndexInArr(int i, int j){return (i*gridWidth)+j;}
}

