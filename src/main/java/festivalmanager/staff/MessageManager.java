package festivalmanager.staff;


import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * this class is a implementation of logic regarding the {@link Message}s
 */
@Service
public class MessageManager {

	@Autowired
	private final MessageRepository messages;
	private static final Logger LOG = LoggerFactory.getLogger(MessageManager.class);

	/**
	 * this initiates the MessageManager with a {@link MessageRepository}
	 * @param messages MessageRepository containing all messages
	 */
	public MessageManager(MessageRepository messages){
		Assert.notNull(messages, "MessageRepository must not be null");
		this.messages = messages;}


	/**
	 * this created a new {@link Message}
	 * @param event the {@link MessageEvent} modeling the message
	 * @return return the Message with the data of the Event
	 */
	public Message createNewMessage(MessageEvent event){
		Message message = new Message(event.getSender(), event.getReceiver(), event.getMessage());
		return messages.save(message);
	}

	/**
	 * this method checks to see if an {@link Account} has any received {@link Message}s
	 * @param account the account whose messages are to be checked
	 * @return returns a boolean true if there are more than zero messages
	 */
	public boolean anyMessages(Account account){
		Streamable<Message> allReceived = findAllByReceiver(account);
		if(allReceived.isEmpty()){
			return false;
		}
		return true;
	}

	/**
	 * this method checks if an {@link Account} has any sent {@link Message}s
	 * @param account the Account to be checked
	 * @return returns a boolean, true if there more than zero sent messages
	 */
	public boolean anySentMessages(Account account){
		Streamable<Message> allSent = findAllBySender(account);
		if(allSent.isEmpty()){
			return false;
		}
		return true;
	}

	/**
	 * this methods finds all {@link Message}s sent by a specified {@link Account}
	 * @param sender the account whose sent messages are to be found
	 * @return returns a Streamable of all sent Messages
	 */
	public Streamable<Message> findAllBySender(Account sender) {

		LOG.info("searching sent messages of " + sender.getUserAccount().getUsername());
		return messages.findAllBySender(sender);
	}

	/**
	 * this method finds all received {@link Message}s for an specifeid {@link Account}
	 * @param receiver the Account whose received messages are to be found
	 * @return returns a Streamable with all received Messages for this account
	 */
	public Streamable<Message> findAllByReceiver(Account receiver) {
		LOG.info("searching received messages of " + receiver.getUserAccount().getUsername());
		Streamable<Message> messagesByReceiver = messages.findAllByReceiver(receiver);
		for(Message message : messagesByReceiver){
			LOG.info(message.getMessage());
		}

		return messagesByReceiver;
	}

	/**
	 * this method finds all existing {@link Message}s
	 * @return returns a Streamable with all messages
	 */
	public Streamable<Message> findAll(){

		return messages.findAll();
	}

	/**
	 * @deprecated version of {@link #findAllByReceiver(Account)}
	 * @param receiver
	 * @return
	 */
	public Streamable<Message> findByReceiver(Account receiver){return messages.findAllByReceiver(receiver);}

	/**
	 * @deprecated version of {@link #findAllBySender(Account)}
	 * @param sender
	 * @return
	 */
	public Streamable<Message> findBySender(Account sender){return messages.findAllBySender(sender);}

}
