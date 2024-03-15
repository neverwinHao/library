/*
 * @Author: LiuHaolalala L206457414@163.com
 * @Date: 2023-03-13 22:29:35
 * @LastEditors: LiuHaolalala L206457414@163.com
 * @LastEditTime: 2023-04-11 09:58:10
 * @FilePath: \web-frontend\src\pages\base\bookInfor\index.tsx
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import { deleteBook, listBookInfor } from "@/services/api/bookInfor";
import { convertPageData } from "@/utils/request";
import { openConfirm } from "@/utils/ui";
import { DeleteOutlined, PlusOutlined } from "@ant-design/icons";
import { ActionType, PageContainer, ProColumns, ProTable } from '@ant-design/pro-components';
import { Button, Modal } from "antd";
import { useRef, useState } from "react";
import InputBorrowDialog from "./inputBorrowDialog";
import InputDialog from "./InputDialog";


export default () => {
    const refAction = useRef<ActionType>(null);
    const [book, setBook] = useState<API.BookInforVO>();
    const [borrow, setBorrow] = useState<API.BorrowsVO>();
    const [selectedRowKeys, selectRow] = useState<number[]>([]);
    const [visible, setVisible] = useState(false);
    const [visibleBorrow, setVisibleBorrow] = useState(false);
    const [selectedId, setSelectedId] = useState<number | undefined>();
    const [selectedName, setSelectedName] = useState<String | undefined>();

    const columns: ProColumns<API.BookInforVO>[] = [
        {
          title: '图书ID',
          dataIndex: 'id',
          width: 60,
          search: false,
        },
        {
          title: '图书名称',
          dataIndex: 'bookName',
          width: 100,
          render: (dom, record) => {
            return (
              <a
                onClick={() => {
                  setBook(record);
                  setVisible(true);
                }}
              >
                {dom}
              </a>
            );
          },
        },
        {
          title: '出版社',
          dataIndex: 'publishingHouse',
          width: 100,
        },
        {
          title: '分类号',
          dataIndex: 'classificationNum',
          width: 50,
          search: false,
        },
        {
          title: 'ISBN',
          dataIndex: 'isbn',
          width: 100,
          search: false,
        },
        {
          title: '出版日期',
          dataIndex: 'publishDate',
          width: 70,
          search: false,
        },
        {
            title: '价格',
            dataIndex: 'price',
            width: 50,
            search: false,
        },
        {
            title: '购买日期',
            dataIndex: 'buyDate',
            width: 70,
            search: false,
        },
        {
            title: '是否在架',
            dataIndex: 'onshelf',
            width: 50,
            search: false,
        },
        {
          title: "操作",
          key: "action",
          width: 50,
          search: false,
          render: (text, record) => (
            <a
              onClick={() => {
                setSelectedId(record.id);
                // setSelectedName(record.bookName);
                setVisibleBorrow(true);
              }}
            >
              借阅
            </a>
          ),
        },
      ];

      const handleDelete = async () => {
        if (!selectedRowKeys?.length) return;
        openConfirm(`您确定删除${selectedRowKeys.length}条记录吗`, async () => {
          await deleteBook(selectedRowKeys);
          refAction.current?.reload();
        });
      };

    return (
    <PageContainer>
        <ProTable<API.BookInforVO>
          actionRef={refAction}
          rowKey="id"
          request={async (params = {}) => {
            return convertPageData(await listBookInfor(params));
          }}
          toolBarRender={() => [
            <Button
              type="primary"
              key="primary"
              onClick={() => {
                setBook(undefined);
                setVisible(true);
              }}
            >
              <PlusOutlined />添加图书
            </Button>,
            <Button
              type="primary"
              key="primary"
              danger
              onClick={handleDelete}
              disabled={!selectedRowKeys?.length}
            >
            <DeleteOutlined /> 删除图书
            </Button>,
          ]}
          columns={columns}
          rowSelection={{
            onChange: (rowKeys) => {
              setSelectedId(selectedRowKeys[1]);
              selectRow(rowKeys as number[]);
            },
          }}
        />
      <InputDialog
        detailData={book}
        onClose={(result) => {
          setVisible(false);
          result && refAction.current?.reload();
        }}
        visible={visible}
      />

      <InputBorrowDialog
        selectedId={selectedId}
        selectedName={selectedName}
        detailData={borrow}
        onClose={(result) => {
          setVisibleBorrow(false);
          result && refAction.current?.reload();
        }}
        visible={visibleBorrow}
      />
    </PageContainer>
    );

};