package Engine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Gtk_command extends Command{

    public Gtk_command(App app, String command) {
        super(app,command);
        if(stopCommand) return;
        try{

            this.sendMessage(calcul(this.argsToString()));
        }catch (NumberFormatException e)
        {
            sendMessage(this.argsToString()+" is not an integer, so not a prime number.");
        }catch (Exception e)
        {
           app.setMessage("We can't do the maths. Use gtk -h if you need help for syntax");
        }

    }

    public String calcul(String calcul) throws ScriptException {


        if (this.getOptions().contains("prim"))
            return isPrime(calcul);

        return calcul+" = "+evaluate(calcul);
        /*
        Pattern pattern = Pattern.compile("[\\+\\-\\*\\^\\/]");
        Matcher matcher = pattern.matcher(command);
        matcher.find();
        char op = matcher.group().charAt(0);
        pattern = Pattern.compile("[0-9]+");
        matcher = pattern.matcher(command);
        matcher.find();
        double a = Double.parseDouble(matcher.group());
        matcher.find();
        double b = Double.parseDouble(matcher.group());
        switch (op) {
            case '*':
                return (a + "*" + b + " = " + String.valueOf(a * b));

            case '/':
                return (a + "/" + b + " = " + String.valueOf(a / b));

            case '+':
                return (a + "+" + b + " = " + String.valueOf(a + b));

            case '-':
                return (a + "-" + b + " = " + String.valueOf(a - b));

            case '^':
                return (a + "^" + b + " = " + String.valueOf(Math.pow(a, b)));

            default:
                return "Operation not available";


        }
        */
    }

    public String isPrime(String calcul) {

            int number = evaluate(calcul).intValue();

            if (isPrime(number)) {
                return (calcul +" ( "+ number +" ) is prime");
            } else {
                return (calcul +" ( "+ number +" ) is not prime");
            }


    }

    private boolean isPrime(int number) {
        if (Math.abs(number) < 3) return false;
        Iterator<Integer> it = IntStream.range(2, (number / 2)+1).iterator();
        boolean isPrime = true;
        while (it.hasNext()) {
            int n = it.next();
            if (number % n == 0 && number != n) {
                isPrime = false;
            }
        }
        return isPrime;
    }
    public static Double evaluate(String calcul)
    {
        try{

            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            Object result = engine.eval(calcul);
            return Double.parseDouble(result.toString());
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }
}
