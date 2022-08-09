## Description :
An application that solves a maze using the `A*` algorithm to calculate the shortest path from the source to the target.
the maze contains 4 type of blocks (cells):
* **Source:** The algorithm will start the path from there.
* **Target:** Up to 2 targets, the algorithm will find the shortest path for the nearest target.
* **Blocked:** Blocking the path from reaching current cell.
* **Unblocked:** Normal cell that the path can come across it.

<img src="https://user-images.githubusercontent.com/103585755/183632751-4c4e3d86-f3db-4366-8d60-791c7cfc84c8.png" width="400" height="300">



## GUI :
* **Main Page**

  In the Main Page you can enter the width & Height for the grid > 1, and generate an empty maze
  
![image](https://user-images.githubusercontent.com/103585755/183631791-f80bfc87-c7d4-415f-a24a-86fa5bc119a3.png)

* **Locate the cells**
  
  in this page you can locate the `source`, `target`, and the `blocked` cells,By choosing one, then click on the cell you want to set, to reset a cell you can choose the `Unblocked` choice.

![image](https://user-images.githubusercontent.com/103585755/183635806-3a2f4885-a318-4689-bef1-1716c2422468.png)


* #**Choosing A heuristic**

 After clicking Save, you can choose the optimal distance to use for calculating the heuristic.
<img src="https://user-images.githubusercontent.com/103585755/183636837-03d4ba8b-134a-4791-b30c-1ea17ed3e8cb.png" width="200" Height="300">
  
