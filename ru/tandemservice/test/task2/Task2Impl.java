package ru.tandemservice.test.task2;



import java.util.*;

/**
 * <h1>Задание №2</h1>
 * Реализуйте интерфейс {@link IElementNumberAssigner}.
 *
 * <p>Помимо качества кода, мы будем обращать внимание на оптимальность предложенного алгоритма по времени работы
 * с учетом скорости выполнения операции присвоения номера:
 * большим плюсом (хотя это и не обязательно) будет оценка числа операций, доказательство оптимальности
 * или указание области, в которой алгоритм будет оптимальным.</p>
 */
public class Task2Impl implements IElementNumberAssigner {

    // ваша реализация должна работать, как singleton. даже при использовании из нескольких потоков.
    public static final IElementNumberAssigner INSTANCE = new Task2Impl();

    private Task2Impl() { }

    @Override
    public void assignNumbers(final List<IElement> elements) {
        //Перебираем по порядку все переданные элементы
        for (int i = 0; i < elements.size(); i++) {
            //Если элементу уже присвоен нужный номер, переходим дальше
            if (elements.get(i).getNumber() == i) continue;
            //присваиваем элементу порядковый номер
            assignNumber(elements.get(i), i, elements, new HashSet<>());
        }
    }

    /**
     * Присвоить элементу {@code element} число {@code number}.
     * Если число {@code number} уже присутствует у одного из элементов,
     * то метод вызывается рекрсивно для устранения этой ситуации
     */
    private void assignNumber(
            IElement element,
            int number,
            List<IElement> elements,
            Set<Integer> numbersInWork
    ) {
        //помещаем в отдельуюю коллекцию
        List<Integer> currentNumbersList = new ArrayList<>();

        for (IElement elem : elements) {
            currentNumbersList.add(elem.getNumber());
        }

        //Если среди элементов уже встречается это число
        if (currentNumbersList.contains(number)) {
            //если возникла ситуации замкнутой рекурсии
            if (!numbersInWork.add(number)) {
                //присваиваем элементу случайной число, которое ещё не встречается среди элементов (для выхода из замкнутой рекурсии)
                assignNumber(element, generateMissingValue(currentNumbersList), elements, numbersInWork);
                return;
            } else {
                //находим элемент с номером, нужным нам для текущего присвоения
                IElement conflictElement = elements.get(currentNumbersList.indexOf(number));
                //и задаем этому элементу его соответствующий номер
                assignNumber(conflictElement, currentNumbersList.indexOf(number), elements, numbersInWork);
            }
        }
        //присваиваем текущему элементу заданный номер
        element.setupNumber(number);
    }

    /**
     * Сгенерировать случайной число, которое не содержится в массиве чисел
     */
    private int generateMissingValue(List<Integer> numbers) {
        int random = new Random().nextInt();
        if (numbers.contains(random)) {
            return generateMissingValue(numbers);
        }
        return random;
    }
}
