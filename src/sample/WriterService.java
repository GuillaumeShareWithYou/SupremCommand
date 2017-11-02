package sample;


import java.util.ArrayList;
import java.util.List;

public class WriterService {

    List<Writer> writers;

    public WriterService() {
        writers = new ArrayList<>();
    }

    public void add(Writer w) {
        writers.add(w);
        while (writers.get(0) != w) {
            try {
                synchronized (w){
                    w.wait();
                }
            } catch (InterruptedException e) {
                System.out.println("probleme dans add");
            }

        }
    }

    public  void remove(Writer w) {
        try {
            writers.remove(w);
            if (writers.size() > 0)
            {
                synchronized (writers.get(0))
                {
                    writers.get(0).notify();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}