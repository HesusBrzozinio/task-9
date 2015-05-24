package service.jms;

import java.util.Date;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.dto.Book;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "MyQueue") })
public class MessageReceiverBean implements MessageListener {

	private static final Logger LOG = LoggerFactory
			.getLogger(MessageReceiverBean.class);

	public MessageReceiverBean() {
	}

	@Override
	public void onMessage(final Message message) {
		try {
			if (message instanceof TextMessage) {
				LOG.info("Queue: I received a TextMessage at " + new Date());
				TextMessage msg = (TextMessage) message;
				LOG.info("Message is : " + msg.getText());

			} else if (message instanceof ObjectMessage) {
				LOG.info("Queue: I received an ObjectMessage at " + new Date());
				ObjectMessage msg = (ObjectMessage) message;
				final Book book = (Book) msg.getObject();
				LOG.info("Message is : {}", book);

			} else {
				LOG.error("Not a valid message for this Queue MDB");
			}

		} catch (final JMSException e) {
			LOG.error(e.getLocalizedMessage(), e);
		}
	}

}
