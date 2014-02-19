import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * JumboSolver is the class to find all permutations of a word in english.
 * Pass the String you want to search for as the first argument.
 * The word list is hardcoded as being wordsEn.txt in src/
 * If that file is not found, it will try to load the file given as the second argument.
 * If we needed more functionality, I would have made the solver a separate class.
 *
 * @author Henry Smith (HenrySmith3 on GitHub)
 * I used the word list found at http://www-01.sil.org/linguistics/wordlists/english/wordlist/wordsEn.txt
 */
public class JumboSolver {

    /**
     * Runs the entire program.
     * This could have been split up into a main method that just handles the args and a separate class that handles
     *      the actual solving of the puzzle. It seemed unnecessary, though, as the whole thing is like 50 lines.
     * Putting the characters in the character map is O(n) where n is the length of the input string.
     * Looping through the words in the dictionary is O(w) where w is the number of words.
     * For each word, we have to look through each character and then compare it to the hashmap of the input string,
     *      giving us O(w*c), where c is the length of the word in the dictionary.
     * Overall runtime is O(n) + O(w*c), but O(w*c) will completely overshadow O(n) unless you have a tiny dictionary
     *      and an obnoxiously huge input string, so the effective runtime is O(w*c).
     * @param args First argument is the word to search for, second is the path to the dictionary if not using default.
     * @throws IOException If neither file can be opened.
     */
    public static void main(String[] args) throws IOException {
        //We can't proceed without a target string.
        if (args.length == 0) {
            throw new RuntimeException("No string entered.");
        }

        //First argument is the word to search for.
        String searchingOn = args[0];
        HashMap<Character, Integer> characterMap = new HashMap<Character, Integer>();
        for (Character c : searchingOn.toCharArray()) {
            if (characterMap.containsKey(c)) {
                characterMap.put(c, characterMap.get(c)+1);
            } else {
                characterMap.put(c, 1);
            }
        }

        List<String> returnValue = new ArrayList<String>();

        BufferedReader reader = null;
        try {
            //This is why people hate file io in java.
            reader = new BufferedReader(new FileReader(new File("src/wordsEn.txt")));
        } catch (FileNotFoundException e) {
            reader = new BufferedReader(new FileReader(new File(args[1])));
        }
        String line;
        while ((line = reader.readLine()) != null) {
            //everything before the tab is the word, including spaces.
            boolean matchFlag = true;
            for (Character c : line.toCharArray()) {
                //a little dirty, but it makes it a one liner without having to import anything not in core library.
                if (!characterMap.containsKey(c) || characterMap.get(c) < line.replaceAll("[^" + c + "]", "").length()) {
                    //not enough occurances in input to match, so this is not a match.
                    matchFlag = false;
                }
            }
            if (matchFlag) {
                returnValue.add(line);
            }

        }
        for (String string: returnValue) {
            System.out.println(string);
        }

    }
}
