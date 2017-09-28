package it.consoftinformatica.corsoHibernate.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/* 
Every persistent entity class must have at least the <code>@Entity</code> annotation.
Hibernate maps this class to a table called <code>MESSAGE</code>.
*/
@Entity
public class Message {

	@Id
	@GeneratedValue
	private Long id;

	private String text;
	private Message nextMessage;

	Message() {
	}

	public Message(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Message getNextMessage() {
		return nextMessage;
	}

	public void setNextMessage(Message nextMessage) {
		this.nextMessage = nextMessage;
	}
}
