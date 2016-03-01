/**
 * A drug dealer with various stats
 * They can either work for you, or a rival gang
 */
public class Dealer
{
	/**
	 * The dealers intelligence. Used when determining whether or not they will accept a deal
	 */
    int intelligence = (int) (50 + Math.random() * 50);
    /**
     * The dealers strength. Used when determining whether or not they will win a fight
     */
	int strength = (int) (Math.random() * 100);
	/**
	 * The dealers patience. The higher their patience, the longer they will haggle
	 */
	int patience = (int) (1 + Math.random() * 5);
	/**
	 * The dealers wage. The amount of money the player must pay them at the end of each day.
	 */
	int wage = (100 + strength * 2);
}