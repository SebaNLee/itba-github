package eda;

public class ArraysUtilities {
    public static void main(String[] args) {
        int[] unsorted = new int[] {34, 10, 8, 60, 21, 17, 28, 30, 2, 70, 50, 15, 62, 40};
        insertionsort(unsorted);
        for (int i : unsorted) {
            System.out.print(i + " ");
        }
        System.out.print('\n');
        quicksort(unsorted);
        for (int i : unsorted) {
            System.out.print(i + " ");
        }
        System.out.print('\n');
        mergesort(unsorted);
        for (int i : unsorted) {
            System.out.print(i + " ");
        }
    }
    //ORDENACION POR INSERTIONSORT
    public static void insertionsort(int[] unsorted) {
        for (int i = 0; i < unsorted.length; i++) {
            int elem = unsorted[i];
            int j;
            for (j = i -1 ; j > 0 && elem < unsorted[j] ; j--) {
                unsorted[j+1] = unsorted[j];
            }
            unsorted[j+1] = elem;
        }
    }
    //ORDENACION POR QUICKSORT
    public static void quicksort(int[] unsorted) {
        quicksort (unsorted, unsorted.length-1);
    }
    public static void quicksort(int[] unsorted, int cantElements) {
        quicksortHelper (unsorted, 0, cantElements);
    }
    private static void quicksortHelper (int[] unsorted, int leftPos, int rightPos) {
        if (rightPos <= leftPos )
            return;
        // tomamos como pivot el primero. Podria ser otro elemento
        int pivotValue= unsorted[leftPos];
        // excluimos el pivot del cjto.
        swap(unsorted, leftPos, rightPos);
        // particionar el cjto sin el pivot
        int pivotPosCalculated= partition(unsorted, leftPos, rightPos-1, pivotValue);
        // el pivot en el lugar correcto
        swap(unsorted, pivotPosCalculated, rightPos);
        // salvo unsorted[middle] todo puede estar mal pero cada particion es autonoma
        quicksortHelper(unsorted, leftPos, pivotPosCalculated - 1);
        quicksortHelper(unsorted, pivotPosCalculated + 1, rightPos );
    }
    static private int partition(int[] unsorted, int leftPos, int rightPos, int pivotValue) {
        while (leftPos <= rightPos) {
            while (leftPos <= rightPos && unsorted[leftPos] < pivotValue)
                leftPos++;
            while (leftPos <= rightPos && unsorted[rightPos] < pivotValue)
                rightPos--;
            if(leftPos <= rightPos)
                swap(unsorted, leftPos++, rightPos--);
        }
        return leftPos;
    }
    static private void swap(int[] unsorted, int pos1, int pos2) {
        int auxi= unsorted[pos1];
        unsorted[pos1]= unsorted[pos2];
        unsorted[pos2]= auxi;
    }
    //ORDENACION POR MERGESORT
    public static void mergesort(int[] unsorted) {
        mergesort(unsorted, unsorted.length-1);
    }
    public static void mergesort(int[] unsorted, int end) {
        int[] aux = new int[unsorted.length];
        System.arraycopy(unsorted, 0, aux, 0, unsorted.length);
        partition(aux, 0, end, unsorted);
    }
    public static void mergesortHelper(int[] unsorted, int left, int mid, int right, int[] aux) {
        int posLeft = left;
        int posRight = mid + 1;
        for (int i = left; i <= right; i++) {
            if (posLeft <= mid && ( posRight > right || unsorted[posLeft] <= unsorted[posRight])) {
                aux[i] = unsorted[posLeft++];
            }
            else {
                aux[i] = unsorted[posRight++];
            }
        }
    }
    public static void partition(int[] unsorted, int left, int right, int[] aux) {
        if (right == left) {
            return;
        }
        int mid = (left+right)/2;
        partition(aux, left, mid, unsorted);
        partition(aux, mid + 1, right, unsorted);
        mergesortHelper(unsorted, left, mid, right, aux);
    }
}