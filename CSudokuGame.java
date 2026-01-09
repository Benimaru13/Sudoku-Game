import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/*
Author: Chibueze Benneth
Class: Computer Science 12
Date: 1st January 2025
Description: This is a simple Java created Sudoku Game using Swing for GUI.
*/

class CSudokuGame{
    // Important Fields
    JFrame frame;
    JLabel titleMessage;
    GridLayout gl;
    
    // keeps track of the currently selected cell in the puzzle
    SButton selectedButton; 

    // keeps track of what number the user has selected from 1 to 9
    int currentNumber; 

    // keeps track of the currently selected number button in the number pad
    private SButton selectedNumberButton; 

    // True when the Solution reveal button is toggled on
    boolean revealMode = false;

    // Error count
    private int errorCount = 0;

    // Error label
    private final JLabel errorLabel;

    // Timer label
    private final JLabel timerLabel;

    // Timer variables
    public int secondsElapsed = 0;
    public Timer gameTimer;

    // Game over flag
    public boolean gameWon = false;
    public boolean gameOver = false;

    // Grid dimensions
    final int rows = 3;
    final int cols = 3;

    // number of hints the user can use
    private int hintsRemaining = 3; 

    // Hints label
    private final JLabel hintsLabel;

    // Control Buttons
    private final JButton eraseBtn;
    private final JButton hintBtn;
    private final JButton NearlyCompleteBtn;
    public final JButton revealBtn;

    // Number Pad Panel
    public final JPanel numPanel;

    // Color Scheme
    public static final Color BG_MAIN        = Color.decode("#F6F0FF");   // very light lavender
    public static final Color BG_BOARD       = Color.decode("#EDE4FF");   // slightly darker
    public static final Color BG_CELL        = Color.decode("#FBF8FF");   // subtle contrast
    public static final Color BG_CELL_GIVEN  = Color.decode("#E1D7FF");   // pre-filled cells

    public static final Color GRID_BORDER    = Color.decode("#7A4ECF");   // medium purple

    public static final Color PAD_BTN_BG     = Color.decode("#5E35B1");   // num button default
    public static final Color PAD_BTN_BG_SEL = Color.decode("#ebdb67");   // selected num button
    public static final Color PAD_BTN_FG     = Color.WHITE;  // white

    public static final Color TEXT_GIVEN     = Color.decode("#4A2C82");   // darker purple
    public static final Color TEXT_USER      = Color.decode("#5E35B1");   // strong purple
    public static final Color MSG_TEXT       = Color.decode("#4A2C82");   // match text tone
    public static final Color MSG_WIN        = Color.decode("#4CAF50");   // keep green for clarity
    public static final Color MSG_RED        = Color.decode("#F44336");   // keep red for clarity

    public static final Color TITLE_BORDER   = Color.decode("#7A4ECF");   // match grid border

