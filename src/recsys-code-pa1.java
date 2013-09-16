import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

class NonPersRecommender {

    public static void main(String[] args) {
	// Movies array contains the movie IDs of the top 5 movies.
	int movies[] = new int[5];
	
	System.out.println("Running");
	NonPersRecommender rec = new NonPersRecommender();
	rec.readRating();

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
    
    
    public void readRating() {
    	 
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
     
    			System.out.println(r[0] + " " + r[1] + " " + r[2]); 
     
    		}
     
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
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
}