package book;

import author.Author;

import java.util.Optional;
import java.util.Set;

//implementacja repozytorium do pobierania danych z bazy, natomiast w scenariuszu tego projektu nie ma implementacji. My korzystamy tylko z interfejsu tego tutaj.

public interface BookRepository {

    Optional<Book> find(String isbn);

    Optional<Book> find(long id);

    Book create(String isbn, String title, Set<Author> authors);

    Book update(Book book);

    void remove(String isbn);

    void remove(long id);
}
