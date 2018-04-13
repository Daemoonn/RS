package com.example.rs.domain;

import java.io.Serializable;

public class Rating implements Serializable {
	private long user_id;
	private long movie_id;
	private float rating;
	private int timestamp;

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getMovie_id() {
		return this.movie_id;
	}

	public void setMovie_id(long movie_id) {
		this.movie_id = movie_id;
	}

	public float getRating() {
		return this.rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public int getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
}