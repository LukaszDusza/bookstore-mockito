package fasade;

public class ClientDto {
    private long cardId;
    private String login;

    public ClientDto(long cardId, String login) {
        this.cardId = cardId;
        this.login = login;
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


}
