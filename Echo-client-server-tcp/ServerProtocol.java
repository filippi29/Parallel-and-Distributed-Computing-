import java.util.StringJoiner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerProtocol {

    public String processRequest(String theInput) {
        System.out.println("Received message from client: " + theInput);

        String theOutput;
        String[] tokens = theInput.split(" ");
        String numberChoice = tokens[0];                      //ο αριθμος επιλογής ειναι ΠΑΝΤΑ το πρωτο στοιχειο
        if (numberChoice.equals("1")){
            StringJoiner sj = new StringJoiner(" ");     //όταν ενώνει στοιχεία βάζει το " " ανάμεσά τους
            for (int i = 1; i < tokens.length; i++) {            // ξεκινάμε από το 1ο στοιχείο και μετά γιατί το στοιχείο 0 είναι αριθμος
                sj.add(tokens[i]);
            }
            theOutput = peza(sj.toString());
        }
        else if (numberChoice.equals("2")){
            StringJoiner sj = new StringJoiner(" ");     //όταν ενώνει στοιχεία βάζει το " " ανάμεσά τους
            for (int i = 1; i < tokens.length; i++) {            // ξεκινάμε από το 1ο στοιχείο και μετά γιατί το στοιχείο 0 είναι αριθμος
                sj.add(tokens[i]);
            }
            theOutput = kefalaia(sj.toString());

        }
        else if (numberChoice.equals("3")){
            String number = tokens[1];                            //παίρνει τον αριθμό κωδικοποίησης
            StringJoiner sj = new StringJoiner(" ");     //όταν ενώνει στοιχεία βάζει το " " ανάμεσά τους
            for (int i = 2; i < tokens.length; i++) {            // ξεκινάμε από το 1ο στοιχείο και μετά γιατί το στοιχείο 0 είναι αριθμος
                sj.add(tokens[i]);
            }
            theOutput = ASCII(sj.toString(), Integer.parseInt(number));
        }
        else if (numberChoice.equals("4")){

            String number = tokens[1];                            //παίρνει τον αριθμό κωδικοποίησης
            StringJoiner sj = new StringJoiner(" ");     //όταν ενώνει στοιχεία βάζει το " " ανάμεσά τους
            for (int i = 2; i < tokens.length; i++) {            // ξεκινάμε από το 1ο στοιχείο και μετά γιατί το στοιχείο 0 είναι αριθμος
                sj.add(tokens[i]);
            }
            theOutput = decryptASCII(sj.toString(), Integer.parseInt(number));
        }
        else if (numberChoice.equals("5")){
            theOutput = reflectMessage(tokens[1]);
        }
        else if (numberChoice.equals("6")) {     //επιστρέφει την ακριβή ώρα και ημερομηνία
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            theOutput = "Current date and time: " + formattedDateTime;
        }
        else if (numberChoice.equals("7")) {
            char letter = tokens[1].charAt(0);
            String text = tokens[2];
            int count = countLetter(text, letter);
            theOutput = "The letter '" + letter + "' appears " + count + " times in the text.";
        }
        else{
            theOutput = "Invalid number. Pick another one from 1 - 4";
        }

        System.out.println("Send message to client: " + theOutput);
        return theOutput;
    }




    public String peza(String theInput) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < theInput.length(); i++) {

            char letter = theInput.charAt(i);
            if (Character.isUpperCase(letter)) {
                result.append(Character.toLowerCase(letter));
            } else {
                result.append(letter);
            }
        }
        return result.toString();
    }

    public String kefalaia(String theInput) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < theInput.length(); i++) {
            char letter = theInput.charAt(i);
            if (Character.isLowerCase(letter)) {
                result.append(Character.toUpperCase(letter));
            } else {
                result.append(letter);
            }
        }
        return result.toString();
    }

    public String ASCII(String theInput, int offset) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < theInput.length(); i++) {

            char character = theInput.charAt(i);

            if (character != ' ') {
                int originalAlphabetPosition = character - 'a';
                int newAlphabetPosition = (originalAlphabetPosition + offset) % 26;
                char newCharacter = (char) ('a' + newAlphabetPosition);
                result.append(newCharacter);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    private String reflectMessage(String theInput) {    //μετατροπή του μηνύματος σε ακριβώς αντίστροφό του
        StringBuilder reversedMessage = new StringBuilder(theInput).reverse();
        return reversedMessage.toString();
    }

    public String decryptASCII(String theInput, int offset) {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < theInput.length(); i++) {
            char character = theInput.charAt(i);
            if (character != ' ') {
                int originalAlphabetPosition = character - 'a';
                // Ανατρέπουμε τη διαδικασία κρυπτογράφησης: αφαιρούμε αντί να προσθέτουμε το κλειδί
                int newAlphabetPosition = (originalAlphabetPosition - offset) % 26;
                // Επίλυση αρνητικών τιμών για να μην χαθεί η σωστή θέση του αλφαβήτου
                if (newAlphabetPosition < 0) {
                    newAlphabetPosition = 26 + newAlphabetPosition;
                }
                char newCharacter = (char) ('a' + newAlphabetPosition);
                result.append(newCharacter);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    private int countLetter(String text, char letter) {   //μετράει πόσες φορές υπάρχει ένα γράμμα στο μήνυμα
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == letter) {
                count++;
            }
        }
        return count;  //επιστρέφει τον αριθμό
    }

}
