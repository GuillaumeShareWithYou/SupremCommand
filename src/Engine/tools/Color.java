package Engine.tools;

public enum Color {
    BLUE("#00F"), GREEN("#099109"), RED("#f00003"), ORANGE("#a94505"), YELLOW("CEC606"), LIGHTBLUE("#148FFF"), GREY("#8A8A8A"), BLACK("#000");

    private String color;

    Color(String color) {
        this.color = color;
    }
    public String getColor(){
        return color;
    }
}
