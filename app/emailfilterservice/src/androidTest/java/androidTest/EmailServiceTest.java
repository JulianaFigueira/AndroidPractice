package androidTest;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import androidx.test.annotation.UiThreadTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ServiceTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.emailfilterservice.EmailService;
import com.example.sharedlibrary.EmailThread;
import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

@RunWith(AndroidJUnit4.class)
public class EmailServiceTest {

    @Rule
    public final ServiceTestRule serviceRule = new ServiceTestRule();
    EmailThread emailThread;
    boolean received = false;
    IBinder service;

    @Before
    public void PrepareService() throws TimeoutException {
        final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Intent serviceIntent = new Intent (context, EmailService.class);
        serviceRule.startService(serviceIntent);
        service = serviceRule.bindService(serviceIntent);

    }

    public EmailThread getMessage() {
        // Data can be passed to the service via the Intent.
        EmailThread emails = new EmailThread();
        emails.pushMessage(new EmailThread.EmailMesage("1"));
        emails.pushMessage(new EmailThread.EmailMesage("2"));
        emails.pushMessage(new EmailThread.EmailMesage("3"));
        emails.pushMessage(new EmailThread.EmailMesage("1"));
        emails.pushMessage(new EmailThread.EmailMesage("2"));
        emails.pushMessage(new EmailThread.EmailMesage("1"));

        return emails;
    }

    @Test @UiThreadTest
    public void EmailService_Test7() throws TimeoutException, InterruptedException, RemoteException {
        // can create and start service
        Assert.assertTrue(service.isBinderAlive());

        //won't test message exchange, but can test in/out "tracer bullets"
        EmailService.IncomingHandler request = new EmailService.IncomingHandler();
        EmailService.ResponseHandler response = new EmailService.ResponseHandler();

        request.handleMessage(Message.obtain(response, 0, new Gson().toJson(getMessage())));
        Assert.assertTrue(request.count == 1);
        Assert.assertTrue(request.getEmails() != null);
        Assert.assertTrue(request.getEmails().getCount() == 6);

        response.handleMessage(Message.obtain(null, 0, new Gson().toJson(getMessage())));
        Assert.assertTrue(response.getEmails() != null);
        Assert.assertTrue(response.getEmails().getCount() == 6);

        //real action test
        EmailService.CleaningTask task = new EmailService.CleaningTask(getMessage(), new Messenger(response));
        Assert.assertTrue(task.getEmails() != null);

        task.run();
        Assert.assertTrue(task.getEmails().getCount() == 3);

        EmailThread.EmailMesage head = task.getEmails().peekMessage();
        Assert.assertTrue(head.message.equals("1"));

        head = head.getNextMsg();
        Assert.assertTrue(head.message.equals("2"));

        head = head.getNextMsg();
        Assert.assertTrue(head.message.equals("3"));
    }
}
