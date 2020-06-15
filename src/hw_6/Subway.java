package hw_6;

import java.io.*;
import java.util.*;

public class Subway {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;
				if(input.startsWith("<")) enterInfo(input.substring(2));
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
		Graph gr = new Graph();

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
					count++;
						
					String id = splitInput[0];
					String name = splitInput[1];
					String line = splitInput[2];	
					Station st = new Station(id, name, line);
					gr.put(st);
					
				} else {
					if(input.isEmpty()) continue;
					else {
						String[] splitInput = input.split("\\s+");
	
						String st1Id = splitInput[0];
						String st2Id = splitInput[1];
						int minutes = Integer.parseInt(splitInput[2]);
						
						Station st1 = gr.findById(st1Id);
						Station st2 = gr.findById(st2Id);
												
						gr.addEdge(st1, st2, minutes);
					}
				}	
			}
			br.close();
		}		
		gr.print();
	}
}

class Graph {
	private int n; // node 개수 
	private HashMap<Station, LinkedList<Edge>> hm;
	
//	public Graph(int n) {
//		this.n = n;
//	}
	
	public Graph() {
		hm = new HashMap();
	}
	
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public HashMap<Station, LinkedList<Edge>> getHm() {
		return hm;
	}

	public void setHm(HashMap<Station, LinkedList<Edge>> hm) {
		this.hm = hm;
	}

	public void put(Station st) {
		LinkedList<Edge> llg = new LinkedList<Edge>();
		hm.put(st, llg);
	}
	
	public void addEdge(Station st1, Station st2, int minutes) {
		// hm은 이미 모든 station key를 당연히 가지고 있을 것 
		// 양방향 연결은 이미 데이터에 개별 분리되어 있음 
		LinkedList<Edge> llg = hm.get(st1);
		Edge edge = new Edge(st1, st2, minutes);
		llg.add(edge);
	}
	
	public void dijkstra(Station source, Station dest) {
		
	}
	
	public Station findById(String Id) {
		for(Station key : hm.keySet()) {
			if(key.getId().equals(Id)) return key;
		}
		return null;
	}
	
	public void print() {
		for(Map.Entry<Station, LinkedList<Edge>> entry : hm.entrySet()) {
		    Station key = entry.getKey();
		    LinkedList<Edge> value = entry.getValue();
		    
		    System.out.println(" << " + key.getName() + " >> ");
		    for(Edge edge : value) {
		    	System.out.println(edge.getSource().getName() + " to " + edge.getDest().getName() + " = " + edge.getMinute() + "mins");
		    }
		}
	}
}

class Station {
	private String id;
	private String name;
	private String line;
	
	public Station(String id, String name, String line) {
		this.id = id;
		this.name = name;
		this.line = line;
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
}

class Edge {
	private Station source;
	private Station dest;
	int minute;

	public Edge(Station source, Station dest, int minute) {
		this.source = source;
		this.dest = dest;
		this.minute = minute;
	}	
	
	public Station getSource() {
		return source;
	}

	public void setSource(Station source) {
		this.source = source;
	}

	public Station getDest() {
		return dest;
	}

	public void setDest(Station dest) {
		this.dest = dest;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}
}