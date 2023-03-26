import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class AmazonPriceAlert {

    public static void main(String[] args) throws Exception {
        String url = "https://www.amazon.com/dp/B07S58MHXY";
        Document doc = Jsoup.connect(url).get();
        Element titleElement = doc.select("#productTitle").first();
        String title = titleElement.text().trim();
        System.out.println(title);

        double price = 199.99; // assume this is the current price
        double BUY_PRICE = 200;

        if (price < BUY_PRICE) {
            String message = title + " is now " + price;

            String from = "YOUR_EMAIL";
            String password = "YOUR_PASSWORD";
            String to = "YOUR_EMAIL";

            Properties props = new Properties();
            props.put("mail.smtp.host", "YOUR_SMTP_ADDRESS");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject("Amazon Price Alert!");
            msg.setText(message + "\n" + url);

            Transport.send(msg);
        }
    }
}