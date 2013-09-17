
public class Rating {
	public Movie Movie;
	public User  User;
	public float Value;
	
	public Rating(User user, Movie movie, float value){
		this.User = user;
		this.Movie = movie;
		this.Value = value;
	}
}
