
public class Start {

	public static void main(String[] args) {
		DataOperation o=new DataOperation();
		o.readData();
		o.countDays();
		o.countActivities();
		o.countActivitiesForEachDay();
		o.durationOfEachActivityForEachLine();
		o.entireDurationOfEachActivity();
		o.activitiesLessThanFiveMinutes();
	}

}
