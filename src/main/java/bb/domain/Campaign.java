package bb.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({ @NamedQuery(name = "findAllByCompany", query = "select o from Campaign o left join o.keywords where o.company = :company") })
public class Campaign implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateFrom;
	@ManyToOne
	private Language language;
	@ManyToOne
	private Company company;

	@OneToMany
	@JoinColumn(name = "campaign_id")
	private List<Keyword> keywords;

	@OneToMany
	@JoinColumn(name = "excludedurls_id")
	private List<ExcludedUrl> excludedUrls;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	public List<ExcludedUrl> getExcludedUrls() {
		return excludedUrls;
	}

	public void setExcludedUrls(List<ExcludedUrl> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

	@Override
	public String toString() {
		return "Campaign [id=" + id + ", name=" + name + ", dateFrom="
				+ dateFrom + ", language=" + language + "]";
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
