/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.mail;

import atm.gob.ec.framework.config.AppConfig;
import atm.gob.ec.framework.exception.TechnicalException;
import atm.gob.ec.framework.interfaces.CryptoService;
import atm.gob.ec.framework.interfaces.MailService;
import atm.gob.ec.framework.security.AesCryptoService;

import java.io.File;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultMailService implements MailService {

    private static final Logger logger = LogManager.getLogger(DefaultMailService.class);
    private final AppConfig config;
    private static String secret = System.getProperty("atm.crypto.key");
    
    public DefaultMailService(AppConfig config) {
        this.config = config;
    }
    
    @Override
    public void send(String subject, String body, String attachment) {
        
        CryptoService crypto = new AesCryptoService(secret);

        try {

            Properties props = new Properties();

            props.put("mail.smtp.host", config.get("MAIL.HOST"));

            props.put("mail.smtp.port", config.get("MAIL.PORT"));

            props.put("mail.smtp.auth", "true");
            
            if ("587".equals(config.get("MAIL.PORT"))) 
                props.put("mail.smtp.starttls.enable", "true");
            else if ("465".equals(config.get("MAIL.PORT"))) 
                props.put("mail.smtp.ssl.enable", "true"); 
            
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(config.get("MAIL.FROM"), crypto.decrypt(config.get("MAIL.PASS")));
                }
            });
            
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(config.get("MAIL.FROM").replace(";", ",")));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(config.get("MAIL.TO").replace(";", ",")));
            
            if (config.get("MAIL.CC") != null && !config.get("MAIL.CC").trim().isEmpty())
                message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(config.get("MAIL.CC").replace(";", ",")));
            
            if (config.get("MAIL.BCC") != null && !config.get("MAIL.BCC").trim().isEmpty()) 
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(config.get("MAIL.BCC").replace(";", ",")));            
            
            message.setSubject(subject);

            //message.setText(body);
            
            MimeBodyPart bodyPart = new MimeBodyPart();

            bodyPart.setContent(body, "text/html; charset=UTF-8" );

            MimeMultipart multipart = new MimeMultipart();

            multipart.addBodyPart(bodyPart);

            /*
             * ADJUNTO OPCIONAL
             */
            if (attachment != null && !attachment.trim().isEmpty()) {

                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.setDataHandler(new DataHandler(new FileDataSource(attachment)));
                attachPart.setFileName(new File(attachment).getName());
                multipart.addBodyPart(attachPart);
            }

            message.setContent(multipart);
            message.setSentDate(new Date());
            Transport.send(message);

            logger.info("Correo enviado correctamente");

        } catch (Exception ex) {
            logger.error("Error enviando correo", ex);
            throw new TechnicalException("Error enviando correo", ex);
        }
    }
}

