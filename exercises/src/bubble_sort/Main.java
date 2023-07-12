package bubble_sort;

import java.util.Arrays;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        int[] arr = {5, 3, 1, 2, 3};

        BubbleSort bubbleSort = new BubbleSort();

        bubbleSort.sort(arr);

        System.out.println(Arrays.toString(arr));
    }

}