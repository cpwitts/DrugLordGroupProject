import java.sql.*;
import java.util.Scanner;

public class DBInterface
{
	static Connection con = null;
	static Statement stmt = null;
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	static Scanner in = new Scanner(System.in);
	
	
	/**
	 * Gets data from the random table to insert into the Person table
	 * @param amount
	 * (int, >= 0) - How many people to create
	 */
	public static void generateRandomPerson(int amount)
	{
		if(amount <= 0)
		{
			return;
		}
		for (int j = 0; j < amount; j++)
		{
			try
			{
	
				String FirstName = null;
				String LastName = null;
				String Location = null;
				String GangName = null;
				for(int i = 0; i < 4; i++)
				{
					pstmt = con.prepareStatement("SELECT * FROM Random WHERE ID = ?");
					int x = (int)(Math.random() * 8)+1;
				    pstmt.setInt(1, x);
				    rs = pstmt.executeQuery();
				    rs.next();
				    switch (i)
				    {
					    case 0:
							FirstName = rs.getString("FirstName");  
					    	break;
					    case 1:
					    	LastName = rs.getString("LastName"); 
					    	break;
					    case 2:
					    	Location = rs.getString("Location");  
					    	break;
					    case 3:
					    	GangName = rs.getString("GangName"); 
					    	break;
				    }
				}
			    double Strength = (Math.random()* 100)+ 1;
			    pstmt = con.prepareStatement("INSERT INTO People VALUES (NULL,?,?,?,?,?,?,?)");
			    pstmt.setString(1, FirstName);
			    pstmt.setString(2, LastName);
			    pstmt.setInt(3, 0);
			    pstmt.setString(4, Location);
			    pstmt.setDouble(5, Strength);
			    pstmt.setDouble(6, Strength * 2);
			    pstmt.setString(7, GangName);
			    pstmt.executeUpdate();
	            System.out.printf("FirstName: %s\nLastName: %s\nLocation: %s\nGangName: %s\n\n",FirstName,LastName,Location,GangName);
			}
			catch (SQLException e) 
			{
				System.out.println("Error: Failed to randomise People.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Gets data from the random table to insert into the gang table
	 * @param amount
	 * (int, >= 0) - How many gangs to create
	 */
	public static void generateRandomGang(int amount)
	{
		if(amount <= 0)
		{
			return;
		}
		for(int j = 0; j < amount; j++)
		{
			try
			{
				String Location = null;
				String GangName = null;
				for(int i = 0; i < 1; i++)
				{
					pstmt = con.prepareStatement("SELECT * FROM Random WHERE ID = ?");
					int x = (int)(Math.random() * 7)+1;
				    pstmt.setInt(1, x);
				    rs = pstmt.executeQuery();
				    rs.next();
				    switch (i)
				    {
					    case 0:
					    	Location = rs.getString("Location");  
					    	break;
					    case 1:
					    	GangName = rs.getString("GangName"); 
					    	break;
				    }
				}
			    int Agression = (int)(Math.random()* 100)+ 1;
			    int Loyalty = (int)(Math.random()* 100)+ 1;
			    pstmt = con.prepareStatement("INSERT INTO People VALUES (?,?,?,?)");
			    pstmt.setString(1, GangName);
			    pstmt.setString(2, Location);
			    pstmt.setInt(3, Agression);
			    pstmt.setInt(4, Loyalty);
	        }
			catch (SQLException e) 
			{
				System.out.println("Error: Failed to randomise Gangs.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Gets data from the random table to insert into the gang table
	 */
	public static void generateRandomGang()
	{
		try
		{
			String Location = null;
			String GangName = null;
			for(int i = 0; i < 1; i++)
			{
				pstmt = con.prepareStatement("SELECT * FROM Random WHERE ID = ?");
				int x = (int)(Math.random() * 7)+1;
				pstmt.setInt(1, x);
				rs = pstmt.executeQuery();
				rs.next();
				switch (i)
			    {
				    case 0:
				    	Location = rs.getString("Location");  
				    	break;
				    case 1:
				    	GangName = rs.getString("GangName"); 
				    	break;
			    }
			}
		   int Agression = (int)(Math.random()* 100)+ 1;
		   int Loyalty = (int)(Math.random()* 100)+ 1;
		   pstmt = con.prepareStatement("INSERT INTO People VALUES (?,?,?,?)");
		   pstmt.setString(1, GangName);
		   pstmt.setString(2, Location);
		   pstmt.setInt(3, Agression);
		   pstmt.setInt(4, Loyalty);
	    }
		catch (SQLException e) 
		{
			System.out.println("Error: Failed to randomise Gangs.");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	/*public static void displayAllProducts()
	{
		try // insert data
		{
			pstmt = con.prepareStatement("INSERT INTO product (prod_id, name, description) values (?, ?, ?)");
		    pstmt.setInt(1, prod_id);
		    pstmt.setString(2, name);
		    pstmt.setString(3, description);
		    pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed to add product.");
			System.out.println(e.getMessage());
		}*/

	public static void cleanup_resources()
	{
		try
		{
			con.close();
		}
		catch (SQLException sqle)
		{
			System.out.println("Error: failed to close the database");
		}
	}
	public static void init_db()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/DrugLord";
			con = DriverManager.getConnection(url, "root", "admin");
			stmt = con.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("Error: Failed to connect to database\n"+e.getMessage());
		}
	}
	public static void displayPlayerDrugs()
	{
		try
		{
			pstmt = con.prepareStatement("SELECT Name, Amount FROM Player");
			rs = pstmt.executeQuery();
			int count = 1;
		    while(rs.next())
		    {
		    	System.out.print(count + ". " +rs.getString(1) + " ");
		    	System.out.println("Qty: " +rs.getString(2));
		    	count++;
		    }
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to find player drugs to database\n"+e.getMessage());
		}
		
	}
	
	/**
	 * Counts the number of types of drugs the player owns
	 * 
	 * @return The number of types of drugs the player owns
	 */
	public static int countPlayerDrugs()
	{
		try
		{
			pstmt = con.prepareStatement("SELECT COUNT(Name)AS total FROM Player");
		    rs = pstmt.executeQuery();
		    rs.next();
			return rs.getInt("total");
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to find player drugs to database\n"+e.getMessage());
			return 0;
		}
	}
	
	/**
	 * 
	 * Calculates the total number of drugs owned by the player.
	 * @return
	 */
	public static int calculatePlayerDrugs()
	{
		try
		{
			pstmt = con.prepareStatement("SELECT SUM(Amount)AS total FROM Player");
		    rs = pstmt.executeQuery();
		    rs.next();
			return rs.getInt("total");
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to calculate the total number of player drugs\n"+e.getMessage());
			return 0;
		}
	}
	
	/**
	 * Counts the total number of drug types in the game
	 * 
	 * @return The total number of drug types
	 */
	public static int countDrugs()
	{
		try
		{
			pstmt = con.prepareStatement("SELECT COUNT(Name)AS total FROM Product");
			rs = pstmt.executeQuery();
			rs.next();
		    return rs.getInt("total");
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to find drugs in database\n"+e.getMessage());
			return 0;
		}
	}
	
	/**
	 * Display all drugs that the player can choose to purchase
	 */
	public static void displayAvailableDrugs()
	{
		try
		{
			pstmt = con.prepareStatement("SELECT Name, Price FROM Product");
			rs = pstmt.executeQuery();
			int count = 1;
		    while(rs.next())
		    {
		    	System.out.print(count + ". " + rs.getString(1) + " ");
		    	System.out.println(rs.getString(2));
		    	count++;
		    }
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to find purchaseable drugs to database\n"+e.getMessage());
		}
	}
	
	/**
	 * Display all employees currently working for the player
	 */
	public static void displayEmployees()
	{
		try
		{
			pstmt = con.prepareStatement("SELECT FirstName, LastName, Strength, Wage FROM People WHERE Loyalty = 1");
			rs = pstmt.executeQuery();
			int count = 1;
		    while(rs.next())
		    {
		    	System.out.println(count + ".\n" + "Name: " + rs.getString(1) + " " + rs.getString(2));
		    	System.out.println("Strength: " + rs.getString(3));
		    	System.out.println("Wage: " + rs.getString(4) + "\n");
		    	count++;
		    }
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to find people in database\n"+e.getMessage());
		}
	}
	
	/**
	 * Displays the drug dealers that the player can hire
	 */
	public static void displayHireableDealers()
	{
		try
		{
			pstmt = con.prepareStatement("SELECT FirstName, LastName, Strength, Wage FROM People WHERE GangName = 'NONE' && Loyalty = 0");
			rs = pstmt.executeQuery();
			int count = 1;
		    while(rs.next())
		    {
		    	System.out.println(count + ".\n" + "Name: " + rs.getString(1) + " " + rs.getString(2));
		    	System.out.println("Strength: " + rs.getString(3));
		    	System.out.println("Wage: " + rs.getString(4) + "\n");
		    	count++;
		    }
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to display potential employees\n"+e.getMessage());
		}
	}

	/**
	 * Remove a drug from the players owned drugs
	 * 
	 * @param drugName The name of the drug to be removed
	 */
	public static void removeDrug(String drugName)
	{
		try
		{
			//Check to see if that drug is owned by player
			pstmt = con.prepareStatement("SELECT Name, Amount FROM Player WHERE Name = ?");
			pstmt.setString(1, drugName);
			rs = pstmt.executeQuery();
			//If it is, check the amount
			if (rs.next())
			{
				int amount = rs.getInt("Amount");
				//If amount is more than one, decrease by one
				if(amount > 1)
				{
					pstmt = con.prepareStatement("UPDATE Player SET Amount = Amount - 1 WHERE Name = ?");
					pstmt.setString(1, drugName);
					pstmt.executeUpdate();
				}
				//Else, remove the drug
				else
				{
					pstmt = con.prepareStatement("DELETE FROM Player WHERE Name = ?");
					pstmt.setString(1, drugName);
					pstmt.executeUpdate();
				}
			}
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to remove drug from the player\n"+e.getMessage());
		}
	}
	
	/**
	 * Remove a RANDOM drug from the players owned drugs
	 * (This will be called when a deal turns bad, and the player gets robbed)
	 */
	public static void removeRandomDrug()
	{
		try
		{
			int total = countPlayerDrugs();
			
			//Generate a random number between 1 and the number of drug types.
			//Add the corresponding drug.
			int random = (int) (Math.random() * total) + 1;
			pstmt = con.prepareStatement("SELECT Name FROM Player LIMIT ?, 1");
			pstmt.setInt(1, random - 1);
			rs = pstmt.executeQuery();
			rs.next();
			removeDrug(rs.getString(1));		
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to remove random drugs from player\n"+e.getMessage());
		}
	}
	
	/**
	 * Adds a drug to the players owned drugs
	 * 
	 * @param drugName The name of the drug to be added
	 */
	public static void addDrug(String drugName)
	{
		try
		{
			//Check to see if that drug is already owned by player
			pstmt = con.prepareStatement("SELECT Name FROM Player WHERE Name = ?");
			pstmt.setString(1, drugName);
			rs = pstmt.executeQuery();
			//If it is, increase the amount by 1
			if (rs.next())
			{
				pstmt = con.prepareStatement("UPDATE Player SET Amount = Amount + 1 WHERE Name = ?");
				pstmt.setString(1, drugName);
				pstmt.executeUpdate();
			}
			//Else, add it to player, with amount set to 1
			else
			{
				pstmt = con.prepareStatement("INSERT INTO Player VALUES (?, 100.00, 1, ?);");
				pstmt.setString(1, drugName);
				pstmt.setString(2, drugName);
				pstmt.executeUpdate();
			}
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to add drug to the player\n"+e.getMessage());
		}
	}
	
	/**
	 * Retrieves a random dealer from a given rival gang
	 */
	public static Dealer getRandomDealer()
	{
		try
		{
			int total = countDealers();
			
			//Generate a random number between 1 and the number of drug types.
			//Add the corresponding drug.
			int random = (int) (Math.random() * total) + 1;
			pstmt = con.prepareStatement("SELECT Strength, GangName FROM People WHERE LOYALTY = 0 && GangName != 'NONE' LIMIT ?, 1");
			pstmt.setInt(1, random);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				int strength = rs.getInt(1);
				String gangName = rs.getString(2);
				
				pstmt = con.prepareStatement("SELECT Agression, Intelligence FROM Gang WHERE GangName = ?");
				pstmt.setString(1, gangName);
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					int agression = rs.getInt(1);
					int patience = 11 - agression;
					int intelligence = rs.getInt(2);
					Dealer dealer = new Dealer(patience, strength, intelligence);
					return dealer;
				}
			}
			return null;
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to find random enemy dealer\n"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * Gets a reference to a specific drug owned by the player
	 * 
	 * @param drugNumber The players choice
	 * @return The drug chosen
	 */
	public static Drug getPlayerDrug(int drugNumber)
	{
		try
		{
			//Check to see if that drug is owned by player
			pstmt = con.prepareStatement("SELECT Name FROM Player LIMIT ?, 1");
			pstmt.setInt(1, drugNumber - 1);
			rs = pstmt.executeQuery();
			//If it is, increase the amount by 1
			if (rs.next())
			{
				String drugName = rs.getString(1);
				
				pstmt = con.prepareStatement("SELECT Name, Price FROM Product WHERE Name = ?");
				pstmt.setString(1, drugName);
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					Drug drug = new Drug(rs.getInt(2), rs.getString(1));
					return drug;
				}
				else
				{
					return null;
				}
			}
			else
			{
				return null;
			}
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to get specific player owned drug\n"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * Gets a reference to a specific drug available
	 * 
	 * @param drugNumber The players choice
	 * @return The chosen Drug
	 */
	public static Drug getDrug(int drugNumber)
	{
		try
		{
			pstmt = con.prepareStatement("SELECT * FROM Product LIMIT ?, 1");
			pstmt.setInt(1, drugNumber - 1);
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				Drug drug = new Drug(rs.getInt(2), rs.getString(1));
				return drug;
			}
			else
			{
				return null;
			}
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to get specific drug from database\n"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * Add a dealer to the players employees
	 * 
	 * @param dealerNumber The number of the chosen dealer
	 */
	public static void hireEmployee(int dealerNumber)
	{
		try
		{
			pstmt = con.prepareStatement("SELECT ID FROM People WHERE Loyalty = 0 && GangName = 'NONE' LIMIT ?, 1");
			pstmt.setInt(1, dealerNumber - 1);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				int id = rs.getInt(1);
				pstmt = con.prepareStatement("UPDATE People SET Loyalty = 1 WHERE ID = ?");
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
			}
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to hire dealer\n"+e.getMessage());
		}
	}
	
	/**
	 * Removes a dealer from the players employees
	 * 
	 * @param dealerNumber The number of the chosen dealer
	 */
	public static void fireEmployee(int dealerNumber)
	{
		try
		{
			pstmt = con.prepareStatement("SELECT ID FROM People WHERE Loyalty = 1 LIMIT ?, 1");
			pstmt.setInt(1, dealerNumber - 1);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				int id = rs.getInt(1);
				pstmt = con.prepareStatement("UPDATE People SET Loyalty = 0 WHERE ID = ?");
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
			}
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to fire employee\n"+e.getMessage());
		}
	}

	/**
	 * Adds a random drug
	 */
	public static void addRandomDrug() 
	{
		try
		{
			//Count total number of drug types
			int total = 0;
			pstmt = con.prepareStatement("SELECT COUNT(Name) as total FROM Product");
			rs = pstmt.executeQuery();
			rs.next();
			total = rs.getInt(1);
			
			//Generate a random number between 1 and the number of drug types.
			//Add the corresponding drug.
			int random = (int) (Math.random() * total) + 1;
			pstmt = con.prepareStatement("SELECT Name FROM Product LIMIT ?, 1");
			pstmt.setInt(1, random);
			rs = pstmt.executeQuery();
			rs.next();
			addDrug(rs.getString(1));		
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to add random drugs to the player\n"+e.getMessage());
		}
	}

	public static int countHireableDealers() 
	{
		try
		{
			pstmt = con.prepareStatement("SELECT COUNT(FirstName) AS total FROM People WHERE Loyalty = 0 && GangName = 'NONE'");
		    rs = pstmt.executeQuery();
		    rs.next();
			return rs.getInt(1);
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to count the number of potential hireable dealers\n"+e.getMessage());
			return 0;
		}
	}

	
	public static int countEmployees() 
	{
		try
		{
			pstmt = con.prepareStatement("SELECT COUNT(FirstName)AS total FROM People WHERE Loyalty = 1");
			rs = pstmt.executeQuery();
			rs.next();
		    return rs.getInt("total");
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to count the number of employees currently employed by the player\n"+e.getMessage());
			return 0;
		}
	}
	
	public static int countDealers() 
	{
		try
		{
			pstmt = con.prepareStatement("SELECT COUNT(FirstName)AS total FROM People WHERE Loyalty = 0 && GangName != 'NONE'");
		    rs = pstmt.executeQuery();
		    rs.next();
			return rs.getInt(1);
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to count total number of dealers\n"+e.getMessage());
			return 0;
		}
	}

	
	public static Dealer getEmployee(int employeeNumber) 
	{
		try
		{
			pstmt = con.prepareStatement("SELECT Strength FROM People WHERE Loyalty = 1 LIMIT ?, 1");
			pstmt.setInt(1, employeeNumber - 1);
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				int strength = rs.getInt(1);
				Dealer dealer = new Dealer (strength);
				return dealer;
			}
			else
			{
				return null;
			}
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to get reference to specific employee\n"+e.getMessage());
			return null;
		}
	}

	
	/**
	 * Checks if the People table has been generated or not
	 * @return
	 */
	public static boolean checkTablesArePopulated() 
	{
		try
		{
			pstmt = con.prepareStatement("SELECT COUNT(FirstName)AS total FROM People");
		    rs = pstmt.executeQuery();
		    rs.next();
			if(rs.getInt(1) > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		catch(Exception e)
		{
			System.out.println("Error: Failed to check if tables were already populated\n"+e.getMessage());
			return false;
		}
	}
}
