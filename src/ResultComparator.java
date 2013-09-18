import java.util.Comparator;


public class ResultComparator implements Comparator<Result> {

	@Override
	public int compare(Result o1, Result o2) {
		if(o1.get_score() < o2.get_score()) return 1;
		else if(o1.get_score() > o2.get_score()) return -1; 
		return 0;
	}

}
