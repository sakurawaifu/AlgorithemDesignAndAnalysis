package Greedy;

public class Item implements Comparable<Item> {
    public int ordinal;
    public double weight;
    public double value;
    public double ratio;// value-weight ratio 价值重量比，即单位重量的价值

    public Item(int ordinal, double weight, double value) {
        this.ordinal = ordinal;
        this.weight = weight;
        this.value = value;
        ratio = value / weight;
    }

    public int compareTo(Item o) {
        if (ratio < o.ratio)
            return -1;
        else if (ratio == o.ratio)
            return 0;
        else
            return 1;
    }
}
