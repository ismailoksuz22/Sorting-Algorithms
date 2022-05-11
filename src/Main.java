import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class Main {
    // Arrays
    public static int[] ten = createArray(10);
    public static int[] hundred = createArray(100);
    public static int[] thousand = createArray(1000);
    public static int[] tenThousand = createArray(10000);
    public static int[] hundredThousand = createArray(100000);

    // Temporary Arrays
    public static int[] tmpTen = new int[10];
    public static int[] tmpHundred = new int[100];
    public static int[] tmpThousand = new int[1000];
    public static int[] tmpTenThousand = new int[10000];
    public static int[] tmpHundredThousand = new int[100000];

    // Decimal Format to Show Milliseconds
    public static NumberFormat formatter = new DecimalFormat("#0.0000");

    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        int k;

        // Gets the value of K from the user to find the kthSmallestValue.
        // Forces the user to enter an integer value greater than or equal to 1.
        while(true){
            System.out.print(">> Give k Value for kthSmallest (k>=1): ");

            try {
                k = Integer.parseInt(scanner.nextLine());
                if (k >= 1)
                    break;
                else
                    System.out.println("[!] The input must be greater than or equal to 1.");
            } catch (NumberFormatException e) {
                System.out.println("[!] The input must be integer.");
            }
        }

        // Measures the speed of each sorting algorithm for array with 10 indexes.
        System.out.println("\n<< Results of Array with 10 Indexes (ms) >>");
        calculateTime(ten, tmpTen, k);
        System.out.println("\n");

        // Measures the speed of each sorting algorithm for array with 100 indexes.
        System.out.println("<< Results of Array with 100 Indexes (ms) >>");
        calculateTime(hundred, tmpHundred, k);
        System.out.println("\n");

        // Measures the speed of each sorting algorithm for array with 1000 indexes.
        System.out.println("<< Results of Array with 1000 Indexes (ms) >>");
        calculateTime(thousand, tmpThousand, k);
        System.out.println("\n");

        // Measures the speed of each sorting algorithm for array with 10 Thousand indexes.
        System.out.println("<< Results of Array with 10 Thousand Indexes (ms) >>");
        calculateTime(tenThousand, tmpTenThousand, k);
        System.out.println("\n");

        // Measures the speed of each sorting algorithm for array with 100 Thousand indexes.
        System.out.println("<< Results of Array with 100 Thousand Indexes (ms) >>");
        calculateTime(hundredThousand, tmpHundredThousand, k);
        System.out.println("\n");
    }

    // Creates random numbers between 1 and 10 times the length parameter it takes,
    // and creates an array in the size of the parameter it receives.
    public static int[] createArray(int length){
        int[] array = new int[length];

        Random random = new Random();
        for(int i=0; i<length; i++)
            array[i] = random.nextInt(length*10) + 1;
        return array;
    }

    public static int[] setSameValue(int[] array, int value){
        for(int i=0; i<array.length; i++){
            array[i] = value;
        }

        return array;
    }

    // Copies an array with its contents into another array.
    private static void copyArray(int[] temp, int[] array){
        System.arraycopy(array, 0, temp, 0, array.length);
    }

    // Tests the array parameter it receives in all sorting algorithms and returns its measurements.
    public static void calculateTime(int[] array, int[] tmpArray, int k){
        long startTime;
        long timeDifference=0;
        String totalTime, totalTimeBest, totalTimeWorst;
        int kthSmallest = 0;

        // QUICK SORT ALGORITHM
        copyArray(tmpArray,array);
        startTime = System.nanoTime();
        kthSmallest = quickSort(tmpArray, 0, array.length-1, k);
        timeDifference = System.nanoTime() - startTime;
        totalTime = formatter.format(timeDifference / Math.pow(10, 6));

        // For the worst case we order the array.
        Arrays.stream(tmpArray).sorted();

        startTime = System.nanoTime();
        kthSmallest = quickSort(tmpArray, 0, array.length-1, k);
        timeDifference = System.nanoTime() - startTime;
        totalTimeWorst = formatter.format(timeDifference / Math.pow(10, 6));

        if(kthSmallest==0)
            System.out.print("Quick Sort: Stack Error!");
        if(kthSmallest==-1)
            System.out.print("Quick Sort: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: Out of Bounds Array\n");
        else
            System.out.print("Quick Sort: \n\t" + totalTime + " ms - Worst: " + totalTimeWorst + " ms | " + k +  "th Smallest Element: "+kthSmallest+"\n");
        //////////////////////////////

        // HEAP SORT ALGORITHM
        copyArray(tmpArray,array);

        startTime = System.nanoTime();
        kthSmallest = heapSort(tmpArray, k);
        totalTime = formatter.format((System.nanoTime() - startTime) / Math.pow(10, 6));

        // For the best case we make all the elements the same number.
        setSameValue(tmpArray, kthSmallest);

        startTime = System.nanoTime();
        kthSmallest = heapSort(tmpArray, k);
        totalTimeBest = formatter.format((System.nanoTime() - startTime) / Math.pow(10, 6));

        if(kthSmallest!=-1)
            System.out.print("Heap Sort: \n\t" + totalTime + " ms - Best: " + totalTimeBest + " ms | " + k +  "th Smallest Element: "+kthSmallest+"\n");
        else
            System.out.print("Heap Sort: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: Out of Bounds Array\n");
        //////////////////////////////

        // MERGE SORT ALGORITHM
        copyArray(tmpArray,array);
        startTime = System.nanoTime();
        kthSmallest = mergeSort(tmpArray, 0, array.length-1, k);
        totalTime = formatter.format((System.nanoTime() - startTime) / Math.pow(10, 6));

        // For the best case we order the array.
        Arrays.stream(tmpArray).sorted();

        startTime = System.nanoTime();
        kthSmallest = mergeSort(tmpArray, 0, array.length-1, k);
        totalTimeBest = formatter.format((System.nanoTime() - startTime) / Math.pow(10, 6));


        if(kthSmallest!=-1)
            System.out.print("Merge Sort: \n\t" + totalTime + " ms - " + " Best: " + totalTimeBest + " ms | " + k + "th Smallest Element: "+kthSmallest+"\n");
        else
            System.out.print("Merge Sort: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: Out of Bounds Array\n");
        //////////////////////////////

        // INSERTION SORT ALGORITHM
        copyArray(tmpArray,array);
        startTime = System.nanoTime();
        kthSmallest = insertionSort(tmpArray, k);
        totalTime = formatter.format((System.nanoTime() - startTime) / Math.pow(10, 6));

        // For the best case we order the array.
        Arrays.stream(tmpArray).sorted();

        startTime = System.nanoTime();
        kthSmallest = insertionSort(tmpArray, k);
        totalTimeBest = formatter.format((System.nanoTime() - startTime) / Math.pow(10, 6));



        if(kthSmallest!=-1)
            System.out.print("Insertion Sort: \n\t" + totalTime + " ms - " + "Best: " + totalTimeBest + " ms | " + k +  "th Smallest Element: "+kthSmallest+"\n");
        else
            System.out.print("Insertion Sort: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: Out of Bounds Array\n");
        //////////////////////////////


        // SELECTION SORT ALGORITHM
        copyArray(tmpArray,array);
        startTime = System.nanoTime();
        kthSmallest = selectionSort(tmpArray, k);
        totalTime = formatter.format((System.nanoTime() - startTime) / Math.pow(10, 6));

        if(kthSmallest!=-1)
            System.out.print("Selection Sort: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: "+kthSmallest+"\n");
        else
            System.out.print("Selection Sort: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: Out of Bounds Array\n");
        //////////////////////////////


        // QUICK SELECT ALGORITHM
        copyArray(tmpArray,array);
        startTime = System.nanoTime();
        kthSmallest = quickSelect(tmpArray, 0, array.length-1 , k);
        totalTime = formatter.format((System.nanoTime() - startTime) / Math.pow(10, 6));

        if(kthSmallest!=-1)
            System.out.print("Quick Select: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: "+kthSmallest+"\n");
        else
            System.out.print("Quick Select: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: Out of Bounds Array\n");
        //////////////////////////////

        // QUICK SELECT (median-of-three) ALGORITHM
        copyArray(tmpArray,array);
        startTime = System.nanoTime();
        kthSmallest = quickSelect3(tmpArray, 0, array.length-1 , k);
        totalTime = formatter.format((System.nanoTime() - startTime) / Math.pow(10, 6));

        if(kthSmallest!=-1)
            System.out.print("Quick Select (median-of-three): \n\t" + totalTime + " ms | " + k +  "th Smallest Element: "+kthSmallest+"\n");
        else
            System.out.print("Quick Select (median-of-three): \n\t" + totalTime + " ms | " + k +  "th Smallest Element: Out of Bounds Array\n");
        //////////////////////////////
    }

    //////////////////////////////////////
    ///////// COMMON FUNCTIONS////////////
    //////////////////////////////////////

    static void swap(int[] array, int i, int j)
    {
        int temporary = array[i];
        array[i] = array[j];
        array[j] = temporary;
    }

    static int partition(int[] array, int lower, int upper){
        int p = lower, j;

        for(j=lower+1; j <= upper; j++)
            if(array[j] < array[lower])
                swap(array, ++p, j);

        swap(array, lower, p);

        return p;
    }

    //////////////////////////////////////
    ////////// INSERTION SORT ////////////
    //////////////////////////////////////

    static int insertionSort(int array[], int k) {
        int length = array.length, temporary, j;

        if(k>array.length)
            return -1;

        for (int i = 1; i < length; i++) {
            temporary = array[i];
            for (j = i; j > 0; j--) {
                if (array[j - 1] > temporary)
                    array[j] = array[j - 1];
                else
                    break;
            }
            array[j] = temporary;
        }

        return array[k-1];
    }

    //////////////////////////////////////
    ////////// MERGE SORT ////////////////
    //////////////////////////////////////

    static int mergeSort(int array[], int lower, int upper, int k) {
        if(k>array.length)
            return -1;

        if (lower >= upper)
            return 0;

        int m = (lower + upper) / 2;

        mergeSort(array, lower, m, k);
        mergeSort(array, m + 1, upper, k);
        merge(array, lower, upper);

        return array[k-1];
    }

    private static void merge(int array[], int lower, int upper) {
        int m = (lower + upper) / 2;
        int a[] = new int[m - lower + 1];
        int b[] = new int[upper - m];
        int i, k = 0, k1 = 0, k2 = 0;

        for (i = lower; i <= m; i++, k++)
            a[k] = array[i];

        k = 0;

        for (; i <= upper; i++, k++)
            b[k] = array[i];

        for (i = lower; i <= upper && k1 < m - lower + 1 && k2 < upper - m; i++) {
            if (a[k1] < b[k2]) {
                array[i] = a[k1];
                k1++;
            } else {
                array[i] = b[k2];
                k2++;
            }
        }

        for (; k1 < m - lower + 1; k1++)
            array[i++] = a[k1];
        for (; k2 < upper - m; k2++)
            array[i++] = b[k2];
    }

    //////////////////////////////////////
    ////////// QUICK SORT ////////////////
    //////////////////////////////////////

    static int quickSort(int[] array, int lower, int upper, int k){
        if(k>array.length)
            return -1;

        if(lower < upper){

            try {
                int p = partition(array, lower, upper);
                quickSort(array, lower, p - 1, k);
                quickSort(array, p + 1, upper, k);
            }
            catch (StackOverflowError e){
                return 0;
            }
        }

        return array[k-1];
    }

    //////////////////////////////////////
    ////////// SELECTION SORT ////////////
    //////////////////////////////////////

    static int selectionSort(int array[], int k) {
        int length = array.length, position;

        if(k>length)
            return -1;

        for (int i = 0; i < length; i++) {
            position = i;
            for (int j = i + 1; j < length; j++) {
                if (array[j] < array[position])
                    position = j;
            }
            swap(array, i, position);
        }

        return array[k-1];
    }

    //////////////////////////////////////
    ///////////// HEAP SORT //////////////
    //////////////////////////////////////

    static int heapSort(int array[], int k) {
        if(k>array.length)
            return -1;

        makeMaxHeap(array);

        for (int i = array.length - 1; i > 0; i--) {
            swap(array, i, 0);
            heapAdjust(array, 0, i);
        }

        return array[k-1];
    }

    private static void makeMaxHeap(int array[]) {
        int length = array.length;

        for (int i = length / 2 - 1; i >= 0; --i) {
            heapAdjust(array, i, length);
        }
    }

    private static void heapAdjust(int array[], int i, int n) {
        int j = 2 * i + 1;
        int temporary = array[i];

        while (j < n) {
            if (j < n - 1 && array[j] < array[j + 1])
                j++;
            if (temporary > array[j])
                break;
            array[(j - 1) / 2] = array[j];
            j = 2 * j + 1;
        }

        array[(j - 1) / 2] = temporary;
    }

    //////////////////////////////////////
    ////////// QUICK SELECT //////////////
    //////////////////////////////////////

    public static int quickSelect(int[] array, int lower,
                                  int upper, int k)
    {
        if(k>array.length)
            return -1;

        // Find the partition
        int partition = partition(array, lower, upper);

        // If partition value is equal to the kth position,
        // return value at k.
        if (partition == k - 1)
            return array[partition];

        // If partition value is less than kth position,
        // search right side of the array.
        else if (partition < k - 1)
            return quickSelect(array, partition + 1, upper, k);

        // If partition value is more than kth position,
        // search left side of the array.
        else
            return quickSelect(array, lower, partition - 1, k);
    }

    //////////////////////////////////////
    /// QUICK SELECT (median-of-three) ///
    //////////////////////////////////////

    public static int quickSelect3(int[] array, int lower,
                                  int upper, int k)
    {
        if(k>array.length)
            return -1;

        // Find the partition
        int partition = partition3(array, lower, upper);

        // If partition value is equal to the kth position,
        // return value at k.
        if (partition == k - 1)
            return array[partition];

        // If partition value is less than kth position,
        // search right side of the array.
        else if (partition < k - 1)
            return quickSelect3(array, partition + 1, upper, k);

        // If partition value is more than kth position,
        // search left side of the array.
        else
            return quickSelect3(array, lower, partition - 1, k);
    }

    public static int partition3(int[] array, int lower, int upper){
        int p = pivot(array, lower, upper);

        swap(array, lower, p);

        int j = upper + 1;
        int i = lower;
        while(true){

            while(array[lower] < array[--j])
                if(j==lower)   break;

            while(array[++i] < array[lower])
                if(i==upper) break;

            if(i >= j)  break;
            swap(array, i, j);
        }
        swap(array, lower, j);
        return j;
    }

    public static int pivot(int[] array, int lower, int upper){
        int mid = (lower+upper)/2;
        int pivot = array[lower] + array[upper] + array[mid] -
                Math.min(Math.min(array[lower], array[upper]), array[mid]) -
                Math.max(Math.max(array[lower], array[upper]), array[mid]);

        if(pivot == array[lower])
            return lower;
        else if(pivot == array[upper])
            return upper;
        return mid;
    }
}
