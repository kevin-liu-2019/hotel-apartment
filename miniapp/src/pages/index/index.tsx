import { useState, useEffect } from 'react';
import Taro from '@tarojs/taro';
import { View, Text, Image, ScrollView } from '@tarojs/components';
import { Hotel } from '../../types';
import { hotelApi } from '../../api/hotel';
import { formatPrice } from '../../utils/format';
import './index.scss';

export default function IndexPage() {
  const [hotels, setHotels] = useState<Hotel[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadHotels();
  }, []);

  const loadHotels = async () => {
    try {
      const result = await hotelApi.getList(1, 6);
      setHotels(result.records || []);
    } catch (e) {
      console.error('Failed to load hotels', e);
    } finally {
      setLoading(false);
    }
  };

  const goToHotelDetail = (id: number) => {
    Taro.navigateTo({ url: `/pages/hotel-detail/index?id=${id}` });
  };

  const goToHotelList = () => {
    Taro.switchTab({ url: '/pages/hotel-list/index' });
  };

  return (
    <View className="index-page">
      {/* Banner */}
      <View className="banner">
        <View className="banner-content">
          <Text className="banner-title">酒店公寓</Text>
          <Text className="banner-subtitle">品质生活，从这里开始</Text>
        </View>
      </View>

      {/* Quick Search */}
      <View className="search-bar" onClick={goToHotelList}>
        <Text className="search-placeholder">🔍 搜索公寓/酒店</Text>
      </View>

      {/* Feature Icons */}
      <View className="features">
        <View className="feature-item">
          <Text className="feature-icon">🏠</Text>
          <Text className="feature-label">短租公寓</Text>
        </View>
        <View className="feature-item">
          <Text className="feature-icon">🏢</Text>
          <Text className="feature-label">长租公寓</Text>
        </View>
        <View className="feature-item">
          <Text className="feature-icon">⭐</Text>
          <Text className="feature-label">品质保证</Text>
        </View>
        <View className="feature-item">
          <Text className="feature-icon">📋</Text>
          <Text className="feature-label">电子合同</Text>
        </View>
      </View>

      {/* Hotel List */}
      <View className="section">
        <View className="section-header">
          <Text className="section-title">精选酒店</Text>
          <Text className="section-more" onClick={goToHotelList}>查看更多 &gt;</Text>
        </View>
        {loading ? (
          <View className="loading">
            <Text>加载中...</Text>
          </View>
        ) : (
          <ScrollView className="hotel-list" scrollX>
            {hotels.map((hotel) => (
              <View
                key={hotel.id}
                className="hotel-card"
                onClick={() => goToHotelDetail(hotel.id)}
              >
                <Image
                  className="hotel-image"
                  src={hotel.coverImage || 'https://via.placeholder.com/200x120'}
                  mode="aspectFill"
                />
                <View className="hotel-info">
                  <Text className="hotel-name">{hotel.name}</Text>
                  <Text className="hotel-address">{hotel.address}</Text>
                  <Text className="hotel-price">
                    起 <Text className="price-num">{formatPrice(hotel.minPrice)}</Text>/月
                  </Text>
                </View>
              </View>
            ))}
          </ScrollView>
        )}
      </View>
    </View>
  );
}
