package service.jms;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@LocalBean
public class MessageSenderBean {

	private static final Logger LOG = LoggerFactory
			.getLogger(MessageSenderBean.class);

	@Resource(mappedName = "java:/JmsXA")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "java:/MyQueue")
	private Queue queue;

	public void sendMessage(final Serializable message) {
		final MessageProducer messageProducer;
		final ObjectMessage objectMessage;
		try {
			final Connection connection = connectionFactory.createConnection();
			final Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			messageProducer = session.createProducer(queue);
			objectMessage = session.createObjectMessage();
			// textMessage.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);

			objectMessage.setObject(message);
			messageProducer.send(objectMessage);

			messageProducer.close();
			session.close();
			connection.close();
		} catch (final JMSException e) {
			LOG.error(e.getLocalizedMessage(), e);
		}

	}
}
