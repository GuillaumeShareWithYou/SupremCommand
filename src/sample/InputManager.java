package sample;

import Engine.App;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InputManager {
    private TextField input;
    private App app;
    private PileMessage pileMessage;
    final KeyCombination keyCombinationShiftC = new KeyCodeCombination(KeyCode.TAB, KeyCombination.SHIFT_DOWN);
    public InputManager(TextField input, App app) {
        this.input = input;
        this.app = app;
        pileMessage = new PileMessage();
        input.setOnKeyPressed(e -> {

            if (e.getCode() == KeyCode.ENTER) {
                String command = input.getText();
                if (seemsOk(command)) {
                    app.work(command);
                }
                pileMessage.add(command);
                input.setText("");
            } else if (e.getCode() == KeyCode.UP) {
                input.setText(pileMessage.lireMessagePrec());
                input.positionCaret(input.getText().length());
            } else if (e.getCode() == KeyCode.DOWN) {
                input.setText(pileMessage.lireMessageSuiv());
                input.positionCaret(input.getText().length());
            }else if(keyCombinationShiftC.match(e))
            {
                String autocomplete = app.seekAutocompletion(input.getText(),false);
                if(autocomplete!=null) {
                    input.setText(autocomplete);
                }
                input.requestFocus();
                input.positionCaret(input.getText().length());

            }
            else if(e.getCode()==KeyCode.TAB){
                String autocomplete = app.seekAutocompletion(input.getText(),true);
                if(autocomplete!=null) {
                    input.setText(autocomplete);
                }
                    input.requestFocus();
                    input.positionCaret(input.getText().length());
            }
        });
    }

    public boolean seemsOk(String command) {
        Pattern pattern = Pattern.compile("^[^\\s *0-9].*$");
        Matcher matcher = pattern.matcher(command);
        return matcher.find();
    }
}
