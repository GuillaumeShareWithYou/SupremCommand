package sample;

import Engine.App;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;


public class InputManager {
    private TextField input;
    private App app;
    private PileMessage pileMessage;
    public InputManager(TextField input, App app) {
        this.input = input;
        this.app = app;
         pileMessage = new PileMessage();
        input.setOnKeyPressed(e->{
            if(e.getCode()== KeyCode.ENTER)
            {
                String command = input.getText();
                if(command.isEmpty()) return;
                app.work(command);
                pileMessage.add(command);
                input.setText("");
            }else if(e.getCode()==KeyCode.UP){
                input.setText(pileMessage.lireMessagePrec());
                input.positionCaret(input.getText().length());
            }else if(e.getCode()==KeyCode.DOWN)
            {
                input.setText(pileMessage.lireMessageSuiv());
                input.positionCaret(input.getText().length());
            }else{
                // on verra
            }
        });
    }
}
