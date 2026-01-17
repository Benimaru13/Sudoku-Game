package src.game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Handles mouse events for Sudoku grid cells.
 * Responds to cell clicks to select cells, place numbers, and check game state.
 * Also manages the hint system and win/loss conditions.
 * 
 * @author c.benneth
 * @version 1.0
 */
public class SMouseHandler implements MouseListener {
    /** Reference to the parent Sudoku game */
    CSudokuGame game;
    
    /** True if this cell is pre-filled (cannot be modified) */
    boolean isFilled;

    /**
     * Constructs a mouse handler for a Sudoku cell.
     * 
     * @param game reference to the parent Sudoku game
     * @param isFilled true if this cell is pre-filled (cannot be modified)
     */
    SMouseHandler(CSudokuGame game, boolean isFilled) {
        this.game = game;  
        this.isFilled = isFilled;  
    }

    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {
        SButton button = (SButton)me.getSource();
        game.setSelectedCell(button); // this selects and highlights the clicked cell

        if (game.revealMode) return;

        // Only allow number placement if the cell is not already filled
        if (!isFilled) {
            if (game.getCurrentNumber() > 0) {
                int selectedNum = game.getCurrentNumber();
                button.setDisplayValue(selectedNum);
                game.revealedBtnNumbers[button.row][button.col] = selectedNum;game.revealedBtnNumbers[button.row][button.col] = selectedNum;

                // Check if the move is correct
                game.checkMove(button.row, button.col, selectedNum);

                // Check if the game is won
                if (game.checkWinCondition()) {
                    game.gameTimer.stop();
                    game.disableAllButtons();
                    game.revealBtn.setEnabled(false);
                    game.titleMessage.setText("Congratulations! You've completed the puzzle!");
                    game.showGameEndDialog(
                    "You've completed the puzzle in " + formatTime(game.secondsElapsed) + "!", 
                    "Congratulations!"
                );
                }
                
                // Check if the game is over due to too many errors
                if (game.gameOver) {
                    game.gameTimer.stop();
                    game.disableAllButtons();
                    game.titleMessage.setText("Too many errors! Try again.");
                    // Show custom dialog for game over
                    game.showGameEndDialog(
                        "Game Over! Too many errors.", 
                        "Game Over");
                }
            
        }
    }
}

    
    /**
     * Formats elapsed time in seconds to a mm:ss string format.
     * 
     * @param seconds the number of seconds elapsed
     * @return a formatted time string in mm:ss format
     */
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%d:%02d", minutes, secs);
}
    
    }

