package view;

import controller.Controller;
import exception.MyException;

//import javax.imageio.IIOException;
import java.io.IOException;

public class RunExample extends Command {
    private Controller ctr;

    public RunExample(String key, String desc, Controller ctr) {
        super(key, desc);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try {
            ctr.allStep();
        } catch (InterruptedException e2) {

        }
        //here you must treat the exceptions that can not be solved in the controller
    }

    @Override
    public void executeOneStep() {
        //ctr.oneStepForAllPrg();
    }
}