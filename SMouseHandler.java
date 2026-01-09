
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author c.benneth
 */
class SMouseHandler implements MouseListener {
    CSudokuGame game;
    boolean isFilled; // Boolean that ensures that a number is only placed where empty

    SMouseHandler(CSudokuGame game, boolean isFilled) {
        this.game = game;  
        this.isFilled = isFilled;  
    }

    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {}

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

        
    

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    // Helper method to format time in seconds to mm:ss
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%d:%02d", minutes, secs);
}
    
    }

