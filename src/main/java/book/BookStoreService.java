package book;

import common.Mapper;
import fasade.*;
import remote.NotEnoughMoneyException;
import remote.PaymentService;


import java.util.Optional;

// z opisu biznesowego wynika, ze musimy sprawdzic czy dana ksiazka istnieje, jesli nie
//to odpowidamy rezultatem - niezlaneziono. W tym celu musimy skorzystac z BookRepository

public class BookStoreService implements BookStore {

    private BookRepository bookRepository;
    // nie przypiszemy obiektu, ponieważ nie mamy implementacji.
    // nie moze roweniz uzyc @Autowired wlasnie z tego powodu.

    private PaymentService paymentService; //takze nie posiadamy implementacji.

    private Mapper<Book, BookDto> mapper = new BookMapper();

    //z biznesowego pisu wynika, ze musimy pobrac z repozytorium ksiazke.

    //jesli nie znalezlismy ksiazki.
    @Override
    public ResultDto buy(ISBNDto isbnDto, ClientDto clientDto) {
        //nasze repozytorium zwraca Optional od Book. Na ywpadek nie znalezienia ksiazki.
      Optional<Book> bookOptional = bookRepository.find(isbnDto.getIsbn());

      //kiedy nie posiadamy ksiazki
      if(!bookOptional.isPresent()) {
         return ResultDto.ofBookNotFound(
                  String.format("Book with isbn=%s not found", isbnDto.getIsbn())
          );
      }

      //zanim uzyjemy payment service musimy odpakowac Optional i pobrac cene ksiazki.
        Book book = bookOptional.get();

      //kiedy posiadamy ksiazke. metoda deklaruje wyjatek.
        try {
            paymentService.pay(clientDto.getCardId(), book.getPrice());
        } catch (NotEnoughMoneyException e) {
            //jesli cos poszlo ie tak, zwracamy rezultat payment error.
           return ResultDto.ofPaymentError((e.getMessage()));
        }

        //jesli wszystko sie udalo, to chcemy zwrocic ksiazke do klienta.
        //potrzebujemy obiektu BookDto, natomiast my mamy tylko Book. aby nie udostepniac naszej encji wykorzystujemy mapera.

        //teraz uzywamy mappera.
        BookDto bookDto = mapper.map(book);

        return ResultDto.ofSuccess(bookDto);
    }

    //jest to implementacja metody buy.
    // Musmy sprawdzic, czy dobrze integrujemy sie z BookRepository i PaymentService.
    //Sprawdzamy czy nasza metoda buy ma dobrze napisaną logikę.
    // Tworzymy test tej klasy.


}
