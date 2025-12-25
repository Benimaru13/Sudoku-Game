import java.awt.Font;
import javax.swing.JButton;
import java.awt.Dimension;

public class SButton extends JButton{
    // Keep the variables public
    int row;
    int col;
    boolean boolValue;
    int cellNum;
    
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
    }
        
    SButton(int num) {
        this.cellNum = num;
            this.setPreferredSize(new Dimension(75, 75));
            this.setText(Integer.toString(num));
            this.setFont(new Font("Verdana", Font.BOLD, 32));

        // this.addMouseListener(new SButtonMouseListener(this));
         }
    
    public int getRow() {      
        return row;
    }

    public int getCol() {
        return col;
    }

}
