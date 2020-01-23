package festivalmanager.staff;

import org.springframework.context.ApplicationEvent;


/**
 * this class is a custom {@link ApplicationEvent} to send {@link Message}s
 */
public class MessageEvent extends ApplicationEvent {
	private Account sender;
	private Account receiver;
	private MessageState state;
	private String message;

	/**
	 * this contrucst the event with the necessary data
	 * @param source the source
	 * @param sender the sender
	 * @param receiver the receiver
	 * @param message the text literal
	 */
	public MessageEvent(Object source, Account sender, Account receiver, String message) {
		super(source);
		this.sender = sender;
		this. receiver = receiver;
		this.message = message;
	}

	public Account getSender() {
		return sender;
	}

	public Account getReceiver() {
		return receiver;
	}

	public MessageState getState() {
		return state;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public Object getSource() {
		return super.getSource();
	}
}
