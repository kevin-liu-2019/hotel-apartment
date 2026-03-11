import { useState, useEffect } from 'react';
import Taro from '@tarojs/taro';
import { View, Text, ScrollView } from '@tarojs/components';
import { Order, OrderStatus, OrderStatusText } from '../../types';
import { orderApi } from '../../api/order';
import { formatPrice, formatDateTime } from '../../utils/format';
import { storage } from '../../utils/storage';
import './index.scss';

const TABS = [
  { label: '全部', status: undefined },
  { label: '待处理', status: OrderStatus.PENDING_SIGN },
  { label: '进行中', status: OrderStatus.IN_PROGRESS },
  { label: '已完成', status: OrderStatus.COMPLETED },
];

export default function OrderListPage() {
  const [activeTab, setActiveTab] = useState(0);
  const [orders, setOrders] = useState<Order[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!storage.isLoggedIn()) {
      Taro.navigateTo({ url: '/pages/my/index' });
      return;
    }
    loadOrders();
  }, [activeTab]);

  const loadOrders = async () => {
    setLoading(true);
    try {
      const result = await orderApi.getList(TABS[activeTab].status, 1, 20);
      setOrders(result.records || []);
    } catch (e) {
      console.error('Failed to load orders', e);
    } finally {
      setLoading(false);
    }
  };

  const goToDetail = (orderNo: string) => {
    Taro.navigateTo({ url: `/pages/order-detail/index?orderNo=${orderNo}` });
  };

  return (
    <View className="order-list-page">
      {/* Tabs */}
      <View className="tabs">
        {TABS.map((tab, i) => (
          <Text
            key={i}
            className={`tab ${activeTab === i ? 'active' : ''}`}
            onClick={() => setActiveTab(i)}
          >
            {tab.label}
          </Text>
        ))}
      </View>

      <ScrollView scrollY className="list-container">
        {loading ? (
          <View className="loading"><Text>加载中...</Text></View>
        ) : orders.length === 0 ? (
          <View className="empty">
            <Text className="empty-icon">📋</Text>
            <Text className="empty-text">暂无订单</Text>
          </View>
        ) : (
          orders.map((order) => (
            <View key={order.id} className="order-card" onClick={() => goToDetail(order.orderNo)}>
              <View className="order-header">
                <Text className="order-no">订单号: {order.orderNo}</Text>
                <Text className={`status status-${order.status}`}>
                  {OrderStatusText[order.status as OrderStatus]}
                </Text>
              </View>
              <View className="order-body">
                <Text className="order-date">
                  {order.startDate} ~ {order.endDate}
                </Text>
                <Text className="order-price">{formatPrice(order.totalPrice)}</Text>
              </View>
              <View className="order-footer">
                <Text className="order-time">下单时间: {formatDateTime(order.createdAt)}</Text>
              </View>
            </View>
          ))
        )}
      </ScrollView>
    </View>
  );
}
