package sample;


import Engine.App;
import javafx.scene.control.TextField;

public class Input extends TextField {
    private String command;
    private App app;

    public Input() {
        super();
        this.setOnKeyPressed(e-> System.out.println(e.getCode()));
    }
    public void setApp(App app)
    {
        this.app = app;
    }
}
