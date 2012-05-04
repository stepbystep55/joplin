package com.ippoippo.joplin.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class Contribution implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7619132274630975852L;

	private String id;
	
	@NotEmpty
	private String articleId;

	@NotEmpty
	private String userId;

	private String displayName;

	private YoutubeItem item;

	private Long rank;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public YoutubeItem getItem() {
		return item;
	}

	public void setItem(YoutubeItem item) {
		this.item = item;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Contribution other = (Contribution) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Contribution [id=" + id + ", articleId=" + articleId
				+ ", userId=" + userId + ", displayName=" + displayName
				+ ", item=" + item + ", rank=" + rank + "]";
	}

}
