import java.util.ArrayList;
import java.util.List;


public class User {
	public String UserId;
	public String Name;
	List<Rating> Ratings;
	
	public User(String id, String Name)
	{
		UserId = id;
		this.Name = Name;
		Ratings = new ArrayList<Rating>();
	}
	
	public int getRatingCount(){
		return Ratings.size();
	}
}