    // Border Styles
    public static final Border correctBorder = BorderFactory.createLineBorder(MSG_WIN, 3);
    public static final Border wrongBorder = BorderFactory.createLineBorder(MSG_RED, 3);
    public static final Border selectedBorder = BorderFactory.createLineBorder(Color.BLUE, 3);
    public static final Border defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1); 
    public static final Border gridBorder = BorderFactory.createLineBorder(GRID_BORDER, 2);
    public static final Border titleBorder = BorderFactory.createLineBorder(TITLE_BORDER, 2);


    // IMPORTANT SUDOKU ARRAYS    
    // Sudoku solution array

    // Tracks which numbers have been revealed in 
    public int [][] revealedBtnNumbers = new int[9][9];

    // To track which cells are filled (given) in the version 1 puzzle
    boolean [][] solvedBoolArr = new boolean[9][9];
  
    // 2D array to hold references to all Sudoku grid buttons
    SButton[][] buttons = new SButton[9][9];

    // The solution grid (fixed)
    final int [][] intArr = {
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

    // Tracks which cells are filled (given) and which are empty (user-fillable)
    final boolean [][] boolArrVersion1 = {
        {true, true, true, false, true, true, false, false, false},          
        {false, false, true, false, false, false, true, false, true},
        {false, false, true, false, false, false, false, false, false},
        {false, true, true, false, true, true, false, false, false},
        {true, true, false, false, false, false, false, true, false},
        {false, false, false, true, false, true, false, true, true},
        {false, false, false, false, false, true, false, true, false},
        {true, false, true, true, true, false, false, true, false},
        {false, true, false, false, true, true, false, false, true},       
    }; 

    // Nearly complete puzzle for debugging
    final boolean[][] nearlyCompleted = {
        {true, true, true, false, true, true, false, false, false}, 
        {true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true},    
        {true, true, true, true, true, true, true, true, true}    
    };
    
    // Debug feature: printStatement(intArr, boolArr);


    public CSudokuGame() {
        // ----- Frame and Panels Setup -----
        // Initialize the main frame
        frame = new JFrame("My Sudoku Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400); // Temporary size
        frame.getContentPane().setBackground(BG_MAIN);

        // Initialize the Message Panel and the Status Panel
        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(BG_MAIN);
        messagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, TITLE_BORDER));
        messagePanel.setLayout(new BorderLayout()); // This allows for top and bottom placement

        // Title Panel 
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(BG_MAIN);

        // Title Message
        titleMessage =  new JLabel();
        titleMessage.setText("Chibueze's Sudoku Game");
        titleMessage.setForeground(MSG_TEXT);

        Font messageFont = new Font("Roboto", Font.BOLD, 30);
        titleMessage.setFont(messageFont);
        titlePanel.add(titleMessage);

        messagePanel.add(titlePanel, BorderLayout.NORTH);
                        
        // Status Panel
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(BG_MAIN);
        statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 5)); // spacing between labels

        // Error Label
        errorLabel = new JLabel("Errors: 0");
        errorLabel.setForeground(MSG_RED);
        errorLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        statusPanel.add(errorLabel);

        // Timer Label
        timerLabel = new JLabel("Time: 0:00");
        timerLabel.setForeground(TEXT_GIVEN);
        timerLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        statusPanel.add(timerLabel);

        // Hints Label
        hintsLabel = new JLabel("Hints Left: " + hintsRemaining);
        hintsLabel.setForeground(TEXT_USER);
        hintsLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        statusPanel.add(hintsLabel);

        messagePanel.add(statusPanel, BorderLayout.SOUTH);

        frame.add(messagePanel, BorderLayout.NORTH);

        // ----- MAIN GRID PANEL -----
        // Initialize the main Sudoku grid panel
        gl = new GridLayout(rows, cols);
        JPanel mainPanel = new JPanel(new BorderLayout()); 
        mainPanel.setBackground(BG_MAIN);

        // initialize a main Sudoku Grid with a 3x3 layout
        JPanel gridPanel = new JPanel(gl); 
        gridPanel.setBackground(BG_BOARD);

        // Create the 9x9 Sudoku grid using 3x3 sub-grids
        for (int gridrow = 0; gridrow < 3; gridrow++) {
            for (int gridcol = 0; gridcol < 3; gridcol++) {

                // For each Sudoku main grid, create a sub 3x3 grid 
                JPanel subPanel = new JPanel(gl);
                subPanel.setBackground(BG_BOARD);
                subPanel.setBorder(gridBorder);   

                // Create buttons for each cell in the 3x3 sub-grid
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {

                        // Calculate the actual row and column in the 9x9 grid
                        int rowIndex = gridrow * 3 + r;
                        int colIndex = gridcol * 3 + c;
                        solvedBoolArr[rowIndex][colIndex] = boolArrVersion1[rowIndex][colIndex];
                        final boolean isFilled = solvedBoolArr[rowIndex][colIndex]; // for use in mouse listener
                        
                        // Create the button with appropriate parameters
                        SButton button = new SButton(intArr[rowIndex][colIndex], isFilled, rowIndex, colIndex);
                        buttons[rowIndex][colIndex] = button;
                        button.addMouseListener(new SMouseHandler(this, isFilled)); // add mouse listener to handle clicks
                        
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
        sidePanel.setBorder(gridBorder);
        sidePanel.setLayout(new BorderLayout());

        // Number Pad Panel
        numPanel = new JPanel(gl);
        // Add the numbers 1-9 as buttons
        for (int i = 1; i <= 9; i++) {
            SButton numButton = new SButton(i);
            final int selectedNum = i; // for use in lambda

            // Handles operations when a number button is clicked
            numButton.addActionListener(e -> {
            currentNumber = selectedNum; // when a number button is clicked, update currentNumber
            setSelectedNumberButton(numButton); // set the selected number button
                
        // reset all number button colours
        resetColor(numPanel, PAD_BTN_BG);

        // Highlight the selected number button
        if (selectedNumberButton != null && selectedNumberButton != numButton) {
            selectedNumberButton.setSelectedVisual(false);
            selectedNumberButton.setBackground(PAD_BTN_BG);
        }
        selectedNumberButton = numButton;
        numButton.setSelectedVisual(true);
        numButton.setBackground(PAD_BTN_BG_SEL);
    }); 
            
        // Design features for Number Pad
        numButton.setBackground(PAD_BTN_BG);
        numButton.setForeground(PAD_BTN_FG);
        numButton.setOpaque(true);
        numButton.setFocusPainted(false); // removes focus rectangle around the number button
        numButton.setBorder(gridBorder);
        numPanel.add(numButton);
    }

    // ----- SIDE PANEL CONTROL BUTTONS -----
    JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 50));
    controlPanel.setBackground(BG_MAIN);

        // Erase Button functionality
        eraseBtn = new JButton("Erase");
        eraseBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        eraseBtn.addActionListener(e -> {
        // Remove highlight from previously selected number button
        if (selectedButton != null && !selectedButton.isFixed) {
            selectedButton.setSelectedVisual(false);
            selectedButton.setDisplayValue(0); // Clear the cell display
            selectedButton.setBackground(BG_CELL);
            selectedButton = null; // Deselect the cell
        }

            System.out.println("Erase mode: no number selected.");
        });

        // eraseBtn design features
        eraseBtn.setBackground(PAD_BTN_BG);
        eraseBtn.setForeground(PAD_BTN_FG);
        eraseBtn.setOpaque(true);
        eraseBtn.setFocusPainted(false);
        eraseBtn.setBorder(gridBorder);

        controlPanel.add(eraseBtn);


        // Implement "Hint" button functionality
        hintBtn = new JButton("Hint");
        hintBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        hintBtn.setBackground(PAD_BTN_BG);
        hintBtn.setForeground(PAD_BTN_FG);
        hintBtn.setOpaque(true);
        hintBtn.setBorder(gridBorder);
        hintBtn.addActionListener(e -> giveHint(hintBtn));

        controlPanel.add(hintBtn);

        sidePanel.add(controlPanel, BorderLayout.NORTH);


        // Nearly Complete Button functionality (for debugging)
        NearlyCompleteBtn = new JButton("Nearly Complete (Debug)");
        NearlyCompleteBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        NearlyCompleteBtn.setBackground(PAD_BTN_BG);
        NearlyCompleteBtn.setForeground(PAD_BTN_FG);
        NearlyCompleteBtn.setOpaque(true);
        NearlyCompleteBtn.setBorder(gridBorder);
        
        NearlyCompleteBtn.addActionListener(e -> {
        // Load a nearly complete puzzle for debugging
        loadNearlyCompletePuzzle();
        });

        controlPanel.add(NearlyCompleteBtn);


        // Implement "Reveal Solution" button functionality
        revealBtn = new JButton("Reveal Solution");
        revealBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        revealBtn.setBackground(PAD_BTN_BG);
        revealBtn.setForeground(PAD_BTN_FG);
        revealBtn.setOpaque(true);
        revealBtn.setFocusPainted(false);
        revealBtn.setBorder(gridBorder);

        revealBtn.addActionListener(e -> {
            revealMode = !revealMode;   // toggle on/off

        if (revealMode) {
            revealBtn.setText("Hide Solution");
            revealSolution();           
            disableAllButtons();
        } 
        
        else {
            revealBtn.setText("Reveal Solution");
            hideSolution();
            reEnableAllButtons();
        }
    });

        controlPanel.add(revealBtn);


        // titled border to number pad
        TitledBorder tBorder = BorderFactory.createTitledBorder(titleBorder, "Choose a Number (from 1-9)");
        tBorder.setTitleColor(TEXT_GIVEN);
        numPanel.setBorder(tBorder);

        // Add padding and fixed size to the number pad to prevent stretching
        JPanel paddedNumberPadPanel = new JPanel(new GridBagLayout());
        paddedNumberPadPanel.setBackground(BG_MAIN);

        JPanel innerPadPanel = new JPanel(new BorderLayout());
        innerPadPanel.setBackground(BG_MAIN);
        innerPadPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        innerPadPanel.add(numPanel, BorderLayout.CENTER);
        innerPadPanel.setPreferredSize(new Dimension(270, 270));
        paddedNumberPadPanel.add(innerPadPanel, new GridBagConstraints());
        sidePanel.add(paddedNumberPadPanel, BorderLayout.CENTER);
        mainPanel.add(sidePanel, BorderLayout.EAST);
        

        // Add the main panel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // center on screen
        startTimer();

    }


    // ----- HELPER FUNCTIONS -----
    // Reveal the solution in the grid
    public void revealSolution() {
    for (int r = 0; r < 9; r++) {
        for (int c = 0; c < 9; c++) {
            SButton btn = buttons[r][c];
            btn.setDisplayValue(intArr[r][c]);
            btn.setForeground(MSG_WIN);   // optional: make them look “solution colored”
            }
        }
    }

    // Hide the solution and revert to user inputs
    public void hideSolution() {
    for (int r = 0; r < 9; r++) {
        for (int c = 0; c < 9; c++) {

            SButton btn = buttons[r][c];

            if (solvedBoolArr[r][c]) {
                // original given cell
                btn.setDisplayValue(intArr[r][c]);
                btn.setForeground(TEXT_GIVEN);
            } else {
                // user cell — show whatever user has currently entered (stored in btn.displayValue)
                btn.setDisplayValue(revealedBtnNumbers[r][c]); 
                btn.setForeground(TEXT_USER);
                }
            }
        }
    }

    // Load a nearly complete puzzle for debugging
    public void loadNearlyCompletePuzzle() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                solvedBoolArr[row][col] = nearlyCompleted[row][col];
                SButton btn = buttons[row][col];

                if (solvedBoolArr[row][col]) {
                    btn.setDisplayValue(intArr[row][col]);
                    btn.setForeground(TEXT_GIVEN);
                } else {
                    btn.setDisplayValue(0);
                    btn.setForeground(TEXT_USER);
                }
            }
        }
    }


    // Start the game timer
    private void startTimer() {
        gameTimer = new Timer(1000, e -> {
            secondsElapsed++;

            int minutes = secondsElapsed / 60;
            int seconds = secondsElapsed % 60;

            timerLabel.setText(String.format("Time: %d:%02d", minutes, seconds));
        });

        gameTimer.start();
        AudioManager.loop("clock_ticks");
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
            if (selectedButton.isFixed) {
                selectedButton.setBackground(BG_CELL_GIVEN);
                selectedButton.setBorder(gridBorder);
            } else {
                selectedButton.setBackground(BG_CELL);}
            selectedButton.setForeground(TEXT_USER);
        }

        // highlight new cell
        selectedButton = btn;
        btn.setSelectedVisual(true);
        btn.setBackground(PAD_BTN_BG_SEL);
        btn.setForeground(TEXT_USER);
        // set text color based on correctness
    }

    // Check if the user's move is correct
    public void checkMove(int row, int col, int value) {
        int correct = intArr[row][col];

        if (value == correct) {
            // Highlight cell in green
            buttons[row][col].setBorder(correctBorder);
            AudioManager.play("correct");
        } else {
           // highlight cell in red
            buttons[row][col].setBorder(wrongBorder);
            AudioManager.play("wrong");
            
            errorCount++;
            errorLabel.setText("Errors: " + errorCount);

            if (errorCount >= 3) {
                gameOver = true;                
            }
        }
    }

    // Disable all buttons (used when game is Won)
    public void disableAllButtons() {
        eraseBtn.setEnabled(false);
        hintBtn.setEnabled(false);
        NearlyCompleteBtn.setEnabled(false);

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                buttons[r][c].setEnabled(false);
            }
        }
    }

    // Re-enable all buttons (used when hiding solution)
    public void reEnableAllButtons() {
        eraseBtn.setEnabled(true);
        hintBtn.setEnabled(true);
        NearlyCompleteBtn.setEnabled(true);

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                // only re-enable if it was not originally given
                if (!solvedBoolArr[r][c]) {
                    buttons[r][c].setEnabled(true);
                }
            }
        }
    }

    // Check if the game is won
    public boolean checkWinCondition() {
        // Check if all cells are filled correctly
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                SButton btn = buttons[r][c];
                if (btn.displayValue != intArr[r][c]) {
                    return gameWon = false; // Not yet won
                }
            }
        }

        return gameWon = true;
        }


    // Provide a hint to the user by filling in one correct cell
   public void giveHint(JButton hintBtn) {

    if (hintsRemaining <= 0) {
        System.out.println("No hints left.");
        hintBtn.setEnabled(false);
        return;
    }

    java.util.List<SButton> unfilledCells = new java.util.ArrayList<>();

    for (int r = 0; r < 9; r++) {
        for (int c = 0; c < 9; c++) {

            // only reveal if it was NOT originally given
            if (!solvedBoolArr[r][c]) {              
                SButton btn = buttons[r][c];

                // only reveal if it's currently wrong or empty
                if (btn.displayValue != intArr[r][c]) {
                    unfilledCells.add(btn);
                }
            }
        }
    }

    if (unfilledCells.isEmpty()) {
        System.out.println("Nothing left to hint!");
        return;
    }

    // pick a random candidate
    java.util.Random rand = new java.util.Random();
    SButton chosenCell = unfilledCells.get(rand.nextInt(unfilledCells.size()));

    // fill with correct number
    chosenCell.setDisplayValue(intArr[chosenCell.row][chosenCell.col]);
    chosenCell.setBorder(gridBorder);
    chosenCell.setForeground(Color.decode("#4A148C"));   // optional: nice purple tone

    // Play animation to highlight the hinted cell
    flashCell(chosenCell, Color.decode("#39ea8eff"), 2);

    // reduce hints
    hintsRemaining--;
    secondsElapsed += 30; // 30 seconds penalty
    hintsLabel.setText("Hints remaining: " + hintsRemaining);

    if (hintsRemaining == 0) {
        hintsLabel.setText("No hints left");
        hintBtn.setEnabled(false);
    }

    // Check if the game is won after the hint
    checkWinCondition();
    if (gameWon) {
        gameTimer.stop();
        disableAllButtons();
        revealBtn.setEnabled(false);
        titleMessage.setText("Congratulations! You've completed the puzzle!");
        showGameEndDialog(
            "You've completed the puzzle in " + formatTime(secondsElapsed) + "!", 
            "Congratulations!"
            );
        }
    }

        // Helper method to format time in seconds to mm:ss
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%d:%02d", minutes, secs);
}

    // Show custom dialog with Start Over and Exit options
    public void showGameEndDialog(String message, String title) {
        // Define custom button labels
        Object[] options = {"Start Over", "Exit"};

        // Display the dialog with custom buttons
        int choice = JOptionPane.showOptionDialog(
            frame,                          // Parent component
            message,                        // Message to display
            title,                          // Dialog title
            JOptionPane.DEFAULT_OPTION,     // Option type
            JOptionPane.INFORMATION_MESSAGE,// Message type
            null,                           // Icon (null for default)
            options,                        // The custom buttons
            options[0]                      // Initially selected button
        );

    // Process the user's choice
    switch (choice) {
        case 0 -> {
            // Start Over selected
            System.out.println("Start Over selected");
            resetGame();
            }
        case 1 -> {
            // Exit selected
            System.out.println("Exit selected");
            frame.dispose();  // Close the frame
            System.exit(0);   // Exit the application
            }
        default -> {
            // Dialog closed without selection (X button)
            System.out.println("Dialog closed");
            // You can treat this as "Exit" if you want
            frame.dispose();
            System.exit(0);
            }
    }
}  

    // Method to reset void
    public void resetColor(JPanel panel, Color color) {
        // reset all number button colours
        for (Component c : panel.getComponents()) {
            if (c instanceof SButton) {
                c.setBackground(color);
            }
        } 
    }

