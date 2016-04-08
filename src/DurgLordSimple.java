import java.util.Scanner;

/**
 * A game where you live out your fantasies of being a drug lord
 */
public class DurgLordSimple 
{   
    /**Your available money*/
    static int money = 10000;
    /**Your available drugs*/
    static int drugs = 0;
    /**Your available employees*/
    static int employees = 0;
    /**Your strength*/
    static int yourStrength = 75;

    public static void main(String[] args) 
    {       
    	try
    	{
    	DBInterface.init_db();
    	if(!DBInterface.checkTablesArePopulated())
    	{
    		DBInterface.generateRandomPerson(50);
    	}
    	drugs = DBInterface.calculatePlayerDrugs();
    	employees = DBInterface.countEmployees();
    	Scanner in = new Scanner(System.in);
        //Making sure you're still in business
        boolean dealing = true;
        while(dealing)
        {
            //If the player has money or drugs, display their options, and read their input. Calls appropriate functions
        	if(money > 0 || drugs > 0)
            {
                System.out.println("\nYou have " + drugs + " drugs, " + employees + " employees, and " + money + " money\nBUY, SELL, HIRE, FIRE or RETIRE:\n");
                String input = in.next();
                if(input.equals("BUY"))
                {
                    //Must have at least one employee to send out
                	if(employees > 0)
                    {
                        System.out.println("Who will you send out on the deal? (Enter the number of the employee ot send them): \n");
                        Dealer sentDealer = selectDealer();
                         System.out.println("Which drug will you try to Buy? (Enter the number of the drug to sell it): \n");
                        Drug boughtDrug = selectDrugToBuy();
                        buy(boughtDrug, sentDealer);
                    }
                    else
                    {
                         System.out.println("\nYou have no employees to send. You should try to HIRE some dealers!\n");
                    }
                }
                else if(input.equals("SELL"))
                {
                    //Must have a drug and an employee to send out
                	if(drugs > 0 && employees > 0)
                    {
                        System.out.println("Who will you send out on the deal? (Enter the number of the employee ot send them, enter a negative number to go back): \n");
                        Dealer sentDealer = selectDealer();
                        System.out.println("Which drug will you try to sell? (Enter the number of the drug to sell it, enter a negative number to go back): \n");
                        Drug soldDrug = selectDrugToSell();
                        //If the drug was sold, remove it
                        if(sell(sentDealer, soldDrug))
                        {
                        	DBInterface.removeDrug(soldDrug.name);
                        }
                    }
                    else
                    {
                        if (drugs <= 0)
                        {
                            System.out.println("\nYou have no drugs to sell. You should try to BUY some drugs!\n");
                        }
                        
                        if (employees <= 0)
                        {
                            System.out.println("\nYou have no employees to send. You should try to HIRE some dealers!\n");
                        }
                    }
                }
                else if(input.equals("HIRE"))
                {
                    hire();
                }
                else if(input.equals("FIRE"))
                {
                    //Must have an employee to fire
                	if (employees <= 0)
                    {
                        System.out.println("\nYou have no employees to fire\n");
                    }
                    else
                    {
                        fire();
                    }
                }
                else if(input.equals("RETIRE"))
                {
                    dealing = false;
                    System.out.println("\nYou retired with " + money + ".\nThat's impressive!\n");
                    in.close();
                    DBInterface.cleanup_resources();
                }
            }
        	
        	//If no drugs or money, game over
            else
            {
                dealing = false;
                System.out.println("You ran out of money and drugs... \nGame Over");
                in.close();
                DBInterface.cleanup_resources();
            }
            
        }
    	}
    	finally
    	{
    		DBInterface.cleanup_resources();
    	}
    }
    
