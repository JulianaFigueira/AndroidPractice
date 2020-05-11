package com.example.lists;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void ReplaceSpace_Test1() {
        char[] testStr = "User is not allowed      ".toCharArray();
        char[] corrStr = "User&32is&32not&32allowed".toCharArray();

        StringListFilter.ReplaceSpace(testStr, 19);

        assertArrayEquals(testStr, corrStr);
    }

    @Test
    public void CheckJumbledLetter_Test2() {
        assertTrue(StringListFilter.CheckJumbledLetter("you", "yuo"));
        assertTrue(StringListFilter.CheckJumbledLetter("probably", "porbalby"));
        assertTrue(StringListFilter.CheckJumbledLetter("despite", "desptie"));
        assertFalse(StringListFilter.CheckJumbledLetter("moon", "nmoo"));
        assertFalse(StringListFilter.CheckJumbledLetter("misspellings", "mpeissngslli"));
    }

    @Test
    public void HasTypos_Test3() {
        assertTrue(StringListFilter.HasTypos("pale", "ple"));
        assertTrue(StringListFilter.HasTypos("pales", "pale"));
        assertTrue(StringListFilter.HasTypos("pale", "bale"));
        assertFalse(StringListFilter.HasTypos("pale", "bake"));
    }

    @Test
    public void FilterWordList_Test4() {
        ArrayList<String> resultList = new ArrayList<String>();

        StringListFilter.ApplyFilterToList ("ple", resultList);
        assertTrue(resultList.size() == 1);
        assertTrue(((String)resultList.get(0)).equals("pale"));

        StringListFilter.ApplyFilterToList ("desptie", resultList);
        assertTrue(resultList.size() == 1);
        assertTrue(((String)resultList.get(0)).equals("despite"));

    }
}