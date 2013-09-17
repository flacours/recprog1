import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class NonPersRecommender {

	private List<Rating> _listRatings ;
	private Map<String,Movie> _mapMovies;
	private Map<String,User> _mapUsers;

	NonPersRecommender() {
		_listRatings = new ArrayList<Rating>();
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
    	// my user ID 4118593
    	// my movie 7443 77 120
    	try {
    		PrintWriter writerSimple = new PrintWriter("pa1-simple.txt","UTF-8");
    		PrintWriter writerAdvance = new PrintWriter("pa1-advance.txt","UTF-8");
    		for(String a : args)
    		{
    			System.out.println("Computing for " + a.toString());
    			List<Result> recSimple = rec.ComputeSimple(a);
    			List<Result> recAdvance = rec.ComputeAdvance(a);

    			writerSimple.print(a+",");
    			writerAdvance.print(a+",");

				printResult(writerSimple, recSimple);
				printResult(writerAdvance, recAdvance);
    		}
    		writerAdvance.close();
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
    	System.out.println("Good bye.");
    	// Movies array contains the movie IDs of the top 5 movies.
    	
    	/*
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
    	}
	
	*/
    }

	private static void printResult(PrintWriter writer, List<Result> recs) {
		int i=0;
		for(Result r : recs){
			writer.print(r.get_movie() + "," + r.get_score());
			i++;
			if(i ==5) break;
			writer.print(",");
		}
		writer.print("\n");
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
    			
    			_listRatings.add(rating);
    			
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
    	System.out.printf("Read %d ratings\n", _listRatings.size());
    }
    
    private List<Result> ComputeSimple(String movieId)
    {
    	ArrayList<Result> a = new ArrayList<Result>();
//    	for(int i = 11; i < 15; i++){
//    		Movie m = _mapMovies.get(Integer.toString(i));
//    		Result r = new Result(m, (float)i);
//    		a.add(r);
//    	}
//		Movie m = _mapMovies.get(Integer.toString(24));
//		Result r = new Result(m, (float)24);
//		a.add(r);
    	
    	for(String userId : _mapUsers.keySet()){
    		for(String movieY : _mapMovies.keySet())
    		{
    			Boolean b = GetXandY(movieId, movieY, userId);
    			int xCount = GetRatingNumber(movieId);
    			float score = (b ? 1 : 0) / xCount;
    			Result r = new Result(movieY, score);
    			a.add(r);
    		}
    	}
    	
    	return a;
    }
    
    private List<Result> ComputeAdvance(String movieId)
    {
    	ArrayList<Result> a = new ArrayList<Result>();
    	for(int i = 11; i < 15; i++){
    		Movie m = _mapMovies.get(Integer.toString(i));
    		Result r = new Result(m.Id, (float)i);
    		a.add(r);
    	}
    	Movie m = _mapMovies.get(Integer.toString(24));
		Result r = new Result(m.Id, (float)24);
		a.add(r);
    	return a;
    }
    
    private int GetRatingNumber(String movieId)
    {
    	Movie m = _mapMovies.get(movieId);
    	if(m == null) return 0;
    	Map<String,User> users = m.get_mapUsers();
    	return users != null ? users.size():0;
    }
    
    private int GetNotRatingNumber(String movieId)
    {
    	Movie m = _mapMovies.get(movieId);
    	if(m == null) return 0;
    	Map<String,User> users = m.get_mapUsers();
    	return users != null ? GetNbMovies() - users.size() : GetNbMovies();
    }

    private int GetNbMovies()
    {
    	return _mapMovies.size();
    }
    
    private Boolean GetXandY(String movieX, String movieY, String userId)
    {
    	User uX = getUserForMovie(movieX, userId);
    	User uY = getUserForMovie(movieX, userId);
    	return uX != null && uY != null;
    }

	private User getUserForMovie(String movieId, String userId) {
		User u = null;
		Movie m = _mapMovies.get(movieId);
    	if(m != null) {
    		Map<String,User> users = m.get_mapUsers();
    		if(users != null) u = users.get(userId);
    	}
    	return u;
	}
    
    private Boolean GetNotXandY(String movieX, String movieY, String userId)
    {
    	User uX = getUserForMovie(movieX, userId);
    	User uY = getUserForMovie(movieX, userId);
    	return uX == null && uY != null;
    }
}