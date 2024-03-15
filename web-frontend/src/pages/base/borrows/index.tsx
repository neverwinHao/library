/*
 * @Author: LiuHaolalala L206457414@163.com
 * @Date: 2023-03-21 22:53:18
 * @LastEditors: LiuHaolalala L206457414@163.com
 * @LastEditTime: 2023-04-19 11:04:57
 * @FilePath: \web-frontend\src\pages\base\borrows\index.tsx
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */

import { deleteBorrows, getBorrowById, listBorrows, returnBook } from "@/services/api/borrows";
import { convertPageData } from "@/utils/request";
import { openConfirm } from "@/utils/ui";
import { DeleteOutlined, InteractionTwoTone, PlusOutlined, ProjectTwoTone } from "@ant-design/icons";
import { ActionType, PageContainer, ProColumns, ProTable } from "@ant-design/pro-components";
import { useModel } from "@umijs/max";
import { Button, message } from "antd";
import { useRef, useState } from "react";

export default () => {
    const refAction = useRef<ActionType>(null);
    const [borrow, setBorrow] = useState<API.BorrowsVO>();
    const [selectedRowKeys, setSelectedRowKeys] = useState([]);
    const [visible, setVisible] = useState(false);
    const columns: ProColumns<API.BorrowsVO>[] = [
        {
          title: '借阅记录ID',
          dataIndex: 'borrowId',
          width: 60,
          search: false,
        },
        {
          title: '借阅人姓名',
          dataIndex: 'borrower',
          width: 60,
        },
        {
            title: '图书id',
            dataIndex: 'bookId',
            width: 60,
            search: false,
        },
        {
            title: '图书名称',
            dataIndex: 'bookName',
            width: 60,
        },
        {
          title: '借出时间',
          dataIndex: 'borrowTime',
          width: 100,
          search: false,
        },
        {
            title: '归还时间',
            dataIndex: 'returnTime',
            width: 100,
            search: false,
        },
      ];
      
      const handleDelete = async () => {
        if (!selectedRowKeys?.length) return;
        openConfirm(`您确定删除${selectedRowKeys.length}条记录吗`, async () => {
          await deleteBorrows(selectedRowKeys);
          refAction.current?.reload();
        });
      };

      const handleReturnBook = () => {
        //未选中行时提示
        if (!selectedRowKeys.length) {
          message.warning('请先选择需要归还的书籍');
          return;
        }
      
        const confirmText = `你确定要归还${selectedRowKeys.length}本书吗？`;
        openConfirm(confirmText, () => {
          let successCount = 0;//记录归还成功个数
          let failCount = 0;//记录失败个数

          const borrowAll = selectedRowKeys.map((borrowId) => {
            return getBorrowById(borrowId).then((borrow) => {
              if (borrow.returnTime) {
                 //如果已经归还过，则 failCount + 1
                failCount++;
                return;
              }
               //归还时间为空，调用 returnBook 函数归还图书，并更新成功计数器
              return returnBook(borrowId).then(() => {
                successCount++;
              }).catch(() => {
                failCount++;
              });
            });
          });
      
          Promise.all(borrowAll)
            .then(() => {
              if (failCount === 0) {//没有失败的
                message.success(`归还成功${successCount}本`);
              } else {//存在已经归还过的书
                message.warning(`归还成功${successCount}本，归还失败${failCount}本`);
              }
              setSelectedRowKeys([]);
              refAction?.current?.reload();
            })
            .catch(() => {
              message.error('归还失败，请稍后再试');
            });
        });
      };
      
    return (
    <PageContainer>
        <ProTable<API.BorrowsVO>
          actionRef={refAction}
          rowKey="borrowId"
          request={async (params = {}) => {
            return convertPageData(await listBorrows(params));
          }}
          toolBarRender={() => [
            <Button
              type="primary"
              key="primary"
              onClick={handleReturnBook}
            >
           <ProjectTwoTone /> 归还书籍
            </Button>,

            <Button
            type="primary"
            key="primary"
            danger
            onClick={handleDelete}
            disabled={!selectedRowKeys?.length}
          >
          <DeleteOutlined /> 删除记录
          </Button>,
          
          ]}
          columns={columns}
          rowSelection={{
            selectedRowKeys,
            onChange: (rowKeys) => {
              setSelectedRowKeys(rowKeys);
            },
          }}
        />
    </PageContainer>
    );
};