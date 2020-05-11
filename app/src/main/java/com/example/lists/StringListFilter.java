package com.example.lists;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.Arrays;

public class StringListFilter extends Filter{

    private static String[] WordList = { "you", "probably", "despite", "moon", "misspellings", "pale",
                                "pales", "bake"};

    public ArrayList<String> FilteredWordList;

    public StringListFilter() {
        FilteredWordList = new ArrayList(Arrays.asList(WordList));
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();

        if (constraint!=null && constraint.length()>0) {
            ArrayList<String> tempList = new ArrayList<String>();

            ApplyFilterToList(constraint, tempList);

            filterResults.count = tempList.size();
            filterResults.values = tempList;
        } else {
            filterResults.count = WordList.length;
            filterResults.values = new ArrayList(Arrays.asList(WordList));
        }

        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        FilteredWordList = (ArrayList<String>) results.values;
    }

    //complexity in time  and space

    /*
    * Replacing characters in place:
    * Input
    *       Array of characters
    * Output
    *       Replace all the spaces with “&32”
    *       Perform this  operation in place with no other auxiliary structure
    *       + The array has sufficient slots at the end to hold the additional characters
    *       + The length of the array is "true" 
     * */
    public static void ReplaceSpace(char[] input, int lastNotSpace) {
        int spaces = 2;

        for (int i = 0; i < input.length; i++) {
            if(Character.isSpaceChar(input[i])) {
                //"push" next chars by two spaces
                for (int k = lastNotSpace -1; k > i; k--) {
                    input[k + spaces] = input[k];
                }
                //Update last non space character index
                lastNotSpace += spaces;

                //add the new chars
                input[i] = '&';
                input[i + 1] = '3';
                input[i + 2] = '2';
            }
        }
    }

    /*
    * Check words with jumbled letters:
    * Input
    *   Two strings: 'right word', 'input to check'
    * Output
    *   If one string is a partial­permutation of the other
    *       Consider a  partial­permutation only if:
    *           ­ The first letter hasn’t changed place 
    *           ­ If word has more than 3 letters, up to 2/3 of the letters have changed place
    * */
    public static boolean CheckJumbledLetter(String str1, String str2) {
        boolean res = false;
        int length = str1.length();

        if (str2.length() == length && str1.charAt(0) == str2.charAt(0)) {
            //same size and start w/ same letter
            int jumbledLetters = Math.floorDiv(length * 2 , 3);
            int k; //index of letter on the first string

            for (int i = 1; i < length; i++) {
                //check if the first string has the same letter of the second string, after the first
                k = str1.indexOf(str2.charAt(i),1);
                if(k == -1) {
                    //doesn't even have that letter
                    res = false;
                    break;
                } else if(k != i) {
                    //letter contained in the first string is probably in a diff place on the second
                    jumbledLetters--;
                }
            }

            if (jumbledLetters >= 0) {
                res = true;
            }
        }
        return res;
    }

    /*
    * Check word with typo:   
    * Input
    *   two strings: 'right word', 'input to check'
    * Output
    *   if they are one typo (or zero typos) away [?]
    *       There are three types of typos:
    *           - insert a character
    *           - remove a character
    *           - replace a character
    * Ju's assumption 1: a typo is a one letter difference
    * Ju's Assumption 2: if they have more than one different character
    *                       can't say if it's a typo, it could be another word maybe. . .
    * */
    public static boolean HasTypos(String str1, String str2) {
        boolean res = false;

        if (str1.equals(str2)) {
            res = false; //same word, has no typo
        } else {
            int length1 = str1.length();
            int length2 = str2.length();

            if (length1 ==  length2 + 1) {
                res = CountMissingLetters(str1, str2, length1, length2) == 1;
            } else if (length1 ==  length2 - 1) {
                res = CountMissingLetters(str2, str1, length2, length1) == 1;
            } else if (length1 == length2) {
                res = CountDifferentLetters(str1, str2, length1) == 1;
            }
        }
        return res;
    }

    private static int CountMissingLetters(String str1, String str2, int length1, int length2) {
        int missLetters = 0;
        char c1, c2;

        str2 += ' '; //extra space to not break the comparison

        //find missing letters
        for (int i = 0; i < length1; i++) {
            c2 = str2.charAt(i);
            c1 = str1.charAt(i);
            if(c1 != c2) {
                str2 = str2.substring(0, i) + c1 + str2.substring(i, length1 - 1);
                missLetters++;
            }
        }

        return missLetters;
    }

    private static int CountDifferentLetters(String str1, String str2, int length) {
        int diffLetters = 0;

        for (int i = 0; i < length; i++) {
            if(str1.charAt(i) != str2.charAt(i)) {
                diffLetters++;
            }
        }

        return diffLetters;
    }

    /*
     * Filter search term on word list:   
     * Input
     *   CharSequence - search term
     *   ArrayList<String> - to hold resulting list
     * Output
     *   The search returns a result even if word typed is jumbled  or has a typo, but not both 
     * */
    public static void ApplyFilterToList(CharSequence constraint, ArrayList<String> tempList) {
        String search = constraint.toString();
        tempList.clear();

        //filter
        for (String word : WordList) {
            if (word.equals(search) ||
                    (CheckJumbledLetter(word, search) ^ HasTypos(word, search))) {
                tempList.add(word);
            }
        }
    }
}
