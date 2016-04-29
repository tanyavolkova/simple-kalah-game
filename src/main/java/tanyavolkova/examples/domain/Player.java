package tanyavolkova.examples.domain;

/**
 * @author Tetiana Volkova
 */
public class Player {

    private String name;

    private String id;

    private int pitsetId;


    public Player(String name, String id, int pitsetId) {
        this.name = name;
        this.id = id;
        this.pitsetId = pitsetId;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getPitsetId() {
        return pitsetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Player player = (Player)o;

        return id.equals(player.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
