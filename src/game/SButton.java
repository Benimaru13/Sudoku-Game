package src.game;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;

/**
 * Custom button class representing a single cell in the Sudoku grid
 * or a button in the number selection pad.
 * 
 * @author Chibueze Benneth
 * @version 1.0
 */

public class SButton extends JButton{
    /** Row index (0-8) in the 9x9 grid */
    int row;
    
    /** Column index (0-8) in the 9x9 grid */
    int col;
    
    /** True if this is a pre-filled (fixed) cell that cannot be modified */
    boolean isFixed;
    
    /** The correct solution number for this cell */
    int cellNum;
    
    /** The value currently displayed on this cell (0 if empty) */
    int displayValue;

    /**
     * Constructs a Sudoku grid cell button.
     * 
     * @param num the correct solution number for this cell
     * @param boolFlag true if this is a pre-filled cell, false if user-editable
     * @param row the row index (0-8) in the grid
     * @param col the column index (0-8) in the grid
     */ 
    
    public SButton(int num, boolean boolFlag, int row, int col) {
        this.setPreferredSize(new Dimension(60, 60));
        this.row = row;
        this.col = col;
        this.cellNum = num;
        this.isFixed = boolFlag;

        if (this.isFixed) {
            this.setEnabled(false); //This distinguishes the defaults from the open buttons
            this.setText(Integer.toString(this.cellNum));
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
        
    /**
     * Constructs a number pad button (1-9).
     * Used for the number selection pad, not for grid cells.
     * 
     * @param num the number this button represents (1-9)
     */
    SButton(int num) {
        this.cellNum = num;
            this.setPreferredSize(new Dimension(75, 75));
            this.setText(Integer.toString(num));
            this.setFont(new Font("Verdana", Font.ITALIC, 32));
            this.setBackground(CSudokuGame.PAD_BTN_BG);
            this.setForeground(CSudokuGame.PAD_BTN_FG);
            this.setOpaque(true);
         }
    
    /**
     * Updates the value displayed on this cell.
     * Only used for non-fixed cells; pre-filled cells cannot be modified.
     * 
     * @param value the new value to display (0 to clear the cell)
     */
    public void setDisplayValue(int value) {
            this.displayValue = value;
            this.setText(value > 0 ? Integer.toString(this.displayValue) : "");
            this.setFont(new Font("Verdana", Font.BOLD, 25));

        }
        
    /**
     * Sets the visual style to indicate whether this cell is selected.
     * Selected cells display with a blue border; unselected with default border.
     * 
     * @param selected true to show selected state, false to show normal state
     */
    
    public void setSelectedVisual(boolean selected) {
    
            if (selected) {
                this.setBorder(CSudokuGame.selectedBorder);

            } else {
                this.setBorder(CSudokuGame.defaultBorder);
            }
        }
    
    /**
     * Gets the row index of this button in the grid.
     * 
     * @return the row index (0-8)
     */
    public int getRow() {      
        return row;
    }

    /**
     * Gets the column index of this button in the grid.
     * 
     * @return the column index (0-8)
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets the correct solution number for this cell.
     * 
     * @return the cell's solution number
     */
    public int getCellNum() {
        return cellNum;
    }
}
