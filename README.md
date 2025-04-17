# Multiple-Java-Projects

This repository contains multiple Java projects, each organized under its respective package structure.

- **algorithms/**:
  
  **CustomDrawing:** This is a Java application that allows users to draw lines and ovals using mouse movement, based on their selection. Instead of relying on Java Swing’s built-in drawing functions, it utilizes mathematical algorithms—specifically Bresenham's line algorithm and the Midpoint ellipse algorithm—to render shapes pixel by pixel.

  **DijkstraAlgorithm:** A console-based Java application that implements Dijkstra's algorithm to compute the shortest path between nodes in a weighted graph. The user can define the graph structure and source node, and the program outputs the minimum distances from the source to all other nodes.
	
  **FordFulkerson:** A console-based Java application that implements the Ford-Fulkerson algorithm to calculate the maximum flow in a flow network. Users can define the network through adjacency matrices or edge lists, and the program computes the maximum possible flow from a given source node to a sink node using the Edmonds-Karp (BFS-based) approach.
	
  **ImageFilterApp:** ImageFilterApp: A graphical user interface (GUI) application that enables users to open images and apply various image processing filters. Supported filters include grayscale, blur, sharpening, and thresholding, all implemented using custom algorithms.
		
  **Permutations_Combinations:** This application implements four algorithms to generate and display all possible permutations and combinations of a given set, rather than merely calculating the total count of possible outcomes.

- **deep_learning/**:

  **diabetis_test:** A Java-based console application that analyzes medical data to predict the likelihood of diabetes in a patient. The program uses input features such as glucose level, BMI, age, and other health indicators to provide a diagnostic result, potentially based on rule-based logic or a simple machine learning model.

- **gui/**:

  **Calc:** A graphical user interface (GUI) calculator application designed with a functional layout and interactive buttons. While the interface is fully implemented, the backend logic for performing calculations has not yet been developed.

  **ExtendedShapesExample:** A GUI-based Java application that demonstrates the rendering of various geometric shapes using Java Swing’s built-in drawing capabilities. It showcases how to utilize the Graphics API to create and display multiple shapes within a windowed interface.

  **SLERPCircle:** A Java application that draws a gradient-colored circle by applying the mathematical principles of the Spherical Linear Interpolation (SLERP) function. This project demonstrates how the SLERP algorithm can be utilized to smoothly transition between colors along a circular path, producing a seamless gradient effect.

- **library/**:

  **BibliotheksMenu:** A console-based library management system designed to efficiently manage library resources. This application allows users to perform essential operations such as adding, removing, and searching for books, as well as managing user records and borrowing activities.
