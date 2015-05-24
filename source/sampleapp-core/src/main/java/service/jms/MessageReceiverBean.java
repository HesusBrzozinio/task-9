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

public class MessageReceiverBean {

	private static final Logger LOG = LoggerFactory
			.getLogger(MessageReceiverBean.class);

}
