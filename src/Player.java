package src;

/**
 * The player class
 * @author Rayane
 */
public class Player {
    private final String name;
    private int score = 0;
    private static int numberOfPlayers = 0;

    /**
     * The player constructor with name and score
     * @param name The name of the player
     * @param score The score of the player
     */
    public Player(String name, int score){
        this.name = name;
        this.score = score;
        numberOfPlayers++;
    }

    /**
     * The player constructor with only name
     * @param name The name of the player
     */
    public Player(String name){
        this(name,0);
    }

    /**
     * The player constructor without any parameters
     */
    public Player(){
        this("Player" + (numberOfPlayers + 1),0);
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

    @Override
    public String toString(){
        if(this.score <= 1){
            return this.name + " : " + this.score + " pt";
        }
        return this.name + " : " + this.score + " pts";
    }

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
