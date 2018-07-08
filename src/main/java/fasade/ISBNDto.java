package fasade;

import java.io.Serializable;

public class ISBNDto implements Serializable {
    private String isbn;

    public ISBNDto(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
