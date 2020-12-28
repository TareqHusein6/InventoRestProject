package InvetoRestProject.classes;

import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JOptionPane;

//Send email class , used for sending mails(ordes) to providers
public class SendEmail {
	public static void main(String[] args) {
		 final String restaurantName= args[1];
		 String providerEmail=args[0];
		 final String fileName= args[2];
		// TODO Auto-generated method stub
		 
		// Recipient's email ID needs to be mentioned.
	      String to = providerEmail;//email for provider

	      // Sender's email ID needs to be mentioned
	      String email = "inventorest2019@gmail.com";
	      String password="IR2019project";

	      // Get the Session object.
	      Properties properties = System.getProperties();
	      properties.put("mail.smtp.host", "smtp.gmail.com"); 
	      properties.put("mail.smtp.socketFactory.port", "465"); 
	      properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	      properties.put("mail.smtp.auth", "true"); 
	      properties.put("mail.smtp.port", "465"); 

	      
	      Session session = Session.getDefaultInstance(properties,
	    		  new javax.mail.Authenticator() {   
	            protected PasswordAuthentication getPasswordAuthentication() {   
	            	//checks if username and password is ok
	                return new PasswordAuthentication(email,password);    
	                }
	            });

	      try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(email));

	         // Set To: header field of the header.
	         message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));

	         // Set Subject: header field
	         message.setSubject(restaurantName+"'s Order");//name of the restaurant plus order

	         // Create the body part 
	         BodyPart messageBodyPart1 = new MimeBodyPart();
	         // Fill the message
	         messageBodyPart1.setText(" Hi,\n I would like this order as fast as you can \n regards,\n"
	         		+restaurantName+ "'s manager");
	         
	         
	         //create new MimeBodyPart object and set DataHandler object to this object  
	         MimeBodyPart messageBodyPart2 = new MimeBodyPart();
	         DataSource source = new FileDataSource(fileName);  
	         messageBodyPart2.setDataHandler(new DataHandler(source));  
	         messageBodyPart2.setFileName(fileName);  
	         
	         // Create a multi part object
	         Multipart multipart = new MimeMultipart();
	         // Set text message part
	         multipart.addBodyPart(messageBodyPart1);
	         multipart.addBodyPart(messageBodyPart2);      

	         // set the multipart object to the message object  
	         message.setContent(multipart);

	         // Send message
	         Transport.send(message);
	         
	         showMessage("Order sent succesfully","Success");
	         
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	         showMessage("Order wasn't sent ","Error");
	      }
	}
	//function for showing messages
	public static void showMessage(String infoMessage,String titleBar)
	{
		JOptionPane.showMessageDialog(null,infoMessage,titleBar,JOptionPane.INFORMATION_MESSAGE);
	}

}
