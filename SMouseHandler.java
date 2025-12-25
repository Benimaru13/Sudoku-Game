
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author c.benneth
 */
public class SMouseHandler implements MouseListener {

    CSudokuGame game;
    public SMouseHandler(CSudokuGame game){
        this.game = game;    
    }

    @Override
    public void mouseClicked(MouseEvent me) {
       
    }

    @Override
    public void mousePressed(MouseEvent me) {
            }

    @Override
    public void mouseReleased(MouseEvent me) {
        SButton button = (SButton)me.getSource();
        int n = game.getCurrentNum();
        // only allow placing numbers in non-default (editable) cells
        if (!button.cellFlag && n > 0) {
            button.cellNum = n;
            button.setText(Integer.toString(n));
        }
    // only allow placing numbers in non-default (editable) cells
        if (!button.cellFlag && n > 0) {
            button.cellNum = n;
            button.setText(Integer.toString(n));
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
    
    
}