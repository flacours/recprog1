
public class Result {
	private String _movieId;
	private float _score;
	
	public Result(String movieId, float score){
		set_score(score);
		set_movie(movieId);
	}


	public float get_score() {
		return _score;
	}

	public void set_score(float _score) {
		this._score = _score;
	}


	public String get_movie() {
		return _movieId;
	}


	public void set_movie(String _movie) {
		this._movieId = _movie;
	}
}
