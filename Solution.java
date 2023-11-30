import java.util.ArrayList;
import java.util.HashMap;

public class Solution {

    private ArrayList<String> availableWords;

    private char[] currentTemplate;

    private String target;

    public Solution(int length, ArrayList<String> dictionary) {
        // all init to '_' with length
        StringBuilder template = new StringBuilder();
        for (int i = 0; i < length; i++) {
            template.append("_");
        }
        ArrayList<String> initialStrings = new ArrayList<String>();
        // create families
        for (String s : dictionary) {
            if (s.length() == length) {
                initialStrings.add(s);
            }
        }
        availableWords = initialStrings;
        currentTemplate = template.toString().toCharArray();
    }

    public boolean isSolved() {
        return target != null;
    }

    public void printProgress() {
        for (char c : currentTemplate) {
            System.out.print(c + " ");
        }
        System.out.println();
    }
    // for test purpose this class is public, actually it should be private.
    public HashMap<String, ArrayList<String>> constructFamiliesMap(char guess) {
        HashMap<String, ArrayList<String>> familiesMap = new HashMap<String, ArrayList<String>>();
        for (String s : availableWords) {
            char[] template = currentTemplate.clone();
            // create new templateString for each word
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == guess) {
                    template[i] = guess;
                }
            }
            // add it to the according family
            String templateString = new String(template);
            if (familiesMap.containsKey(templateString)) {
                familiesMap.get(templateString).add(s);
            } else {
                ArrayList<String> newFamily = new ArrayList<String>();
                newFamily.add(s);
                familiesMap.put(templateString, newFamily);
            }
        }
        return familiesMap;
    }

    public Boolean updateUsingMap(HashMap<String, ArrayList<String>> familiesMap) {
        int maxFamilySize = 0;
        String maxFamilyKey = "";
        for (String key : familiesMap.keySet()) {
            if (familiesMap.get(key).size() > maxFamilySize) {
                maxFamilySize = familiesMap.get(key).size();
                maxFamilyKey = key;
            }
            // It's a corner case, where there are multiple families with the same size,
            // and we want to keep the one that is same to current template.
            if (familiesMap.get(key).size() == maxFamilySize && key.equals(new String(currentTemplate)))
                maxFamilyKey = key;
        }

        availableWords = familiesMap.get(maxFamilyKey);
        currentTemplate = maxFamilyKey.toCharArray();
        // if all the words in current template are not '_', then we find the target.
        if (!maxFamilyKey.contains("_")) {
            target = maxFamilyKey;
            return true;
        }
        return false;
    }

    public boolean addGuess(char guess) {
        HashMap<String, ArrayList<String>> map = constructFamiliesMap(guess);
        // find the family with the largest size
        return updateUsingMap(map);
    }

    public String getTarget() {
        return target;
    }

    public String getCurrentTemplate() {
        return new String(currentTemplate);
    }
}
