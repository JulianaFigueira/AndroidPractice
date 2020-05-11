package com.example.lists;

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

    private EmailMesage head;

    public EmailMesage peekMessage() {
        return  head;
    }

    public void pushMessage(EmailMesage em) {
        if(em != null) {
            em.nextMsg = head;
            head = em;
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
    *   return new lenght of linked list
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
}
