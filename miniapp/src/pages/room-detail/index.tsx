import { useState, useEffect } from 'react';
import Taro, { useRouter } from '@tarojs/taro';
import { View, Text, Image, ScrollView, Picker, Textarea } from '@tarojs/components';
import { Room } from '../../types';
import { roomApi } from '../../api/room';
import { formatPrice } from '../../utils/format';
import { storage } from '../../utils/storage';
import './index.scss';

export default function RoomDetailPage() {
  const router = useRouter();
  const roomId = Number(router.params.id);

  const [room, setRoom] = useState<Room | null>(null);
  const [loading, setLoading] = useState(true);
  const [rentalType, setRentalType] = useState(0);
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [remark, setRemark] = useState('');

  const today = new Date().toISOString().split('T')[0];

  useEffect(() => {
    roomApi.getDetail(roomId)
      .then(setRoom)
      .catch(console.error)
      .finally(() => setLoading(false));
  }, [roomId]);

  const handleBook = () => {
    if (!storage.isLoggedIn()) {
      Taro.navigateTo({ url: '/pages/my/index' });
      return;
    }
    if (!startDate || !endDate) {
      Taro.showToast({ title: '请选择租赁日期', icon: 'none' });
      return;
    }
    if (rentalType === 1) {
      const s = new Date(startDate);
      const e = new Date(endDate);
      const months = (e.getFullYear() - s.getFullYear()) * 12 + (e.getMonth() - s.getMonth());
      if (months < 6) {
        Taro.showToast({ title: '长租最少6个月', icon: 'none' });
        return;
      }
    }
    const params = encodeURIComponent(JSON.stringify({
      roomId,
      hotelId: router.params.hotelId,
      rentalType,
      startDate,
      endDate,
      remark,
    }));
    Taro.navigateTo({ url: `/pages/order-confirm/index?params=${params}` });
  };

  if (loading) return <View className="loading"><Text>加载中...</Text></View>;
  if (!room) return <View className="loading"><Text>房间不存在</Text></View>;

  const images = (() => {
    try {
      return JSON.parse(room.images as unknown as string || '[]');
    } catch { return []; }
  })();

  return (
    <View className="room-detail-page">
      <ScrollView scrollY className="scroll-content">
        {/* Images */}
        {images.length > 0 && (
          <Image className="room-cover" src={images[0]} mode="aspectFill" />
        )}

        {/* Basic Info */}
        <View className="card">
          <Text className="room-name">{room.name}</Text>
          <View className="room-meta-row">
            <Text className="meta-item">🏠 {room.area}㎡</Text>
            <Text className="meta-item">🏢 {room.floor}层</Text>
            <Text className="meta-item">🧭 {room.orientation}</Text>
            <Text className="meta-item stock">剩余{room.stock}套</Text>
          </View>
          <Text className="room-price">
            <Text className="price-num">{formatPrice(room.price)}</Text>
            <Text className="price-unit">/月</Text>
          </Text>
          {room.description && (
            <Text className="description">{room.description}</Text>
          )}
        </View>

        {/* Booking Form */}
        <View className="card">
          <Text className="section-title">预订信息</Text>

          {/* Rental Type */}
          <View className="form-row">
            <Text className="form-label">租赁类型</Text>
            <View className="rental-type-btns">
              <Text
                className={`rental-btn ${rentalType === 0 ? 'active' : ''}`}
                onClick={() => setRentalType(0)}
              >短租</Text>
              <Text
                className={`rental-btn ${rentalType === 1 ? 'active' : ''}`}
                onClick={() => setRentalType(1)}
              >长租(6月起)</Text>
            </View>
          </View>

          {/* Start Date */}
          <View className="form-row">
            <Text className="form-label">入住日期</Text>
            <Picker mode="date" start={today} value={startDate} onChange={(e) => setStartDate(e.detail.value)}>
              <Text className={`date-picker ${startDate ? 'selected' : 'placeholder'}`}>
                {startDate || '请选择'}
              </Text>
            </Picker>
          </View>

          {/* End Date */}
          <View className="form-row">
            <Text className="form-label">退房日期</Text>
            <Picker mode="date" start={startDate || today} value={endDate} onChange={(e) => setEndDate(e.detail.value)}>
              <Text className={`date-picker ${endDate ? 'selected' : 'placeholder'}`}>
                {endDate || '请选择'}
              </Text>
            </Picker>
          </View>

          {/* Remark */}
          <View className="form-row column">
            <Text className="form-label">备注</Text>
            <Textarea
              className="textarea"
              placeholder="有什么特殊需求，请在这里说明"
              value={remark}
              onInput={(e) => setRemark(e.detail.value)}
              maxlength={200}
            />
          </View>
        </View>
      </ScrollView>

      {/* Bottom Bar */}
      <View className="bottom-bar">
        <Text className="bottom-price">
          <Text className="price-num">{formatPrice(room.price)}</Text>/月
        </Text>
        <Text className="book-btn" onClick={handleBook}>立即预订</Text>
      </View>
    </View>
  );
}
