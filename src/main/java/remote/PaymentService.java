package remote;

//folder remote wskazuje na to, ze otrzymamy implementacje z zewnarz.
//implementacja interfejsu z zewnetrznego servisu platnosci.
// dla tego przykladu obecnie nie mamy dostepu do tej implementacji.

public interface PaymentService {
    void pay(long cardId, double amount) throws NotEnoughMoneyException;
}
