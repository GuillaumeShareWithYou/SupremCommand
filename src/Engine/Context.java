package Engine;

public enum Context implements Comparable<Context> {
    SLEEP("?","sleep"),
    INIT("Sys-init","init"),
    STANDARD("E-corp","standard"),
    MANAGER("g@math","manager");

    private String symbole;
    private String name;

    Context (String sym, String n)
    {
        symbole = sym;
        name = n;

    }

    public String getSymbole() {
        return symbole;
    }

    public String getName() {
        return name;
    }

    public void setSymbole(String symbole) {
        this.symbole = symbole;
    }

}
