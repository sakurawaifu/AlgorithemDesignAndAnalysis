package dac;

import java.util.Arrays;
import java.util.Random;

public class MergeSort {
    public static void main(String[] args) {
        String s;
        int[] a = getRandomArray(15, 30);
        System.out.println("输入：" + Arrays.toString(a));

        mergeSort(a);
        System.out.println("输出：" + Arrays.toString(a));

        // checkMySort();
    }

    /*
     * 非递归自然归并排序
     * s为待排序数组a的各有序子数组的起始下标构成的数组
     * len为s已使用单元数，相当于已知的有序子数组的个数
     * 需要额外用startIndex[len]存放a.length供后续merge使用。又因为最坏情况逆序，所以给startIndex申请a.length+1个空间
     */
    public static void mergeSort(int[] a) {
        int[] aux = new int[a.length + 2];//辅助数组，多用两个空间放哨兵
        int[] s = new int[a.length + 1];
        int len = getStartIndex(a, s);// 求startIndex及其len

        // 开始合并
        int mergeTime = len >>> 1;// 需要进行合并的次数。向0(下)取整
        while (mergeTime != 0) {
            // 一轮两两归并。有序子数组为奇数个时最后一个不进行合并
            for (int i = 0, p = 0; i < mergeTime; i++, p += 2) {// i为当前已循环次数，p为当前应合并的左子数组的索引
                merge(a, aux, s[p], s[p + 1] - 1, s[p + 2] - 1);// 合并第p和p+1有序子数组(左右子数组)
                s[i] = s[p];// 本轮的后续迭代不会使用s[0..i]的值，故两子数组合并后直接更新值，供下一轮归并使用。
            }
            // 本轮两两归并结束
            if (mergeTime << 1 == len) {// 归并前有序子数组有偶数个
                len = mergeTime;// 两两归并次数即新长度
            } else {// 奇数个
                s[mergeTime] = s[len - 1];// 放入第n个有序子数组的起始下标
                len = mergeTime + 1;// 前n-1个归并成(n-1)/2=mergeTime个，第n个本轮未归并
            }
            mergeTime = len >>> 1;// 下轮归并次数(等价于新长度/2)
            s[len] = a.length;
        }
    }

    /*
     * 求取入startIndex，即将数组a的各有序子数组的起始下标放入startIndex中，返回startIndex已使用单元数len
     * len最小值为1，即原数组已经有序；最大值为n，即原数组逆序
     * 第一个有序子数组起始下标一定为0。但数组元素默认全0，不用再赋a[0]为0
     */
    public static int getStartIndex(int[] a, int[] s) {
        int len = 1;
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i])
                s[len++] = i;// len既是下标数组已使用的长度，又是下个元素(下个有序子数组起始下标)应填入的位置
        }
        s[len] = a.length;//存放最后一个有序子数组的终止下标+1，即a.length
        return len;
    }

    // 每次合并都使用aux[0..high-low+2]，后面的不使用。通过len1、2来限制边界，不会读取到aux后面的脏数据
    // 用a的元素覆盖aux的前high-low+3个元素，覆盖完成后：
    // aux[0..mid-low+1]为左子数组，其中aux[mid-low+1]为哨兵
    // aux[mid-low+2..high-low+2]为右子数组，其中aux[high-low+2]为哨兵
    private static void merge(int[] a, int[] aux, int low, int mid, int high) {
        int len1 = mid - low + 1;// 左子数组长度
        int len2 = high - mid;// 右子数组长度
        int i = 0, j = mid - low + 2;// i，j分别为左右子数组在aux中的起始下标

        System.arraycopy(a, low, aux, i, len1);
        aux[len1] = Integer.MAX_VALUE;
        System.arraycopy(a, mid + 1, aux, j, len2);
        aux[high - low + 2] = Integer.MAX_VALUE;

        for (int k = low; k <= high; k++)
            a[k] = aux[i] <= aux[j] ? aux[i++] : aux[j++];
    }

    public static void checkMySort() {
        int[] a;
        int lenOfA = 1000;
        int checkTimes = 10000;
        for (int i = 0; i < checkTimes; i++) {
            a = getRandomArray(lenOfA);
            mergeSort(a);

            for (int j = 0; j < a.length - 1; j++) {
                if (a[j] > a[j + 1]) {
                    System.out.println("Incorrect");
                    return;
                }
            }
        }
        System.out.println("correct");
    }

    public static int[] getRandomArray(int elemNum) {
        int[] a = new int[elemNum];
        Random random = new Random();
        for (int i = 0; i < elemNum; i++)
            a[i] = random.nextInt();
        return a;
    }

    public static int[] getRandomArray(int elemNum, int bound) {
        int[] a = new int[elemNum];
        Random random = new Random();
        for (int i = 0; i < elemNum; i++)
            a[i] = random.nextInt(bound);
        return a;
    }
}
