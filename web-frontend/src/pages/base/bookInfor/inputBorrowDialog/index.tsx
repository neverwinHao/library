/*
 * @Author: LiuHaolalala L206457414@163.com
 * @Date: 2023-03-30 21:06:46
 * @LastEditors: LiuHaolalala L206457414@163.com
 * @LastEditTime: 2023-04-15 20:14:11
 * @FilePath: \web-frontend\src\pages\base\bookInfor\inputBorrowDialog\index.tsx
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import { addBook, getBookinforById, updateBook } from "@/services/api/bookInfor";
import { waitTime } from "@/utils/request";
import { ModalForm, ProForm, ProFormDatePicker, ProFormInstance, ProFormSelect, ProFormText } from "@ant-design/pro-components";
import { message } from "antd";
import moment from "moment";
import { useEffect, useRef, useState } from "react";
import { addBorrows, updateBorrows } from "@/services/api/borrows";
import borrows from "../../borrows";



interface InputBorrowDialogProps {
    detailData?: API.BorrowsDTO;
    selectedId?: number;
    selectedName?: String;
    visible: boolean;
    onClose: (result: boolean) => void;
  }

  
  export default function InputBorrowDialog(props: InputBorrowDialogProps){
    const form = useRef<ProFormInstance>(null);
    const [bookName, setbookName] = useState<String | undefined>();

    useEffect(() => {
        waitTime().then(() => {
          if (props.detailData) {
            form?.current?.setFieldsValue(props.detailData);
          } else {
            form?.current?.resetFields();
          }
        });
      }, [props.detailData, props.visible]);

      useEffect(() => {
        if (props.selectedId) {
          getBookinforById(props.selectedId).then((data) => {
            setbookName(data.bookName);
          });
        } else {
          setbookName('');
        }
      }, [props.selectedId]);

      const onFinish = async (values: any) => {
        const { borrowId, bookId, bookName, borrowTime, returnTime,borrower} = values;
        const currentTime = moment().format('YYYY-MM-DD HH:mm:ss');
        const data: API.BorrowsDTO = {
          borrowId: props.detailData?.borrowId,
          bookId,
          bookName,
          borrowTime,
          returnTime,
          borrower,
        };
        const originalData = await getBookinforById(bookId);
        if (originalData.onshelf === '不在架') {
          message.error('借阅失败，当前书籍已被借出！');
          return false;
        } else {
          if (props.detailData) {
            data.borrowTime=currentTime;
            await updateBorrows(data);
          } else {
            data.borrowTime=currentTime;
            await addBorrows(data);
          }
          await updateBook({ ...originalData,onshelf: '不在架' });
          message.success('借阅成功');
          props.onClose(true);
          return true;
        }
      };
      return (
        <ModalForm
          width={600}
          onFinish={onFinish}
          formRef={form}
          modalProps={{
            destroyOnClose: true,
            onCancel: () => props.onClose(false),
          }}
          title={props.detailData ? '借阅图书' : '借阅图书'}
          open={props.visible}
        >
          <ProFormText
            name="borrower"
            label="借阅人姓名"
            rules={[
              {
                required: true,
                message: '请输入借阅人姓名！',
              },
            ]}
          />

          <ProFormText
            name="bookId"
            label="图书id"
            rules={[
              {
                required: false,
                message: '请输入图书id！',
              },
            ]}
            initialValue={props.selectedId}
            disabled={true}
          />

          <ProFormText
            name="bookName"
            label="图书名称"
            rules={[
              {
                required: false,
                message: '请输入图书名称',
              },
            ]}
            initialValue={bookName}
            disabled={true}
          />

        </ModalForm>
      );

  }