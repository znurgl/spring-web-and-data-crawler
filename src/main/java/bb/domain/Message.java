package bb.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Message implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	private String messageFrom;
	
	private String subject;
	
	private String body;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	private Boolean isRead;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	

}
