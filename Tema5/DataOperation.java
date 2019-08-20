import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataOperation {
	private String fileName2="D:\\School - V\\java projects\\Tema5\\Rezultat.txt";
	private BufferedWriter	writer;
	private List<MonitoredData> activities=new ArrayList<MonitoredData>();
	public DataOperation()
	{
		try {
			writer=new BufferedWriter(new FileWriter(fileName2));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public void readData()
	{
		List<String> list=new ArrayList<String>();
		String fileName="D:\\School - V\\java projects\\Tema5\\Activities.txt";
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			list = stream.collect(Collectors.toList());
			activities=list.stream().map(s->s.split("\t\t")).map(values->new MonitoredData(values[0],values[1],values[2])).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try 
		{
			writer.write("Rezultate");
			writer.newLine();
			writer.append("Citirea si scrierea in fisier a activitatilor");
			writer.newLine();
			for(MonitoredData data: activities)
			{
				writer.append(data.toString());
				writer.newLine();
				System.out.println(data.toString());
			}
			
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	}
	public void countDays()
	{
		long days;
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd");
		days=activities.stream().map(m->{
			try {
				return dateFormat.parse(m.getStartTime());
			} catch (ParseException e1) {
				
				e1.printStackTrace();
			}
			return null;
		}).distinct().count();
		System.out.println("Numarul de zile in care a avut loc monitorizarea este: "+days);
		try {

			writer.append("Numarul de zile in care a avut loc monitorizarea este: "+days);
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}
	public void countActivities()
	{
		Map<String,Long> map=new HashMap<String,Long>();
		List<String> list=new ArrayList<String>();
		list=activities.stream().map(m->m.getActivity()).distinct().collect(Collectors.toList());
		list.forEach(m->{
			long c;
			c=activities.stream().filter(n->(n.getActivity().equals(m))).count();
			map.put(m, c);
		});
		for(Entry<String, Long> m: map.entrySet())
		{
			System.out.println("Activitatea "+m.getKey()+" apare de "+m.getValue()+" ori ");
			try {
				writer.newLine();
				writer.append("Activitatea "+m.getKey()+" apare de "+m.getValue()+" ori ");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void countActivitiesForEachDay()
	{
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd");
		List<Date> days=new ArrayList<Date>();
		
		days=activities.stream().map(m->{
			try {
				return dateFormat.parse(m.getStartTime());
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			return null;
		}).distinct().collect(Collectors.toList());
		days.forEach(m->{
			List<MonitoredData> list=new ArrayList<MonitoredData>();
			list=activities.stream().filter(n->{
				try {
					return dateFormat.parse(n.getStartTime()).equals(m) || dateFormat.parse(n.getEndTime()).equals(m);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				return false;
			}).collect(Collectors.toList());
			final List<MonitoredData> innerlist=list;
			list.stream().map(r->r.getActivity()).distinct().forEach(p->{
				long c;
				c=innerlist.stream().filter(q->q.getActivity().equals(p)).count();
				System.out.println("Activitatea "+p+" in ziua de "+m.getDate()+" apare de "+c+" ori ");
				try {
					writer.newLine();
					writer.write("Activitatea "+p+" in ziua de "+m.getDate()+" apare de "+c+" ori ");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
		});
	
	}
	public void durationOfEachActivityForEachLine()
	{
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		activities.forEach(m->{
			Duration duration=null;
			try {
				duration=Duration.between(dateFormat.parse(m.getStartTime()).toInstant(),dateFormat.parse(m.getEndTime()).toInstant());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			System.out.println("Activitatea "+m.getActivity()+" care incepe la "+m.getStartTime()+" si se termina la "+m.getEndTime()+ " dureaza "+duration.toString());
			try {
				writer.newLine();
				writer.write("Activitatea "+m.getActivity()+" care incepe la "+m.getStartTime()+" si se termina la "+m.getEndTime()+ " dureaza "+duration.toString());

			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
	}
	public void entireDurationOfEachActivity()
	{
		List<String> list=new ArrayList<String>();
		list=activities.stream().map(r->r.getActivity()).distinct().collect(Collectors.toList());
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		list.forEach(m->{
			List<Duration> duration=new ArrayList<Duration>();
			duration=activities.stream().filter(n->n.getActivity().equals(m)).map(p->{
				try {
					return Duration.between(dateFormat.parse(p.getStartTime()).toInstant(),dateFormat.parse(p.getEndTime()).toInstant());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return null;
			}).collect(Collectors.toList());
			
			Duration total=Duration.ZERO;
			for(Duration d:duration)
				total=total.plus(d);
			System.out.println("Durata totala a activitatii "+m+" este "+total);
			try {
				writer.newLine();
				writer.write("Durata totala a activitatii "+m+" este "+total);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		
	}
	public void activitiesLessThanFiveMinutes()
	{
		Map<String,Long> map=new HashMap<String,Long>();
		List<String> list=new ArrayList<String>();
		List<String> list2=new ArrayList<String>();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		list=activities.stream().map(r->r.getActivity()).distinct().collect(Collectors.toList());
		list.forEach(m->map.put(m, 0L));
		list.forEach(m->{
			long c=activities.stream().filter(n->
				{
					try {
						return (n.getActivity().equals(m) && (Duration.between(dateFormat.parse(n.getStartTime()).toInstant(),dateFormat.parse(n.getEndTime()).toInstant()).toMinutes()<5L));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return false;
				}).count();
			map.put(m,c);
		});
		list2=map.keySet().stream().filter(m->map.get(m)>=0.9*activities.stream().filter(n->(n.getActivity().equals(m))).count()).collect(Collectors.toList());
		System.out.println("Activitatile care au 90% din durata mai putin de 5 min sunt: ");
		list2.forEach(m->System.out.print(m+" "));
		try {
			writer.newLine();
			writer.write("Activitatile care au 90% din durata mai putin de 5 min sunt: ");
			list2.forEach(m->{
				try {
					writer.newLine();
					writer.write(m);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	try {
		writer.close();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
		
	}
}			
