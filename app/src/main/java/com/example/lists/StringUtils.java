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

                for (int k = lastNotSpace -1; k > i; k--) {
                    input[k + spaces] = input[k];
                }
                lastNotSpace += spaces;

                input[i] = '&';
                input[i + 1] = '3';
                input[i + 2] = '2';
            }
        }
    }
}
