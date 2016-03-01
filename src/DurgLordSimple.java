import java.util.ArrayList;
import java.util.Scanner;

/**
 * A game where you live out your fantasies of being a drug lord
 */
public class DurgLordSimple 
{   
    /**Your available money*/
    static int money = 10000;
    /**Your available drugs*/
    static int drugs = 1;
    /**Your available employees*/
    static int employees = 1;
    /**Your strength*/
    static int yourStrength = 75;
    /**Your employeesArr*/
    static ArrayList<Dealer> employeesArr = new ArrayList<Dealer>();
    /**Your drugs*/
    static ArrayList<Drug> drugsArr = new ArrayList<Drug>();
    
    public static void main(String[] args) 
    {       
        Scanner in = new Scanner(System.in);
        //Starts the player with one employee
        employeesArr.add(new Dealer());
        //Starts the player with one drug
        drugsArr.add(new Drug());
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
                        System.out.println("Who will you send out on the deal? (Enter the number of the employee ot send them, enter a negative number to go back): \n");
                        Dealer sentDealer = selectDealer();
                        buy(sentDealer);
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
                        Drug soldDrug = selectDrug();
                        //If the drug was sold, remove it
                        if(sell(sentDealer, soldDrug))
                        {
                        	drugsArr.remove(soldDrug);
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
                }
                
                //Deducts employee wages from player's money
                if(employeesArr.size() > 0 && dealing)
                {
                    int cost = 0;
                    for ( Dealer employee : employeesArr )
                    {
                        cost += employee.wage;
                    }
                    System.out.println("You pay your employees " + cost + "\n");
                    money -= cost;
                }
            }
        	
        	//If no drugs or money, game over
            else
            {
                dealing = false;
                System.out.println("You ran out of money and drugs... \nGame Over");
                in.close();
            }
            
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
    public static void buy(Dealer employee)
    {
        Scanner in = new Scanner(System.in);
        Dealer employee1 = employee;
        Dealer dealer1 = new Dealer(); //Instntiates a Dealer (randomized stats)
        Drug drug1 = new Drug();//Instntiates a Dealer (randomized stats)
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
                    drugsArr.add(drug1);
                    bought = true;
                    System.out.println("Good deal!");
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
        Dealer dealer1 = new Dealer(); //Instntiates a Dealer (randomized stats)
        Drug drug1 = drug;//Instntiates a Dealer (randomized stats)
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
        boolean decided = false; //used to loop the function while the user inputs values
        Dealer employee = new Dealer();//Instatieates a Dealer with randomized stats
        int count = 0;//Used to limit number of employees available
        
        //Offers the player up to 5 different employees to hire
        while(!decided && count < 5)
        {
            //Prints dealer stats
            System.out.println("Stength = " + employee.strength + "\nWage = " + employee.wage + "\nWould you like to hire him? YES or NO: ");
            String input = in.next();
            //If player decides to hire, add the employee to the arrayList, increment employees
            if(input.equals("YES"))
            {
                employeesArr.add(employee);
                employees++;
                decided = true;
                System.out.println("You hired him!");
            }
            
            else if(input.equals("NO"))
            {
                System.out.println("You didn't hire him...");
                count++;
                employee = new Dealer();//Create a new dealer for consideration
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
            printemployeesArr(); //Displays all owned employees
            System.out.println("Which would you like to fire (enter digit, enter negative number to go back)?");
            int input;
            input = in.nextInt();
            //Cancel if the player enters a negative value
            if (input < 0)
            {
                System.out.println("You decide to fire nobody!");
                decided = true;
            }
            //If the value is valid, remove employee and decrement employees
            else if(input <= employeesArr.size())
            {
                employeesArr.remove(input-1);
                decided = true;
                employees--;
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
                drugsArr.add(new Drug());
                drugs++;
            }
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
                drugsArr.remove(0);
                drugs--;
            }
        }
    }
    
    /**
     * A function that displays the information of all currently employed dealers.
     * It displays their strength and wage.
     * 
     * 
     * -------------------------------------TO DO--------------------------------------
     * Get all of the players employees from the database
     * 
     * 
     */
    public static void printemployeesArr()
    {
        int count = 1;
        System.out.println("\n");
        for ( Dealer employee : employeesArr )
        {
            System.out.println("Employee " + count + ":\nStength = " + employee.strength + "\nWage = " + employee.wage + "\n");
            count++;
        }
    }
    
    /**
     * A function that displays the information of all currently owned drugs.
     * It displays their value.
     * 
     * 
     * -------------------------------------TO DO--------------------------------------
     * Get all the player owned drugs from the database
     * 
     * 
     */
    public static void printDrugs()
    {
        int count = 1;
        for ( Drug drug : drugsArr )
        {
            System.out.println("Drug " + count + ":\nValue = " + drug.price + "\n");
            count++;
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
            printemployeesArr(); //Displays all owned employees
            int input;
            input = in.nextInt();
            if (input <= 0)
            {
                return null;
            }
            else if(input <= employeesArr.size())
            {
                return employeesArr.get(input - 1);
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
    public static Drug selectDrug()
    {
        Scanner in = new Scanner(System.in);
        boolean decided = false;//Used to loop the function
        while(!decided)
        {       
            printDrugs(); //Displays all owned employees
            int input;
            input = in.nextInt();
            if (input <= 0)
            {
                return null;
            }
            else if(input <= drugsArr.size())
            {
                return drugsArr.get(input - 1);
            }
        }
        return null;
    }
}
