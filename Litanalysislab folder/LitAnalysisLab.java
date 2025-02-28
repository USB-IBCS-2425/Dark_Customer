import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*; 

class LitAnalysisLab {
    public JFrame mainframe;
    public JPanel output;

    public JButton readB;
    public JButton avgB;
    public JButton writeB;
    public JButton brianR;
    public JButton shortestB;
    public JButton longestB;
    public JButton mostCommonB;
    public JButton leastCommonB;
    public JButton mostVowelsB;
    public JButton leastVowelsB;
    public JButton avgSentenceB;
    public JButton uniqueSentenceB;

    public static JTextField toRead;
    public static JTextArea resultT;
    public static ArrayList<String> textTokens;
    public static ArrayList<String> allwords;

    public LitAnalysisLab() {

        textTokens = new ArrayList<>();
        allwords   = new ArrayList<>();

        mainframe = new JFrame("Literature Analysis");
        mainframe.setSize(1500, 900);
        mainframe.setLayout(null);

        mainframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }        
        });

        toRead = new JTextField("BrianVu_IS_Super_Handsome.txt");
        toRead.setBounds(50, 50, 800, 30);
        mainframe.add(toRead);

        output = new JPanel();
        output.setBounds(200, 250, 100, 40);
        mainframe.add(output);

        readB = new JButton("Read File");
        readB.setActionCommand("READ");
        readB.addActionListener(new ButtonClickListener());
        readB.setBounds(50, 100, 100, 50);
        mainframe.add(readB);

        avgB = new JButton("Average Word");
        avgB.setActionCommand("AVG");
        avgB.addActionListener(new ButtonClickListener());
        avgB.setBounds(160, 100, 120, 50);
        mainframe.add(avgB);

        writeB = new JButton("Write File");
        writeB.setActionCommand("WRITE");
        writeB.addActionListener(new ButtonClickListener());
        writeB.setBounds(290, 100, 100, 50);
        mainframe.add(writeB);

        brianR = new JButton("BrianRemove");
        brianR.setActionCommand("brianR");  
        brianR.addActionListener(new ButtonClickListener());
        brianR.setBounds(400, 100, 120, 50);
        mainframe.add(brianR);

        shortestB = new JButton("Shortest Word");
        shortestB.setActionCommand("SHORTEST");
        shortestB.addActionListener(new ButtonClickListener());
        shortestB.setBounds(530, 100, 120, 50);
        mainframe.add(shortestB);

        longestB = new JButton("Longest Word");
        longestB.setActionCommand("LONGEST");
        longestB.addActionListener(new ButtonClickListener());
        longestB.setBounds(660, 100, 120, 50);
        mainframe.add(longestB);

        mostCommonB = new JButton("Most Common");
        mostCommonB.setActionCommand("MOST_COMMON");
        mostCommonB.addActionListener(new ButtonClickListener());
        mostCommonB.setBounds(790, 100, 120, 50);
        mainframe.add(mostCommonB);

        leastCommonB = new JButton("Least Common");
        leastCommonB.setActionCommand("LEAST_COMMON");
        leastCommonB.addActionListener(new ButtonClickListener());
        leastCommonB.setBounds(920, 100, 120, 50);
        mainframe.add(leastCommonB);

        mostVowelsB = new JButton("Most Vowels");
        mostVowelsB.setActionCommand("MOST_VOWELS");
        mostVowelsB.addActionListener(new ButtonClickListener());
        mostVowelsB.setBounds(1050, 100, 120, 50);
        mainframe.add(mostVowelsB);

        leastVowelsB = new JButton("Least Vowels");
        leastVowelsB.setActionCommand("LEAST_VOWELS");
        leastVowelsB.addActionListener(new ButtonClickListener());
        leastVowelsB.setBounds(50, 160, 120, 50);
        mainframe.add(leastVowelsB);

        avgSentenceB = new JButton("Avg Sentence");
        avgSentenceB.setActionCommand("AVG_SENTENCE");
        avgSentenceB.addActionListener(new ButtonClickListener());
        avgSentenceB.setBounds(180, 160, 120, 50);
        mainframe.add(avgSentenceB);

        uniqueSentenceB = new JButton("Unique Sentence");
        uniqueSentenceB.setActionCommand("UNIQUE_SENTENCE");
        uniqueSentenceB.addActionListener(new ButtonClickListener());
        uniqueSentenceB.setBounds(310, 160, 140, 50);
        mainframe.add(uniqueSentenceB);

        resultT = new JTextArea("");
        resultT.setBounds(50, 250, 2000, 800);
        mainframe.add(resultT);

        mainframe.setVisible(true);
    }

    public static void main(String[] args) {
        new LitAnalysisLab();
    }

    public static double round(double x, int places) {
        int mult = (int)Math.pow(10, places);
        int y = (int)(x * mult);
        return y / (double) mult;
    }

    
    public static void readFile() {
        String fname = toRead.getText();
        textTokens.clear();

        try {
            File f = new File(fname);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String data = s.next();
                textTokens.add(data);
            }
            s.close();
        }
        catch (FileNotFoundException err) {
            System.out.println("An error occurred.");
            err.printStackTrace();
        }

        resultT.setText("File Read\nFile has " + textTokens.size() + " tokens");
    }
    
   
    public static void readFileGutenberg() {
        String fname = toRead.getText();
        textTokens.clear();
        ArrayList<String> content = new ArrayList<>();
        
        try {
            File f = new File(fname);
            Scanner scanner = new Scanner(f);
            boolean contentStarted = false;
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                
                if (!contentStarted && line.toUpperCase().contains("START OF THE PROJECT GUTENBERG EBOOK")) {
                    contentStarted = true;
                    continue; 
                }
                
                if (contentStarted && line.toUpperCase().contains("END OF THE PROJECT GUTENBERG EBOOK")) {
                    break;
                }
                if (contentStarted) {
                    content.add(line);
                }
            }
            scanner.close();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        
        for(String line : content){
            String[] tokens = line.split("\\s+");
            for(String token : tokens){
                if(!token.isEmpty()){
                    textTokens.add(token);
                }
            }
        }
        resultT.setText("File Read with Gutenberg Header/Footer Removed\nFile has " + textTokens.size() + " tokens");
    }

    public static void parseWords() {
        allwords.clear();
        for (String token : textTokens) {
         
            String[] tempWords = token.split("\\s|--");
            for (String s : tempWords) {
              
                s = s.replaceAll("[\\p{P}_]", "");
                s = s.toLowerCase();
              
                if (!s.isEmpty()) {
                    allwords.add(s);
                }
            }
        }
    }

    public static void showAvg() {
        if (allwords.isEmpty()) {
            resultT.setText("No words to analyze.");
            return;
        }
        double totLen = 0;
        for (String w : allwords) {
            totLen += w.length();
        }
        double avgLen = totLen / allwords.size();
        avgLen = round(avgLen, 2);
        String res = "The average word length is:\n" + avgLen + " characters";
        resultT.setText(res);
    }

    public static void writeFile() {
        String fname = toRead.getText();
        String toWrite = resultT.getText();

        try {
            FileWriter w = new FileWriter(fname);
            w.write(toWrite);
            w.close();
        }
        catch (IOException er) {
            System.out.println("Error message:");
            er.printStackTrace();
        }
    }

    
    public static void brianRemove() {
        int startIndex = -1;
        int endIndex   = -1;

        for (int i = 0; i < allwords.size(); i++) {
            if (allwords.get(i).equals("start")) {
                startIndex = i;
                break;
            }
        }
        for (int i = allwords.size() - 1; i >= 0; i--) {
            if (allwords.get(i).equals("end")) {
                endIndex = i;
                break;
            }
        }
        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            java.util.List<String> sub = allwords.subList(startIndex, endIndex + 1);
            allwords = new ArrayList<>(sub);
        }
    }

    public static void shortest() {
        if (allwords.isEmpty()) {
            resultT.setText("No words to analyze.");
            return;
        }
        int minLen = allwords.get(0).length();
        ArrayList<String> shortestWords = new ArrayList<>();
        shortestWords.add(allwords.get(0));

        for (int i = 1; i < allwords.size(); i++) {
            String w = allwords.get(i);
            if (w.length() < minLen) {
                minLen = w.length();
                shortestWords.clear();
                shortestWords.add(w);
            }
            else if (w.length() == minLen) {
                if (!shortestWords.contains(w)) {
                    shortestWords.add(w);
                }
            }
        }
        String output = "Shortest word length: " + minLen + "\n";
        output += "Shortest word(s):\n";
        for (String sw : shortestWords) {
            output += sw + "\n";
        }
        resultT.setText(output);
    }

    public static void longest() {
        if (allwords.isEmpty()) {
            resultT.setText("No words to analyze.");
            return;
        }
        int maxLen = allwords.get(0).length();
        ArrayList<String> longestWords = new ArrayList<>();
        longestWords.add(allwords.get(0));

        for (int i = 1; i < allwords.size(); i++) {
            String w = allwords.get(i);
            if (w.length() > maxLen) {
                maxLen = w.length();
                longestWords.clear();
                longestWords.add(w);
            }
            else if (w.length() == maxLen) {
                if (!longestWords.contains(w)) {
                    longestWords.add(w);
                }
            }
        }
        String output = "Longest word length: " + maxLen + "\n";
        output += "Longest word(s):\n";
        for (String lw : longestWords) {
            output += lw + "\n";
        }
        resultT.setText(output);
    }

    public static void mostCommonWord() {
        if (allwords.isEmpty()) {
            resultT.setText("No words to analyze.");
            return;
        }
        // Build frequency map
        HashMap<String, Integer> freq = new HashMap<>();
        for (String w : allwords) {
            freq.put(w, freq.getOrDefault(w, 0) + 1);
        }
        // Find max frequency
        int maxFreq = 0;
        for (int val : freq.values()) {
            if (val > maxFreq) {
                maxFreq = val;
            }
        }
        // Gather all words with freq == maxFreq
        ArrayList<String> mostCommon = new ArrayList<>();
        for (Map.Entry<String, Integer> e : freq.entrySet()) {
            if (e.getValue() == maxFreq) {
                mostCommon.add(e.getKey());
            }
        }
        String output = "Most common word frequency: " + maxFreq + "\n";
        output += "Word(s):\n";
        for (String mc : mostCommon) {
            output += mc + "\n";
        }
        resultT.setText(output);
    }

    public static void leastCommonWord() {
        if (allwords.isEmpty()) {
            resultT.setText("No words to analyze.");
            return;
        }
        // Build frequency map
        HashMap<String, Integer> freq = new HashMap<>();
        for (String w : allwords) {
            freq.put(w, freq.getOrDefault(w, 0) + 1);
        }
        // Find min frequency
        int minFreq = Integer.MAX_VALUE;
        for (int val : freq.values()) {
            if (val < minFreq) {
                minFreq = val;
            }
        }
        // Gather all words with freq == minFreq
        ArrayList<String> leastCommon = new ArrayList<>();
        for (Map.Entry<String, Integer> e : freq.entrySet()) {
            if (e.getValue() == minFreq) {
                leastCommon.add(e.getKey());
            }
        }
        String output = "Least common word frequency: " + minFreq + "\n";
        output += "Word(s):\n";
        for (String lc : leastCommon) {
            output += lc + "\n";
        }
        resultT.setText(output);
    }
 
    private static int countVowels(String w) {
        int count = 0;
        for (int i = 0; i < w.length(); i++) {
            char c = w.charAt(i);
            if ("aeiou".indexOf(c) >= 0) {
                count++;
            }
        }
        return count;
    }

    public static void mostVowels() {
        if (allwords.isEmpty()) {
            resultT.setText("No words to analyze.");
            return;
        }
        int maxVowelCount = 0;
        ArrayList<String> wordsWithMax = new ArrayList<>();

        for (String w : allwords) {
            int vCount = countVowels(w);
            if (vCount > maxVowelCount) {
                maxVowelCount = vCount;
                wordsWithMax.clear();
                wordsWithMax.add(w);
            }
            else if (vCount == maxVowelCount) {
                if (!wordsWithMax.contains(w)) {
                    wordsWithMax.add(w);
                }
            }
        }
        String output = "Max number of vowels in a single word: " + maxVowelCount + "\n";
        output += "Word(s) with that many vowels:\n";
        for (String mw : wordsWithMax) {
            output += mw + "\n";
        }
        resultT.setText(output);
    }

    public static void leastVowels() {
        if (allwords.isEmpty()) {
            resultT.setText("No words to analyze.");
            return;
        }
        int minVowelCount = countVowels(allwords.get(0));
        ArrayList<String> wordsWithMin = new ArrayList<>();
        wordsWithMin.add(allwords.get(0));

        for (int i = 1; i < allwords.size(); i++) {
            String w = allwords.get(i);
            int vCount = countVowels(w);
            if (vCount < minVowelCount) {
                minVowelCount = vCount;
                wordsWithMin.clear();
                wordsWithMin.add(w);
            }
            else if (vCount == minVowelCount) {
                if (!wordsWithMin.contains(w)) {
                    wordsWithMin.add(w);
                }
            }
        }
        String output = "Min number of vowels in a single word: " + minVowelCount + "\n";
        output += "Word(s) with that many vowels:\n";
        for (String lw : wordsWithMin) {
            output += lw + "\n";
        }
        resultT.setText(output);
    }
   
    private static ArrayList<ArrayList<String>> parseSentences() {
        ArrayList<ArrayList<String>> sentences = new ArrayList<>();
        ArrayList<String> currentSentence = new ArrayList<>();

        for (String token : textTokens) {
            currentSentence.add(token);
            String lower = token.toLowerCase();
            if (lower.endsWith(".") || lower.endsWith("?") || lower.endsWith("!")) {
                sentences.add(currentSentence);
                currentSentence = new ArrayList<>();
            }
        }
       
        if (!currentSentence.isEmpty()) {
            sentences.add(currentSentence);
        }
        return sentences;
    }

    public static void avgSentenceLength() {
        if (textTokens.isEmpty()) {
            resultT.setText("No tokens to analyze.");
            return;
        }
        ArrayList<ArrayList<String>> sentences = parseSentences();
        if (sentences.isEmpty()) {
            resultT.setText("No sentences found.");
            return;
        }

        int totalWords = 0;
        for (ArrayList<String> sentence : sentences) {
            totalWords += sentence.size();
        }
        double avg = (double) totalWords / sentences.size();
        avg = round(avg, 2);

        String msg = "Number of sentences: " + sentences.size() + "\n"
                   + "Total words across all sentences: " + totalWords + "\n"
                   + "Average sentence length: " + avg + " words.";
        resultT.setText(msg);
    }

    public static void mostUniqueSentence() {
        if (textTokens.isEmpty()) {
            resultT.setText("No tokens to analyze.");
            return;
        }
        ArrayList<ArrayList<String>> sentences = parseSentences();
        if (sentences.isEmpty()) {
            resultT.setText("No sentences found.");
            return;
        }

        int maxUnique = 0;
        ArrayList<ArrayList<String>> winners = new ArrayList<>();

        for (ArrayList<String> sentence : sentences) {
            HashSet<String> uniqueSet = new HashSet<>();
            for (String raw : sentence) {
                String cleaned = raw.replaceAll("[\\p{P}_]", "").toLowerCase();
                if (!cleaned.isEmpty()) {
                    uniqueSet.add(cleaned);
                }
            }
            int distinctCount = uniqueSet.size();
            if (distinctCount > maxUnique) {
                maxUnique = distinctCount;
                winners.clear();
                winners.add(sentence);
            }
            else if (distinctCount == maxUnique) {
                winners.add(sentence);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Max unique words in a sentence: ").append(maxUnique).append("\n\n");
        int count = 1;
        for (ArrayList<String> winningSentence : winners) {
            sb.append("Sentence ").append(count++).append(": ");
            for (String w : winningSentence) {
                sb.append(w).append(" ");
            }
            sb.append("\n\n");
        }
        resultT.setText(sb.toString());
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if ("READ".equals(command)) {
                readFile();
            }
            else if ("AVG".equals(command)) {
                parseWords();
                showAvg();
            }
            else if ("WRITE".equals(command)) {
                writeFile();
            }
            else if ("brianR".equals(command)) {
                
                readFileGutenberg();
                parseWords();
            }
            else if ("SHORTEST".equals(command)) {
                parseWords();
                shortest();
            }
            else if ("LONGEST".equals(command)) {
                parseWords();
                longest();
            }
            else if ("MOST_COMMON".equals(command)) {
                parseWords();
                mostCommonWord();
            }
            else if ("LEAST_COMMON".equals(command)) {
                parseWords();
                leastCommonWord();
            }
            else if ("MOST_VOWELS".equals(command)) {
                parseWords();
                mostVowels();
            }
            else if ("LEAST_VOWELS".equals(command)) {
                parseWords();
                leastVowels();
            }
            else if ("AVG_SENTENCE".equals(command)) {
                avgSentenceLength();
            }
            else if ("UNIQUE_SENTENCE".equals(command)) {
                mostUniqueSentence();
            }
        }
    }
}


// citation:

/* 

Oracle’s Official Java Tutorials:
https://docs.oracle.com/javase/tutorial/

Stack Overflow:
https://stackoverflow.com/

GeeksforGeeks (Java Tutorials and Examples):
https://www.geeksforgeeks.org/

TutorialsPoint (Java Tutorial):
https://www.tutorialspoint.com/java/index.htm

Javatpoint (Java Tutorial):
https://www.javatpoint.com/java-tutorial

W3Schools Java Tutorial:
https://www.w3schools.com/java/
https://www.w3schools.com/java/java_hashmap.asp

GitHub (Open Source Code & Projects):
https://github.com/

Java Code Geeks:
https://www.javacodegeeks.com/

Reddit – r/java:
https://www.reddit.com/r/java/

https://www.geeksforgeeks.org/arraylist-sublist-method-in-java-with-examples/

https://stackoverflow.com/questions/27110563/how-can-i-check-if-a-string-has-a-substring-from-a-list

*/
