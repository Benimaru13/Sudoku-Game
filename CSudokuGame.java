import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;

/*
Author: Chibueze Benneth
Class: Computer Science 12
Date: 1st January 2025
Description: This is a simple Java created Sudoku Game using Swing for GUI.
*/

class CSudokuGame{
    SButton selectedButton; // keeps track of the currently selected cell in the puzzle
    int currentNumber; // keeps track of what number the user has selected from 1 to 9
    private SButton selectedNumberButton; // keeps track of the currently selected number button
    SButton[][] buttons = new SButton[9][9];
    // Variables

    // Purple themed colors

    public static final Color BG_MAIN        = Color.decode("#F6F0FF");   // very light lavender
    public static final Color BG_BOARD       = Color.decode("#EDE4FF");   // slightly darker
    public static final Color BG_CELL        = Color.decode("#FBF8FF");   // subtle contrast
    public static final Color BG_CELL_GIVEN  = Color.decode("#E1D7FF");   // pre-filled cells

    public static final Color GRID_BORDER    = Color.decode("#7A4ECF");   // medium purple

    public static final Color PAD_BTN_BG     = Color.decode("#B388FF");   // num button default
    public static final Color PAD_BTN_BG_SEL = Color.decode("#7C4DFF");   // selected num button
    public static final Color PAD_BTN_FG     = Color.WHITE;               // text on buttons
    
    public static final Color TEXT_GIVEN     = Color.decode("#4A2C82");   // darker purple
    public static final Color TEXT_USER      = Color.decode("#5E35B1");   // strong purple
    public static final Color MSG_TEXT       = Color.decode("#4A2C82");   // match text tone
    public static final Color MSG_WIN        = Color.decode("#4CAF50");   // keep green for clarity
    public static final Color MSG_RED        = Color.decode("#F44336");   // keep red for clarity

    public static final Color TITLE_BORDER   = Color.decode("#7A4ECF");   // match grid border

    final int rows = 3;
    final int cols = 3;

    public static final Border correctBorder = BorderFactory.createLineBorder(MSG_WIN, 3);
    public static final Border wrongBorder = BorderFactory.createLineBorder(MSG_RED, 3);
    public static final Border selectedBorder = BorderFactory.createLineBorder(Color.BLUE, 3);
    public static final Border defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1);



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


    public CSudokuGame() {
        // Debug feature; Print a sample CLI sudoku frame
        printStatement(intArr, boolArr);

        // Initialize the main frame
        JFrame frame = new JFrame("Sudoku Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400); // Temporary size
        frame.setVisible(true);
        frame.getContentPane().setBackground(BG_MAIN);

        // Initialize the message panel
        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(BG_MAIN);

        JLabel titleMessage = new JLabel("Chibueze's Sudoku Game");
        titleMessage.setForeground(MSG_TEXT);

        messagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, TITLE_BORDER));

        messagePanel.add(titleMessage, BorderLayout.NORTH);
        Font messageFont = new Font("Roboto", Font.BOLD, 30);
        titleMessage.setFont(messageFont);
        frame.add(messagePanel, BorderLayout.NORTH);

        // Initialize the main Sudoku grid panel
        GridLayout gl = new GridLayout(rows, cols);
        JPanel mainPanel = new JPanel(new BorderLayout()); // This is the main panel that holds everything
        mainPanel.setBackground(BG_MAIN);

        // initialize a main grid panel with a 3x3 layout
        JPanel gridPanel = new JPanel(gl); 
        gridPanel.setBackground(BG_BOARD);

        for (int gridrow = 0; gridrow < 3; gridrow++) {
            for (int gridcol = 0; gridcol < 3; gridcol++) {
                // For each cell in the main grid, create a sub-panel with a 3x3 layout
                JPanel subPanel = new JPanel(gl);
                subPanel.setBackground(BG_BOARD);
                subPanel.setBorder(BorderFactory.createLineBorder(GRID_BORDER, 2));   
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        int rowIndex = gridrow * 3 + r;
                        int colIndex = gridcol * 3 + c;
                        final boolean isFilled = boolArr[rowIndex][colIndex]; // for use in mouse listener
                        SButton button = new SButton(intArr[rowIndex][colIndex], isFilled, rowIndex, colIndex);
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
        sidePanel.setBackground(BG_MAIN);
        sidePanel.setBorder(BorderFactory.createLineBorder(GRID_BORDER, 2));


        JPanel numPanel = new JPanel(gl);
        // Add the numbers 1-9 as buttons
        for (int i = 1; i <= 9; i++) {
            SButton numButton = new SButton(i);
            final int selectedNum = i; // for use in lambda

            // feature that "listens" for number button clicks
            numButton.addActionListener(e -> {
            currentNumber = selectedNum; // when a number button is clicked, update currentNumber
            setSelectedNumberButton(numButton); // set the selected number button
       
        // Highlight the selected number button
        if (selectedNumberButton != null && selectedNumberButton != numButton) {
            selectedNumberButton.setSelectedVisual(false);
            selectedNumberButton.setBackground(PAD_BTN_BG);
        }
        selectedNumberButton = numButton;
        numButton.setSelectedVisual(true);
        numButton.setBackground(PAD_BTN_BG_SEL);
    }); 
            
        numPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Design features for Number Pad
        numButton.setBackground(PAD_BTN_BG);
        numButton.setForeground(PAD_BTN_FG);
        numButton.setOpaque(true);
        numButton.setFocusPainted(false);
        numButton.setBorder(BorderFactory.createLineBorder(GRID_BORDER, 2));
        numPanel.add(numButton);
    }


        // Implement "Erase" button functionality
        JButton eraseBtn = new JButton("Erase");
        eraseBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        eraseBtn.addActionListener(e -> {
        // Remove highlight from previously selected number button
        if (selectedButton != null) {
            selectedButton.setSelectedVisual(false);
            selectedButton.setDisplayValue(0); // Clear the cell display
            selectedButton = null; // Deselect the cell
        }

            System.out.println("Erase mode: no number selected.");
        });
        eraseBtn.setBackground(PAD_BTN_BG);
        eraseBtn.setForeground(PAD_BTN_FG);
        eraseBtn.setOpaque(true);
        eraseBtn.setFocusPainted(false);
        eraseBtn.setBorder(BorderFactory.createLineBorder(GRID_BORDER, 2));

        sidePanel.add(eraseBtn, BorderLayout.NORTH);

        sidePanel.add(numPanel, BorderLayout.CENTER);
        mainPanel.add(sidePanel, BorderLayout.EAST);
        

        // Add the main panel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);
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
            selectedButton.setBackground(BG_CELL);
            selectedButton.setForeground(TEXT_USER);
            }

        // highlight new cell
        selectedButton = btn;
        btn.setSelectedVisual(true);
        btn.setBackground(PAD_BTN_BG_SEL);
        btn.setForeground(PAD_BTN_FG);
    }

    public void checkMove(int row, int col, int value) {
        int correct = intArr[row][col];

        if (value == correct) {
            System.out.println("Correct move!"); // update this logic to highlight cell in green
            buttons[row][col].setBorder(correctBorder);
        } else {
            System.out.println("Wrong move!"); // update this logic to highlight cell in red
            buttons[row][col].setBorder(wrongBorder);
        }
    }

    public void checkWinCondition() {
        // Check if all cells are filled correctly
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                SButton btn = buttons[r][c];
                if (btn.displayValue != intArr[r][c]) {
                    return; // Not yet won
                }
            }
        }
        System.out.println("Congratulations! You've completed the Sudoku puzzle!");
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
