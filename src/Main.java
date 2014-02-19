import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new RuntimeException("No string entered.");
        }
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

        //This is why people hate file io in java.
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/part-of-speech.txt")));
        String line;
        String word;
        while ((line = reader.readLine()) != null) {
            //everything before the tab is the word, including spaces.
            word = line.split("\t")[0];
            boolean matchFlag = true;
            for (Character c : word.toCharArray()) {
                //a little dirty, but it makes it a one liner without having to import anything not in core library.
                if (!characterMap.containsKey(c) || characterMap.get(c) < word.replaceAll("[^" + c + "]", "").length()) {
                    //not enough occurances in input to match, so this is not a match.
                    matchFlag = false;
                }
            }
            if (matchFlag) {
                returnValue.add(word);
            }

        }
        for (String string: returnValue) {
            System.out.println(string);
        }

    }
}
