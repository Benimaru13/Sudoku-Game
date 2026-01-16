package Testing;

import javax.swing.JOptionPane;

/**
 * Simple example demonstrating a dialog input prompt.
 * Shows how to use JOptionPane to ask the user for their name.
 * 
 * @author Your Team
 * @version 1.0
 */
public class CustomButtonsExample {
    /**
     * Main method demonstrating user input dialog.
     * Prompts the user for their name and displays a greeting.
     * 
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // Display dialog asking for user's name
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
