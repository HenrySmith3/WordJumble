import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
 * I assume that all permutations should be included, even the original word that was passed in.
 *
 * @author Henry Smith (HenrySmith3 on GitHub)
 * I used the word list at http://www-01.sil.org/linguistics/wordlists/english/wordlist/wordsEn.txt (provided in src/)
 */
public class JumboSolver {

    /**
     * Runs the entire program.
     * This could have been split up into a main method that just handles the args and a separate class that handles
     *      the actual solving of the puzzle. It seemed unnecessary, though, as the whole thing is like 50 lines.
     * Putting the characters in the character map is O(n) where n is the length of the input string.
     * Looping through the words in the dictionary is O(w) where w is the number of words.
     * For each word, we have to look through each character and then compare it to the hashmap of the input string,
     *      giving us O(w*c), where c is the length of the word in the dictionary. (hashmap lookup is O(1))
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

        //We're not actually returning this because we're in main.
        //Could be easily modified to be in a separate class and actually return a value instead.
        List<String> returnValue = new ArrayList<String>();

        BufferedReader reader;
        try {
            //This is why people hate file io in java.
            reader = new BufferedReader(new FileReader(new File("src/wordsEn.txt")));
        } catch (FileNotFoundException e) {
            reader = new BufferedReader(new FileReader(new File(args[1])));
        }
        String line;
        while ((line = reader.readLine()) != null) {

            boolean matchFlag = true;
            for (Character c : line.toCharArray()) {
                //a little dirty, but it makes it a one liner without having to import anything not in core library.
                if (!characterMap.containsKey(c) || characterMap.get(c) < line.replaceAll("[^"+c+"]", "").length()) {
                    //not enough occurrences of the character in the input to match, so this is not a match.
                    matchFlag = false;
                    break;//no sense in continuing, all we would do is set it to false again.
                }
            }
            //if we made it through the word and there were enough of each character in the original input string
            //to make the word, then add it to the return list.
            if (matchFlag) {
                returnValue.add(line);
            }
        }

        //end of program, just print out the results.
        //we could have been printing as we go, but that makes this less easily changeable to return instead of print.
        for (String string: returnValue) {
            System.out.println(string);
        }
    }
}
