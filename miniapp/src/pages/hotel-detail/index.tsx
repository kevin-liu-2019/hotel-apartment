import { useState, useEffect } from 'react';
import Taro, { useRouter } from '@tarojs/taro';
import { View, Text, Image, ScrollView, Swiper, SwiperItem } from '@tarojs/components';
import { Hotel, Room } from '../../types';
import { hotelApi } from '../../api/hotel';
import { formatPrice } from '../../utils/format';
import './index.scss';

export default function HotelDetailPage() {
  const router = useRouter();
  const hotelId = Number(router.params.id);

  const [hotel, setHotel] = useState<Hotel | null>(null);
  const [rooms, setRooms] = useState<Room[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([
      hotelApi.getDetail(hotelId),
      hotelApi.getRooms(hotelId),
    ]).then(([hotelData, roomData]) => {
      setHotel(hotelData);
      setRooms(roomData);
    }).catch(console.error)
      .finally(() => setLoading(false));
  }, [hotelId]);

  const goToRoomDetail = (roomId: number) => {
    Taro.navigateTo({ url: `/pages/room-detail/index?id=${roomId}&hotelId=${hotelId}` });
  };

  if (loading) {
    return <View className="loading"><Text>加载中...</Text></View>;
  }

  if (!hotel) {
    return <View className="loading"><Text>酒店不存在</Text></View>;
  }

  const facilities = (() => {
    try {
      return JSON.parse(hotel.facilities as unknown as string || '[]');
    } catch {
      return [];
    }
  })();

  return (
    <View className="hotel-detail-page">
      <ScrollView scrollY className="scroll-view">
        {/* Hotel Images */}
        <Swiper className="swiper" indicatorDots autoplay>
          {hotel.coverImage ? (
            <SwiperItem>
              <Image className="swiper-image" src={hotel.coverImage} mode="aspectFill" />
            </SwiperItem>
          ) : (
            <SwiperItem>
              <View className="swiper-placeholder" />
            </SwiperItem>
          )}
        </Swiper>

        {/* Hotel Basic Info */}
        <View className="hotel-info card">
          <Text className="hotel-name">{hotel.name}</Text>
          <Text className="hotel-address">📍 {hotel.address}</Text>
          <Text className="hotel-phone">📞 {hotel.phone}</Text>
          <Text className="hotel-price">
            月租起价 <Text className="price-num">{formatPrice(hotel.minPrice)}</Text>
          </Text>
        </View>

        {/* Intro */}
        {hotel.intro && (
          <View className="card">
            <Text className="section-title">酒店介绍</Text>
            <Text className="intro-text">{hotel.intro}</Text>
          </View>
        )}

        {/* Facilities */}
        {facilities.length > 0 && (
          <View className="card">
            <Text className="section-title">设施服务</Text>
            <View className="facilities">
              {facilities.map((f: string, i: number) => (
                <Text key={i} className="facility-tag">{f}</Text>
              ))}
            </View>
          </View>
        )}

        {/* Room List */}
        <View className="card">
          <Text className="section-title">可选房型</Text>
          {rooms.length === 0 ? (
            <Text className="empty-text">暂无可用房型</Text>
          ) : (
            rooms.map((room) => (
              <View
                key={room.id}
                className="room-item"
                onClick={() => goToRoomDetail(room.id)}
              >
                <Image
                  className="room-image"
                  src={(room.images && JSON.parse(room.images as unknown as string || '[]')[0]) || 'https://via.placeholder.com/120x90'}
                  mode="aspectFill"
                />
                <View className="room-info">
                  <Text className="room-name">{room.name}</Text>
                  <Text className="room-meta">
                    {room.area}㎡ · {room.floor}层 · {room.orientation}
                  </Text>
                  <Text className="room-stock">
                    剩余 <Text className="stock-num">{room.stock}</Text> 套
                  </Text>
                  <Text className="room-price">
                    <Text className="price-num">{formatPrice(room.price)}</Text>/月
                  </Text>
                </View>
                <Text className="book-btn">预订</Text>
              </View>
            ))
          )}
        </View>
      </ScrollView>
    </View>
  );
}
