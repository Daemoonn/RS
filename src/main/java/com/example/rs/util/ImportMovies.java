package com.example.rs.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.rs.javabean.*;

public class ImportMovies {
	public final static String TABLE_NAME = "movies";
	public final static String ID_COLUMN = "movieId";
	public final static String NAME_COLUMN = "title";
	public final static String PUBLISHED_YEAR_COLUMN = "published_year";
	public final static String TYPE_COLUMN = "genres";

	public static int count;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LineNumberReader lineReader = new LineNumberReader(new FileReader(
					"data/ml-latest-small/movies.csv"));
			String line = "";
			List<Movie> movieList = new ArrayList<Movie>();
			count = 0;
			while ((line = lineReader.readLine()) != null) {
				count++;
				if (count % 100 == 0)
					System.out.println(count);
				movieList.add(fillMovie(line));
			}
			persist(movieList);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("error id num:" + Integer.toString(count));
			e.printStackTrace();
		}
	}

	private static void persist(List<Movie> movies) {
		Connection conn = DBUtil.getJDBCConnection();
		PreparedStatement ps = null;
		String sql = "insert into " + TABLE_NAME + " ( " + ID_COLUMN + ", "
				+ NAME_COLUMN + ", " + PUBLISHED_YEAR_COLUMN + ", "
				+ TYPE_COLUMN + ") values (?, ?, ?, ?)";
		try {
			conn.setAutoCommit(false);

			ps = conn.prepareStatement(sql);

			for (Movie movie : movies) {
				ps.setInt(1, movie.getId());
				ps.setString(2, movie.getName());
				if (movie.getYear() != null) {
					ps.setString(3, movie.getYear());
				}
				if (movie.getType() != null) {
					ps.setString(4, StringUtil.connectString(movie.getType(), ", "));
				}
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

	private static Movie fillMovie(String line) {
		Movie movie = new Movie();
		String[] mo;
		if (line.contains("\"")) {
			mo = new String[3];
			mo[0] = line.substring(0, line.indexOf("\"") - 1);
			mo[1] = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
			mo[2] = line.substring(line.lastIndexOf("\"") + 2);
		} else {
			mo = line.split(",");
		}
		movie.setId(Integer.parseInt(mo[0]));

		if (mo[1].contains("(")) {
			String tname = mo[1].substring(0, mo[1].lastIndexOf("(") - 1);
			tname = tname.replace("\"", "");
			movie.setName(tname);
			movie.setYear(mo[1].substring(mo[1].lastIndexOf("(") + 1,
					mo[1].lastIndexOf(")")));
		} else {
			movie.setName(mo[1]);
			movie.setYear("no published year");
		}

		List<String> type = new ArrayList<String>();
		if (!mo[2].contains("(no genres listed)")) {
			String[] t = mo[2].split("\\|");
			for (int i = 0; i < t.length; i++) {
				type.add(t[i]);
			}
		} else {
			type.add("no genres listed");
		}
		movie.setType(type);
		return movie;
	}
}
