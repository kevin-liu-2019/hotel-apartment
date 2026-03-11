import { Component } from 'react';
import Taro from '@tarojs/taro';
import './app.scss';

class App extends Component {
  componentDidMount() {}

  componentDidShow() {}

  componentDidHide() {}

  componentOnError(err: string) {
    console.error('App error:', err);
  }

  render() {
    return this.props.children;
  }
}

export default App;
