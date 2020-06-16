import java.util.Scanner;

/**
 * This plays the Blackjack card game that we wrote throughout 
 * the videos in this lesson.
 * 
 * Try to play the game and test it out. As you play it, can you think
 * of ways to improve the game? Can you think of ways to improve the code
 * or organize the code?
 * 
 * @author V2
 *
 */
public class Blackjack
{
	/*
	 * Unused Variables
    private static final int HEARTS = 0;
    private static final int DIAMONDS = 1;
    private static final int SPADES = 2;
    private static final int CLUBS = 3;
    
    private static final int JACK = 11;
    private static final int QUEEN = 12;
    private static final int KING = 13;
    */
    private static final int ACE = 14;
    
    // The starting bankroll for the player.
    private static final int STARTING_BANKROLL = 100;
    private int bet=0;
    
    /**
     * Ask the player for a move, hit or stand.
     * 
     * @return A lowercase string of "hit" or "stand"
     * to indicate the player's move.
     */
    private String getPlayerMove()
    {
        while(true)
        {
        	Scanner userInput = new Scanner(System.in);
	       	System.out.print("Enter move (hit/stand/double down) ");
	       	 
	   		String move = userInput.nextLine();
            
            if(move.equals("hit") || move.equals("stand") || move.equals("double down"))
            {
                return move;
            }
            System.out.println("Please try again."); 
        }
    }
    /**
     * Ask the player if they want to insure
     * 
     * @param dealer the dealer's hand
     * @return the insureance value --> 0 means no insurance
     */
    
    private int checkInsurance(Hand dealer){
        //This means that the player can insure
        if (dealer.getCardAt(1).getRank() == ACE){
            int x;
            while (true){
            	while (true) {
            		try {
            	    	Scanner userInput = new Scanner(System.in);
            	       	System.out.print("What is your bet? ");
            	       	String stringBet = userInput.nextLine();
            	       	//userInput.close();
            	       	x = Integer.parseInt(stringBet);
            	       	break;
                	} catch (NumberFormatException e){
                		System.out.println("Must be a number with no whitespace");
                	}
            	}
                if (x > -1){
                    return x;
                }
                System.out.println("Must be 0 or greater!");
            }
        }
        // The player cannot make a bet so it will return 0
        return 0;
    }
    
    /**
     * Find the winner between the player hand and dealer
     * hand. Return how much was won or lost due to the insurance (if it was betted)
     */
    private double insuranceChange(int insurance, double bankroll, Hand dealer) {
    	int change = 0;
    	if (insurance>0){
            System.out.println("You are insured!");
            if (dealer.hasBlackjack()){
                System.out.println("The dealer had a blackjack --> You get $.");
                change += insurance * 2;
                System.out.println("New bankroll: " + bankroll);
            } else {
                System.out.println("The dealer didn't have a blackjack --> You lost $.");
                change -= insurance;
                System.out.println("New bankroll: " + bankroll); 
            }  
        }
    	return change;
    }
    private boolean checkSplit(Hand player){
        if (player.getCardAt(1).getValue() == player.getCardAt(0).getValue()){
            String move;
            while (true){
                
            	
            	Scanner userInput = new Scanner(System.in);
           	    System.out.print("Enter move (hit/stand/double down) ");
           	    move = userInput.nextLine();
            	//move = readLine("You have a split, Would you like to split? (y/n) ");
                
                if (move.equals("y")){
                    return true;
                } else if (move.equals("n")){
                    return false;
                }
                System.out.println("Please try again (Remember only \"y\" or \"n\")");
            }
        }
        return false;
    }
    
    /**
     * Play the dealer's turn.
     * 
     * The dealer must hit if the value of the hand is less
     * than 17. 
     * 
     * @param dealer The hand for the dealer.
     * @param deck The deck.
     */
    private void dealerTurn(Hand dealer, Deck deck)
    {
        while(true)
        {
            System.out.println("Dealer's hand");
            System.out.println(dealer);
            
            int value = dealer.getValue();
            System.out.println("Dealer's hand has value " + value);
            
            Scanner userInput = new Scanner(System.in);
       	 	System.out.print("Enter to continue...");
       	 	userInput.nextLine();
            //readLine("Enter to continue...");
            
            if(value < 17)
            {
                System.out.println("Dealer hits");
                Card c = deck.deal();
                dealer.addCard(c);
                
                System.out.println("Dealer card was " + c);
                
                if(dealer.busted())
                {
                    System.out.println("Dealer busted!");
                    break;
                }
            }
            else
            {
                System.out.println("Dealer stands.");
                break;
            }
        }
    }
    
    /**
     * Play a player turn by asking the player to hit
     * or stand.
     * 
     * Return whether or not the player busted.
     */
    private boolean playerTurn(Hand player, Deck deck)
    {
        while(true)
        {
            String move = getPlayerMove();
            
            if(move.equals("hit")) {
                System.out.println("Here");
                Card c = deck.deal();
                System.out.println("Your card was: " + c);
                player.addCard(c);
                System.out.println("Player's hand");
                System.out.println(player);
                
                
                if(player.busted())
                {
                    return true;
                }
                System.out.println("Here");
                
            } else if(move.equals("double down")) {
                // doubles the value of the bet
                bet = bet * 2;
                
                // Makes the player draw a card
                Card c = deck.deal();
                System.out.println("Your card was: " + c);
                player.addCard(c);
                System.out.println("Player's hand");
                System.out.println(player);
                
                if(player.busted())
                {
                    return true;
                }
                
                //After doubling down you must stand, so return false
                return false;
                
            } else {
                // If we didn't hit, the player chose to 
                // stand, which means the turn is over.
                return false;
            }
            
        }
    }
    
