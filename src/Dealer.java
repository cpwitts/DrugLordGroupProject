/**
 * A drug dealer with various stats
 * They can either work for you, or a rival gang
 */
public class Dealer
{
	/**
	 * The dealers intelligence. Used when determining whether or not they will accept a deal
	 */
    int intelligence;
    /**
     * The dealers strength. Used when determining whether or not they will win a fight
     */
	int strength;
	/**
	 * The dealers patience. The higher their patience, the longer they will haggle
	 */
	int patience;
	/**
	 * The amount to pay the dealer
	 */
	int wage;

	public Dealer(int dealerPatience, int dealerStrength, int dealerIntelligence)
	{
		patience = dealerPatience;
		strength = dealerStrength;
		intelligence = dealerIntelligence;
	}
	
	public Dealer (int dealerStrength)
	{
		strength = dealerStrength;
		wage = strength * 2;
	}
}