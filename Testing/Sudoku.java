package Testing;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Classic Sudoku game implementation with graphical user interface.
 * Provides a 9x9 grid Sudoku puzzle with interactive cell selection,
 * number pad input, and visual feedback including cat image reactions
 * based on game state (correct, incorrect, or winning).
 * 
 * Features:
 * - Interactive 9x9 Sudoku grid
 * - Number selection pad (1-9)
 * - Error highlighting with "See Error" toggle
 * - Number removal with "Remove #" button
 * - Visual feedback through cat images
 * - Win condition detection
 * 
 * @author Your Team
 * @version 1.0
 * @since 2025
 */
public class Sudoku {

    /** The currently selected number from the number pad (1-9, or 0 if none selected) */
    int currentNumber;
    
    /** 2D array storing references to all 81 grid buttons in the 9x9 Sudoku board */
    SDKButton[][] buttons = new SDKButton[9][9];

    /** Flag indicating whether the player has won the game */
    boolean gameWon = false;
    JLabel imageLabel; // Image shown on the right (cat image)
    ImageIcon lockInCatIcon; // default cat image
    ImageIcon incorrectCatIcon; // shown when user places an incorrect number
    ImageIcon winCatIcon; // shown when user wins
    JLabel message; // Move message label to class scope for updates
    int[][] solvedPuzzle; // Store solution for win check
    int rows = 3;
    int cols = 3;

    // Toggle to show/hide wrong-number highlighting
    boolean showWrongNumbers = false;
    JButton showWrongBtn;

    // Track the currently selected cell (for Remove #)
    SDKButton selectedButton = null;
    JButton removeBtn;

    // colors to make it look good
    final Color BG_MAIN = Color.decode("#FFF6E5");
    final Color BG_BOARD = Color.decode("#FFEBD6");
    final Color BG_CELL = Color.decode("#FFFDF7");
    final Color BG_CELL_GIVEN = Color.decode("#FFE1C3");
    final Color GRID_BORDER = Color.decode("#8D6E63");
    final Color PAD_BTN_BG = Color.decode("#6EC1E4");
    final Color PAD_BTN_BG_SEL = Color.decode("#3498DB");
    final Color PAD_BTN_FG = Color.WHITE;
    final Color TEXT_GIVEN = Color.decode("#5D4037");
    final Color TEXT_USER = Color.decode("#1F3C88");
    final Color MSG_TEXT = Color.decode("#1F3C88");
    final Color MSG_WIN = Color.decode("#4CAF50");
    final Color TITLE_BORDER = Color.decode("#8D6E63");

    // Border for selected cell
    final Border SELECTED_BORDER = BorderFactory.createLineBorder(PAD_BTN_BG_SEL, 3);

    /**
     * Gets the currently selected number from the number pad.
     * 
     * @return the current number (1-9), or 0 if no number is selected
     */
    public int getCurrentNumber() {
        return currentNumber;
    }

