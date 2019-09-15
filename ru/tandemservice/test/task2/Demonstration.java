package ru.tandemservice.test.task2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Demonstration {
    public static void main(String[] args) {
        IElementNumberAssigner task2 = Task2Impl.INSTANCE;

        List<IElement> elements = new ArrayList<>();

        ElementExampleImpl.Context context = new ElementExampleImpl.Context();

        elements.add(new ElementExampleImpl(context, 124));
        elements.add(new ElementExampleImpl(context, 3));
        elements.add(new ElementExampleImpl(context, 1));
        elements.add(new ElementExampleImpl(context, 2));
        elements.add(new ElementExampleImpl(context, 5));

        System.out.println("numbers before assignment:");
        for (IElement element : elements) {
            System.out.println(element.getNumber());
        }

        task2.assignNumbers(Collections.unmodifiableList(elements));

        System.out.println("numbers after assignment:");
        for (IElement element : elements) {
            System.out.println(element.getNumber());
        }

        System.out.println("assign calls number: " + context.getOperationCount());
    }
}
