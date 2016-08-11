package test.com.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static com.properties.Emailer.*;
import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

@RunWith(MockitoJUnitRunner.class)
public class EmailerTest {
  private JavaMailSenderImpl emailSender;

  private GreenMail testSmtp;

  @Before
  public void testSmtpInit() {
    testSmtp = new GreenMail(ServerSetupTest.SMTP);
    testSmtp.start();
    emailSender = new JavaMailSenderImpl();

    // don't forget to set the test port!
    emailSender.setPort(port);
    emailSender.setHost(localhost);

    testSmtp.setUser(to, "123");
  }



  @Test
  public void testSendAndVerifyingEmail()
      throws InterruptedException, MessagingException, IOException {
    SimpleMailMessage mail = new SimpleMailMessage();

    mail.setFrom(from);
    mail.setTo(to);
    mail.setSubject(subject);
    mail.setText(message);
    assertNotNull(emailSender.getPort());

    emailSender.send(mail);

    // checking mail has sent or not through subject
    Message[] messages = testSmtp.getReceivedMessages();
    assertNotNull(testSmtp.getReceivedMessages());
    assertEquals("test subject", messages[0].getSubject());

  }

  @Test
  public void zzz() {
      
  }


  @After
  public void cleanup() {
    testSmtp.stop();
  }
}
