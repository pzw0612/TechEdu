/**
 * 
 */
package com.yhd.o2o.demo1;

public class Beibao {
	static int[] a = new int[5]; // 背包重量
	static int[] b = new int[5]; // 结果数组
	static int flag = 0; // 下一个候选项
	static int bound = 20; // 总重量
	static int totle = 0; // 每次选择后的总重量
	/**
	 * 背包
	 * 
	 * @param i
	 *            元素坐标
	 * @param leftbound
	 *            目标重量
	 * @param t
	 */
	public static void inserttry(int i, int leftbound, int t) {
		if (i < 5 && leftbound <= totle) {
			if (a[i] < leftbound) { // 当前的所选的数小于已选数的总和，将当前所选的数放入结果数组，从目标重量减掉当前所选数，递归，选择后的重量数减掉当前所选数
				b[t++] = a[i];
				totle = totle - a[i];
				leftbound = leftbound - a[i];
				i++;
				inserttry(i, leftbound, t);
			} else if (a[i] > leftbound) { // 当前的所选的数大于已选数的总和，不符合条件，选择后的重量数减掉当前所选数，递归
				totle = totle - a[i];
				i++;
				inserttry(i, leftbound, t);
			} else { // 当前所选的数等于已选数的总和
				b[t] = a[i];
				return;
			}
		} else { // 数组中没有符合当前条件的元素，将前一个数值移除，递归
			leftbound = leftbound + b[--t];
			for (int f = 0; f < 5; f++) {
				if (a[f] == b[t]) {
					flag = ++f;
					break;
				}
			}
			b[t] = 0;
			totle = 0;
			for (int m = flag; m < 5; m++) {
				totle += a[m];
			}
			inserttry(flag, leftbound, t);
		}
		return;
	}
	public static void main(String[] args) {
		a[0] = 11;
		a[1] = 8;
		a[2] = 6;
		a[3] = 7;
		a[4] = 5;
		for (int i = 0; i < 5; i++) {
			b[i] = 0;
		}
		for (int i = 0; i < 5; i++) {
			totle += a[i];
		}
		inserttry(0, 20, 0);
		for (int i = 0; i < 5; i++) {
			System.out.println(b[i]);
		}
	}
}

