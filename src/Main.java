import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    // List of states
    private static final String[] STATES = {
            "Alabama",
            "Alaska",
            "Arizona",
            "Arkansas",
            "California",
            "Colorado",
            "Connecticut",
            "Delaware",
            "Florida",
            "Georgia",
            "Hawaii",
            "Idaho",
            "Illinois",
            "Indiana",
            "Iowa",
            "Kansas",
            "Kentucky",
            "Louisiana",
            "Maine",
            "Maryland",
            "Massachusetts",
            "Michigan",
            "Minnesota",
            "Mississippi",
            "Missouri",
            "Montana",
            "Nebraska",
            "Nevada",
            "New Hampshire",
            "New Jersey",
            "New Mexico",
            "New York",
            "North Carolina",
            "North Dakota",
            "Ohio",
            "Oklahoma",
            "Oregon",
            "Pennsylvania",
            "Rhode Island",
            "South Carolina",
            "South Dakota",
            "Tennessee",
            "Texas",
            "Utah",
            "Vermont",
            "Virginia",
            "Washington",
            "West Virginia",
            "Wisconsin",
            "Wyoming"
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Handle user input based on menu selection
        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("1. Display the text");
            System.out.println("2. Search");
            System.out.println("3. Exit program");
            System.out.print("Enter your choice: ");


            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Discard invalid input
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    displayText();
                    break;
                case 2:
                    System.out.print("Enter the search pattern: ");
                    String pattern = scanner.nextLine();
                    ArrayList<Integer> results = searchText(pattern); // Search for pattern in states
                    if (results.isEmpty()) {
                        System.out.println("Pattern not found in any state.");
                    } else {
                        System.out.println("Pattern found at indices: " + results);
                    }
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        scanner.close();
    }

    // Method to display the list of states
    private static void displayText() {
        System.out.println("List of states:");
        for (String state : STATES) {
            System.out.println(state);
        }
    }

    // Method to search for a pattern within the states list using the Boyer-Moore algorithm
    private static ArrayList<Integer> searchText(String pattern) {
        ArrayList<Integer> matches = new ArrayList<>();
        pattern = pattern.toLowerCase(); // Convert pattern to lowercase for case-insensitive search
        HashMap<Character, Integer> badCharacter = buildBadCharacterTable(pattern); // Bad character table

        for (int index = 0; index < STATES.length; index++) {
            String state = STATES[index].toLowerCase(); // Convert state name to lowercase
            int m = pattern.length();
            int n = state.length();
            int i = 0;

            while (i <= n - m) {
                int j = m - 1;

                // Check for matches from the end of the pattern
                while (j >= 0 && pattern.charAt(j) == state.charAt(i + j)) {
                    j--;
                }

                if (j < 0) {
                    // Pattern found in the current list
                    matches.add(index);
                    break;
                } else {
                    // Shift the pattern to align with the next character based on the bad character rule
                    i += Math.max(1, j - badCharacter.getOrDefault(state.charAt(i + j), -1));
                }
            }
        }

        return matches; // Return list of indices where the pattern was found
    }

    private static HashMap<Character, Integer> buildBadCharacterTable(String pattern) {
        HashMap<Character, Integer> badCharacter = new HashMap<>();
        int m = pattern.length();

        // Fill the table with the shift values for each character in the pattern
        for (int i = 0; i < m - 1; i++) {
            badCharacter.put(pattern.charAt(i), m - i - 1);
        }

        return badCharacter;
    }
}
