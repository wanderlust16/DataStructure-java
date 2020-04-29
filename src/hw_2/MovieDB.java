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
    		Genre currGenre = itr.next();
    		if(currGenre.getGenreName().equals(genreValue)) {
    			System.out.println("genre already exists!");
    			genre = currGenre;
    			Iterator<MovieDBItem> mvItr = genre.getGenreMovies().iterator();
          		while (mvItr.hasNext()) {
          			MovieDBItem currItem = mvItr.next();
          			if (currItem.getTitle().equals(titleValue)) {
          				System.out.println("movie alreay exists!");
          				return; 
          			}
          		}
    		} 	
    	}
    	// 일치하는 장르가 없는 경우 
    	if (genre == null) {
    		genre = new Genre(genreValue);
    		genre_list.add(genre);
    	}

    	MyLinkedList<MovieDBItem> tmpMovieList = genre.getGenreMovies();
    	tmpMovieList.add(item);
    	System.out.println("current movie list size changed: " + tmpMovieList.size());
    	
    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        System.err.printf("[trace] MovieDB: INSERT [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public void delete(MovieDBItem item) {
    	String genreValue = item.getGenre();
    	String titleValue = item.getTitle(); 
    	Genre genre = null;
    	
    	Iterator<Genre> grItr = genre_list.iterator();
    	while (grItr.hasNext()) {
    		Genre currGenre = grItr.next();
    		// 일치하는 장르를 찾음 
    		if(currGenre.getGenreName().equals(genreValue)) {
    			genre = currGenre;
//    			break;
    		} 		
    	}
    	
    	// 일치하는 장르가 없는 경우 
    	if (genre == null) {
    		System.out.println("no such genre name!");
    		return;
    	}    	
    	
		MyLinkedList<MovieDBItem> tmpMovieList = genre.getGenreMovies();
       	Iterator<MovieDBItem> mvItr = tmpMovieList.iterator();
    	
    	while(mvItr.hasNext()) {
//    		MovieDBItem currItem = mvItr.next();
    		if(mvItr.next().getTitle().equals(titleValue)) {
    			mvItr.remove(); 
    	    	System.out.println("current list size changed: " + tmpMovieList.size());
    	    	// 해당 장르의 마지막 영화가 삭제되면 해당 장르도 삭제...가 왜 되는 건지는 모르겠지만 
    	    	if(tmpMovieList.size() == 0) {
    	    		grItr.remove(); // 다음 장르 삭제하는 것 같음 
    	        	System.out.println("genre list size changed: " + genre_list.size());
    	    		return;
    	    	}
    		}	
    	}
    	
    	System.out.println("genre list size changed: " + genre_list.size());
    	
    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        System.err.printf("[trace] MovieDB: DELETE [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public MyLinkedList<MovieDBItem> search(String term) {	
        // This tracing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
    	System.err.printf("[trace] MovieDB: SEARCH [%s]\n", term);
    	
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        Iterator<Genre> grItr = genre_list.iterator();
        
        while(grItr.hasNext()) {
        	Iterator<MovieDBItem> mvItr = grItr.next().getGenreMovies().iterator();
        	while(mvItr.hasNext()) {
        		MovieDBItem item = mvItr.next();
        		if(item.getTitle().contains(term)) {
        			results.add(item);
        		}
        	}
        }
        
        return results;
    }
    
    // printCmd 
    public MyLinkedList<MovieDBItem> items() { 
    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        System.err.printf("[trace] MovieDB: ITEMS\n");
        
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();     
        Iterator<Genre> grItr = genre_list.iterator();
   
      	while(grItr.hasNext()) { // 장르가 있을 때까지 
      		Iterator<MovieDBItem> mvItr = grItr.next().getGenreMovies().iterator();
  			while(mvItr.hasNext()) {
  				MovieDBItem item = mvItr.next();
  				results.add(item);
  			}
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