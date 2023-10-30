package it.unibo.collections;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Example class using {@link List} and {@link Map}.
 *
 */
public final class UseListsAndMaps {

    private UseListsAndMaps() {
    }

    /**
     * @param s
     *          unused
     */
    public static void main(final String... s) {
        final int START_INDEX = 1000;
        final int END_INDEX = 2000;
        final int ELEMS = 100_000;
        /*
         * 1) Create a new ArrayList<Integer>, and populate it with the numbers
         * from 1000 (included) to 2000 (excluded).
         */
        final List<Integer> numbers = new ArrayList<>(END_INDEX - START_INDEX);
        for (int i = START_INDEX; i < END_INDEX; i++) {
            numbers.add(i);
        }
        /*
         * 2) Create a new LinkedList<Integer> and, in a single line of code
         * without using any looping construct (for, while), populate it with
         * the same contents of the list of point 1.
         */
        final List<Integer> copyOfNumbers = new LinkedList<>(numbers);
        /*
         * 3) Using "set" and "get" and "size" methods, swap the first and last
         * element of the first list. You can not use any "magic number".
         * (Suggestion: use a temporary variable)
         */
        final int tmp = numbers.set(numbers.size() - 1, numbers.get(0));
        numbers.set(0, tmp);
        /*
         * 4) Using a single for-each, print the contents of the arraylist.
         */
        for (var elem : numbers) {
            System.out.print(elem + " ");
        }
        System.out.println();
        /*
         * 5) Measure the performance of inserting new elements in the head of
         * the collection: measure the time required to add 100.000 elements as
         * first element of the collection for both ArrayList and LinkedList,
         * using the previous lists. In order to measure times, use as example
         * TestPerformance.java.
         */
        long arrayListTime = System.nanoTime();
        for (int i = 1; i <= ELEMS; i++) {
            numbers.add(0, i);
        }
        arrayListTime = System.nanoTime() - arrayListTime;
        var arrayListMillis = TimeUnit.NANOSECONDS.toMillis(arrayListTime);

        long linkedListTime = System.nanoTime();
        for (int i = 1; i <= ELEMS; i++) {
            copyOfNumbers.add(0, i);
        }
        linkedListTime = System.nanoTime() - linkedListTime;
        var linkedListMillis = TimeUnit.NANOSECONDS.toMillis(linkedListTime);
        System.out.println(// NOPMD
                "Array List time: "
                        + arrayListTime
                        + "ns ("
                        + arrayListMillis
                        + "ms)"
                        + "\nLinked List time: "
                        + linkedListTime
                        + "ns ("
                        + linkedListMillis
                        + "ms)");
        /*
         * 6) Measure the performance of reading 1000 times an element whose
         * position is in the middle of the collection for both ArrayList and
         * LinkedList, using the collections of point 5. In order to measure
         * times, use as example TestPerformance.java.
         */
        arrayListTime = System.nanoTime();
        for (int i = 1; i <= 1000; i++) {
            numbers.get(numbers.size() / 2);
        }
        arrayListTime = System.nanoTime() - arrayListTime;
        arrayListMillis = TimeUnit.NANOSECONDS.toMillis(arrayListTime);

        linkedListTime = System.nanoTime();
        for (int i = 1; i <= 1000; i++) {
            copyOfNumbers.get(copyOfNumbers.size() / 2);
        }
        linkedListTime = System.nanoTime() - linkedListTime;
        linkedListMillis = TimeUnit.NANOSECONDS.toMillis(linkedListTime);
        System.out.println(// NOPMD
                "Array List time: "
                        + arrayListTime
                        + "ns ("
                        + arrayListMillis
                        + "ms)"
                        + "\nLinked List time: "
                        + linkedListTime
                        + "ns ("
                        + linkedListMillis
                        + "ms)");
        /*
         * 7) Build a new Map that associates to each continent's name its
         * population:
         *
         * Africa -> 1,110,635,000
         *
         * Americas -> 972,005,000
         *
         * Antarctica -> 0
         *
         * Asia -> 4,298,723,000
         *
         * Europe -> 742,452,000
         *
         * Oceania -> 38,304,000
         */
        Map<String, Long> map = new LinkedHashMap<>();
        map.put("Africa", 1_110_635_000L);
        map.put("Americas", 972_005_000L);
        map.put("Antarctica", 0L);
        map.put("Asia", 4_298_723_000L);
        map.put("Europe", 742_452_000L);
        map.put("Oceania", 38_304_000L);
        /*
         * 8) Compute the population of the world
         */
        long totalPopulation = 0L;
        for (var elem : map.values()) {
            totalPopulation = totalPopulation + elem;
        }
        System.out.println("Total world population is: " + totalPopulation);
    }
}
