package Engine;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Gtk_command extends Command{

    public Gtk_command(App app, String command) {
        super(app,command);
        if(stopCommand) return;
        try{
            this.sendMessage(calcul(command));
        }catch (Exception e)
        {
           app.setMessage("Use gtk -h if you need");
        }

    }

    public String calcul(String command) {

        command = command.substring(3); // retire "gtk" devant la commande
        command = command.replaceFirst(" ","");
        if (this.getOptions().contains("prim"))
            return isPrime(command);

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
    }

    public String isPrime(String command) {

            Pattern pattern = Pattern.compile("[0-9]+");
            Matcher matcher = pattern.matcher(command);
            matcher.find();
            int number = Integer.parseInt(matcher.group());
            System.out.println(number);
            if (isPrime(number)) {
                return (number + " is prime");
            } else {
                return (number + " is not prime");
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
}
