package hw_6;

import java.io.*;
import java.util.*;

public class Subway {
	private static final int TRANSFER_TIME = 5;
	private static HashMap<String, Station> idMap = new HashMap<>();
	private static HashMap<String, LinkedList<Station>> nameMap = new HashMap<>();

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while (true)
		{
			try
			{
				String input = br.readLine();
				if(input.compareTo("QUIT") == 0) break;
				if(input.startsWith("<")) enterInfo(input.substring(2));
				else {
					initNodes();
					command(input);
				}
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}
	
	public static void enterInfo(String fileName) throws IOException {
		File file = new File(fileName);
		String readMode = "stations";

		if(file.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			int count = 0;
			String input = "";
			
			while((input = br.readLine()) != null) {
				if(input.isEmpty()) {
					readMode = "connections";
				} 
				if(readMode == "stations") {
					String[] splitInput = input.split("\\s+");
						
					String id = splitInput[0];
					String name = splitInput[1];
					String line = splitInput[2];	
					Station newSt = new Station(id, name, line);
					
					idMap.put(id, newSt);
					LinkedList<Station> stations = nameMap.get(name);
					
					if(stations == null) {
						stations = new LinkedList<>();
						stations.add(newSt);
						nameMap.put(name, stations);
					} else {
						for(Station st : stations) {
							st.getEdges().add(new Edge(newSt, TRANSFER_TIME));
							newSt.getEdges().add(new Edge(st, TRANSFER_TIME));
						}
						stations.add(newSt);
					}
										
				} else {
					if(input.isEmpty()) continue;
					
					String[] splitInput = input.split("\\s+");
	
					String st1Id = splitInput[0];
					String st2Id = splitInput[1];
					long minutes = Long.parseLong(splitInput[2]);
					
					Station st1 = idMap.get(st1Id);
					Station st2 = idMap.get(st2Id);
					st1.getEdges().add(new Edge(st2, minutes));
				}	
			}
			br.close();
		}		
	}
	
	public static void command(String input) {
		String[] splitInput = input.split("\\s+");
		String srcName = splitInput[0];
		String destName = splitInput[1];
		dijkstra(srcName, destName);
	}
	
	public static void dijkstra(String srcName, String destName) {
		LinkedList<Station> srcStations = nameMap.get(srcName);
		LinkedList<Station> destStations = nameMap.get(destName);
		
		PriorityQueue<Station> unsettled = new PriorityQueue<>();
		
		for(Station st : srcStations) {
			st.setMinutes(0);
		}
		unsettled.addAll(srcStations);
		
		int count = 1;
		
		while(!unsettled.isEmpty()) {
			Station minStation = unsettled.poll();
			minStation.setSettled(true);
			
			for(Edge edge : minStation.getEdges()) {
				Station adjStation = edge.getDest();
				long currMinutes = minStation.getMinutes() + edge.getMinute();
				if(adjStation.getMinutes() > currMinutes) {
					adjStation.setMinutes(currMinutes);
					adjStation.setPrev(minStation);
					
					unsettled.remove(adjStation);
					unsettled.add(adjStation);
				}
			}
			count++;
		}
		
		Station dest = Collections.min(destStations);
		StringBuilder sb = new StringBuilder();
		
		Station curr = dest;
		while(curr != null) {
			Station prev = curr.getPrev();
			if(prev != null && curr.getName().equals(prev.getName())) {
				sb.insert(0, String.format("[%s] ", curr.getName()));
				curr = prev.getPrev();
			} else {
				sb.insert(0, curr.getName() + " ");
				curr = prev;
			}
		}
		System.out.println(sb.toString().trim());
		System.out.println(dest.getMinutes());
	}
	
	private static void initNodes() {
		for(Station st : idMap.values()) {
			st.setMinutes(Long.MAX_VALUE);
			st.setSettled(false);
			st.setPrev(null);
		}
	}
}

class Station implements Comparable<Station> {
	private String id;
	private String name;
	private String line;
	private LinkedList<Edge> edges;
	private long minutes;
	private boolean settled;
	private Station prev;
	
	public Station(String id, String name, String line) {
		super();
		this.id = id;
		this.name = name;
		this.line = line;
		
		this.edges = new LinkedList<>();
		this.minutes = Long.MAX_VALUE;
		this.settled = false;
		this.prev = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}
	
	public LinkedList<Edge> getEdges() {
		return edges;
	}

	public void setEdges(LinkedList<Edge> edges) {
		this.edges = edges;
	}

	public long getMinutes() {
		return minutes;
	}

	public void setMinutes(long minutes) {
		this.minutes = minutes;
	}

	public boolean isSettled() {
		return settled;
	}

	public void setSettled(boolean settled) {
		this.settled = settled;
	}

	public Station getPrev() {
		return prev;
	}

	public void setPrev(Station prev) {
		this.prev = prev;
	}

	@Override
	public int compareTo(Station st) {
		return Long.compare(minutes, st.minutes);
	}
}

class Edge {
	private Station dest;
	private long minute;
	
	public Edge(Station dest, long minute) {
		super();
		this.dest = dest;
		this.minute = minute;
	}

	public Station getDest() {
		return dest;
	}

	public void setDest(Station dest) {
		this.dest = dest;
	}

	public long getMinute() {
		return minute;
	}

	public void setMinute(long minute) {
		this.minute = minute;
	}	
	
}