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

    public EmailMesage peekMessage() {
        return  head;
    }

    public void pushMessage(EmailMesage em) {
        if(em != null) {
            if (head == null) {
                head = em;
            } else {
                em.nextMsg = head;
                head = em;
            }
        }
    }

    private void removeMessage (EmailMesage prevMsg, EmailMesage discardedMsg) {
        prevMsg.nextMsg = discardedMsg.nextMsg;
    }

    /* 
    * Remove duplicates on email thread
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
    *  Linked List Intersection
    * Input   
    *   two singly linked lists
    * Output
    *   return the intersecting node between the two lists (if exists). 
    *   + the intersection is defined by reference, not value
    * Ju's assumption: the lists have no loops (duplicate), and they join towards the end
    * */
    public static EmailMesage IntersectionMessage(EmailThread emails1, EmailThread emails2) {
        EmailMesage res = null;

        HashSet<EmailMesage> emSet = new HashSet<EmailMesage>();
        EmailMesage head = emails1.head;

        //Store first list
        while (head != null) {
            emSet.add(head);
            head = head.nextMsg;
        }

        //find duplicate on second list
        head = emails2.head;

        while (head != null) {
            if (emSet.contains(head)) { //duplicate aka intersection
                res = head;
                break;
            } else {
                emSet.add(head);
            }
            head = head.nextMsg;
        }

        return res;
    }
}