    /**
     * The function that handles buying drugs. The user is prompted to enter a price until
     * a deal is reached, or violence breaks out.
     * 
     * 
     * -------------------------------------TO DO--------------------------------------
     * Get a rival dealer and drug from the database. Update database based on outcome of deal
     * 
     * 
     */
    public static void buy(Drug drug1, Dealer employee)
    {
    	try
    	{
	        Scanner in = new Scanner(System.in);
	        Dealer employee1 = employee;
	        Dealer dealer1 = DBInterface.getRandomDealer(); //Instntiates a Dealer (randomized stats)
	        String input; //Used to take in the users input
	        boolean bought = false; //Used to keep the deal going
	        int sale = 0; //Stores the player's offer
	        while(!bought)
	        {
	            //Warns the player of the deal going bad
	            if(dealer1.patience == 1)
	            {
	                System.out.println("He's starting to lose his patience...");
	            }
	            
	            System.out.println("The drug is worth " + drug1.price + ".\nHow much will you offer to buy it for: ");
	            if(in.hasNextInt())
	            {
	                sale = in.nextInt();
	                //If dealer accepts offer, deduct money, add the drug to the players drug array, increment the number of drugs
	                if(sale > drug1.price * ((float)dealer1.intelligence/100))
	                {
	                    money -= sale;
	                    drugs++;
	                    DBInterface.addDrug(drug1.name);;
	                    bought = true;
	                    System.out.println("Good deal!");
	                    payWages(employee1);
	                }
	                else
	                {
	                    System.out.println("No deal!");
	                    dealer1.patience--; //Dealer loses patience if you offer him a bad deal
	                }
	                
	                //Causes a fight when the Dealer loses patience
	                if(dealer1.patience == 0)
	                {
	                    fight(dealer1, employee1);
	                    bought = true;
	                }
	            }
	            else
	            {
	                input = in.nextLine();
	            }
	        }
	        in.close();
    	}
        finally
        {
        	DBInterface.cleanup_resources();
        }
    }
    
    /**
     * The function that handles selling drugs. The user is prompted to enter a price until
     * a deal is reached, or violence breaks out.
     * 
     * 
     * -------------------------------------TO DO--------------------------------------
     * Get a rival dealer from the database. Update database based on outcome of deal
     * 
     * 
     */
    public static boolean sell(Dealer employee, Drug drug)
    {
        Scanner in = new Scanner(System.in);
        Dealer employee1 = employee;
        Dealer dealer1 = DBInterface.getRandomDealer(); //Instntiates a Dealer (randomized stats)
        Drug drug1 = drug;
        boolean sold = false; //Used to keep the deal going
        int sale; //Stores player's offer
        String input; //Used to take in input
        boolean success = false;
        while(!sold)
        {
            //Warns the player of a deal going bad
            if(dealer1.patience == 1)
            {
                System.out.println("He's starting to lose his patience...");
            }
            
            System.out.println("The drug is worth " + drug1.price + ".\nHow much will you offer to sell it for: ");
            if(in.hasNextInt())
            {
                sale = in.nextInt();
                //If the dealer accepts the offer, add money, decrement drugs
                if(sale < drug1.price * (2 - ((float)dealer1.intelligence/100)))
                {
                    money += sale;
                    drugs--;
                    sold = true;
                    System.out.println("Good deal!");
                    success = true;
                    payWages(employee);
                }
                else
                {
                    System.out.println("No deal!");
                    dealer1.patience--; //Dealer loses patience if you offer him a bad deal
                }
                
                //Causes a fight when Dealer loses patience
                if(dealer1.patience == 0)
                {
                    fight(dealer1, employee1);
                    sold = true;
                }
            }
            else
            {
                input = in.nextLine();
            }
        }
        return success;
    }
    
    /**
     * The function that handles hiring new employees. The user can choose whether or not to
     * employ a randomly generated dealer.
     * 
     * 
     * -------------------------------------TO DO--------------------------------------
     * Get potential recruits from database. Update database based on outcome
     * 
     * 
     */
    public static void hire()
    {
    	Scanner in = new Scanner(System.in);
        boolean decided = false;//Used to loop the function
        while(!decided)
        {       
            DBInterface.displayHireableDealers();
            int input;
            input = in.nextInt();
            if (input <= 0)
            {
                return;
            }
            else if(input <= DBInterface.countHireableDealers())
            {
                DBInterface.hireEmployee(input);
                employees++;
                decided = true;
            }
        }
    }
    
