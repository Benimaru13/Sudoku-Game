import javax.swing.JOptionPane;

public class CustomButtonsExample {
    public static void main(String[] args) {
        // Define custom button labels
        Object[] options = {"Reset", "Exit"};
        
        // Display the dialog with custom buttons
        int choice = JOptionPane.showOptionDialog(
            null,                          // Parent component
            "Choose an action:",           // Message
            "Custom Dialog Title",         // Title
            JOptionPane.DEFAULT_OPTION,    // Option type
            JOptionPane.QUESTION_MESSAGE,  // Message type (for the icon)
            null,                          // Icon (null for default question icon)
            options,                       // The custom buttons
            options[0]                     // The initially selected button
        );

        System.out.println("User selected option index: " + choice);

        // Process the user's choice
        if (choice == 0) {
            System.out.println("Reset selected");
        } else if (choice == 1) {
            System.out.println("Stop selected");
        } else if (choice == 2) {
            System.out.println("Pause selected");
        } else {
            System.out.println("Dialog closed");
        }
    }
}
