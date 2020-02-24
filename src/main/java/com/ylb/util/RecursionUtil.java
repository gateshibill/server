package com.ylb.util;

/**
 * 递归函数
 * @author zhouwanggang
 *
 */
public class RecursionUtil {
	/**
	 * 计算手续费收取
	 * @param price 1000最高配770
	 * @param rate	配额 10
	 * @param month	配率	3%
	 * @param bPrice	本金
	 * @return
	 */
//	public static Double fun(double price,double rate,double month,double bPrice) {
//		if((((price*rate)*month)+price)<bPrice) {
//			return price;
//		}else {
//			price=fun(price-1,rate,month,bPrice);
//			return price;
//		}
//	}
	public static Double fun(double price,double rate,double month,double bPrice) {
		for (int i = 0; i < price; i++) {
			if((price*rate*month+price)<=bPrice) {
				break;
			}else {
				price--;
			}
		}
		return price;
	}
	/**
	 * 服务费
	 * @param price
	 * @param service
	 * @return
	 */
	public static Double service(Double price,int service) {
		Double a=price*service;
		return a;
	}
	
	public static void main(String[] args) {
		System.out.println(fun(130000, 10, 0.03, 130000));
	}
}

