import javax.swing.JFrame;
import javax.swing.JPanel;

class CSudokuGame{

    public CSudokuGame() {
        System.out.println("Sudoku Game Initialized!");
        JFrame frame = new JFrame("Sudoku Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
        JPanel messagePanel = new JPanel();
        frame.add(messagePanel);
        

    }

    public static void main(String[] args) {
        CSudokuGame game = new CSudokuGame();
    }
}
