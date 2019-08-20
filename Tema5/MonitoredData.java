

public class MonitoredData {
	private String startTime;
	private String endTime;
	private String activity;
	
	public MonitoredData(String startTime,String endTime,String activity)
	{
		this.startTime=startTime;
		this.endTime=endTime;
		this.activity=activity;
	}


	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Override
	public String toString()
	{
		return "Activitatea "+activity+" incepe la "+startTime+" si se termina la "+endTime;
	}
}
