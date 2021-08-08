import java.util.function.Consumer;

public class sortedArray {

    private int length = 0;
    private final int[] values = new int[1000];

    public void add(int value) {
        if (length == 999) {
            throw new IndexOutOfBoundsException("reach max size of array");
        }
        int index = searchSubarray(0, length-1, value);
        System.arraycopy(values, index, values, index+1,  length-index);
        values[index] = value;
        length++;
    }

    public void applyToAllElements(Consumer<Integer> consumer){
        for (int i = 0; i<= length-1; i++ ) {
            consumer.accept(values[i]);
        }

    }

    public int searchSubarray(int start, int end, int value) {
        while(start <= end) {
            int middle = (start + end) / 2;
            if (values[middle] < value) {
                start = middle + 1;
            } else if (values[middle] >= value) {
                end = middle - 1;
            }
        }
        return start;
    }

    public static void main(String[] args) {
        sortedArray array = new sortedArray();
        array.add(100);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(20);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(50);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(60);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(70);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(90);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(10);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(100);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(50);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(10);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(110);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(100);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(110);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(130);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(130);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(130);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(130);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
        System.out.println();
        array.add(110);
        array.applyToAllElements(integer -> System.out.print(integer +" "));
    }
}
