package ajs.dragonguyman.playlistextract;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Meant to extract URLs from a locally made NewPipe playlist and put them in a text file for use
 * with youtube-dl.
 */
public class PlaylistExtractor {
	
	/**
     * @param args The command line arguments
     */
	public static void main(String[] args) {
		
		String playlistName; // Playlist to get URLs from
		String dbDriver;     // Driver to use
		String dbPath;       // Full path to database
		String sql;    		 // SQL statement to use on database 
		String writeFile;    // Name of file path that will have urls written to it
		
		ArrayList<String> urls = new ArrayList<>(); // Contains video urls
		Scanner input = new Scanner(System.in);
		
		
		/* Input */
		System.out.println("Enter name of playlist.");
		playlistName = input.nextLine();
		
		System.out.println("Enter path to database file.");
		dbPath = input.nextLine();
		
		input.close();
		
		dbDriver = "sqlite";
		dbPath = "jdbc:" + dbDriver + ":" + dbPath;
		writeFile = "playlist.txt";
		
		sql = "SELECT url "
				+ "FROM playlists "
					+ "JOIN playlist_stream_join ON playlists.uid = playlist_id "
					+ "JOIN streams ON stream_id = streams.uid "
				+ "WHERE name = ? "
				+ "GROUP BY url; ";
		
		/* Driver registration */
		try {
			if (dbDriver.equalsIgnoreCase("sqlite")) {
				Class.forName("org.sqlite.JDBC"); // Registers sqlite driver
			}
		}
		//SQLException will occur later if driver issues occur
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		/* Database stuff */
		try (Connection connec = DriverManager.getConnection(dbPath);
			 PreparedStatement playlistQuery = connec.prepareStatement(sql)) {
			
			playlistQuery.setString(1, playlistName);
			try (ResultSet playlistURLs = playlistQuery.executeQuery()) {
				
				while (playlistURLs.next()) {
					urls.add(playlistURLs.getString(1));
					//System.out.println(urls.get(urls.size() - 1));
				}
			}
			playlistQuery.close();
			connec.close();
			
			// Test for existence of file
			FileManipulator.createFile(writeFile);
			FileManipulator.onePerLine(urls, writeFile);
		}
		catch (SQLException | IOException e) {
			e.printStackTrace();
		}
    }

}