    /**
     * Determine if the player wins. 
     * 
     * If the player busted, they lose. If the player did 
     * not bust but the dealer busted, the player wins.
     * 
     * Then check the values of the hands.
     * 
     * @param player The player hand.
     * @param dealer The dealer hand.
     * @return
     */
    private boolean playerWins(Hand player, Hand dealer)
    {
        if(player.busted())
        {
            return false;
        }
        
        if(dealer.busted())
        {
            return true;
        }
        
        return player.getValue() > dealer.getValue();
    }
    
    /**
     * Check if there was a push, which means the player and
     * dealer tied.
     * 
     * @param player The player hand.
     * @param dealer The dealer hand.
     * @return
     */
    private boolean push(Hand player, Hand dealer)
    {
        return player.getValue() == dealer.getValue();
    }
    
    /**
     * Find the winner between the player hand and dealer
     * hand. Return how much was won or lost.
     */
    private double findWinner(Hand dealer, Hand player, int bet)
    {
        if(playerWins(player, dealer))
        {
            System.out.println("Player wins!");
            
            if(player.hasBlackjack())
            {
                return 1.5 * bet;
            }
            return bet;
        }
        else if(push(player, dealer))
        {
            System.out.println("You push");
            return 0;
        }
        else
        {
            System.out.println("Dealer wins");
            return -bet;
        }
    }
    
    /**
     * This plays a round of blackjack which includes:
     * - Creating a deck
     * - Creating the hands
     * - Dealing the round
     * - Playing the player turn
     * - Playing the dealer turn
     * - Finding the winner
     * 
     * @param bankroll
     * @return The new bankroll for the player.
     */
    private double playRound(double bankroll)
    {
    	
    	while (true) {
			try {
		    	Scanner userInput = new Scanner(System.in);
		       	System.out.print("What is your bet? ");
		       	String stringBet = userInput.nextLine();
		       	//userInput.close();
		       	bet = Integer.parseInt(stringBet);
		       	break;
	    	} catch (NumberFormatException e){
	    		System.out.println("Must be a number with no whitespace");
	    	}
    	}
        Deck deck = new Deck();
        deck.shuffle();
        
        Hand player = new Hand();
        Hand playerSecond = new Hand();
        Hand dealer = new Hand();
        
        player.addCard(deck.deal());
        dealer.addCard(deck.deal());
        player.addCard(deck.deal());
        dealer.addCard(deck.deal());
        
        System.out.println("Player's Hand");
        System.out.println(player);
        
        
        System.out.println("Dealer's hand");
        //System.out.println(dealer);
        dealer.printDealerHand();
        
        //Checks to see if player can insure
        int insurance = checkInsurance(dealer);
        //Checks to see if there is a split
        boolean splitted = checkSplit(player);
        
        System.out.println("********  YOUR TURN  ********");
        if (splitted){
            bet = bet * 2;
            //Remove the first card from the 1st hand and append to second hand
            Card c = player.removeCardAt(1);
            playerSecond.addCard(c);
            
            //Play the first hand without the one of the cards
            System.out.println("******** FIRST HAND");
            boolean playerBusted = playerTurn(player, deck);
            if(playerBusted)
            {
                System.out.println("You busted :(");
            }
        
            //Play the second hand
            System.out.println("********  SECOND HAND");
            boolean playerSecondBusted = playerTurn(playerSecond, deck);
            if(playerSecondBusted)
            {
                System.out.println("You busted :(");
            }
        
            
        } else {
            // Only plays the one hand since there was no split
            boolean playerBusted = playerTurn(player, deck);
            if(playerBusted)
            {
                System.out.println("You busted :(");
            }
        }
        
        @SuppressWarnings("resource")
		Scanner userInput = new Scanner(System.in);
   	 	System.out.print("\"********  DEALER'S TURN  ******** \nEnter for dealer turn...\"");
   	 	userInput.nextLine();//readLine("********  DEALER'S TURN  ********\nEnter for dealer turn...");
        dealerTurn(dealer, deck);
        
        System.out.println("********  RESULTS  ********");
        double bankrollChange = findWinner(dealer, player, bet);
        
        if (splitted){
            double bankrollChangeSecond = findWinner(dealer, playerSecond, bet);
            if (bankrollChangeSecond>bankrollChange){
                bankrollChange = bankrollChangeSecond;
            }
        }
        
        bankroll += bankrollChange;
        System.out.println("New bankroll: " + bankroll);
        
        // Logic for insurance
        bankroll += insuranceChange(insurance, bankroll, dealer);
        
        return bankroll;
    }
    
    
    /**
     * Play the blackjack game. Initialize the bankroll and keep
     * playing roudns as long as the user wants to.
     */
    public Blackjack()
    {
        double bankroll = STARTING_BANKROLL;
        System.out.println("Starting bankroll: " + bankroll);
   
        while(true)
        {
            bankroll = playRound(bankroll);
            
            Scanner userInput = new Scanner(System.in);
       	 	System.out.print("Would you like to play again? (Y/N) ");
       	 	String playAgain = userInput.nextLine();
            
            //String playAgain = readLine("Would you like to play again? (Y/N) ");
            if(playAgain.equalsIgnoreCase("N"))
            {
                break;
            }
            System.out.println("\n      *\n   ********\n**************\n   ********\n      *\n");
        }
        
        System.out.println("Thanks for playing!");
    }
	
}
