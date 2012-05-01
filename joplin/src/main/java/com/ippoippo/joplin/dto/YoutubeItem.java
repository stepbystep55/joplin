package com.ippoippo.joplin.dto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

public class YoutubeItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2365995103355938584L;

	private String id;
	
	@NotEmpty
	private String articleId;

	@NotEmpty
	private String videoId;
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String thumbnailUrl;

	private double rate = 1600;

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

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEncodedTitle() {
		String encodedTitle = null;
		try {
			encodedTitle = URLEncoder.encode(title, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// not happen
			e.printStackTrace();
		}
		return encodedTitle;
	}

	public void setEncodedTitle(String encodedTitle) {
		try {
			this.title = URLDecoder.decode(encodedTitle, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// not happen
			e.printStackTrace();
		}
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getEncodedThumbnailUrl() {
		String encodedThumbnail = null;
		try {
			encodedThumbnail = URLEncoder.encode(thumbnailUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// not happen
			e.printStackTrace();
		}
		return encodedThumbnail;
	}

	public void setEncodedThumbnailUrl(String encodedThumbnailUrl) {
		try {
			this.thumbnailUrl = URLDecoder.decode(encodedThumbnailUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// not happened
			e.printStackTrace();
		}
	}

	private static final double WINNER_POINT = 1;
	private static final double LOSER_POINT = 0;
	private static final double TOURNAMENT_LEVEL = 32;

	/**
	 * 対決後のrate変動量
	 */
	@Transient
	private double rateVaried;

	public void calcRateVaried(boolean win, double opponentRate) {
		double point = (win) ? WINNER_POINT : LOSER_POINT;
		double winProbability = 1 / (1 + Math.pow(10, (opponentRate - this.rate) / 400));
		this.rateVaried = TOURNAMENT_LEVEL * (point - winProbability);
	}

	public double getRateVaried() {
		return this.rateVaried;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((articleId == null) ? 0 : articleId.hashCode());
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
		YoutubeItem other = (YoutubeItem) obj;
		if (articleId == null) {
			if (other.articleId != null)
				return false;
		} else if (!articleId.equals(other.articleId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "YoutubeItem [id=" + id + ", articleId=" + articleId
				+ ", videoId=" + videoId + ", title=" + title + ", thumbnailUrl="
				+ thumbnailUrl + ", rate=" + rate + ", rateVaried=" + rateVaried
				+ "]";
	}

	@Override
	public YoutubeItem clone() {
		YoutubeItem cloneItem = new YoutubeItem();
		cloneItem.setId(this.id);
		cloneItem.setArticleId(this.articleId);
		cloneItem.setVideoId(this.videoId);
		cloneItem.setTitle(this.title);
		cloneItem.setThumbnailUrl(this.thumbnailUrl);
		cloneItem.setRate(this.rate);
		return cloneItem;
	}
}
