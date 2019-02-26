
// Name:Xiaodong Zheng
// USC NetID: Zhen371
// CSCI455 PA2
// Spring 2019

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**

   Class BulgarianSolitaireSimulator
   the simulator has two mode to play Bulgarian Solitaire game. one is automatic mode whcih doesn't need any input from user. The 
   program will automatically produce a initial configuration and play the game until the game is done
   User chooses mode the game plays, -u means user input, which require the user input a sequence of numbers that their sum equals to 
   the total cards. 
   -s means single step which gives user the control of running every round manually   

*/

public class BulgarianSolitaireSimulator {

   public static void main(String[] args) {
     
      boolean singleStep = false;
      boolean userConfig = true;

      for (int i = 0; i < args.length; i++) {
         if (args[i].equals("-u")) {
            userConfig = true;
         }
         else if (args[i].equals("-s")) {
            singleStep = true;
         }
      }
	   Scanner reader = new Scanner(System.in);
     /* if(args.length>0)
    	  userControlMode(userConfig,singleStep,reader);
      else
    	  autoMode();*/
    	  
      userControlMode(userConfig,singleStep,reader);
      
   }
   
 
   /**
    * This method runs user control mode.It creates an SolitaireBoard object 
    * with a sequence of numbers that user inputs as its initial configuration.
    * User can choose whether do single step control or not by input true into the second parameter
    * @param userConfig if true then user initialize the configuration of the Solitaire board
    * @param singleStep must set with userConfig is true, user use enter key to control the game to enter next step
    * @param reader Scanner parameter passed in
    */
   public static void userControlMode(boolean userConfig, boolean singleStep, Scanner reader)
   {  

 	   ArrayList<Integer> arr = new ArrayList<Integer>(); //define the arrayList to be assigned to the board object

	   if((userConfig&true) && (singleStep&true))
	      {
	    	  	    	
	    	receiveInputAndErrorCheck( reader, arr);
	    	
			SolitaireBoard board = new SolitaireBoard(arr);   //creating an board object with the arrayList consists of user inputs	
			
			runSingleMode( board,  reader); //run single step mode
			
			reader.close();	
	      }
	   
	   if((userConfig==true) && (singleStep==false))
	   {

    	  
		    receiveInputAndErrorCheck( reader, arr);
	    	  
			SolitaireBoard board = new SolitaireBoard(arr);   //creating an board object with the arrayList consists of user inputs	
			
			runNonSingleMode( board,  reader);
			
			reader.close();
	   }
	 
   }
   
   
   /**
    * This method runs an automatically mode for this game. 
    * It initialize a SolitaireBoard object with random initial configuration, and 
    * plays automatically until the end of the game
    */
   public static void autoMode()
   {
		SolitaireBoard board = new SolitaireBoard();   //creating an board object with the arrayList consists of user inputs			
		String confg = board.configString();			//display initial configuration
		System.out.println("Initial configuration: " + confg); 	
		
		boolean isDone=false;		//set a flag to check if the condition is fulfilled 
		
		int counter = 1;		//set the counter to show what is the index of the loop

		while(!isDone )	// if the algorithm has been finished then the loop is break, if the hitEnter flag is still non-null value 
		{									// then the loop continues			
			isDone = doOneRound(board, isDone);
			confg = board.configString();
			System.out.println("["+ counter +"]"+ " Current configuration: " + confg);	
			if (isDone == true)
			{
				System.out.println("Done!");
			}
			counter++;								
		}
   }
  /**
   * This method plays one round of the game and return a flag to indicate whether the game is done
   * @param board	SolitaireBoard object 
   * @param isDone	initial flag to indicate whether the game is done
   * @return	return isDone flag as the indication of the game status
   */
   public static boolean doOneRound(SolitaireBoard board, boolean isDone )
   {  
	   
	   board.playRound();
	   isDone = board.isDone();
	   return isDone;
   }
   /**
    * This method receives input from user and do error check for the inputs
    * @param reader	Scanner object passed in
    * @param arr	arrlist object used to store the user inputs
    */
   public static void receiveInputAndErrorCheck(Scanner reader, ArrayList<Integer> arr)
   {

 	  System.out.println("Number of total cards is "+ SolitaireBoard.CARD_TOTAL);
 	  System.out.println("You will be entering the initial configuration of the cards (i.e., how many in each pile).");		
 	  System.out.println("Please enter a space-separated list of positive integers followed by newline:");
 	  
	   while( reader.hasNextLine())		//initializing the arrayList with user inputs
		{		
			String line = reader.nextLine();
			
			try (Scanner lineReader = new Scanner(line);) //using try..catch with closing resources to check exceptions
			{						
				while(lineReader.hasNext())  
				{
					arr.add(lineReader.nextInt());
				}
				
				int sum = 0;
				for(int i = 0; i < arr.size();i++)
				{
					sum+=arr.get(i);
					if(arr.get(i) <= 0)
					{
						throw new IllegalArgumentException();
					}
				}
				if(sum != SolitaireBoard.CARD_TOTAL)
				{
					throw new IllegalArgumentException();
				}
				
				break;
			}
			catch(InputMismatchException exception) // invalid inputs exception which means the user did non-positive integer numbers
			{
				System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be 45");
				arr.clear(); //clear all wrong inputs
			}
			catch(IllegalArgumentException except)
			{
				System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be 45");
				arr.clear();
			}
				//System.out.println();
				System.out.println("Please enter a space-separated list of positive integers followed by newline:");
		}
   }
   /**
    * This method activates single step mode. It enables user to control every round of the game and display the initial configuration of the board
    * @param board SolitaireBoard object
    * @param reader Scanner object 
    */
   public static void runSingleMode(SolitaireBoard board, Scanner reader)
   {
		String confg = board.configString();			//display initial configuration
		System.out.println("Initial configuration: " + confg); 
		
		boolean isDone=board.isDone();		//set a flag to check if the condition is fulfilled 
		if(isDone)
		{
			System.out.print("Done!");
		}
		
		int counter = 1;		//set the counter to show what is the index of the loop
		
		String hitEnter = reader.nextLine(); //read the first hit
		
		while(!isDone && (hitEnter!=null))	// if the algorithm has been finished then the loop is break, if the hitEnter flag is still non-null value 
		{									// then the loop continues
			if(hitEnter.isEmpty())
			{
				isDone = doOneRound(board, isDone);
				confg = board.configString();
				System.out.println("["+ counter +"]"+ " Current configuration: " + confg);
				
				if (isDone == true)
				{
					System.out.print("Done!");
				}
				else
				{
					System.out.print("<Type return to continue>");
				}
				counter++;
			}
			if(reader.hasNextLine()) 
			{
				hitEnter=reader.nextLine(); //read enter key as empty line to store into hitEnter
			}
			else
			{
				hitEnter = null;
			}
			
		}
   }
   /**
    * This method activates automatic mode. it produces a random configuration
    * @param board SolitaireBoard object
    * @param reader Scanner object passed in 
    */
   public static void runNonSingleMode(SolitaireBoard board, Scanner reader)
   {
	    String confg = board.configString();			//display initial configuration
		System.out.println("Initial configuration: " + confg); 
		
		boolean isDone = board.isDone();		//set a flag to check if the condition is fulfilled 
		if(isDone)
		{
			System.out.print("Done!");
		}
		int counter = 1;		//set the counter to show what is the index of the loop

		while(!isDone )	// if the algorithm has been finished then the loop is break, if the hitEnter flag is still non-null value 
		{									// then the loop continues			
			isDone = doOneRound(board, isDone);
			confg = board.configString();
			System.out.println("["+ counter +"]"+ " Current configuration: " + confg);

			if (isDone == true)
			{
				System.out.println("Done!");
			}
			counter++;								
		}
   }
    
}
