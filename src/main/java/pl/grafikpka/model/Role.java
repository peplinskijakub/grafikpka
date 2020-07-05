package pl.grafikpka.model;

public enum Role {
    USER("USER"),
    MODERATOR("MODERATOR"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
