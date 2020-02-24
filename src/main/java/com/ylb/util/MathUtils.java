package com.ylb.util;

import java.math.BigDecimal;

public class MathUtils {

	/**
	 * 把科学计数法显示出全部数字 21
	 * 
	 * @param d
	 */
	public static BigDecimal object2Str(Double d) {
		BigDecimal db = new BigDecimal(d);
		BigDecimal dbs = db.setScale(2, BigDecimal.ROUND_HALF_UP);
		return dbs;
	}

	// 买入成交额
	public static Double buyPrice(Double orderBuyer, Double orderNumber) {
		Double gmv = 0.0;
		Double buyPrice = orderBuyer * orderNumber;
		if (buyPrice < 5) {
			gmv = 5.0;
		} else {
			// 买入佣金
			Double buyCommission = buyPrice * 0.0002;
			// 买入成交金额
			gmv = buyPrice + buyCommission;
		}
		return gmv;
	}

	// 买入佣金
	public static Double buyPrice1(Double orderBuyer, Double orderNumber) {
		Double buyCommission = 0.0;
		Double buyPrice = orderBuyer * orderNumber;
		if(buyPrice * 0.0002 >5.0){
			// 买入佣金
			buyCommission = buyPrice * 0.0002;
		}else{
			buyCommission = 5.0;
		}
		return buyCommission;
	}

	// 卖出成交额
	public Double sellPrice(Double orderExitPrice, Double orderNumber) {
		Double gmv = 0.0;
		Double sellPrice = orderExitPrice * orderNumber;
		if (sellPrice < 5) {
			gmv = 5.0;
		} else {
			// 卖出佣金
			Double sellCommission = sellPrice * 0.0002;
			// 卖出印花税
			Double StampDuty = sellPrice * 0.001;
			// 卖出成交额
			gmv = sellPrice + sellCommission + StampDuty;
		}
		return gmv;
	}

	// 卖出佣金 和 印花税
	public static Double sellPrice1(Double orderExitPrice, Double orderNumber) {
		Double gmv = 0.0;
		Double sellPrice = orderExitPrice * orderNumber;
		if (sellPrice * 0.0002 < 5) {
			// 卖出佣金
			Double sellCommission = 5.0;
			// 卖出印花税
			Double StampDuty = sellPrice * 0.001;
			// 卖出成交额
			gmv = sellCommission + StampDuty;
		} else {
			// 卖出佣金
			Double sellCommission = sellPrice * 0.0002;
			// 卖出印花税
			Double StampDuty = sellPrice * 0.001;
			// 卖出成交额
			gmv = sellCommission + StampDuty;
		}
		return gmv;
	}

	// 过户费
	public static Double transferFee(Double orderNumber) {
		Double transferFee = 0.0;
		if (orderNumber >= 1000) {
			int num = 0;
			while (orderNumber >= 1000) {
				orderNumber = orderNumber - 1000;
				num++;
				if (orderNumber != 0.0 && orderNumber < 1000) {
					num++;
				}
			}
			transferFee = (1000 * 0.00002) * num;
		} else {
			transferFee = 1000 * 0.00002;
		}
		return transferFee;
	}

	// 建仓费 买入
	public static Double openPosition(Double buyPrice, Double transferFee) {
		Double openPosition = transferFee + buyPrice;
		return openPosition;
	}
	
	//成本价
	public static void costPrice(Double orderBuyer, Double orderNumber) {
		Double gmv = 0.0;
		Double buyPrice = orderBuyer * orderNumber;
		
	}

//	public static void main(String[] args) {

//		openPosition(buyPrice(5.0,100.0),transferFee(5000.0));
//		Double a = -7.901234557432098e+21;
//
//		BigDecimal db = new BigDecimal(a);
//
//		BigDecimal ds= object2Str(a);
//		String str=new DecimalFormat("0.00").format(a);

//		double   a   =   111231.5585;
//		BigDecimal   b   =   new   BigDecimal(a);
//		BigDecimal   f1   =   db.setScale(2,   BigDecimal.ROUND_HALF_UP);
//		System.out.println("科学计数：" + str);
//		BigDecimal bg=new BigDecimal("-7.901234557432098e+21");
//		System.out.println(bg.toPlainString());
//
//		System.out.println("普通计数：" + ds);
//	}
	
	//递延费     延期费=持仓量×当日结算价×延期费率
	public static Double deferred(Double orderNumber,Double cp) {
		Double deferred=orderNumber*cp*0.001;
		return deferred;
	}
	
	//股票当日收盘价
	public static Double Cp() {
		
		return 0.0;
	}
	
	/**
	 * <pre>
	 *  
	 * 数字格式化显示  
	 * 小于万默认显示 大于万以1.7万方式显示最大是9999.9万  
	 * 大于亿以1.1亿方式显示最大没有限制都是亿单位
	 * </pre>
	 * 
	 * @param num   格式化的数字
	 * @param kBool 是否格式化千,为true,并且num大于999就显示999+,小于等于999就正常显示
	 * @return
	 */
	public static String formatNum(String num, Boolean kBool) {
		StringBuffer sb = new StringBuffer();
		if (kBool == null)
			kBool = false;

		BigDecimal b0 = new BigDecimal("1000");
		BigDecimal b1 = new BigDecimal("10000");
		BigDecimal b2 = new BigDecimal("100000000");
		BigDecimal b3 = new BigDecimal(num);

		String formatNumStr = "";
		String nuit = "";

		// 以千为单位处理
		if (kBool) {
			if (b3.compareTo(b0) == 0 || b3.compareTo(b0) == 1) {
				return "999+";
			}
			return num;
		}

		// 以万为单位处理
		if (b3.compareTo(b1) == -1) {
			sb.append(b3.toString());
		} else if ((b3.compareTo(b1) == 0 && b3.compareTo(b1) == 1) || b3.compareTo(b2) == -1) {
			formatNumStr = b3.divide(b1).toString();
			nuit = "万";
		} else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) == 1) {
			formatNumStr = b3.divide(b2).toString();
			nuit = "亿";
		}
		if (!"".equals(formatNumStr)) {
			int i = formatNumStr.indexOf(".");
			if (i == -1) {
				sb.append(formatNumStr).append(nuit);
			} else {
				i = i + 1;
				String v = formatNumStr.substring(i, i + 1);
				if (!v.equals("0")) {
					sb.append(formatNumStr.substring(0, i + 1)).append(nuit);
				} else {
					sb.append(formatNumStr.substring(0, i - 1)).append(nuit);
				}
			}
		}
		if (sb.length() == 0)
			return "0";
		return sb.toString();
	}

}
