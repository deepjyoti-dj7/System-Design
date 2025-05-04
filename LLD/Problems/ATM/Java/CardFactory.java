public class CardFactory {
    public static Card createCard(String cardNumber, String pin) {
        return new Card(cardNumber, pin);
    }
}
