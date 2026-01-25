package src;

/**
 * The main class
 * @author Rayane
 */
public class Main {
    
    public static void main(String args[]){
        System.out.println("\nNombre total de joueurs : " + Player.getNumberOfPlayers() + "\n");

        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");
        Player player3 = new Player();
        Player player4 = new Player();

        System.out.println(player1);
        System.out.println(player2);
        System.out.println(player3);
        System.out.println(player4);
        System.out.println();

        player1.addScore(50);
        player2.reduceScore(90);

        System.out.println(player1);
        System.out.println(player2);
        System.out.println(player3);
        System.out.println(player4);
        System.out.println();

        System.out.println("Nombre total de joueurs : " + Player.getNumberOfPlayers() + "\n");

        //Tests equals

        Player playerTmp1 = new Player("BOB");
        Player playerTmp2 = player2;

        System.out.println("Tests : ");
        System.out.println(player1.equals("Alice"));
        System.out.println(player1.equals(player2));
        System.out.println(player2.equals(playerTmp1));
        System.out.println(player2 == playerTmp1);
        System.out.println(player2.equals(playerTmp2));
    }
}