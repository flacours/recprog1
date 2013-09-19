import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



class NonPersRecommender {
	private Boolean _debug = false;
	private static int MAX_OUTPUT = 5; //Integer.MAX_VALUE;
	
	private List<Rating> _listRatings ;
	private Map<String,Movie> _mapMovies;
	private Map<String,User> _mapUsers;

	private String _moviesFile;
	private String _usersFile;
	private String _ratingsFile;

	NonPersRecommender() {
		_listRatings = new ArrayList<Rating>();
		_mapMovies = new HashMap<String,Movie>();
		_mapUsers = new HashMap<String,User>();
		if(_debug){
			_moviesFile="debug-data-movies.csv";
			_usersFile = "debug-data-users.csv";
			_ratingsFile = "debug-data-ratings.csv";
		}
		else {
			_moviesFile="recsys-data-movie-titles.csv";
			_usersFile = "recsys-data-users.csv";
			_ratingsFile = "recsys-data-ratings.csv";
		}
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
    		writerSimple.close();
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
		System.out.println("PrintResult");
		for(Result r : recs){
			writer.print(r.get_movie() + "," + round(r.get_score(),2));
			i++;
			if(i >=MAX_OUTPUT) break;
			writer.print(",");
		}
		writer.print("\n");
	}
	
	 public static float round(float d, int decimalPlace) {
	        BigDecimal bd = new BigDecimal(Float.toString(d));
	        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
	        return bd.floatValue();
	    }
    
    public void readMovies() {
    	String csvFile = _moviesFile;
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
    	String csvFile = _usersFile;
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
    	 
    	String csvFile = _ratingsFile;
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
    
//    private List<Result> ComputeFake(String movieId)
//    {
//    	ArrayList<Result> a = new ArrayList<Result>();
//    	for(int i = 11; i < 15; i++){
//    		Movie m = _mapMovies.get(Integer.toString(i));
//    		Result r = new Result(m.Id, (float)i);
//    		a.add(r);
//    	}
//    	Movie m = _mapMovies.get(Integer.toString(24));
//    	Result r = new Result(m.Id, (float)24);
//    	a.add(r);
//    	Collections.sort(a, new ResultComparator());
//    	return a;
//    }
    
    private List<Result> ComputeSimple(String movieId)
    {
    	ArrayList<Result> a = new ArrayList<Result>();
    	System.out.println("ComputeSimple");
    	for(String movieY : _mapMovies.keySet())
    	{
    		if(movieY.equals(movieId) == false) 
    		{
    			Movie y = _mapMovies.get(movieY);
    			Map<String,User> users = y.get_mapUsers();
    			int xCountSum = 0;
    			for(String userId : users.keySet()){
    				if(GetXandY(movieId, movieY, userId)) xCountSum ++;
    			}
    			int xCount = GetRatingNumber(movieId);
    			float score = (float)xCountSum / xCount;
    			Result r = new Result(movieY, score);
    			a.add(r);
    		}
    	}
    	
    	Collections.sort(a, new ResultComparator());
    	return a;
    }
    
    private List<Result> ComputeAdvance(String movieId)
    {
    	System.out.println("ComputeAdvance");
    	ArrayList<Result> a = new ArrayList<Result>();
    	for(String movieY : _mapMovies.keySet())
    	{
    		if(movieY.equals(movieId) == false) 
    		{
    			Movie y = _mapMovies.get(movieY);
    			Map<String,User> users = y.get_mapUsers();
    			int xCountSum = 0;
    			int xNotCountSum = 0;
    			for(String userId : users.keySet()){
    				if(GetXandY(movieId, movieY, userId)) xCountSum ++;
    				if( GetNotXandY(movieId, movieY, userId)) xNotCountSum++;
    			}

    			try
    			{
    				int rn = GetRatingNumber(movieId);
    				int nrn = GetNotRatingNumber(movieId);
    				
    				float score = (float)xCountSum / rn;
    				float scoreNot = (float)xNotCountSum/ nrn;
    				score = score / scoreNot;
    				if(score != Float.POSITIVE_INFINITY && score != Float.NEGATIVE_INFINITY){
    				Result r = new Result(movieY, score);
    				a.add(r);
    				}
    				else
    				{
    					System.out.println("infinity for " + movieId + " " + movieY);
    				}
    
    			}
    			catch(Exception ex)
    			{
    			}
    		}
    	}
   
    	
    	Collections.sort(a, new ResultComparator());
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
    	return users != null ? GetNbUsers() - users.size() : GetNbUsers();
    }

        
    private int GetNbUsers()
    {
    	return _mapUsers.size();
    }
    
    private Boolean GetXandY(String movieX, String movieY, String userId)
    {
    	User uX = getUserForMovie(movieX, userId);
    	User uY = getUserForMovie(movieY, userId);
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
    	User uY = getUserForMovie(movieY, userId);
    	return uX == null && uY != null;
    }
}