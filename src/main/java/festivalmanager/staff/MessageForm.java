package festivalmanager.staff;

import javax.validation.constraints.NotEmpty;

/**
 * this is a form for creating a new {@link Message}
 */
public class MessageForm  {

	@NotEmpty
	private String sender;


	private String receiver;

	@NotEmpty
	private String message;

	private String role;

	/**
	 * this provides the form with the necessary data
	 * @param sender the sender
	 * @param receiver the receiver
	 * @param role the role for messages who are supposed to go to every person who belongs to one specific {@link org.salespointframework.useraccount.Role}
	 * @param message the text literal
	 */
	public MessageForm(String sender, String receiver, String role, String message){
		this.message = message;
		this.sender = sender;
		this.receiver = receiver;
		this.role = role;
	}

	public String getMessage() {
		return message;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getSender() {
		return sender;
	}

	public String getRole() {
		return role;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}


	public void setRole(String role) {
		this.role = role;
	}
}
