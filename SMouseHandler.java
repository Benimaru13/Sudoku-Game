
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

        if (!isFilled) {
            if (game.getCurrentNumber() > 0) {
                int selectedNum = game.getCurrentNumber();
                if (selectedNum > 0) button.setDisplayValue(selectedNum);

                // Check if the move is correct
                game.checkMove(button.row, button.col, selectedNum);

                // Check if the game is won
                game.checkWinCondition();
            }
        }
        // only allow placing numbers in non-default (editable) cells
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
    
    
}