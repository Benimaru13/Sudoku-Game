import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/*
Author: Chibueze Benneth
Class: Computer Science 12
Date: 1st January 2025
Description: This is a simple Java created Sudoku Game using Swing for GUI.
*/

class CSudokuGame{

    public CSudokuGame() {
        // Variables
        int rows = 3;
        int cols = 3;

        // Main Sudoku Arrays
        // Initialize a 9 x 9 array of numbers for the Sudoku grid
        int [][] intArr = {
            {7, 4, 1, 8, 9, 5, 3, 6, 2},
            {2, 5, 8, 6, 3, 4, 7, 9, 1},
            {3, 9, 6, 1, 7, 2, 4, 5, 8},
            {1, 6, 4, 3, 8, 9, 5, 2, 7},
            {9, 2, 7, 4, 5, 1, 6, 8, 3},
            {8, 3, 5, 7, 2, 6, 1, 4, 9},
            {5, 1, 2, 9, 6, 7, 8, 3, 4},
            {6, 7, 3, 2, 4, 8, 9, 1, 5},
            {4, 8, 9, 5 ,1, 3, 2, 7, 6}                 
        };

        // Initialize a 9 x 9 boolean array to track filled cells        
        boolean [][] boolArr = {
            {true, true, false, false, false, false, false, false, false},
            {false, false, false, false, true, false, false, false, true},
            {false, false, true, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false},
            {false, true, false, false, true, false, false, false, false},
            {false, false, false, false, false, true, false, true, false},
            {false, false, false, false, false, false, false, false, false},
            {false, true, false, false, true, false, false, false, false},
            {false, false, false, false, false, false, false, false, true},       
        };   


        // Initialize the main frame
        JFrame frame = new JFrame("Sudoku Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400); // Temporary size
        frame.setVisible(true);

        // Initialize the message panel
        JPanel messagePanel = new JPanel();
        JLabel titleMessage = new JLabel("Chibueze's Sudoku Game");
        messagePanel.add(titleMessage, BorderLayout.NORTH);
        Font messageFont = new Font("Arial", Font.BOLD, 16);
        titleMessage.setFont(messageFont);
        frame.add(messagePanel);

        // Initialize the main Sudoku grid panel
        GridLayout gl = new GridLayout(rows, cols);
        JPanel mainPanel = new JPanel(new BorderLayout()); // This is the main panel that holds everything

        // initialize a main grid panel with a 3x3 layout
        JPanel gridPanel = new JPanel(gl); 
        for (int gridrow = 0; gridrow < 3; gridrow++) {
            for (int gridcol = 0; gridcol < 3; gridcol++) {
                // For each cell in the main grid, create a sub-panel with a 3x3 layout
                JPanel subPanel = new JPanel(gl);
                subPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK));   
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        int rowIndex = gridrow * 3 + r;
                        int colIndex = gridcol * 3 + c;
                        SButton button = new SButton(intArr[rowIndex][colIndex], boolArr[rowIndex][colIndex]);
                        subPanel.add(button);

                        
                    }
                }
                gridPanel.add(subPanel);
            }
        }

        mainPanel.add(gridPanel, BorderLayout.CENTER);
        frame.add(mainPanel, BorderLayout.SOUTH);
        frame.pack();


    }

    public static void main(String[] args) {
        CSudokuGame game = new CSudokuGame();
    }
}
