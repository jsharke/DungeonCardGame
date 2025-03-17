import java.util.Arrays;
import java.util.Scanner;

public class InputController {
    private Scanner scanner;

    public InputController() {
        this.scanner = new Scanner(System.in);
    }

    String getInput() {
        //System.out.println("\nEnter key:");
        return this.scanner.nextLine();
    }

    boolean isInputValid(char firstChar) {
        return Arrays.asList('1', '2', '3', '4', 'q', 'w', 'r').contains(firstChar);
    }

}
