/*
 * @Author: LiuHaolalala L206457414@163.com
 * @Date: 2023-04-18 20:14:35
 * @LastEditors: LiuHaolalala L206457414@163.com
 * @LastEditTime: 2023-04-18 20:15:39
 * @FilePath: \web-frontend\src\pages\base\wordCloud\index.tsx
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import { wordCloud } from "@/services/api/bookInfor";
import React, { useState, useEffect } from "react";
import { TagCloud } from "react-tagcloud";

const WordCloud = () => {
  //定义 bookNameData 状态，初始值为空数组
  const [bookNameData, setBookNameData] = useState<string[]>([]);

  //在组件挂载时，使用 useEffect 钩子获取书名数据
  useEffect(() => {
    const fetchData = async () => {
      try {
        const allData = await wordCloud();
        const tempData = allData.map((bookInfor) => bookInfor.book_name);

        //过滤无效数据
        const filteredData = tempData.filter((name) => name !== undefined) as string[];
        setBookNameData(filteredData);
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  }, []);

  //计算每个书名出现的次数
  const calculateCount = (tags: {value: string}[]) => {
    const countMap = new Map<string, number>(); //Map存储每个书名出现的次数
    const finalTag: {value: string, count: number}[] = []; //数组存储格式化后的书名和计数

    //遍历 tags 数组中的每个元素，计算每个书名出现的次数
    tags.forEach(tag => {
      if (countMap.has(tag.value)) { //如果该书名已经出现过，将出现次数加 1
        countMap.set(tag.value, countMap.get(tag.value) + 1);
      } else { //如果该书名还没有出现过，将其加入 Map 中，并将其出现次数初始化为 1
        countMap.set(tag.value, 1);
        finalTag.push({value: tag.value, count: 1}); //同时将格式化后的书名和计数加入 finalTag 数组中
      }
    });

    //遍历 finalTag 数组，将其中每个书名的计数设置为其在 Map 中对应的值（如果不存在，则设置为 0）
    finalTag.forEach(tag => {
      tag.count = countMap.get(tag.value) || 0;
    });


    return finalTag;
  };

  //在页面上渲染书名的云图
  return (
    <div style={{ display: 'flex', justifyContent: 'center' }}>
      <TagCloud
        tags={calculateCount(bookNameData.map((name) => ({ value: name })))} //将书名转换为 react-tagcloud 接受的格式，并计算每个标签的计数
        minSize={35}
        maxSize={65}
        shuffle={true} // 打乱标签的位置
        style={{
          borderRadius: '25%', //设置形状
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          width: '100vh',
          height: '80vh',
          backgroundColor: '#fff',
          border: '2px solid #ccc',
        }}
      />
    </div>
  );
};

export default WordCloud;
