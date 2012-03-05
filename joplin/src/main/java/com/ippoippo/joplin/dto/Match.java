package com.ippoippo.joplin.dto;



public class Match {

	private String articleId;

	private YoutubeItem firstItem;

	private YoutubeItem secondItem;

	private String winnerItemId;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public YoutubeItem getFirstItem() {
		return firstItem;
	}

	public void setFirstItem(YoutubeItem firstItem) {
		this.firstItem = firstItem;
	}

	public YoutubeItem getSecondItem() {
		return secondItem;
	}

	public void setSecondItem(YoutubeItem secondItem) {
		this.secondItem = secondItem;
	}

	public YoutubeItem winnerItem;
	public YoutubeItem loserItem;

	public String getChosenItemId() {
		return winnerItemId;
	}

	public void setWinnerItemId(String winnerItemId) {
		this.winnerItemId = winnerItemId;

		if (this.firstItem.getId().equals(this.winnerItemId)) {
			this.winnerItem = this.firstItem;
			this.winnerItem.calcNewRate(true, this.secondItem.getRate());
			this.loserItem = this.secondItem;
			this.loserItem.calcNewRate(false, this.firstItem.getRate());

		} else if (this.secondItem.getId().equals(this.winnerItemId)) {
			this.winnerItem = this.secondItem;
			this.winnerItem.calcNewRate(true, this.firstItem.getRate());
			this.loserItem = this.firstItem;
			this.loserItem.calcNewRate(false, this.secondItem.getRate());

		} else {
			throw new RuntimeException("Illegal requested winnerItemId.");
		}
	}
	
	public YoutubeItem getWinnerItem() {
		return this.winnerItem;
	}

	public YoutubeItem getLoserItem() {
		return this.loserItem;
	}
}