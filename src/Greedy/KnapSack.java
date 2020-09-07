package Greedy;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class KnapSack {
    double totalWeight;
    int numOfItems;// 物品数
    Item[] items;// 物品数组

    public KnapSack(double totalWeight, Item[] items) {
        this.totalWeight = totalWeight;
        this.numOfItems = items.length;
        this.items = items;
    }

    public void greedy() {
        int maxValue = 0;// 当前物品总价值
        double remainWeight = totalWeight; // 当前剩余重量
        int[] solution = new int[numOfItems];
        // Arrays.sort(items, Collections.reverseOrder());// 按价重比降序排序
        quickSort(items, 0, items.length - 1, Collections.reverseOrder());
        int i = 0;
        while (i < numOfItems) {// 退出时i指向装不下的那个物品
            if (remainWeight < items[i].weight)// 剩余容量不足
                break;
            maxValue += items[i].value;
            remainWeight -= items[i].weight;
            solution[i] = items[i].ordinal;
            i++;
        }
        if (remainWeight != 0) {// 还剩一小部分空间
            maxValue += remainWeight * items[i].ratio;
            solution[i] = items[i].ordinal;
        }
        System.out.println("最大价值:" + maxValue);
        System.out.println("装包方案:" + Arrays.toString(solution));
        if (remainWeight != 0)
            System.out.println("方案的最后一个物品装入" + remainWeight + "kg");
    }

    public static <T extends Comparable<? super T>> void quickSort(T[] a, int low, int high, Comparator<? super T> c) {
        if (low >= high)
            return;
        int p = partition(a, low, high, c);
        quickSort(a, low, p - 1, c);
        quickSort(a, p + 1, high, c);
    }

    private static <T extends Comparable<? super T>> int partition(T[] a, int low, int high, Comparator<? super T> c) {
        T pivot = a[low];// 以第一个元素做为主元
        while (low < high) {
            while (low < high && c.compare(a[high], pivot) >= 0)
                high--;
            if (low < high) {
                a[low] = a[high];
                low++;
            }
            while (low < high && c.compare(a[low], pivot) < 0)
                low++;
            if (low < high) {
                a[high] = a[low];
                high--;
            }
        }
        a[low] = pivot;
        return low;
    }
}

