import java.util.Scanner;

public class Tester {

	public static void main(String[] args) {
		
		//Scanner
		Scanner userInput = new Scanner(System.in);
       	System.out.println("What is your bet?");
       	String stringBet = userInput.nextLine();
       	//userInput.close();
       	
       	// Print results
       	int bet = Integer.parseInt(stringBet);
       	System.out.println("Result 1: " + bet);
       
       

       	
      //Scanner
  		
     	System.out.println("What is your bet?");
     	String stringBetT = userInput.nextLine();
     	userInput.close();
     	
     	// Print results
     	int betT = Integer.parseInt(stringBetT);
     	System.out.print(betT);
	}

}
