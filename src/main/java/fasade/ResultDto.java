package fasade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResultDto implements Serializable {

    public enum Status {
        SUCCESS,
        PAYMENT_ERROR,
        BOOK_NOT_FOUND
    }

    private Status status;
    private String message;
    private List<BookDto> bookDtos = new ArrayList<>();

    public static ResultDto ofSuccess(BookDto bookDto) {
        return new ResultDto(Status.SUCCESS, "", Arrays.asList(bookDto));
    }

    public static ResultDto ofPaymentError(String message) {
        return new ResultDto(Status.PAYMENT_ERROR, message, Collections.emptyList());
    }

    public static ResultDto ofBookNotFound(String message) {
        return new ResultDto(Status.BOOK_NOT_FOUND,message,Collections.emptyList());
    }


    public ResultDto(Status status, String message, List<BookDto> bookDtos) {
        this.status = status;
        this.message = message;
        this.bookDtos = bookDtos;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BookDto> getBookDtos() {
        return bookDtos;
    }

    public void setBookDtos(List<BookDto> bookDtos) {
        this.bookDtos = bookDtos;
    }
}
