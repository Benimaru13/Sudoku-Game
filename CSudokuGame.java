import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/*
import java.awt.Dimension;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.border.Border; */
/*
Author: Chibueze Benneth
Class: Computer Science 12
Date: 1st January 2025
Description: This is a simple Java created Sudoku Game using Swing for GUI.
*/

class CSudokuGame{
     SButton selectedButton = null;
        int currentNumber = 0;
        private SButton selectedNumberButton = null; // for highlighting the number button

       /* JButton removeBtn;
            // Border for selected cell
 
        
         // "Remove #" button (right)
        removeBtn = new JButton("Remove #");
        removeBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        // Let the button size itself to the text + margins
        removeBtn.setMargin(new Insets(8, 12, 8, 12));
        removeBtn.setOpaque(true);
        removeBtn.setFocusPainted(false);
        removeBtn.setBorder(BorderFactory.createLineBorder(Color.decode("#8D6E63"), 2));
        */

    public CSudokuGame() {
        // Variables
        int rows = 3;
        int cols = 3;
       



        // Main Sudoku Arrays
        // Initialize a 9 x 9 array of numbers for the Sudoku grid
        int [][] intArr = {
            {1, 7, 2, 5, 8, 4, 3, 6, 9}, 
            {9, 3, 5, 6, 2, 7, 8, 1, 4},
            {8, 6, 4, 1, 3, 9, 5, 7, 2},
            {7, 2, 1, 4, 6, 3, 9, 5, 8},
            {3, 5, 6, 7, 9, 8, 4, 2, 1},
            {4, 8, 9, 2, 5, 1, 6, 3, 7},
            {5, 9, 7, 8, 1, 6, 2, 4, 3},
            {2, 1, 3, 9, 4, 5, 7, 8, 6},
            {6, 4, 8, 3, 7, 2, 1, 9, 5}             
        };

        // Initialize a 9 x 9 boolean array to track filled cells        
        boolean [][] boolArr = {
                    {true, true, true, true, true, true, false, false, false},          
                    {false, false, true, false, false, false, true, false, true},
                    {false, false, true, false, false, false, false, false, false},
                    {false, true, true, false, true, true, false, false, false},
                    {true, true, false, false, false, false, false, true, false},
                    {false, false, false, true, false, true, false, true, true},
                    {false, false, false, false, false, true, false, true, false},
                    {true, false, true, true, true, false, false, true, false},
                    {false, true, false, false, true, true, false, false, true},       
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

        // Add Side Panel for Number Selection
        JPanel sidePanel = new JPanel();
        // sidePanel.setPreferredSize(new Dimension(300, 300));
        sidePanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK));
        JPanel numPanel = new JPanel(gl);
        for (int i = 1; i <= 9; i++) {
            SButton numButton = new SButton(i);
            final int selectedNum = i; // for use in lambda
            numButton.addActionListener(e -> {
            currentNumber = selectedNum;
        // optional: highlight selected number button
        if (selectedNumberButton != null && selectedNumberButton != numButton) {
            selectedNumberButton.setSelectedVisual(false);
        }
        selectedNumberButton = numButton;
        numButton.setSelectedVisual(true);
    });
            numPanel.add(numButton);
        }
        sidePanel.add(numPanel, BorderLayout.CENTER);
        mainPanel.add(sidePanel, BorderLayout.EAST);
        

        // Add the main panel to the frame
        frame.add(mainPanel, BorderLayout.SOUTH);
        frame.pack();


    }
        
    // Getter for the current number selected by the user
    public int getCurrentNumber() {
            return currentNumber;
        }

    public static void main(String[] args) {
        CSudokuGame game = new CSudokuGame();
    }
}
