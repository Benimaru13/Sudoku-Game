import java.awt.Font;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.border.Border;

public class SButton extends JButton{
    // Keep the variables public
    int row;
    int col;
    boolean boolValue;
    int cellNum;
    int displayValue;
    final Border selectedBorder = BorderFactory.createLineBorder(Color.BLUE, 3);
    final Border defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

    public SButton(int num, boolean boolFlag) {
        this.cellNum = num;
        this.boolValue = boolFlag;

        if (boolFlag) {
            this.setEnabled(false); //This distinguishes the defaults from the open buttons
            this.setText(Integer.toString(num));
            this.setFont(new Font("Verdana", Font.BOLD, 32));

        } 
        else {
            this.setText("");
        }
    }
        
    SButton(int num) {
        this.cellNum = num;
            this.setPreferredSize(new Dimension(75, 75));
            this.setText(Integer.toString(num));
            this.setFont(new Font("Verdana", Font.ITALIC, 32));

        // this.addMouseListener(new SButtonMouseListener(this));
         }
    
    // Update the value shown on this cell (only used for non-given cells)
    public void setDisplayValue(int value) {
            this.displayValue = value;
            this.setText(value > 0 ? Integer.toString(value) : "");
            System.out.println("Set cell to " + value);
            this.setFont(new Font("Verdana", Font.BOLDOLD, 32));
            // this.setForeground(TEXT_USER);
        }
        
    // Visual cue for which cell is currently selected
    public void setSelectedVisual(boolean selected) {
            if (selected) {
                this.setBorder(selectedBorder);
            } else {
                this.setBorder(defaultBorder);
            }
        }
    

    public int getRow() {      
        return row;
    }

    public int getCol() {
        return col;
    }

}
