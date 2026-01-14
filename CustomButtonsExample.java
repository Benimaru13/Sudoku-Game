
import javax.swing.JOptionPane;

public class CustomButtonsExample {
    public static void main(String[] args) {
        // Define a custom button that asks for user input when clicked
       String userName = JOptionPane.showInputDialog(null,
         "Enter your name:", 
         "User Name",
         JOptionPane.QUESTION_MESSAGE);

         if (userName != null && !userName.trim().isEmpty()) {
              // User clicked OK and entered a name
              System.out.println("Hello, " + userName + "!");
    }
}

}
