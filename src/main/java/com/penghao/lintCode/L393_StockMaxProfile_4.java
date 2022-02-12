/**
 * Author:   PengHao
 * Date:     2022/2/12 21:16
 * Description: 买卖股票4
 */
package com.penghao.lintCode;

public class L393_StockMaxProfile_4 {
    public int stockMaxProfile4(int K, int prices[]) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }

        //退化成买卖股票2
        if (K > n / 2) {
            int res = 0;
            for (int i = 1; i < n; i++) {
                res += Math.max(0, prices[i] - prices[i - 1]);
            }
            return res;
        }

        //f[i][j]:前i天结束后处于状态j时的最大获利
        int[][] f = new int[n + 1][2 * K + 1];
        f[0][0] = 0;
        for (int i = 1; i < 2 * K + 1; i++) {
            f[0][i] = Integer.MIN_VALUE;
        }

        for (int i = 1; i <= n; i++) {
            //阶段0，2，4，...，2*K 手中无股票的状态:f[i][j]=max{f[i-1][j],f[i-1][j-1]+P[i-1]-P[i-2]}
            for (int j = 0; j <= 2 * K; j += 2) {
                f[i][j] = f[i - 1][j];
                //注意边界条件
                if (j - 1 >= 0 && (i - 2) >= 0 && f[i - 1][j - 1] != Integer.MIN_VALUE) {
                    f[i][j] = Math.max(f[i][j], f[i - 1][j - 1] + (prices[i - 1] - prices[i - 2]));
                }
            }
            //阶段1，3，...，2*K-1 手中有股票状态：f[i][j]=max{f[i-1][j]+P[i-1]-P[i-2],f[i-1][j-1],f[i-1][j-2]+P[i-1]-P[i-2]}
            for (int k = 1; k <= 2 * K; k += 2) {
                f[i][k] = f[i - 1][k - 1];
                if (i - 2 >= 0) {
                    //注意边界条件
                    if (f[i - 1][k] != Integer.MIN_VALUE) {
                        f[i][k] = Math.max(f[i][k], f[i - 1][k] + (prices[i - 1] - prices[i - 2]));
                    }
                    if (k - 2 >= 0 && f[i - 1][k - 2] != Integer.MIN_VALUE) {
                        f[i][k] = Math.max(f[i][k], f[i - 1][k - 2] + (prices[i - 1] - prices[i - 2]));
                    }
                }
            }
        }

        int res = 0;
        for (int i = 0; i <= 2 * K; i += 2) {
            res = Math.max(res, f[n][i]);
        }
        return res;
    }
}
