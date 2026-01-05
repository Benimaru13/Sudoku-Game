
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

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
                    JOptionPane.showMessageDialog(game.frame, "You've completed the puzzle!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
}
                
            if (game.gameOver) {
                    game.gameTimer.stop();
                    game.disableAllButtons();
                    game.titleMessage.setText("Too many errors! Try again.");
                    JOptionPane.showMessageDialog(game.frame, "Game Over! Too many errors.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
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
    
    }

