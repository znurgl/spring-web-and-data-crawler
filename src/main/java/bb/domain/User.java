package bb.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({ @NamedQuery(name = "findByLogin", query = "select u from User u where u.login = :login") })
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@Column(unique = true)
	private String login;
	private String password;
	private String name;
	private Boolean valid;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	@OneToMany
	@JoinColumn(name = "user_id")
	private List<Message> messages;

	@ManyToOne(fetch = FetchType.EAGER)
	private Company company;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "User_Role")
	private List<Role> roles;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return name;
	}

	public void setFirstName(String firstName) {
		this.name = firstName;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password
				+ ", name=" + name + "]";
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company companie) {
		this.company = companie;
	}

}