    /**
     * Constructs a Sudoku game with the specified puzzle configuration.
     * Initializes the GUI including the game board, number pad, and control buttons.
     * 
     * @param solvedPuzzle a 9x9 2D array containing the complete solution to the puzzle
     * @param isGiven a 9x9 boolean array where true indicates pre-filled cells
     */
    public Sudoku(int[][] solvedPuzzle, boolean[][] isGiven) {
        this.solvedPuzzle = solvedPuzzle; // Store solution
        JFrame frame = new JFrame("Sudoku");
        frame.getContentPane().setBackground(BG_MAIN);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Message panel at the top
        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(BG_MAIN);
        message = new JLabel("Welcome to Sudoku!");
        messagePanel.add(message);
        message.setForeground(MSG_TEXT);
        frame.add(messagePanel, BorderLayout.NORTH);
        message.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));

        // Main container panel for layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_MAIN);

        // Main game panel with 3x3 mini panels (each mini panel is a 3x3 grid)
        JPanel gamePanel = new JPanel(new GridLayout(rows, cols));
        gamePanel.setBackground(BG_BOARD);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JPanel miniPanel = new JPanel(new GridLayout(rows, cols));
                miniPanel.setBackground(BG_BOARD);
                miniPanel.setBorder(BorderFactory.createLineBorder(GRID_BORDER, 2));
                //mini row/col
                for (int k = 0; k < 9; k++) {
                    int row = i * 3 + k / 3;
                    int col = j * 3 + k % 3;
                    // button for each cell, passing the solution and whether it's a given
                    SDKButton button = new SDKButton(solvedPuzzle[row][col], isGiven[row][col]);
                    // mouse listener to handle user input
                    button.addMouseListener(new MouseHandler(this, isGiven[row][col]));
                    buttons[row][col] = button;
                    miniPanel.add(button);
                }
                gamePanel.add(miniPanel);
            }
        }

        // Right panel with BorderLayout: image at top, number pad at bottom
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(BG_MAIN);

        // Top right: image
        imageLabel = new JLabel();

        // Load and scale cat images
        lockInCatIcon = scaleIcon(new ImageIcon("you_got_this.png"), 250, 250);
        incorrectCatIcon = scaleIcon(new ImageIcon("incorrect_cat.png"), 250, 250);
        winCatIcon = scaleIcon(new ImageIcon("happy_cat.png"), 250, 250);

        imageLabel.setIcon(lockInCatIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        // Wrap image in a panel with bottom padding so it doesn't touch the button
        JPanel imageWrapper = new JPanel(new BorderLayout());
        imageWrapper.setBackground(BG_MAIN);
        imageWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0)); // space BELOW image
        imageWrapper.add(imageLabel, BorderLayout.CENTER);
        rightPanel.add(imageWrapper, BorderLayout.NORTH);

        // Middle right: action buttons (See Error / Remove #)
        JPanel midPanel = new JPanel();
        midPanel.setBackground(BG_MAIN);
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.X_AXIS));
        midPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20)); // left/right padding + small space below

        // "See Error" button (left)
        showWrongBtn = new JButton("See Error");
        showWrongBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        // Let the button size itself to the text + margins (no fixed preferred size)
        showWrongBtn.setMargin(new Insets(8, 12, 8, 12));
        showWrongBtn.setOpaque(true);
        showWrongBtn.setBackground(PAD_BTN_BG);
        showWrongBtn.setForeground(PAD_BTN_FG);
        showWrongBtn.setFocusPainted(false);
        showWrongBtn.setBorder(BorderFactory.createLineBorder(GRID_BORDER, 2));
        // lanbda expression for button click, basically shorter way to write ActionListener
        showWrongBtn.addActionListener(e -> {
            showWrongNumbers = !showWrongNumbers;
            showWrongBtn.setText(showWrongNumbers ? "Hide Error" : "See Error");
            updateWrongHighlights();
        });

        // "Remove #" button (right)
        removeBtn = new JButton("Remove #");
        removeBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        // Let the button size itself to the text + margins
        removeBtn.setMargin(new Insets(8, 12, 8, 12));
        removeBtn.setOpaque(true);
        removeBtn.setBackground(BG_CELL_GIVEN);
        removeBtn.setForeground(TEXT_GIVEN);
        removeBtn.setFocusPainted(false);
        removeBtn.setBorder(BorderFactory.createLineBorder(GRID_BORDER, 2));
        removeBtn.addActionListener(e -> {
            // Remove the number from the currently selected cell (if it's not a given)
            if (selectedButton != null && !selectedButton.isGiven && !gameWon) {
                selectedButton.setDisplayValue(0);
                updateCatImage();
            }
        });

        midPanel.add(showWrongBtn);
        midPanel.add(Box.createHorizontalGlue());
        midPanel.add(removeBtn);

        rightPanel.add(midPanel, BorderLayout.CENTER);

        // Bottom right: number pad for selecting numbers
        JPanel numberPadPanel = new JPanel(new GridLayout(rows, cols, 10, 10));
        numberPadPanel.setBackground(BG_MAIN);
        for (int n = 1; n <= 9; n++) {
            NumPadButton numButton = new NumPadButton(n);
            // When a number button is clicked, update currentNumber
            numButton.addActionListener(e -> {
                currentNumber = numButton.getNumber();
                // loops through all number pad buttons to reset color so only one is highlighted
                for (Component c : numberPadPanel.getComponents()) {
                    if (c instanceof NumPadButton) {
                        c.setBackground(PAD_BTN_BG);
                    }
                }
                numButton.setBackground(PAD_BTN_BG_SEL);
            });
            numberPadPanel.add(numButton);
        }
        // titled border to number pad
        javax.swing.border.TitledBorder tb = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(TITLE_BORDER, 2), "Select Number");
        tb.setTitleColor(TEXT_GIVEN);
        numberPadPanel.setBorder(tb);

        // Add padding and fixed size to the number pad to prevent stretching
        JPanel paddedNumberPadPanel = new JPanel(new GridBagLayout());
        paddedNumberPadPanel.setBackground(BG_MAIN);
        JPanel innerPadPanel = new JPanel(new BorderLayout());
        innerPadPanel.setBackground(BG_MAIN);
        innerPadPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        innerPadPanel.add(numberPadPanel, BorderLayout.CENTER);
        innerPadPanel.setPreferredSize(new Dimension(270, 270));
        paddedNumberPadPanel.add(innerPadPanel, new GridBagConstraints());

        rightPanel.add(paddedNumberPadPanel, BorderLayout.SOUTH);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Checks if there are any incorrect numbers placed on the board.
     * A number is considered incorrect if it's non-empty and doesn't match the solution.
     * 
     * @return true if at least one incorrect number is found, false otherwise
     */
    public boolean hasAnyIncorrect() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int v = buttons[row][col].displayValue;
                if (v != 0 && v != solvedPuzzle[row][col]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Updates the cat image displayed on the right side based on the current board state.
     * Shows the confused cat if there are incorrect numbers, otherwise shows the encouraging cat.
     * Does nothing if the game is already won.
     */
    public void updateCatImage() {
        // just in case win image does not get overwritten
        if (gameWon) return;
        if (hasAnyIncorrect()) {
            imageLabel.setIcon(incorrectCatIcon);
        } else {
            imageLabel.setIcon(lockInCatIcon);
        }
    }

    /**
     * Updates the highlighting of incorrect numbers based on the "See Error" toggle.
     * When enabled, displays incorrect numbers in red; otherwise displays them normally.
     */
    public void updateWrongHighlights() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                SDKButton b = buttons[row][col];
                if (!showWrongNumbers) {
                    // normal mode
                    b.setForeground(TEXT_USER);
                    continue;
                }
                int v = b.displayValue;
                if (v != 0 && v != solvedPuzzle[row][col]) {
                    b.setForeground(Color.RED);
                } else {
                    b.setForeground(TEXT_USER);
                }
            }
        }
    }

    /**
     * Checks if the game has been won by verifying all cells match the solution.
     * 
     * @return true if all cells match the solution, false otherwise
     */
    public boolean checkWin() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (buttons[row][col].displayValue != solvedPuzzle[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Custom button class representing a single cell in the Sudoku grid.
     * Displays either a pre-filled number (given) or a user-entered number.
     */
    class SDKButton extends JButton {
        /** The value currently displayed on the button (0 if the cell is empty) */
        int displayValue;
        
        /** True if this is a pre-filled (given) cell that cannot be modified */
        boolean isGiven;
        
        /** The default border style for this button */
        Border defaultBorder;

        /**
         * Constructs a Sudoku cell button.
         * 
         * @param number the value to display in this cell (0 means empty)
         * @param isGiven true if this is a pre-filled cell, false if user-editable
         */
        SDKButton(int number, boolean isGiven) {
            this.isGiven = isGiven;
            this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
            this.setPreferredSize(new Dimension(60, 60));
            this.setOpaque(true);
            this.setFocusPainted(false);
            this.setBorder(BorderFactory.createLineBorder(GRID_BORDER, 1));
            this.defaultBorder = this.getBorder();

            // Initialize button based on whether it's a given cell
            if (isGiven) {
                this.displayValue = number;
                this.setText(Integer.toString(number));
                this.setBackground(BG_CELL_GIVEN);
                this.setForeground(TEXT_GIVEN);
            } else {
                this.displayValue = 0;
                this.setText("");
                this.setBackground(BG_CELL);
                this.setForeground(TEXT_USER);
            }
        }

        /**
         * Updates the value displayed on this cell.
         * Only used for non-given cells; pre-filled cells cannot be modified.
         * 
         * @param value the new value to display (0 to clear the cell)
         */
        public void setDisplayValue(int value) {
            this.displayValue = value;
            this.setText(value > 0 ? Integer.toString(value) : "");
            this.setForeground(TEXT_USER);
        }

        /**
         * Sets the visual style of this button to indicate whether it's selected.
         * Selected cells display with a thick blue border; unselected with a thin border.
         * 
         * @param selected true to show selected state, false to show normal state
         */
        public void setSelectedVisual(boolean selected) {
            if (selected) {
                this.setBorder(SELECTED_BORDER);
            } else {
                this.setBorder(defaultBorder);
            }
        }
    }

    /**
     * Custom button class for the number selection pad (1-9).
     * Displays a number and provides visual feedback when selected.
     */
    class NumPadButton extends JButton {
        /** The number displayed by this button (1-9) */
        int number;

        /**
         * Constructs a number pad button.
         * 
         * @param number the number to display (1-9)
         */
        NumPadButton(int number) {
            super(Integer.toString(number));
            this.number = number;
            this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
            this.setOpaque(true);
            this.setBackground(PAD_BTN_BG);
            this.setForeground(PAD_BTN_FG);
            this.setFocusPainted(false);
            this.setBorder(BorderFactory.createLineBorder(GRID_BORDER, 2));
        }

        /**
         * Gets the number represented by this button.
         * 
         * @return the number (1-9)
         */
        public int getNumber() {
            return number;
        }
    }

    /**
     * Handles mouse events for Sudoku cell buttons.
     * Responds to clicks to select cells and place numbers.
     */
    class MouseHandler implements MouseListener {
        /** Reference to the parent Sudoku game */
        Sudoku game;
        
        /** True if this cell is a given (pre-filled and cannot be changed) */
        boolean isGiven;

        /**
         * Constructs a mouse handler for a Sudoku cell.
         * 
         * @param game reference to the parent Sudoku game
         * @param isGiven true if this cell is a given (cannot be changed)
         */
        public MouseHandler(Sudoku game, boolean isGiven) {
            this.game = game;
            this.isGiven = isGiven;
        }

        @Override public void mouseClicked(MouseEvent me) {}
        @Override public void mousePressed(MouseEvent me) {}
        @Override
        public void mouseReleased(MouseEvent me) {
            if (game.gameWon) return; // Prevent changes after win

            SDKButton button = (SDKButton) me.getSource();

            // Remember the last clicked cell so "Remove #" knows what to clear
            if (game.selectedButton != null && game.selectedButton != button) {
                game.selectedButton.setSelectedVisual(false);
            }
            game.selectedButton = button;
            game.selectedButton.setSelectedVisual(true);

            // Allow clearing with Remove # OR placing a number
            if (!isGiven) {
                if (game.getCurrentNumber() > 0) {
                    int entered = game.getCurrentNumber();
                    button.setDisplayValue(entered);
                }
                game.updateCatImage();
                // If the user has toggled "See Error", keep highlights updated
                if (game.showWrongNumbers) {
                    game.updateWrongHighlights();
                }

                if (game.checkWin()) {
                    game.gameWon = true;
                    // Change cat image when the user wins
                    game.imageLabel.setIcon(game.winCatIcon);
                    game.message.setText("Congratulations! You won!");
                    game.message.setForeground(MSG_WIN);
                }
            }
            }
        @Override public void mouseEntered(MouseEvent me) {}
        @Override public void mouseExited(MouseEvent me) {}
    }

    /**
     * Scales an ImageIcon to a specified width and height.
     * Prevents distorted or overly zoomed images.
     * 
     * @param icon the original ImageIcon to scale
     * @param w the target width in pixels
     * @param h the target height in pixels
     * @return a new ImageIcon scaled to the specified dimensions
     */
    private ImageIcon scaleIcon(ImageIcon icon, int w, int h) {
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    /**
     * Main entry point for the Sudoku game application.
     * Creates and starts a new game with a predefined puzzle.
     * 
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // 9 by 9 grid with solved cells
        int[][] solvedGrid = {
            {8,2,7,1,5,4,3,9,6},
            {9,6,5,3,2,7,1,4,8},
            {3,4,1,6,8,9,7,5,2},
            {5,9,3,4,6,8,2,7,1},
            {4,7,2,5,1,3,6,8,9},
            {6,1,8,9,7,2,4,3,5},
            {7,8,6,2,3,5,9,1,4},
            {1,5,4,7,9,6,8,2,3},
            {2,3,9,8,4,1,5,6,7}
        };
        // Boolean array for which cells are given (true) or empty (false)
        /*
        boolean[][] given = {
            {true,false,false,false,true,false,false,true,true},
            {false,false,true,false,false,true,false,true,false},
            {true,true,false,false,true,false,true,false,false},
            {false,false,false,true,false,true,false,true,false},
            {true,false,true,false,true,false,false,false,true},
            {true,false,true,false,false,true,true,true,false},
            {false,true,false,false,false,true,true,false,false},
            {false,false,false,true,false,false,false,true,false},
            {true,true,false,false,true,false,false,false,true}
        };*/

        boolean[][] given = {
            {true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true}
        };

        
        
        
        
        
        
        
        boolean[][] alltrue = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                alltrue[i][j] = true;
            }
        }
        alltrue[0][0] = false; // for testing purposes
        // Start the game
       Sudoku Sgame = new Sudoku(solvedGrid, given);
    }
}
