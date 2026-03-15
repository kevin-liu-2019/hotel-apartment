import { useState, useEffect } from 'react';
import Taro from '@tarojs/taro';
import { View, Text, Image, ScrollView } from '@tarojs/components';
import { Hotel, PageResult } from '../../types';
import { hotelApi } from '../../api/hotel';
import { formatPrice } from '../../utils/format';
import './index.scss';

export default function HotelListPage() {
  const [hotels, setHotels] = useState<Hotel[]>([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);

  useEffect(() => {
    loadHotels(1, true);
  }, []);

  const loadHotels = async (pageNum: number, reset = false) => {
    if (!hasMore && !reset) return;
    setLoading(true);
    try {
      const result = await hotelApi.getList(pageNum, 10);
      const newHotels = result.records || [];
      setHotels(reset ? newHotels : [...hotels, ...newHotels]);
      setPage(pageNum);
      setHasMore(pageNum < result.pages);
    } catch (e) {
      console.error('Failed to load hotels', e);
    } finally {
      setLoading(false);
    }
  };

  const goToDetail = (id: number) => {
    Taro.navigateTo({ url: `/pages/hotel-detail/index?id=${id}` });
  };

  return (
    <View className="hotel-list-page">
      <ScrollView
        className="list-container"
        scrollY
        onScrollToLower={() => loadHotels(page + 1)}
      >
        {hotels.map((hotel) => (
          <View key={hotel.id} className="hotel-item" onClick={() => goToDetail(hotel.id)}>
            <Image
              className="hotel-cover"
              src={hotel.coverImage || 'https://via.placeholder.com/160x120'}
              mode="aspectFill"
            />
            <View className="hotel-info">
              <Text className="hotel-name">{hotel.name}</Text>
              <Text className="hotel-address">📍 {hotel.address}</Text>
              {hotel.facilities && (
                <View className="tags">
                  {JSON.parse(hotel.facilities as unknown as string || '[]').slice(0, 3).map((f: string, i: number) => (
                    <Text key={i} className="tag">{f}</Text>
                  ))}
                </View>
              )}
              <Text className="hotel-price">
                起 <Text className="price-num">{formatPrice(hotel.minPrice)}</Text>/月
              </Text>
            </View>
          </View>
        ))}
        {loading && (
          <View className="loading-tip">
            <Text>加载中...</Text>
          </View>
        )}
        {!hasMore && hotels.length > 0 && (
          <View className="no-more">
            <Text>没有更多了</Text>
          </View>
        )}
      </ScrollView>
    </View>
  );
}
