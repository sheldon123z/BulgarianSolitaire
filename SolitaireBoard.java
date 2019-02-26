// Name:Xiaodong Zheng
// USC NetID: Zhen371
// CSCI455 PA2
// Spring 2019

import java.util.ArrayList;
import java.util.Random;

/*
  class SolitaireBoard
  The board for Bulgarian Solitaire.  You can change what the total number of cards is for the game
  by changing NUM_FINAL_PILES, below.  Don't change CARD_TOTAL directly, because there are only some values
  for CARD_TOTAL that result in a game that terminates.
  (See comments below next to named constant declarations for more details on this.)
*/


public class SolitaireBoard {
   
   public static final int NUM_FINAL_PILES = 9;
   // number of piles in a final configuration
   // (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)
   
   public static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;
   // bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
   // see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
   // the above formula is the closed form for 1 + 2 + 3 + . . . + NUM_FINAL_PILES

   // Note to students: you may not use an ArrayList -- see assgt description for details.
   
   
   /**
      Representation invariant:

     -- pileNumber is the number of piles stored in the array
     -- UPPER_BOUND is the maximum size of the number of piles
     -- 0 < pileNumber <= UPPER_BOUND
     -- the sum of values in piles always equals to CARD_TOTAL

   */
   
   // <add instance variables here>
   private static final int UPPER_BOUND=CARD_TOTAL;  
   private int[] piles = new int[UPPER_BOUND];
   private int pilesNum;

 
   /**
      Creates a solitaire board with the configuration specified in piles.
      piles has the number of cards in the first pile, then the number of cards in the second pile, etc.
      PRE: piles contains a sequence of positive numbers that sum to SolitaireBoard.CARD_TOTAL
   */
   public SolitaireBoard(ArrayList<Integer> piles) {
	   
	   
	   for (int i = 0; i <piles.size();i++)
	   {
			   this.piles[i] = piles.get(i);		   
	   }
	   pilesNum=piles.size();
	   
      assert isValidSolitaireBoard();   // sample assert statement (you will be adding more of these calls)
   }
 
   
   /**
      Creates a solitaire board with a random initial configuration.
   */
   public SolitaireBoard() {
	   Random random = new Random();
	  
	   this.pilesNum = random.nextInt(UPPER_BOUND)+1;//assign an random number as the number of piles
	   if(pilesNum==1)
	   {
		   piles[0]=45;
	   }
	   else if(pilesNum!=1)
	   {
		   int assignedCards = random.nextInt(CARD_TOTAL-pilesNum+1)+1;// assign a random number  
		   //the first pile must fulfill the condition that num<card_total-pilesNum+1
		   this.piles[0]=assignedCards;//as the first/last pile number
		   
		   //loop from the second pile to assign the number of cards to every pile
		   for(int i=1; i < this.pilesNum;i++) 
		   {	
			   if(i==(this.pilesNum-1)&& (assignedCards<CARD_TOTAL-1))
			   {
				   piles[i]=CARD_TOTAL-assignedCards;
				   break;
			   }
			   //the number of cards to be assigned = total-the number of cards of last pile - the number of rest piles
			   int currentPileCards = CARD_TOTAL-assignedCards-(this.pilesNum-1-i);
			   
			   this.piles[i] = random.nextInt(currentPileCards)+1;//assign cards to the current pile 			   
			   assignedCards =assignedCards + this.piles[i];//update the number of cards of current pile to lastPile
			   
			   
		   }
	   
	   }
	   assert isValidSolitaireBoard();
   }
   
