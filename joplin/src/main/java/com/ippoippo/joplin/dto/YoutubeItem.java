package com.ippoippo.joplin.dto;

import javax.inject.Inject;

import org.hibernate.validator.constraints.NotEmpty;

import com.ippoippo.joplin.util.Encryptor;

public class YoutubeItem {

	@Inject
	private Encryptor encryptor;

	private String id;
	
	@NotEmpty
	private String articleId;

	@NotEmpty
	private String videoId;
	
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

	/**
	 * get the encrypted rate value for hidden input
	 * @return
	 */
	public String getErate() {
		return encryptor.encrypt(""+this.rate);
	}
	
	/**
	 * set the encrypted rate value for hidden input
	 * @return
	 */
	public void setErate(String erate) {
		this.rate = new Double(encryptor.decrypt(erate));
	}

	private static final double WINNER_POINT = 1;
	private static final double LOSER_POINT = 1;
	private static final double TOURNAMENT_LEVEL = 32;

	private double newRate;

	public void calcNewRate(boolean win, double opponentRate) {
		double point = (win) ? WINNER_POINT : LOSER_POINT;
		double winProbability = 1 / (1 + Math.pow(10, (opponentRate - this.rate) / 400));
		this.newRate = rate + TOURNAMENT_LEVEL * (point - winProbability);
	}

	public double getNewRate() {
		return this.newRate;
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
		YoutubeItem other = (YoutubeItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
