package dp;

import java.util.Scanner;

public class Knapsack {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = 3;
        int totalWeight = 10;
        int[] w = {0, 3, 4, 5};
        int[] v = {0, 4, 5, 6};

        totalWeight = in.nextInt();
        n = in.nextInt();
        w = new int[n + 1];
        v = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            w[i] = in.nextInt();
            v[i] = in.nextInt();
        }
        int[][] dp = new int[v.length][totalWeight + 1];//����ʹ��i=0,w=0����ֹ�±겻��Ҫ-1
/*        for (int i = 0; i <= v.length; ++i) {
            Arrays.fill(m[i], -1);
        }*/
        // System.out.println(TopDown(v, w, w.length - 1, totalWeight, m));
        System.out.println(BottomUp(v, w, totalWeight, dp));
        reconstruction(dp, w, v, totalWeight);
    }

    public static int TopDown(int[] v, int[] w, int i, int r, int[][] m) {
        if (m[i][r] != -1)
            return m[i][r];

        if (i == 0 || r == 0) {
            m[i][r] = 0;
            return 0;
        }

        if (r < w[i]) {
            m[i][r] = TopDown(v, w, i - 1, r, m);
        } else {
            int t1 = TopDown(v, w, i - 1, r - w[i], m) + v[i];
            int t2 = TopDown(v, w, i - 1, r, m);
            m[i][r] = Math.max(t1, t2);
        }
        return m[i][r];
    }

    public static int BottomUp(int[] v, int[] w, int totalWeight, int[][] dp) {
        // int[][] m = new int[v.length][totalWeight + 1];

        for (int i = 1; i < dp.length; ++i) {// iΪ��i����Ʒ��1~v.length��
            for (int j = 1; j < dp[i].length; ++j) {// jΪ��ǰ��������
                if (j < w[i]) {// װ���µ�i��
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j - w[i]] + v[i], dp[i - 1][j]);
                }
            }
        }
        return dp[v.length - 1][totalWeight];
    }

    public static void reconstruction(int[][] dp, int[] w, int[] v, int totalWeight) {
        int i = w.length - 1;// 物品下标
        // int rv = m[i][totalWeight];
        int rw = totalWeight;
        while (dp[i][rw] > 0) {
            if (dp[i][rw] == dp[i - 1][rw]) {// 没放物品i
                i--;
            } else {
                System.out.print(i + " ");
                rw -= w[i];
                i--;
            }
        }
    }
}

