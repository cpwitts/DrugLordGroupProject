import java.sql.*;
import java.util.Scanner;

public class DBInterface
{
	static Connection con = null;
	static Statement stmt = null;
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	static Scanner in = new Scanner(System.in);
	
	public static void generateRandom()
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
				int x = (int)(Math.random() * 7)+1;
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
            System.out.printf("FirstName: %s\nLastName: %s\nLocation: %s\nGangName: %s\n",FirstName,LastName,Location,GangName);
		}
		catch (SQLException e) 
		{
			System.out.println("Error: Failed to randomise tables.");
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
	
	/*
	 * 
	 * 
	 * 
	 * ------------------------------------ TO DO-----------------------------------------
	 * 
	 * Below are the method headers and javadoc comments for the methods that need implementing
	 * 
	 * -----------------------------------------------------------------------------------
	 * 
	 * 
	 * 
	 * 
	 */
	
	/**
	 * Display a list of all drugs owned by the player
	 */
	public static void displayPlayerDrugs()
	{
		
	}
	
	/**
	 * Display all drugs that the player can choose to purchase
	 */
	public static void displayAvailableDrugs()
	{
		
	}
	
	/**
	 * Display all employees currently working for the player
	 */
	public static void displayEmployees()
	{
		
	}
	
	/**
	 * Displays the drug dealers that the player can hire
	 */
	public static void displayHireableDealers()
	{
		
	}

	/**
	 * Remove a drug from the players owned drugs
	 * 
	 * @param drugName The name of the drug to be removed
	 */
	public static void removeDrug(String drugName)
	{
		
	}
	
	/**
	 * Remove a RANDOM drug from the players owned drugs
	 * (This will be called when a deal turns bad, and the player gets robbed)
	 */
	public static void removeRandomDrug()
	{
	
	}
	
	/**
	 * Adds a drug to the players owned drugs
	 * 
	 * @param drugName The name of the drug to be added
	 */
	public static void addDrug(String drugName)
	{
	
	}
	
	/**
	 * Retrieves a random dealer from a given rival gang
	 * 
	 * @param gangName The gang from which to get the dealer
	 */
	public static void getRandomRival(String gangName)
	{
		
	}
	
	/**
	 * Add a dealer to the players employees
	 * 
	 * @param id The id of the dealer
	 */
	public static void hireEmployee(int id)
	{
		
	}
	
	/**
	 * Remove an employee from the players employees
	 * 
	 * @param id The id of the employee
	 */
	public static void fireEmployee(int id)
	{
		
	}
}