// Reset the game to start over
public void resetGame() {
    // Reset game state flags
    gameWon = false;
    gameOver = false;
    errorCount = 0;
    hintsRemaining = 3;
    secondsElapsed = 0;
    revealMode = false;

    // don't do solvedBoolArr = boolArrVersion1;
    // Reset the solvedBoolArr by copyng the values from boolArrVersion1
    for (int r = 0; r < 9; r++) {
        System.arraycopy(boolArrVersion1[r], 0, solvedBoolArr[r], 0, 9);
    }

    // Reset UI labels
    titleMessage.setText("Chibueze's Sudoku Game");
    errorLabel.setText("Errors: 0");
    timerLabel.setText("Time: 0:00");
    hintsLabel.setText("Hints Left: " + hintsRemaining);

        
    // Re-enable all buttons
    reEnableAllButtons();
    revealBtn.setEnabled(true);
    revealBtn.setText("Reveal Solution");

    // Unselect the number button
    if (selectedNumberButton != null) {
        selectedNumberButton.setSelectedVisual(false);
        selectedNumberButton = null;
        currentNumber = 0;
        resetColor(numPanel, PAD_BTN_BG);
    }

    // Reset the Sudoku grid
    for (int r = 0; r < 9; r++) {
        for (int c = 0; c < 9; c++) {
            SButton btn = buttons[r][c];
            revealedBtnNumbers[r][c] = 0;
            
            if (solvedBoolArr[r][c]) {
                // Reset given cells
                btn.setDisplayValue(intArr[r][c]);
                btn.setForeground(TEXT_GIVEN);
                btn.setBorder(defaultBorder);
            } else {
                // Clear user cells
                btn.setDisplayValue(0);
                btn.setForeground(TEXT_USER);
                btn.setBorder(defaultBorder);
                btn.setBackground(BG_CELL);
            }
        }
    }
    
    // Reset selected cells
    selectedButton = null;
    selectedNumberButton = null;
    currentNumber = 0;
    
    // Restart timer
    if (gameTimer != null) {
        gameTimer.stop();
    }
    startTimer();
    
    System.out.println("Game reset successfully!");
}

// ----- Animation Helper Function -----
// Flash a cell with a specified color for a number of times
public void flashCell(JButton cell, Color flashColor, int flashes) {
    final Color original = cell.getBackground();

    Timer timer = new Timer(150, null);
    final int[] count = {0};

    timer.addActionListener(e -> {
        if (count[0] % 2 == 0) {
            cell.setBackground(flashColor);
        } else {
            cell.setBackground(original);
        }

        count[0]++;

        if (count[0] > flashes * 2) {
            cell.setBackground(original);
            timer.stop();
        }
    });

    timer.start();
}

// ----- DEBUG FUNCTION -----
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
