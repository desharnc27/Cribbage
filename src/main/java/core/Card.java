package core;

/**
 * An instance of this class represents a card of cribbage.
 *
 * @author desharnc27
 */
public class Card implements Comparable<Card> {

    /**
     * Number of card. Note: number = 1 if ace, 11 if jack... etc
     */
    private int num;
    /**
     * Suit of card
     */
    private int suit;
    /**
     * Unique identifier of card. Note: id= suit x 13 +num -1
     */
    private int id;

    /**
     * Builds a card with its id
     *
     * @param id the id of the card to buid
     */
    public Card(int id) {
        this.id = id;
        suit = (id / 13);
        num = id - suit * 13;
        num++;
    }

    /**
     * Builds a card with its suit and number
     *
     * @param num the number of the card
     * @param id the id of the card to buid
     */
    public Card(int suit, int num) {
        this.num = num;
        this.suit = suit;
        id = suitNumToId(suit, num);
    }

    /**
     * Returns the suit of the card
     *
     * @return the suit of the card
     */
    public int getSuit() {
        return suit;
    }

    /**
     * Returns the String representation of the suit of the card.
     *
     * @return the String representation of the suit of the card.
     */
    public String strSuit() {
        return DataForStrings.strOfSuit(suit);
    }

    /**
     * Returns the number of the card
     *
     * @return the number of the card
     */
    public int getNum() {
        return num;
    }

    /**
     * Returns the String representation of the number of the card.
     *
     * @return the String representation of the number of the card.
     */
    public String strNum() {
        return DataForStrings.strNum[num];
    }

    /**
     * Returns the String representation of the card.
     *
     * @return the String representation of the card.
     */
    public String verbos() {
        return strSuit() + "-" + strNum();
    }

    /**
     * Returns the String representation of the card.
     *
     * @return the String representation of the card.
     */
    @Override
    public String toString() {
        return verbos();
    }

    /**
     * Returns the id of the card
     *
     * @return the id of the card
     */
    public int getId() {
        return id;
    }

    /**
     * Return the value of a card when counting 15's
     *
     * @return the value of a card when counting 15's
     */
    public int numVal() {
        if (num > 10) {
            return 10;
        }
        return num;
    }

    @Override
    public int compareTo(Card t) {
        if (num < t.num) {
            return -1;
        } else if (num > t.num) {
            return 1;
        } else if (suit < t.suit) {
            return -1;
        } else if (suit > t.suit) {
            return 1;
        }
        return 0;

    }

    /**
     * Calculates the id of a card that has specific number and suit.
     *
     * @param suit suit of card
     * @param num number of card
     * @return id of card
     */
    public static int suitNumToId(int suit, int num) {
        return 13 * suit + num - 1;
    }

}
