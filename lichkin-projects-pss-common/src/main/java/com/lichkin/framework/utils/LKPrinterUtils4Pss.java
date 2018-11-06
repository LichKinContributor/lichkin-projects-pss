package com.lichkin.framework.utils;

import java.math.BigDecimal;
import java.util.List;

import com.lichkin.framework.defines.beans.impl.OrderProduct;
import com.lichkin.framework.defines.beans.impl.PayPrice;
import com.lichkin.framework.defines.enums.impl.LKDateTimeTypeEnum;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKPrinterUtils4Pss {

	/**
	 * 打印订单内容
	 * @return 打印成功返回true，发生异常时返回false。
	 */
	public static boolean print(String storeName, String cashierNo, String posNo, String orderNo, String discountCouponPrice, List<OrderProduct> listProduct, List<PayPrice> listPayPrice) {
		StringBuilder content = new StringBuilder();
		content.append("--------------------------------").append("\n");
		content.append(storeName).append("\n");
		content.append("--------------------------------").append("\n");
		content.append(orderNo.substring(17)).append("\n");
		content.append("店员号：" + cashierNo).append("\n");
		content.append("POS号：" + posNo).append("\n");
		content.append("打印时间：" + LKDateTimeUtils.toString(LKDateTimeUtils.toDateTime(orderNo.substring(0, 17)), LKDateTimeTypeEnum.STANDARD)).append("\n");
		content.append("--------------------------------").append("\n");
		content.append("\n");
		BigDecimal total = new BigDecimal("0");
		BigDecimal totalReduced = new BigDecimal("0");
		for (OrderProduct product : listProduct) {
			content.append(product.getNo()).append("\n");
			content.append(product.getName()).append("\n");
			BigDecimal subTotal = new BigDecimal(product.getCount()).multiply(new BigDecimal(product.getPrice())).setScale(2, BigDecimal.ROUND_HALF_UP);
			total = total.add(subTotal);
			totalReduced = totalReduced.add(new BigDecimal(product.getReducePrice()));
			StringBuilder sb = new StringBuilder();
			sb.append(product.getCount()).append(" × ").append("￥").append(product.getPrice());
			int length = sb.length();
			if (length < 19) {
				for (int i = 0; i < (19 - length); i++) {
					sb.append(" ");
				}
			}
			content.append(sb).append(subTotal).append("\n").append("\n");
		}
		content.append("--------------------------------").append("\n");
		content.append("合计金额：").append("￥").append(total).append("\n");
		content.append("优惠金额：").append("-￥").append(totalReduced).append("\n");
		content.append("优惠券金额：").append("-￥").append(discountCouponPrice).append("\n");
		content.append("\n");
		content.append("应付金额：").append("￥").append(total.subtract(totalReduced).subtract(new BigDecimal(discountCouponPrice))).append("\n");
		for (PayPrice payPrice : listPayPrice) {
			content.append(payPrice.getPayType().getName()).append("结算").append("：").append("￥").append(payPrice.getPrice()).append("\n");
		}
		content.append("--------------------------------").append("\n");
		content.append("\n");
		content.append("\n");
		content.append("\n");
		return LKPrinterUtils.print(content.toString());
	}

}
