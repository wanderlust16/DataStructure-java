package hw_2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를  
 * 유지하는 데이터베이스이다. 
 */
public class MovieDB { 
	
	private MyLinkedList<Genre> genre_list;
	
    public MovieDB() {
        // FIXME implement this
    	
    	// HINT: MovieDBGenre 클래스를 정렬된 상태로 유지하기 위한 
    	// MyLinkedList 타입의 멤버 변수를 초기화 한다.
    	genre_list = new MyLinkedList<Genre>();
    }

    public void insert(MovieDBItem item) { // 장르 추가 
        // FIXME implement this
        // Insert the given item to the MovieDB.
    	String genreValue = item.getGenre();
    	String titleValue = item.getTitle();
    	Genre genre = null;
    	
    	Iterator<Genre> itr = genre_list.iterator();
    	while (itr.hasNext()) {
    		System.out.println("start while loop");
    		Genre currGenre = itr.next();
    		if(currGenre.getGenreName().equals(genreValue)) {
    			System.out.println("genre already exists!");
    			genre = currGenre;
    		} 
    	}
    	// 중복하는 장르가 없는 경우 
    	if (genre == null) {
    		genre = new Genre(genreValue);
    		genre_list.add(genre);
    	}

    	MyLinkedList<MovieDBItem> tmpMovieList = genre.getGenreMovies();
    	tmpMovieList.add(item);
    	System.out.println("list size changed: " + tmpMovieList.size());
    	
    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        System.err.printf("[trace] MovieDB: INSERT [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public void delete(MovieDBItem item) {
        // FIXME implement this
        // Remove the given item from the MovieDB.
    	
    	String genreValue = item.getGenre();
    	String titleValue = item.getTitle(); 
    	
    	Genre genre = new Genre(genreValue, item); // 다시 정의 필요 
    	
    	MyLinkedList<MovieDBItem> tmpMovieList = genre.getGenreMovies();
    	
    	Node<MovieDBItem> currNode = tmpMovieList.head; // 타입 정의가 이상한가? 
    	Iterator<MovieDBItem> listIt = tmpMovieList.iterator();
    	
    	while(listIt.hasNext()) {
    		currNode = currNode.getNext();
    		if(currNode.getItem() == item) {
    			listIt.remove(); // iterator에서 지워주나? 
    		}
    	}
    	
    	
    	// 참고: 생활코딩 
//    	while(li.hasNext()){
//    	    if((int)li.next() == 15)
//    	        li.remove();
//    	}
    	
    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        System.err.printf("[trace] MovieDB: DELETE [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public MyLinkedList<MovieDBItem> search(String term) {
        // FIXME implement this
        // Search the given term from the MovieDB.
        // You should return a linked list of MovieDBItem.
        // The search command is handled at SearchCmd class.
    	
    	// Printing search results is the responsibility of SearchCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.
    	
        // This tracing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
    	System.err.printf("[trace] MovieDB: SEARCH [%s]\n", term);
    	
    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.   
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();

        return results;
    }
    
    // printCmd 
    public MyLinkedList<MovieDBItem> items() { 
        // FIXME implement this
        // Search the given term from the MovieDatabase.
        // You should return a linked list of QueryResult.
        // The print command is handled at PrintCmd class.

    	// Printing movie items is the responsibility of PrintCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.

    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        System.err.printf("[trace] MovieDB: ITEMS\n");

    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.   
        
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
                
      	while(genre_list.iterator().hasNext()) { // 장르가 있을 때까지 
        	Node<Genre> currGenre = genre_list.head.getNext(); // 장르 list의 첫 번째 노드 (head 바로 다음) 
        	for(MovieDBItem item : currGenre.getItem().getGenreMovies()) {
        		results.add(item);
        	}
        	currGenre = currGenre.getNext(); // genre_list의 모든 장르에 대하여 실행 
        }

        return results;
    }
}

// 구현 
class Genre extends Node<String> implements Comparable<Genre> {
//	hashCode와 equals의 overriding을 위해 non-static field를 만들어줘야 함
	
	private String genreName; 
	private MyLinkedList<MovieDBItem> genreMovies = new MyLinkedList<MovieDBItem>();
	
	public Genre(String name) { // print 시 사용?
		super(name);
		genreName = name;
	}
	
	public Genre(String name, MovieDBItem item) { // insert, delete 시 사용?
		super(name);
		genreName = name;
		genreMovies = new MyLinkedList<>();
	};

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public MyLinkedList<MovieDBItem> getGenreMovies() {
		return genreMovies;
	}

	public void setGenreMovies(MyLinkedList<MovieDBItem> genreMovies) {
		this.genreMovies = genreMovies;
	}

	@Override
	public int compareTo(Genre o) {
		int nameDiff = genreName.compareToIgnoreCase(o.genreName);
		return nameDiff;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genreName == null) ? 0 : genreName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genre other = (Genre) obj;
		if (genreName == null) {
			if (other.genreName != null)
				return false;
		} else if (!genreName.equals(other.genreName))
			return false;
		return true;
	}
}

class MovieList implements ListInterface<String> {	
	public MovieList() {
	}

	@Override
	public Iterator<String> iterator() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public void add(String item) {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public String first() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public void removeAll() {
		throw new UnsupportedOperationException("not implemented yet");
	}
}