import java.awt.Font;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.*;



public class SButton extends JButton{
    // Keep the variables public
    int row;
    int col;
    boolean boolValue;
    int cellNum;
    int displayValue;

    
    public SButton(int num, boolean boolFlag, int row, int col) {
        this.setPreferredSize(new Dimension(60, 60));
        this.row = row;
        this.col = col;
        this.cellNum = num;
        this.boolValue = boolFlag;

        if (boolFlag) {
            this.setEnabled(false); //This distinguishes the defaults from the open buttons
            this.setText(Integer.toString(num));
            this.setFont(new Font("Verdana", Font.BOLD, 25));
            this.setBackground(CSudokuGame.BG_CELL_GIVEN);
            this.setForeground(CSudokuGame.TEXT_GIVEN);
            this.setOpaque(true);
            this.setBorder(CSudokuGame.defaultBorder);

        } 
        else {
            this.setText("");
            this.setBackground(CSudokuGame.BG_CELL);
            this.setForeground(CSudokuGame.TEXT_USER);
            this.setOpaque(true);
            this.setBorder(CSudokuGame.defaultBorder);

        }
    }
        
    // Constructor for number pad buttons
    SButton(int num) {
        this.cellNum = num;
            this.setPreferredSize(new Dimension(75, 75));
            this.setText(Integer.toString(num));
            this.setFont(new Font("Verdana", Font.ITALIC, 32));
            this.setBackground(CSudokuGame.PAD_BTN_BG);
            this.setForeground(CSudokuGame.PAD_BTN_FG);
            this.setOpaque(true);
         }
    
    // Update the value shown on this cell (only used for non-given cells)
    public void setDisplayValue(int value) {
            this.displayValue = value;
            this.setText(value > 0 ? Integer.toString(value) : "");
            System.out.println("Set cell to " + value);
            this.setFont(new Font("Verdana", Font.BOLD, 25));
            // this.setForeground(TEXT_USER);
        }
        
    // Visual cue for which cell is currently selected
    public void setSelectedVisual(boolean selected) {
    
            if (selected) {
                this.setBorder(CSudokuGame.selectedBorder);

            } else {
                this.setBorder(CSudokuGame.defaultBorder);
            }
        }
    

    public int getRow() {      
        return row;
    }

    public int getCol() {
        return col;
    }



}
