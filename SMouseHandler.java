
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author c.benneth
 */
public class SMouseHandler implements MouseListener {
    CSudokuGame game;
    boolean isFilled; // Boolean that ensures that a number is only placed where empty

    public SMouseHandler(CSudokuGame game, boolean isFilled) {
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

        game.selectedButton = button;
        button.setSelectedVisual(true);

        if (!isFilled) {
            if (game.getCurrentNumber() > 0) {
                int selectedNum = game.getCurrentNumber();
                button.setDisplayValue(selectedNum);
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