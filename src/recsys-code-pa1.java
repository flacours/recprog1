import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class NonPersRecommender {

	private Map<String,Rating> _mapRatings ;
	private Map<String,Movie> _mapMovies;
	private Map<String,User> _mapUsers;

	NonPersRecommender() {
		_mapRatings = new HashMap<String,Rating>();
		_mapMovies = new HashMap<String,Movie>();
		_mapUsers = new HashMap<String,User>();
	}
	
    public static void main(String[] args) {
    	System.out.println("Running");
    	NonPersRecommender rec = new NonPersRecommender();
    	rec.readUsers();
    	rec.readMovies();
    	rec.readRatings();
    	rec.showStats();
    	List<Result> recSimple = rec.ComputeSimple();
    	List<Result> recAdvance = rec.ComputeAdvance();

    	System.out.printf("Done");
    	
    	// Movies array contains the movie IDs of the top 5 movies.
	int movies[] = new int[5];
	

	// Write the top 5 movies, one per line, to a text file.
	try {
	    PrintWriter writer = new PrintWriter("pa1-result.txt","UTF-8");
       
	    for (int movieId : movies) {
		writer.println(movieId);
	    }

	    writer.close();
	    
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
	System.out.println("Done");
    }
    
    public void readMovies() {
    	String csvFile = "recsys-data-movie-titles.csv";
    	BufferedReader br = null;
    	String line = "";
    	String cvsSplitBy = ",";
    	try {

    		br = new BufferedReader(new FileReader(csvFile));
    		while ((line = br.readLine()) != null) {
    		        // use comma as separator
    			String[] r = line.split(cvsSplitBy);
    			if(r.length != 2) throw new Exception("Invalid line format for : " + line);
    			String id = r[0];
    			String name = r[1];
    			Movie m = new Movie(id, name);
    			_mapMovies.put(id, m);
    		}
     
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
			e.printStackTrace();
		} finally {
    		if (br != null) {
    			try {
    				br.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}  
    }
    
    public void readUsers() {
    	String csvFile = "recsys-data-users.csv";
    	BufferedReader br = null;
    	String line = "";
    	String cvsSplitBy = ",";
     
    	try {
    		br = new BufferedReader(new FileReader(csvFile));
    		while ((line = br.readLine()) != null) {
     
    		        // use comma as separator
    			String[] r = line.split(cvsSplitBy);
    			if(r.length != 2) throw new Exception("Invalid line format for : " + line);
    			String id = r[0];
    			String name = r[1];
    			User u = new User(id, name);
    			_mapUsers.put(id, u);
     		}
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
			e.printStackTrace();
		} finally {
    		if (br != null) {
    			try {
    				br.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}  
    	
    }
    public void readRatings() {
    	 
    	String csvFile = "recsys-data-ratings.csv";
    	BufferedReader br = null;
    	String line = "";
    	String cvsSplitBy = ",";
     
    	try {
     
    		// file format  user number, movie ID, and rating
    		br = new BufferedReader(new FileReader(csvFile));
    		while ((line = br.readLine()) != null) {
     
    		        // use comma as separator
    			String[] r = line.split(cvsSplitBy);
    			if(r.length != 3) throw new Exception("Invalid line format for : " + line);
    			String userId = r[0];
    			String movieId = r[1];
    			float value = Float.parseFloat(r[2]);

    			User u = _mapUsers.get(userId);
    			Movie m = _mapMovies.get(movieId);
    			
    			Rating rating = new Rating(u, m, value);
    			// user keeps a list of rating
    			u.Ratings.add(rating);
    			// movie keeps reference to user
    			m.get_mapUsers().put(u.UserId,u);
    		}
     
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
			e.printStackTrace();
		} finally {
    		if (br != null) {
    			try {
    				br.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}  
    }
    private void showStats()
    {
    	System.out.printf("Read %d users\n", _mapUsers.size());
    	System.out.printf("Read %d movies\n", _mapMovies.size());
    	System.out.printf("Read %d ratings\n", _mapRatings.size());
    }
    
    private List<Result> ComputeSimple()
    {
    	return null;
    }
    private List<Result> ComputeAdvance()
    {
    	return null;
    }
    
    private int GetRatingNumber(int movieId)
    {
    return 0;
    }

    private int GetNbMovies()
    {
    	return _mapMovies.size();
    }
    
    private int GetXandY(int movieX, int movieY, int userId)
    {
    	return 0;
    }
    
    private int GetNotXandY(int movieX, int movieY, int userId)
    {
    	return 0;
    }
}