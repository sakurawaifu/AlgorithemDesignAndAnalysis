import java.util.Arrays;

public class Permutation {
    private static int num;

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5};
        fp(a);
        System.out.println(num);
    }

    public static void fp(int[] a) {
        int depth = 1;
        while (depth > 0) {
            if (depth == a.length - 1) {
                System.out.println(Arrays.toString(a));
                depth--;
            }
            for (int i = depth; i < a.length; i++) {
                swap(a, i, depth);

                swap(a, i, depth);
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
