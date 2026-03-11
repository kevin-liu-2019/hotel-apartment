import { useState, useEffect } from 'react';
import Taro, { useRouter } from '@tarojs/taro';
import { View, Text } from '@tarojs/components';
import { Room, RentalType, RentalTypeText } from '../../types';
import { roomApi } from '../../api/room';
import { orderApi } from '../../api/order';
import { formatPrice, getDaysBetween, getMonthsBetween } from '../../utils/format';
import './index.scss';

export default function OrderConfirmPage() {
  const router = useRouter();
  const params = JSON.parse(decodeURIComponent(router.params.params || '{}'));

  const [room, setRoom] = useState<Room | null>(null);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    roomApi.getDetail(params.roomId)
      .then(setRoom)
      .catch(console.error)
      .finally(() => setLoading(false));
  }, [params.roomId]);

  const calcPrice = (): number => {
    if (!room || !params.startDate || !params.endDate) return 0;
    if (params.rentalType === RentalType.SHORT_RENT) {
      const days = getDaysBetween(params.startDate, params.endDate);
      const dailyPrice = room.price / 30;
      const total = dailyPrice * days;
      return total < room.price ? room.price : total;
    } else {
      const months = getMonthsBetween(params.startDate, params.endDate);
      return room.price * months;
    }
  };

  const handleSubmit = async () => {
    if (submitting) return;
    setSubmitting(true);
    try {
      const order = await orderApi.create({
        hotelId: Number(params.hotelId),
        roomId: params.roomId,
        rentalType: params.rentalType,
        startDate: params.startDate,
        endDate: params.endDate,
        remark: params.remark,
      });
      Taro.showToast({ title: '下单成功', icon: 'success' });
      setTimeout(() => {
        Taro.redirectTo({ url: `/pages/order-detail/index?orderNo=${order.orderNo}` });
      }, 1500);
    } catch (e) {
      console.error('Order creation failed', e);
    } finally {
      setSubmitting(false);
    }
  };

  if (loading || !room) return <View className="loading"><Text>加载中...</Text></View>;

  const totalPrice = calcPrice();

  return (
    <View className="order-confirm-page">
      <View className="card">
        <Text className="section-title">房间信息</Text>
        <View className="info-row">
          <Text className="info-label">房间名称</Text>
          <Text className="info-value">{room.name}</Text>
        </View>
        <View className="info-row">
          <Text className="info-label">房间面积</Text>
          <Text className="info-value">{room.area}㎡</Text>
        </View>
      </View>

      <View className="card">
        <Text className="section-title">租赁信息</Text>
        <View className="info-row">
          <Text className="info-label">租赁类型</Text>
          <Text className="info-value">{RentalTypeText[params.rentalType as RentalType]}</Text>
        </View>
        <View className="info-row">
          <Text className="info-label">入住日期</Text>
          <Text className="info-value">{params.startDate}</Text>
        </View>
        <View className="info-row">
          <Text className="info-label">退房日期</Text>
          <Text className="info-value">{params.endDate}</Text>
        </View>
        {params.remark && (
          <View className="info-row">
            <Text className="info-label">备注</Text>
            <Text className="info-value">{params.remark}</Text>
          </View>
        )}
      </View>

      <View className="card price-card">
        <View className="info-row">
          <Text className="info-label total-label">总价</Text>
          <Text className="total-price">{formatPrice(totalPrice)}</Text>
        </View>
      </View>

      <View className="submit-area">
        <Text className="submit-btn" onClick={handleSubmit}>
          {submitting ? '提交中...' : '确认下单'}
        </Text>
      </View>
    </View>
  );
}
