package tk.mamong_us.game;

public enum Map {
    SKELD("The Skeld"),
    MARISTENKOLLEG("Maristenkolleg");

    private String name;

    Map(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
