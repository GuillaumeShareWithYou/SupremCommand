package sample;
import Engine.App;
import javafx.beans.Observable;

public class Controller {

    PromptManager promptManager;
    InputManager inputManager;
    public Controller(View v, App app)
    {
        promptManager = new PromptManager(v.getPrompt(),app);
        inputManager = new InputManager(v.getInput(),app);

        //ne pas perdre le focus sur input
        v.getPrompt().setOnMouseClicked(e->v.getInput().requestFocus());
    }
    public Controller(){}

}