   /**
      Plays one round of Bulgarian solitaire.  Updates the configuration according to the rules
      of Bulgarian solitaire: Takes one card from each pile, and puts them all together in a new pile.
      The old piles that are left will be in the same relative order as before, 
      and the new pile will be at the end.
   */
   public void playRound() {
	  int lastPileCardNum=0; // this is used to count the number of cards of last pile 
	  int zeroCount=0;		// this is used to count the times of zero encountered in the sequence
	  for(int pos=0;pos<pilesNum;pos++)
	  {			 
			  this.piles[pos]-=1;
			  lastPileCardNum++;	
			  if(piles[pos] == 0)
			  { 
				 zeroCount++;
			  }
	  } 
	
	  int[] newArr = new int[UPPER_BOUND]; //create a new array to update the array pile 
	  int newArrLen = pilesNum-zeroCount+1;
	  for(int i = 0,j = 0;i<piles.length;i++)
	  { 
		  if(piles[i] != 0)
		  {
			  newArr[j] = piles[i];
			  j++;
		  }
	  }
	  
	  newArr[newArrLen-1]=lastPileCardNum;	  
	  System.arraycopy(newArr, 0, piles, 0, newArrLen);//update the pile array
	  pilesNum=newArrLen;
	  
	  assert isValidSolitaireBoard();
		
   }
   
   /**
      Returns true iff the current board is at the end of the game.  That is, there are NUM_FINAL_PILES
      piles that are of sizes 1, 2, 3, . . . , NUM_FINAL_PILES, in any order.
   */
   
   public boolean isDone() {
	  if(pilesNum!= NUM_FINAL_PILES)// if the piles number does not equal to final piles number return false
	  {
		  return false;   
	  }
    	      	   
   	  int pos = 0;
	  while(pos < pilesNum) //check whether there are two numbers in the piles array are same
	  { 							
		  for(int i = pos+ 1;i < pilesNum;i++) //i==1 because pos == 0
		  { 
		  if(piles[pos]==piles[i]) //if same then the loop is not done yet, so return false
			  {
			  return false;
			  } 
		  }
		  pos++;	//increase the position by one
	  }
	  int maximum = piles[0];
	  int minimum = piles[0]; //check whether the maximum number equals to 9
	  for(int i =1; i < pilesNum; i++)
	  {
		  if(maximum<piles[i])
		  {
			maximum=piles[i];
		  }
		  if(minimum>piles[i])
		  {  
			minimum=piles[i];		  
		  }
	  }
	  if(maximum != NUM_FINAL_PILES || minimum!=1)
		  {
		  	return false;
		  }
	  
	  assert isValidSolitaireBoard();
      
	  return true;
      
   }

   
   /**
      Returns current board configuration as a string with the format of
      a space-separated list of numbers with no leading or trailing spaces.
      The numbers represent the number of cards in each non-empty pile.
   */
   public String configString() {
	   
	   String config = Integer.toString(piles[0])+" ";
	   for(int i=1;i<pilesNum;i++)
	   {
		   config = config + Integer.toString(piles[i])+" ";
		  
	   }
	   config = config.trim(); //eliminate trailing spaces
	  
	  assert isValidSolitaireBoard();
      
	  return config;   // dummy code to get stub to compile
   }
   
   
   /**
      Returns true iff the solitaire board data is in a valid state
      (See representation invariant comment for more details.)
     
     Representation invariants:
     -- pileNumber is the number of piles stored in the array
     -- UPPER_BOUND is the maximum size of the number of piles
     -- 0 < pileNumber <= UPPER_BOUND
     -- the sum of values in piles always equals to CARD_TOTAL
   */
   private boolean isValidSolitaireBoard() {
	  
	   //check the upper bound
	  if (pilesNum> UPPER_BOUND || pilesNum < 0)
		  {
		  return false;
		  }
	  
	  //check the total
	  int total=0;
	  for (int i = 0; i< pilesNum; i++)
	  {
		  total+=piles[i];
	  }
	  if( total != CARD_TOTAL)
		  {
		  return false;
		  }
	  
	  //check whether a configuration(any time) has 0 in it
	  for(int i = 0; i< pilesNum; i++)
	  {
		  if(piles[i]==0)
			  {
			  return false;
			  }
	  }
	  
     
      return true;  

   }
   

}
