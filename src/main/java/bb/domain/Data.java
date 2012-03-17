package bb.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the data database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Data.findBySourceId", query = "select u from Data u where u.sourceId = :sourceId") })
public class Data implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "TABLE_GEN", table = "SEQUENCE", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "SEQ_GEN", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	private String body;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date createDate;

	@ManyToMany()
    @JoinTable(name="Keyword_Data")
	private List<Keyword> keywords;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "original_date")
	private Date originalDate;

	private String sourceId;

	private String sourceUser;

	private String title;

	private String type;

	private String url;

	@ManyToOne
	private Source source;

	@OneToMany(mappedBy = "data")
	private List<MetaData> metadatas;

	@ManyToOne
	private SearchSession searchSession;

	public Data() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	public Date getOriginalDate() {
		return this.originalDate;
	}

	public void setOriginalDate(Date originalDate) {
		this.originalDate = originalDate;
	}

	public String getSourceId() {
		return this.sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceUser() {
		return this.sourceUser;
	}

	public void setSourceUser(String sourceUser) {
		this.sourceUser = sourceUser;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public List<MetaData> getMetadatas() {
		return metadatas;
	}

	public void setMetadatas(List<MetaData> metadatas) {
		this.metadatas = metadatas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Data [id=" + id + ", body=" + body + ", createDate="
				+ createDate + ", originalDate=" + originalDate + ", sourceId="
				+ sourceId + ", sourceUser=" + sourceUser + ", title=" + title
				+ ", type=" + type + ", url=" + url + ", source=" + source
				+ "]";
	}

	public SearchSession getSearchSession() {
		return searchSession;
	}

	public void setSearchSession(SearchSession searchSession) {
		this.searchSession = searchSession;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((sourceId == null) ? 0 : sourceId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Data other = (Data) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sourceId == null) {
			if (other.sourceId != null)
				return false;
		} else if (!sourceId.equals(other.sourceId))
			return false;
		return true;
	}

}