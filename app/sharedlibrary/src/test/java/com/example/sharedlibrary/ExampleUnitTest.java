package com.example.sharedlibrary;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void RemoveDuplicatedEmails_Test5() {
        EmailThread emails = new EmailThread();

        emails.pushMessage(new EmailThread.EmailMesage("1"));
        emails.pushMessage(new EmailThread.EmailMesage("2"));
        emails.pushMessage(new EmailThread.EmailMesage("1"));
        emails.pushMessage(new EmailThread.EmailMesage("3"));
        emails.pushMessage(new EmailThread.EmailMesage("2"));
        emails.pushMessage(new EmailThread.EmailMesage("1"));

        assertTrue(EmailThread.RemoveDuplicatedMessages(emails) == 3);

        EmailThread.EmailMesage head = emails.peekMessage();
        assertTrue(head.message.equals("1"));

        head = head.getNextMsg();
        assertTrue(head.message.equals("2"));

        head = head.getNextMsg();
        assertTrue(head.message.equals("3"));
    }

    @Test
    public void FindThreadsIntersection_Test7() {
        EmailThread.EmailMesage intersection;
        EmailThread emails1 = new EmailThread();
        emails1.pushMessage(new EmailThread.EmailMesage("A"));
        emails1.pushMessage(new EmailThread.EmailMesage("B"));
        emails1.pushMessage(new EmailThread.EmailMesage("J"));
        emails1.pushMessage(new EmailThread.EmailMesage("F"));
        emails1.pushMessage(new EmailThread.EmailMesage("D"));

        intersection = emails1.peekMessage().getNextMsg().getNextMsg(); //D -> F -> J

        EmailThread emails2 = new EmailThread();
        emails2.pushMessage(intersection);
        emails2.pushMessage(new EmailThread.EmailMesage("H"));
        emails2.pushMessage(new EmailThread.EmailMesage("E"));
        emails2.pushMessage(new EmailThread.EmailMesage("A"));
        emails2.pushMessage(new EmailThread.EmailMesage("C"));

        assertTrue(EmailThread.IntersectionMessage(emails1, emails2) == intersection);
    }
}