package Engine;

public enum Color {
    BLUE("#00F"), GREEN("#099109"), RED("#f00003"), ORANGE("#a94505"), YELLOW("CEC606"), LIGHTBLUE("rgb(71,92,165)"), BLACK("#000");

    private String color;

    Color(String color) {
        this.color = color;
    }
    public String getColor(){
        return color;
    }
}
