package com.example.rs.util;

import com.example.rs.javabean.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImportRatings {

	public final static String TABLE_NAME = "ratings";
	public final static String USER_ID_COLUMN = "userID";
	public final static String MOVIE_ID_COLUMN = "movieID";
	public final static String RATING = "rating";
	public final static String TIMESTAMP = "timestamp";
	public static int count;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LineNumberReader lineReader = new LineNumberReader(new FileReader(
					"data/ml-latest-small/ratings.csv"));
			String line = "";
			List<Rating> ratingList = new ArrayList<Rating>();
			count = 0;
			while ((line = lineReader.readLine()) != null) {
				count++;
				if (count % 100 == 0)
					System.out.println(count);
				ratingList.add(fillRating(line));
			}
			insertRatings(ratingList);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static Rating fillRating(String line) {

		String[] ra = line.split(",");
		Rating rating = new Rating();
		rating.setUser_id(Integer.parseInt(ra[0]));
		rating.setMovie_id(Integer.parseInt(ra[1]));
		rating.setRating(Float.parseFloat(ra[2]));
		rating.setTimestamp(Integer.parseInt(ra[3]));
		return rating;
	}


	public static void insertRatings(List<Rating> ratings) {
		Connection conn = DBUtil.getJDBCConnection();
		PreparedStatement ps = null;
		String sql = "insert into " + TABLE_NAME + " ( " + USER_ID_COLUMN
				+ ", " + MOVIE_ID_COLUMN + ", " + RATING + ", " + TIMESTAMP
				+ ") values (?, ?, ?, ?)";
		try {
			conn.setAutoCommit(false);

			ps = conn.prepareStatement(sql);

			for (Rating rating : ratings) {
				ps.setInt(1, rating.getUser_id());
				ps.setInt(2, rating.getMovie_id());
				ps.setFloat(3, rating.getRating());
				ps.setInt(4, rating.getTimestamp());
				ps.addBatch();
			}

			ps.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
