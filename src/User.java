import java.util.ArrayList;
import java.util.List;


public class User {
	public int UserId;
	public List<Rating> Ratings;
	
	public User(int id)
	{
		UserId = id;
		Ratings = new ArrayList<Rating>();
	}
}

