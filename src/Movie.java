import java.util.HashMap;
import java.util.Map;


public class Movie {
	public String Id;
	public String Name;
	
	private Map<String,User> _mapUsers;
	
	public Movie(String id, String name) {
		this.Id = id;
		this.Name = name;
		set_mapUsers(new HashMap<String,User>());
	}

	public Map<String,User> get_mapUsers() {
		return _mapUsers;
	}

	public void set_mapUsers(Map<String,User> _mapUsers) {
		this._mapUsers = _mapUsers;
	}
	
	
}
