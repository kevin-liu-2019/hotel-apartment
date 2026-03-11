import { useState, useEffect } from 'react';
import Taro, { useRouter } from '@tarojs/taro';
import { View, Text } from '@tarojs/components';
import { Order, OrderStatus, OrderStatusText, RentalTypeText, RentalType } from '../../types';
import { orderApi } from '../../api/order';
import { formatPrice, formatDateTime } from '../../utils/format';
import './index.scss';

export default function OrderDetailPage() {
  const router = useRouter();
  const orderNo = router.params.orderNo as string;

  const [order, setOrder] = useState<Order | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    orderApi.getDetail(orderNo)
      .then(setOrder)
      .catch(console.error)
      .finally(() => setLoading(false));
  }, [orderNo]);

  const handleCancel = async () => {
    const res = await Taro.showModal({ title: '提示', content: '确定要取消订单吗？' });
    if (res.confirm) {
      try {
        await orderApi.cancel(orderNo);
        Taro.showToast({ title: '取消成功', icon: 'success' });
        setOrder(prev => prev ? { ...prev, status: OrderStatus.CANCELLED } : null);
      } catch (e) {
        console.error('Cancel failed', e);
      }
    }
  };

  const handlePay = async () => {
    if (!order) return;
    try {
      await orderApi.prepay(order.orderNo);
      Taro.showToast({ title: '支付功能对接中', icon: 'none' });
    } catch (e) {
      console.error('Pay failed', e);
    }
  };

  if (loading) return <View className="loading"><Text>加载中...</Text></View>;
  if (!order) return <View className="loading"><Text>订单不存在</Text></View>;

  const canCancel = order.status === OrderStatus.PENDING_SIGN || order.status === OrderStatus.PENDING_PAY;
  const canPay = order.status === OrderStatus.PENDING_PAY;

  return (
    <View className="order-detail-page">
      {/* Status Banner */}
      <View className={`status-banner status-${order.status}`}>
        <Text className="status-text">{OrderStatusText[order.status as OrderStatus]}</Text>
      </View>

      {/* Order Info */}
      <View className="card">
        <Text className="section-title">订单信息</Text>
        <View className="info-row">
          <Text className="label">订单号</Text>
          <Text className="value">{order.orderNo}</Text>
        </View>
        <View className="info-row">
          <Text className="label">租赁类型</Text>
          <Text className="value">{RentalTypeText[order.rentalType as RentalType]}</Text>
        </View>
        <View className="info-row">
          <Text className="label">入住日期</Text>
          <Text className="value">{order.startDate}</Text>
        </View>
        <View className="info-row">
          <Text className="label">退房日期</Text>
          <Text className="value">{order.endDate}</Text>
        </View>
        <View className="info-row">
          <Text className="label">总价</Text>
          <Text className="value price">{formatPrice(order.totalPrice)}</Text>
        </View>
        {order.remark && (
          <View className="info-row">
            <Text className="label">备注</Text>
            <Text className="value">{order.remark}</Text>
          </View>
        )}
        <View className="info-row">
          <Text className="label">下单时间</Text>
          <Text className="value">{formatDateTime(order.createdAt)}</Text>
        </View>
        {order.payTime && (
          <View className="info-row">
            <Text className="label">支付时间</Text>
            <Text className="value">{formatDateTime(order.payTime)}</Text>
          </View>
        )}
      </View>

      {/* Action Buttons */}
      {(canCancel || canPay) && (
        <View className="action-area">
          {canCancel && (
            <Text className="cancel-btn" onClick={handleCancel}>取消订单</Text>
          )}
          {canPay && (
            <Text className="pay-btn" onClick={handlePay}>立即支付</Text>
          )}
        </View>
      )}
    </View>
  );
}
