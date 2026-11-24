package nagikh.dev;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class BookService {
    private HashMap<Long, Book> books = new HashMap<>();
    private Long nextId = 1L;

    public ResponseEntity<Book> save(@RequestBody Book book) {
        book.setId(nextId++);
        books.put(book.getId(), book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = books.get(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        if (!books.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        bookDetails.setId(id);
        books.put(id, bookDetails);
        return ResponseEntity.ok(bookDetails);
    }

    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!books.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        books.remove(id);
        return ResponseEntity.noContent().build();
    }
}
