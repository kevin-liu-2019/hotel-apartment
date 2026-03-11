export default defineAppConfig({
  pages: [
    'pages/index/index',
    'pages/hotel-list/index',
    'pages/hotel-detail/index',
    'pages/room-detail/index',
    'pages/order-confirm/index',
    'pages/order-list/index',
    'pages/order-detail/index',
    'pages/my/index',
  ],
  window: {
    backgroundTextStyle: 'light',
    navigationBarBackgroundColor: '#1a73e8',
    navigationBarTitleText: '酒店公寓',
    navigationBarTextStyle: 'white',
    backgroundColor: '#f5f5f5',
  },
  tabBar: {
    color: '#999',
    selectedColor: '#1a73e8',
    backgroundColor: '#fff',
    borderStyle: 'black',
    list: [
      {
        pagePath: 'pages/index/index',
        text: '首页',
        iconPath: 'assets/icons/home.png',
        selectedIconPath: 'assets/icons/home-active.png',
      },
      {
        pagePath: 'pages/hotel-list/index',
        text: '找房',
        iconPath: 'assets/icons/search.png',
        selectedIconPath: 'assets/icons/search-active.png',
      },
      {
        pagePath: 'pages/order-list/index',
        text: '订单',
        iconPath: 'assets/icons/order.png',
        selectedIconPath: 'assets/icons/order-active.png',
      },
      {
        pagePath: 'pages/my/index',
        text: '我的',
        iconPath: 'assets/icons/my.png',
        selectedIconPath: 'assets/icons/my-active.png',
      },
    ],
  },
});
