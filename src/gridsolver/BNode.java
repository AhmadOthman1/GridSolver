/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gridsolver;

import javafx.scene.control.Button;

/**
 *
 * @author HI-TECH
 */
public class BNode {
    public double finalH;
    public int cost;
    public Button currentButton;
    public BNode ParentNode;
    public int i;
    public int j;
    
   public BNode(int Cost,double h,Button currentButton,BNode ParentNode){
        this.finalH=h;
        this.cost=Cost;
        this.currentButton=currentButton;
        this.ParentNode=ParentNode;
        String []cellId=this.currentButton.getId().split("_");
        cellId[0] = cellId[0].substring(1, cellId[0].length() );
        i=Integer.parseInt(cellId[0]);
        j=Integer.parseInt(cellId[1]);
    }
    public BNode(){}
    
}
