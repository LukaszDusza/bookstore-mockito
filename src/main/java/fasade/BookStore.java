package fasade;

//zadanie to implementacja interfejsu BookStroe nie posiadajac implementacji interfejsu BookRepozitory.
// naszym zadaniem jest zaimplementowanie tego interfejsu nie posiadajac implementacji interfejsu BookRepository oraz PaymentService

//Mamy zasady biznesowe, ktore opisuja implementacje BookStore

//Tworzymy klasę BookStoreService

public interface BookStore {
    ResultDto buy(ISBNDto isbn, ClientDto clientDto);
}
