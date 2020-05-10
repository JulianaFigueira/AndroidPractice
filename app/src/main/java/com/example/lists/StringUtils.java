package com.example.lists;

public class StringUtils {

    //complexity in time  and space

    /*
    * Replacing characters in place:
    * Input
    *       Array of characters
    * Output
    *       Replace all the spaces with “&32”
    *       Perform this  operation in place with no other auxiliary structure
    * Affordances
    *       The array has sufficient slots at the end to hold the additional characters
    *       The length of the array is "true" 
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
    *   Two strings
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
}
