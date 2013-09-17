
public class Result {
	private Movie _movie;
	private float _score;
	
	public Result(Movie movie, float score){
		set_score(score);
		set_movie(movie);
	}


	public float get_score() {
		return _score;
	}

	public void set_score(float _score) {
		this._score = _score;
	}


	public Movie get_movie() {
		return _movie;
	}


	public void set_movie(Movie _movie) {
		this._movie = _movie;
	}
}
