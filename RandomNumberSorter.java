public class RandomNumberSorter {
    public static void main(String[] args) {
        int[] numbers = new int[50];

        
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = (int) (Math.random() * 100); 
        }

        System.out.println("Random Numbers:");
        printArray(numbers);

        
        bubbleSort(numbers);

        System.out.println("\nSorted Numbers:");
        printArray(numbers);
    }

    
    public static void bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

   
    public static void printArray(int[] array) {
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
