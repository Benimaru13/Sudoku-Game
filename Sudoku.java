import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

// Sudoku game class
public class Sudoku {

    // Tracks the currently selected number from the number pad
    int currentNumber;
    // 2D array to store references to all grid buttons
    SDKButton[][] buttons = new SDKButton[9][9];

    boolean gameWon = false; // Track if the game is won
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

    // Getter for the current number selected by the user
    public int getCurrentNumber() {
        return currentNumber;
    }

    // Constructor to set up the GUI
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

    // Returns true if there is at least one incorrect (non-empty) number on the board
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

    // Updates the cat image based on the current board status
    public void updateCatImage() {
        // just in case win image does not get overwritten
        if (gameWon) return;
        if (hasAnyIncorrect()) {
            imageLabel.setIcon(incorrectCatIcon);
        } else {
            imageLabel.setIcon(lockInCatIcon);
        }
    }

    // Updates cell text colors to show incorrect entries in red when toggled on
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

    // Method to check if the game is won
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

    // Button class for Sudoku cells
    class SDKButton extends JButton {
        // Stores the value currently displayed on the button (0 if empty)
        int displayValue;
        boolean isGiven;
        Border defaultBorder;

        // Constructor to set up the button
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

        // Update the value shown on this cell (only used for non-given cells)
        public void setDisplayValue(int value) {
            this.displayValue = value;
            this.setText(value > 0 ? Integer.toString(value) : "");
            this.setForeground(TEXT_USER);
        }

        // Visual cue for which cell is currently selected
        public void setSelectedVisual(boolean selected) {
            if (selected) {
                this.setBorder(SELECTED_BORDER);
            } else {
                this.setBorder(defaultBorder);
            }
        }
    }

    // Button class for number pad
    class NumPadButton extends JButton {
        int number;

        // Constructor to set up the number pad button
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

        public int getNumber() {
            return number;
        }
    }

    // Handles mouse events for each Sudoku cell
    class MouseHandler implements MouseListener {
        Sudoku game;
        boolean isGiven; // True if this cell is a given (cannot be changed)

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

    // Helper to scale icons to a fixed size (prevents zoomed-in images) 
    private ImageIcon scaleIcon(ImageIcon icon, int w, int h) {
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

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
