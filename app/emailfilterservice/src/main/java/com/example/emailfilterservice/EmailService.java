package com.example.emailfilterservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.RestrictTo;

import com.example.sharedlibrary.EmailThread;
import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailService extends Service {

    public static class CleaningTask implements Runnable {
        private EmailThread emails;
        private Messenger client;

        public CleaningTask(EmailThread emailThread, Messenger replyTo) {
            emails = emailThread;
            client = replyTo;
        }

        @Override
        public void run() {
            EmailThread.RemoveDuplicatedMessages(emails);

            try {
                client.send(Message.obtain(null, 0, new Gson().toJson(emails)));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public EmailThread getEmails() { return emails; }
    }

    public static class IncomingHandler extends Handler {
        private EmailThread emails = null;
        public int count = 0;

        @Override
        public void handleMessage(Message msg) {
            emails = new Gson().fromJson(msg.obj.toString(), EmailThread.class);
            executor.submit(new CleaningTask(emails, msg.replyTo));
            count++;
        }

        public EmailThread getEmails() { return emails; }
    }

    public static class ResponseHandler extends Handler {
        private EmailThread emails = null;
        @Override
        public void handleMessage(Message msg) {
            emails = new Gson().fromJson(msg.obj.toString(), EmailThread.class);
        }

        public EmailThread getEmails() { return emails; }
    }

    final Messenger messenger = new Messenger(new IncomingHandler());
    private static ExecutorService executor;

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}