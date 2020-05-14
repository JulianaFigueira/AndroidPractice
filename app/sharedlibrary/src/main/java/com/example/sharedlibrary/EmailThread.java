package com.example.sharedlibrary;

import java.util.HashSet;

public class EmailThread {

    public static class EmailMesage {
        public String message;
        private EmailMesage nextMsg = null;

        public EmailMesage(String msg) {
            message = msg;
        }

        public EmailMesage getNextMsg() {
            return nextMsg;
        }
    }

    private EmailMesage head = null;
    private int count;

    public int getCount() { return count; }

    public EmailMesage peekMessage() {
        return  head;
    }

    public void pushMessage(EmailMesage em) {
        if(em != null) {
            if (head == null) {
                head = em;
                count = countTail(em);
            } else {
                em.nextMsg = head;
                head = em;
                count++;
            }
        }
    }

    private void removeMessage (EmailMesage prevMsg, EmailMesage discardedMsg) {
        prevMsg.nextMsg = discardedMsg.nextMsg;
        count--;
    }

    public static int countTail(EmailMesage head) {
        EmailMesage h = head;
        int i = 0;
        while (h != null) {
            i++;
            h = h.nextMsg;
        }
        return i;
    }

    /* 
    * Remove duplicates on email thread:  time O(n) w/ access to hash table being constant O(1),
    *                                       space O(n) 'cause of hash table
    * Input
    *   email thread : singly unsorted linked list of messages
    * Output
    *   remove duplicated messages from input. 
    *   return new lenght of linked list for testing purposes
    * */
    public static int RemoveDuplicatedMessages(EmailThread emails) {
        HashSet<String> emSet = new HashSet<String>();
        EmailMesage head = emails.head;
        EmailMesage prev = null;

        while (head != null) {
            if (emSet.contains(head.message)) {
                emails.removeMessage(prev, head);
            } else {
                emSet.add(head.message);
                // new last item add to set
                prev = head;
            }

            head = head.nextMsg;
        }

        return emSet.size();
    }

    /*
    *  Linked List Intersection: time O(n), space O(1)
    * Input   
    *   two singly linked lists
    * Output
    *   return the intersecting node between the two lists (if exists). 
    *   + the intersection is defined by reference, not value
    * Ju's assumption: the lists have no loops (duplicate), and they join towards the end
    *
    *
    * */
    public static EmailMesage IntersectionMessage(EmailThread emails1, EmailThread emails2) {
        EmailMesage res = null;
        int diff = Math.abs(emails1.count-emails2.count);

        EmailMesage curr1 = emails1.head;
        EmailMesage curr2 = emails2.head;

        //even starting point
        if(emails1.count > emails2.count)
        {
            curr1 = traverseNodes(curr1, diff);
        } else {
            curr2 = traverseNodes(curr2, diff);
        }

        //symmetric traverse until the equal node aka intersection
        while (curr1 != null && curr2 != null) {
            if (curr1 == curr2) {
                return curr1;
            }
            curr1 = curr1.nextMsg;
            curr2 = curr2.nextMsg;
        }

        return res;
    }

    private static EmailMesage traverseNodes(EmailMesage emails1, int diff) {
        EmailMesage resp = emails1;
        for(int i = 0; i < diff && resp != null; i++) {
            resp = resp.nextMsg;
        }
        return resp;
    }
}
