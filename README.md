## Description 
An application that solves a maze using the `A*` algorithm to calculate the shortest path from the source to the target.
the maze contains 4 type of blocks (cells):
* **Source:** The algorithm will start the path from there.
* **Target:** Up to 2 targets, the algorithm will find the shortest path for the nearest target.
* **Blocked:** Blocking the path from reaching current cell.
* **Unblocked:** Normal cell that the path can come across it.

<img src="https://user-images.githubusercontent.com/103585755/183632751-4c4e3d86-f3db-4366-8d60-791c7cfc84c8.png" width="400" height="300">



## GUI 
* **Main Page**

  In the Main Page you can enter the width & Height for the grid > 1, and generate an empty maze
  
![image](https://user-images.githubusercontent.com/103585755/183631791-f80bfc87-c7d4-415f-a24a-86fa5bc119a3.png)

* **Locate the cells**
  
  in this page you can locate the `source`, `target`, and the `blocked` cells,By choosing one, then click on the cell you want to set, to reset a cell you can choose the `Unblocked` choice.

![image](https://user-images.githubusercontent.com/103585755/183635806-3a2f4885-a318-4689-bef1-1716c2422468.png)


* **Choosing A heuristic**

 After clicking Save, you can choose the optimal distance to use for calculating the heuristic.
 
<img src="https://user-images.githubusercontent.com/103585755/183636837-03d4ba8b-134a-4791-b30c-1ea17ed3e8cb.png" width="200" Height="300">
  
In the `step time` textfield you can enter a time(Msec) to show every step the algorithm takes to reach the target, If you entered 0, Only the final shortest path will appear on the grid 

![image](https://user-images.githubusercontent.com/103585755/183638477-31034322-81ef-44ff-bf93-f5fe4f307c35.png)


* **Result**


In this page you will see the final results for the grid, every cell has a `final heuristic / step#` and if you dragged the mouse over the cell you can see the `heuristic + cost / step#`  

![image](https://user-images.githubusercontent.com/103585755/183641663-ad58f308-b984-463d-8676-d21844889617.png)

<img src="https://user-images.githubusercontent.com/103585755/183640933-5f77259b-41a3-423a-92db-f0f731238f0f.gif" width="400" Height="400">




