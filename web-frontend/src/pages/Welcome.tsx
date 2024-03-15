import React from 'react';
import { Typography, Button } from 'antd';
import { SearchOutlined } from '@ant-design/icons';

const { Title } = Typography;

const WelcomePage = () => {
  return (
    <div style={{ 
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'center',
      height: '100vh',
      backgroundImage:
      "url('https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/V-_oS6r-i7wAAAAAAAAAAAAAFl94AQBr')",
    }}>
      <div style={{ marginBottom: '50px' }}>
        <Title style={{ color: '#1890ff' }} level={1}>
          这里是刘浩的个人图书馆
        </Title>
      </div>
      <div style={{ marginBottom: '50px' }}>
        <Button
          type="primary"
          size="large"
          icon={<SearchOutlined />}
          onClick={() => (window.location.href = 'http://localhost:8000/base/bookInfor')}
        >
          图书信息
        </Button>
      </div>
      <div style={{ maxWidth: '800px' }}>
        <p style={{ fontSize: '20px', lineHeight: '30px', textAlign: 'center' }}>
          这里是刘浩的个人图书馆，拥有各种电子信息类专业图书！可以借阅书籍!
          <br />
          点击上面的按钮进行操作吧！
        </p>
      </div>
    </div>
  );
};

export default WelcomePage;
