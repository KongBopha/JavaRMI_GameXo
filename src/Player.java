public class Player {

    // Fields representing the player's ID, name, timestamp, and associated match
    private int id;
    private String name;
    private long timestamp;
    private Match match;
  
    // Constructor to initialize the player's details
    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.match = null; // Initially, the player is not in a match
        this.timestamp = System.currentTimeMillis(); // Sets the current timestamp
    }
  
    // Getter methods
    public int getId() { 
        return this.id; 
    }
  
    public String getName() { 
        return this.name; 
    }
  
    public Match getMatch() { 
        return this.match; 
    }
  
    // Setter method for match
    public void setMatch(Match match) { 
        this.match = match; 
    }
  
    // Updates the player's timestamp to the current system time
    public void updateTimestamp() {
        this.timestamp = System.currentTimeMillis();
    }
  
    // Checks if the player has timed out
    public boolean hasTimedOut() {
        return (System.currentTimeMillis() - this.timestamp) >= TicTacToe.TIMEOUT_MATCH * 1000;
    }
  }
  