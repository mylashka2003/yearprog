package ru.yearprog.yearprog;

import ru.yearprog.yearprog.input.IntegerInput;
import ru.yearprog.yearprog.windows.MainFrame;
import ru.yearprog.yearprog.workers.CountCycle;

public class MainProperties {
    private static int fieldSize;
    private static MainFrame f;
    private static IntegerInput input;
    private static CountCycle countCycle;


    public static int getFieldSize() {
        return fieldSize;
    }

    public static void setFieldSize(int fieldSize) {
        MainProperties.fieldSize = fieldSize;
    }

    public static MainFrame getF() {
        return f;
    }

    public static void setF(MainFrame f) {
        MainProperties.f = f;
    }

    public static IntegerInput getInput() {
        return input;
    }

    public static void setInput(IntegerInput input) {
        MainProperties.input = input;
    }

    public static CountCycle getCountCycle() {
        return countCycle;
    }

    public static void setCountCycle(CountCycle countCycle) {
        MainProperties.countCycle = countCycle;
    }
}
