package book;

import fasade.BookStore;
import fasade.ClientDto;
import fasade.ISBNDto;
import fasade.ResultDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import remote.NotEnoughMoneyException;
import remote.PaymentService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


// na koniec po testach assert-
//adnotacja i uruchomienie.
@RunWith(MockitoJUnitRunner.class)
public class BookStoreServiceTest {

    //aby przetestowac logikę klasy BookStoreService
    // powinnismy posiadac implementacje BookRepository i PaymentService.
    //bedziemy symulowac dzialanie tych interfejsow.

    //najpierw testuejmy przypadek, gdzie nie posiadamy odpowiedniej ksiazki.
    //symulujemy brak kiazki i probujemy zwrocic Optionala empty. Pomoze na w tym bibioteka Mockito.
    //tworzymy mocka.

   @Mock
   private BookRepository bookRepository;

   //przykladowy nr ISBN;
    private String isbn = "1234567890";
    //potrzebujemy jeszcze mocka IsbnDto,

    //tworzymy obiekt BookStoreService, ktorego nie mockujemy, bo chcemy ją testować.
    @InjectMocks
    private BookStore bookStore = new BookStoreService();

    //do metody buy potrzebujemy jeszcze ClientDto.
    @Mock
    private ClientDto clientDto;

    // poniewaz metoda buy przyjmuje obiekt tej klasy i dopiero ana nim wolana jest metoda getIsbn().
    @Mock
    private ISBNDto isbnDto;

    @Test
    public void shouldReturnBookNotFoundIfBookNotExist() throws Exception{

        //given - szablon pomagajacy umieszczac kawalki kodu.
        //mozna przeniesc na setup
        when(isbnDto.getIsbn()).thenReturn(isbn); //symulujemy pobranie wartosci z obiektu isbnDto.
        when(bookRepository.find(isbn)).thenReturn(Optional.empty());

        //when
        ResultDto actual = bookStore.buy(isbnDto, clientDto);

        //then
        assertThat(actual.getStatus()).isEqualTo(ResultDto.Status.BOOK_NOT_FOUND);
        assertThat(actual.getMessage()).contains(isbn + " not found");
        assertThat(actual.getBookDtos().isEmpty()).isTrue();
    }


    // ======================================= test 2 =============================
    //drugi test - jesli nie uda sie zaplacic za ksiazke to musi pojawic sie odpowiedni komunikat.
    // i zwrócić konkretną książkę
    //potrzebujemy mocka PaymentService;
    // w tym przypadku postaramy sie ominąć instrukcję warunkową z metody buy

    @Mock //mockujemy konkretną ksiażke.
    private Book book;
    @Mock
    PaymentService paymentService;

    private long cardId = 5555667787878L; //dodajemy fikcyjny numer karty.
    private double bookPrice = 10.99;

    private String errorMessage = "Payment error";

    //jesli ktoras metoda sie powtarza mozna zrobic setup adnotacja @Before

//    @Before
//    public void setup() {
//        when(isbnDto.getIsbn()).thenReturn(isbn);
//    }


    @Test
    public void shouldReturnPaymentError() throws Exception{

        //given
        when(isbnDto.getIsbn()).thenReturn(isbn); //mozna przeniesc na setup, bo sie powtarza.
        when(clientDto.getCardId()).thenReturn(cardId);
        when(book.getPrice()).thenReturn(bookPrice);
        when(bookRepository.find(isbn)).thenReturn(Optional.of(book));

        //do tej metody potrzebujemy cardId i cene ksiazki bookPrice.
        doThrow(new NotEnoughMoneyException(errorMessage)).when(paymentService).pay(cardId, bookPrice);

        //when
        ResultDto actual = bookStore.buy(isbnDto, clientDto);

        //then
        assertThat(actual.getStatus()).isEqualTo(ResultDto.Status.PAYMENT_ERROR);
        assertThat(actual.getMessage()).isEqualTo(errorMessage);
        assertThat(actual.getBookDtos().isEmpty()).isTrue();
    }


    //testowanie przypadku istnienia ksiazki. piszemy od konca. chchemy aby wykonal sie blok try{} w metodzie buy.

    @Test
    public void shouldReturnSuccess() throws Exception{
        //given //musimy spreparowac aby byla zwracana odpowiednia ksiazka.
        when(isbnDto.getIsbn()).thenReturn(isbn); //wrzucic do before.
        when(clientDto.getCardId()).thenReturn(cardId);
        when(book.getPrice()).thenReturn(bookPrice);
        when(bookRepository.find(isbn)).thenReturn(Optional.of(book));
        doNothing().when(paymentService).pay(cardId, bookPrice);

        //when
        ResultDto actual = bookStore.buy(isbnDto, clientDto);

        //then
        assertThat(actual.getStatus()).isEqualTo(ResultDto.Status.SUCCESS);
        assertThat(actual.getMessage()).isEmpty();
        assertThat(actual.getBookDtos().size()).isEqualTo(1);
    }

    //uruchomić z codeCoverage.


}