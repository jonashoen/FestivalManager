package festivalmanager.staff;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Message {

	@Id	@GeneratedValue
	private  long id;

	@OneToOne
	private Account sender;

	@OneToOne
	private Account receiver;
	private String message;
	private MessageState state;

	public Message(Account sender, Account receiver, String message){
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
		this.state = MessageState.SENT;

	}

	public Message(){};

	public String getMessage() {
		return message;
	}

	public Account getReceiver() {
		return receiver;
	}

	public Account getSender() {
		return sender;
	}

	public void setState(MessageState state) {
		this.state = state;
	}
}
