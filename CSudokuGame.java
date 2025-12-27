import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;

/*
Author: Chibueze Benneth
Class: Computer Science 12
Date: 1st January 2025
Description: This is a simple Java created Sudoku Game using Swing for GUI.
*/

class CSudokuGame{
     SButton selectedButton = null; // keeps track of the currently selected cell in the puzzle
        int currentNumber = 0; // keeps track of what number the user has selected from 1 to 9
        private SButton selectedNumberButton = null; // keeps track of the currently selected number button
        SButton[][] buttons = new SButton[9][9];


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

        // Print a sample CLI sudoku frame
        printStatement(intArr, boolArr);

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
                subPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));   
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        int rowIndex = gridrow * 3 + r;
                        int colIndex = gridcol * 3 + c;
                        final boolean isFilled = boolArr[rowIndex][colIndex]; // for use in mouse listener
                        SButton button = new SButton(intArr[rowIndex][colIndex], isFilled);
                        buttons[rowIndex][colIndex] = button;
                        button.addMouseListener(new SMouseHandler(this, isFilled));
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
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JPanel numPanel = new JPanel(gl);
        // Add the numbers 1-9 as buttons
        for (int i = 1; i <= 9; i++) {
            SButton numButton = new SButton(i);
            final int selectedNum = i; // for use in lambda

            // feature that "listens" for number button clicks
            numButton.addActionListener(e -> {
            currentNumber = selectedNum; // when a number button is clicked, update currentNumber
            setSelectedNumberButton(numButton); // set the selected number button
        // optional: highlight selected number button
        // if a number button was selected and
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

    // Setter for the selected number button (for visual highlighting)
    
    public void setSelectedNumberButton(SButton btn) {
    if (selectedNumberButton != null) selectedNumberButton.setSelectedVisual(false);
    selectedNumberButton = btn;
    if (btn != null) btn.setSelectedVisual(true);
}

    // Setter for the selected cell in the Sudoku grid
public void setSelectedCell(SButton btn) {
    // remove highlight from previous one
    if (selectedButton != null && selectedButton != btn) {
        selectedButton.setSelectedVisual(false);
    }
    
    // highlight new cell
    selectedButton = btn;
    btn.setSelectedVisual(true);
}


    public static void printStatement (int[][] intList, boolean[][] boolList) {
        for (int row = 0; row < 9; row++) {
            // This makes the row lines
            for(int spc = 0; spc < 20; spc++){
                System.out.print("_");
            }
            System.out.println();
            for(int col = 0; col < 9; col++) {
                if (boolList [row][col] == true) {
                    int num = intList[row][col];
                    // This makes the col lines
                    System.out.print(" " + num);
                }
                else {
                    System.out.print(" .");
                    
                }
     
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        CSudokuGame game = new CSudokuGame();
    }
}
