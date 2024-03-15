import { addBook, updateBook } from "@/services/api/bookInfor";
import { waitTime } from "@/utils/request";
import { ModalForm, ProForm, ProFormDatePicker, ProFormInstance, ProFormSelect, ProFormText } from "@ant-design/pro-components";
import { message } from "antd";
import moment from "moment";
import { useEffect, useRef } from "react";
import { Input } from 'antd';



interface InputDialogProps {
    detailData?: API.BookInforDTO;
    visible: boolean;
    onClose: (result: boolean) => void;
  }

  export default function InputDialog(props: InputDialogProps){
    const form = useRef<ProFormInstance>(null);

    useEffect(() => {
        waitTime().then(() => {
          if (props.detailData) {
            form?.current?.setFieldsValue(props.detailData);
          } else {
            form?.current?.resetFields();
          }
        });
      }, [props.detailData, props.visible]);

      const onFinish = async (values: any) => {
        const { bookName, publishingHouse, classificationNum, isbn, publishDate,price,buyDate,onshelf} = values;
        const data: API.BookInforDTO = {
          id: props.detailData?.id,
          bookName,
          publishingHouse,
          classificationNum,
          isbn,
          publishDate,
          price,
          buyDate,
          onshelf,
        };
        if (props.detailData) {
          await updateBook(data);
        } else {
          await addBook(data);
        }
        message.success('保存成功');
        props.onClose(true);
        return true;
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
          title={props.detailData ? '修改图书' : '新建图书'}
          open={props.visible}
        >
          <ProFormText
            name="bookName"
            label="图书名称"
            rules={[
              {
                required: true,
                message: '请输入图书名称！',
              },
            ]}
          />
          <ProForm.Group>
            <ProFormText
              name="publishingHouse"
              label="出版社"
              rules={[
                {
                  required: true,
                  message: '请输入出版社名称！',
                },
              ]}
            />
            <ProFormText
              name="classificationNum"
              label="分类号"
              rules={[
                {
                  required: true,
                  message: '请输入分类号',
                },
              ]}
            />
            
            <ProFormText
              name="isbn"
              label="ISBN"
              tooltip="输入必须为13位"
              rules={[
                {
                  min:13,
                  max:13,
                  required: true,
                  message: '请输入13位ISBN',
                },
              ]}
            />
            <ProFormDatePicker
              name="publishDate"
              label="出版日期"
              initialValue={moment('2021-10-13')}
              rules={[
                {
                  required: true,
                  message: '请输入出版日期',
                },
              ]}
            />
             <ProFormText
              name="price"
              label="价格"
              tooltip="请输入数字"
              rules={[
                {
                  required: true,
                  message: '请输入价格',
                },
              ]}
            />
            <ProFormDatePicker
              name="buyDate"
              label="购买日期"
              initialValue={moment()}
              rules={[
                {
                  required: true,
                  message: '请输入购买日期',
                },
              ]}
            />
            <ProFormSelect
              name="onshelf"
              label="在架"
              rules={[
                {
                  required: true,
                  message: '请输入是否在架',
                },
              ]}
              options={[
                {
                  value: '在架',
                  label: '在架',
                },

                {
                  value: '不在架',
                  label: '不在架',
                },
              ]}
              
            />
          </ProForm.Group>
        </ModalForm>
      );

  }