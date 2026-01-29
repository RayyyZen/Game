package src;

/**
 * The main class
 * @version 1.1 (First world)
 * @since 1.0
 * @author Rayane
 */
public class Main {
    
    /**
     * The main method
     * @param args An array of String arguments passed into the main method from the input of the application
     */
    public static void main(String args[]){
        System.out.println("\nTotal number of players : " + Player.getNumberOfPlayers() + "\n");

        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");
        Player player3 = new Player();
        Player player4 = new Player();

        System.out.println(player1);
        System.out.println(player2);
        System.out.println(player3);
        System.out.println(player4);
        System.out.println();

        player1.modifyScore(50);
        player2.modifyScore(-90);

        System.out.println(player1);
        System.out.println(player2);
        System.out.println(player3);
        System.out.println(player4);
        System.out.println();

        System.out.println("Total number of players : " + Player.getNumberOfPlayers() + "\n");

        //Tests equals

        Player playerTmp1 = new Player("BOB");
        Player playerTmp2 = player2;

        System.out.println("Tests : ");
        System.out.println(player1.equals("Alice"));
        System.out.println(player1.equals(player2));
        System.out.println(player2.equals(playerTmp1));
        System.out.println(player2 == playerTmp1);
        System.out.println(player2.equals(playerTmp2));
        System.out.println("");

        //End of tests
    }
}