    /**
     * The function that handles firing employees. The information of all employees will be
     * displayed, and the user will choose who to fire. The user can back out if they do
     * not wish to fire anybody.
     * 
     * 
     * -------------------------------------TO DO--------------------------------------
     * Update the database based on outcome
     * 
     * 
     */
    public static void fire()
    {
    	Scanner in = new Scanner(System.in);
        boolean decided = false;//Used to loop the function
        while(!decided)
        {       
            DBInterface.displayEmployees();
            int input;
            input = in.nextInt();
            if (input <= 0)
            {
                return;
            }
            else if(input <= DBInterface.countEmployees())
            {
                DBInterface.fireEmployee(input);
                employees--;
                decided = true;
            }
        }
    }
    
    /**
     * The function that handles a fight occurring in the event of a deal going bad.
     * Compares the user's strength to the enemy strength.
     * 
     * @param dealer1 The enemy drug dealer
     */
    public static void fight(Dealer dealer1, Dealer employee1)
    {
        System.out.println("He attacked you!");
        //Checks if you are stronger than the enemy dealer
        if(employee1.strength >= dealer1.strength)
        {
            System.out.println("You beat him up and stole his drugs and money!");
            //Grants a somewhat randomised amount of money and drugs
            int gainedDrugs = (int)(1 + Math.random() * 5);
            money+= (int)(1000 + Math.random() * 4001);
            for(int i = gainedDrugs; i > 0; i--)
            {
                DBInterface.addRandomDrug();
                drugs++;
                
            }
            payWages(employee1);
        }
        
        else
        {
            System.out.println("You got beaten up!\nHe stole your drugs and money!");
            //You lose a somewhat randomized amount of money and drugs
            int lostDrugs = (int)(1 + Math.random() * 3);
            money-= (int)(1000 + Math.random() * 2001);
            //No negative drugs
            if(lostDrugs > drugs)
            {
                lostDrugs = drugs;
            }
            for(int i = lostDrugs; i > 0; i--)
            {
                DBInterface.removeRandomDrug();
                drugs--;
            }
            payWages(employee1);
        }
    }
    

    
    /**
     * Displays all of the players employees, and asks them to select one
     * 
     * -------------MAY HAVE TO CHANGE BASED ON DATABASE IMPLEMENTATION--------------
     * 
     * @return Dealer the selected employee
     */
    public static Dealer selectDealer()
    {
        Scanner in = new Scanner(System.in);
        boolean decided = false;//Used to loop the function
        while(!decided)
        {       
            DBInterface.displayEmployees(); //Displays all owned employees
            int input;
            input = in.nextInt();
            if (input <= 0)
            {
                ;
            }
            else if(input <= DBInterface.countEmployees())
            {
                return DBInterface.getEmployee(input);
            }
            else
            {
            	;
            }
        }
        return null;
    }

    /**
     * Displays all of the players drugs, and asks them to select one
     * 
     * -------------MAY HAVE TO CHANGE BASED ON DATABASE IMPLEMENTATION--------------
     * 
     * @return Drug the selected drug
     */
    public static Drug selectDrugToSell()
    {
    	Scanner in = new Scanner(System.in);
        boolean decided = false;//Used to loop the function
        while(!decided)
        {       
            DBInterface.displayPlayerDrugs(); //Displays all owned drugs
            int input;
            input = in.nextInt();
            if (input <= 0)
            {
                return null;
            }
            else if(input <= DBInterface.countPlayerDrugs())
            {
                return DBInterface.getPlayerDrug(input);
            }
        }
        return null;
    }
    
    /**
     * Displays all of the available drugs, and asks the player to select one
     * 
     * -------------MAY HAVE TO CHANGE BASED ON DATABASE IMPLEMENTATION--------------
     * 
     * @return Drug the selected drug
     */
    public static Drug selectDrugToBuy()
    {
    	Scanner in = new Scanner(System.in);
        boolean decided = false;//Used to loop the function
        while(!decided)
        {       
            DBInterface.displayAvailableDrugs(); //Displays all owned drugs
            int input;
            input = in.nextInt();
            if (input <= 0)
            {
                ;
            }
            else if(input <= DBInterface.countDrugs())
            {
                return DBInterface.getDrug(input);
            }
            else
            {
            	;
            }
        }
        return null;
    }

    public static void payWages(Dealer employee)
    {       
         int cost = employee.wage;

         System.out.println("You pay your employee " + cost + "\n");
         money -= cost;
    }
}
