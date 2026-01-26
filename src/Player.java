package src;

/**
 * The player class
 * @author Rayane
 */
public class Player {
    private final String name;
    private int score;
    private static int numberOfPlayers = 0;

    /**
     * The player constructor with only name
     * @param name The name of the player
     */
    public Player(String name){
        this.name = name;
        this.score = 0;
        numberOfPlayers++;
    }

    /**
     * The player constructor without any parameters
     */
    public Player(){
        this("Player" + (numberOfPlayers + 1));
    }

    /**
     * Returns the name of the player
     * @return The name of the player
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the score of the player
     * @return The score of the player
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Returns the number of players created
     * @return The number of players created
     */
    public static int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    /**
     * Adds a score to the player's current score
     * @param score The score that will be added to the player's score
     */
    public void addScore(int score){
        this.score += score;
    }

    /**
     * Substract a score from the player's current score
     * @param score The score that will be deducted from the player's score
     */
    public void reduceScore(int score){
        this.score -= score;
        if(this.score < 0){
            this.score = 0;
        }
    }

    /**
     * Displays the player's name and score
     */
    public void displayPlayer(){
        System.out.println(this.getName() + " | Score : " + this.getScore());
    }

    /**
     * Returns a String from the player's attributs
     * @return A String that contains the name and the score of the player
     */
    @Override
    public String toString(){
        String str = this.name + " : " + this.score + " pt";
        if(this.score > 1){
            str += "s";
        }
        return str;
    }

    /**
     * Checks if a player is equal to an object
     * @param obj The object that will be compared to the player
     * @return true if they are equal or false if they aren't
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        else if(obj == this){
            return true;
        }
        else if(!(obj instanceof Player)){
            return false;
        }
        Player player = (Player)obj;
        if(this.name.toLowerCase().equals(player.getName().toLowerCase())){
            return true;
        }
        return false;
    }
}
