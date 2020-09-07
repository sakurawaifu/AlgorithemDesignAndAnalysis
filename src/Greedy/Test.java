package Greedy;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double totalWeight = in.nextDouble();
        int n = in.nextInt();
        Item[] items = new Item[n];
        for (int i = 0; i < n; i++) {
            double w = in.nextDouble();
            double v = in.nextDouble();
            items[i] = new Item(i, w, v);
        }
        KnapSack knapSack = new KnapSack(totalWeight, items);
        knapSack.greedy();
    }
}
