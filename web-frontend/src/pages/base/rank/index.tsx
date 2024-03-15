import React, { useEffect, useState } from 'react';
import * as echarts from 'echarts';
import { bookChart, listBookInfor, wordCloud } from '@/services/api/bookInfor';
import { Button } from 'antd';
import { SwitcherTwoTone } from '@ant-design/icons';

const BookPriceChart = () => {
  const [bookPriceData, setBookPriceData] = useState<Array<number>>([]);
  const [chartType, setChartType] = useState<'bar' | 'pie'>('bar');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const allData = await wordCloud();
        const tempData = allData.map((bookInfor) => bookInfor.price);
        const filteredData = tempData.filter((price) => price !== undefined) as number[]; //过滤无效数据
        setBookPriceData(filteredData);
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  }, []);

  //切换ChartType的状态，bar->pie或者pie->bar
  const toggleChartType = () => {
    setChartType(chartType === 'bar' ? 'pie' : 'bar');
  };

  useEffect(() => {
    const chart = echarts.init(document.getElementById('book-price-chart') as HTMLDivElement);
    const option = chartType === 'bar' ? {
      title: {
        text: '图书价格分布图',
      },
      tooltip: {},
      xAxis: {
        type: 'category',
        data: ['0-10', '10-20', '20-30', '30-40', '40-50', '>50'],
      },
      legend: {
        orient: 'vertical',
        right: 'right'
      },
      yAxis: {
        type: 'value',
      },
      series: [
        {
          name: '价格区间',
          type: 'bar',
          data: [
            bookPriceData.filter((price) => price >= 0 && price < 10).length,
            bookPriceData.filter((price) => price >= 10 && price < 20).length,
            bookPriceData.filter((price) => price >= 20 && price < 30).length,
            bookPriceData.filter((price) => price >= 30 && price < 40).length,
            bookPriceData.filter((price) => price >= 40 && price <= 50).length,
            bookPriceData.filter((price) => price > 50).length,
          ],
        },
      ],
    } : {
      title: {
        text: '图书价格分布图',
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}: {c} ({d}%)',
      },
      legend: {
        orient: 'vertical',
        right: 'right'
      },
      series: [
        {
          name: '价格区间',
          type: 'pie',
          radius: ['40%', '70%'],
          avoidLabelOverlap: false,
          label: {
            show: false,
            position: 'center',
          },
          emphasis: {
            label: {
              show: true,
              fontSize: '30',
              fontWeight: 'bold',
            },
          },
          labelLine: {
            show: false,
          },
          data: [
            { value: bookPriceData.filter((price) => price >= 0 && price < 10).length, name: '0-10' },
            { value: bookPriceData.filter((price) => price >= 10 && price < 20).length, name: '10-20' },
            { value: bookPriceData.filter((price) => price >= 20 && price < 30).length, name: '20-30' },
            { value: bookPriceData.filter((price) => price >= 30 && price < 40).length, name: '30-40' },
            { value: bookPriceData.filter((price) => price > 50).length, name: '>50' },
          ],
        },
      ],
    };
    chart.setOption(option);

    // 没有下面这个return在饼形图出现时，柱形的横坐标还会存在
    return () => {
      chart.dispose();
    };
    }, [bookPriceData, chartType]);
    
    return (
      <div>
        <div id="book-price-chart" style={{ width: '100%', height: '500px' }}></div>
        <Button type="primary"  shape="round" onClick={toggleChartType}>
        <SwitcherTwoTone />
          {chartType === 'bar' ? '切换饼图' : '切换为柱状图'}
        </Button>
      </div>
    );
    };
    
    export default BookPriceChart;
    