package com.example.lists;

import org.junit.Test;

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

        StringUtils.ReplaceSpace(testStr, 19);

        assertArrayEquals(testStr, corrStr);
    }

    @Test
    public void CheckJumbledLetter_Test2() {
        assertTrue(StringUtils.CheckJumbledLetter("you", "yuo"));
        assertTrue(StringUtils.CheckJumbledLetter("probably", "porbalby"));
        assertTrue(StringUtils.CheckJumbledLetter("despite", "desptie"));
        assertFalse(StringUtils.CheckJumbledLetter("moon", "nmoo"));
        assertFalse(StringUtils.CheckJumbledLetter("misspellings", "mpeissngslli"));
    }

    @Test
    public void CheckTypos_Test3() {
        assertTrue(StringUtils.CheckTypos("pale", "ple"));
        assertTrue(StringUtils.CheckTypos("pales", "pale"));
        assertTrue(StringUtils.CheckTypos("pale", "bale"));
        assertFalse(StringUtils.CheckTypos("pale", "bake"));
    }
}