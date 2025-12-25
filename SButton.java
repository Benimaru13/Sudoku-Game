import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class SButton extends JButton{
    // Keep the variables public
    int row;
    int col;
    int cellNum;
    boolean boolValue;

    SButton(int num, boolean boolFlag) {
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
        
        // this.addMouseListener(new SButtonMouseListener(this));
    }
    
    public int getRow() {      
        return row;
    }

    public int getCol() {
        return col;
    }

}
