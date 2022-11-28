<template>
  <t-dialog v-model:visible="visible" :closeOnOverlayClick="false" :destroy-on-close="true" :footer="false"
            :header="title" scroll-to-first-error="smooth" style="z-index: 100" width="750">
    <template #body style="z-index: 100">
      <t-form ref="form" :data="data" style="min-height: 250px;z-index: 100" @submit="save">
        <t-row :gutter="[0, 20]">
          <t-col :span="12">
            <t-form-item
              :rules="[
          ]"
              label="图像" name="photo">
              <t-upload
                v-model="photo"
                :action="getUploadUrl() "
                :format-response="({data})=>{
                  this.data.photo = data.relativePath;
                  return {name:data.name,url:data.url};
                }"
                :headers="getStorageHeaderToken()"
                :size-limit="{ size: 10, unit: 'MB', message: '图片大小不超过 {sizeLimit} MB' }"
                accept="image/*"
                name="file"
                theme="image"
                @remove="this.data.photo = undefined"
              ></t-upload>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item
              :rules="[
            { required: true },
            { whitespace: true },
            { validator: checkUserName},
          ]"
              label="用户名" name="username">
              <t-input v-model="data.username" :maxcharacter="50" placeholder="请输入用户名"/>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item
              :rules="[
            { required: true },
            { whitespace: true }
          ]"
              label="姓名" name="name">
              <t-input v-model="data.name" :maxcharacter="50" placeholder="请输入姓名"/>
            </t-form-item>
          </t-col>
          <t-col v-if="isAdd" :span="6">
            <t-form-item
              :rules="[
            { required: true },
            { whitespace: true },
            { min: 6, message: '不能少于6个字符' },
          ]"
              label="密码"
              name="password"
            >
              <t-input
                v-model="data.password"
                :maxcharacter="100"
                autocomplete="new-password"
                placeholder="为了账号安全，请输入较为复杂的密码"
                type="password" @change="this.$refs.form.validate({
            fields: ['rePassword'],
          });"
              />
            </t-form-item>
          </t-col>
          <t-col v-if="isAdd" :span="6">
            <t-form-item
              :rules="[
            { required: true },
            { whitespace: true },
            { min: 6, message: '不能少于6个字符' },
            { validator: checkPassword},
          ]"
              label="重复密码" name="rePassword">
              <t-input v-model="data.rePassword" :maxcharacter="100" autocomplete="new-password" placeholder="请重复输入上述密码"
                       type="password"/>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item
              :rules="[]"
              label="手机号" name="mobile">
              <t-input v-model="data.mobile" :maxcharacter="20" placeholder="请输入手机号"/>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item
              :rules="[{ email: true, message: '请输入合法邮箱' },]"
              label="邮箱" name="email">
              <t-input v-model="data.email" :maxcharacter="50" placeholder="请输入邮箱"/>
            </t-form-item>
          </t-col>
          <t-col v-if="!isAdd" :span="6">
            <t-form-item
              :rules="[{ required: true }]"
              label="状态" name="status">
              <t-radio-group v-model="data.status">
                <t-radio :value="RECORD_STATUS.enable.value">
                  <span class="status success">{{ RECORD_STATUS.enable.text }}</span>
                </t-radio>
                <t-radio :value="RECORD_STATUS.disable.value">
                  <span class="status error">{{ RECORD_STATUS.disable.text }}</span>
                </t-radio>
              </t-radio-group>
            </t-form-item>
          </t-col>
          <t-col :span="12">
            <t-form-item>
              <t-button variant="outline" @click="close()">取消</t-button>
              <t-button theme="primary" type="submit">保存</t-button>
            </t-form-item>
          </t-col>
        </t-row>
      </t-form>
    </template>
  </t-dialog>
</template>
<script>
import {request} from '@/utils/request';
import {RECORD_STATUS} from "@/constants";
import {getUploadUrl, resolveFile} from "@/utils/param";
import {getStorageHeaderToken} from "@/config/global";

export default {
  name: 'UserForm',
  emits: ['refresh'],
  setup() {
    return {RECORD_STATUS, getUploadUrl, getStorageHeaderToken}
  },
  data() {
    return {
      visible: false,
      title: undefined,
      data: {}
    }
  },
  computed: {
    isAdd: function () {
      return this.data.userId === undefined;
    },
    // 上传使用的 是个数组
    photo: function () {
      if (this.data.photo) {
        return [{url: resolveFile(this.data.photo)}]
      }
      return [];
    }
  },
  methods: {
    open(title, userId) {
      this.photo = [];
      this.title = title;
      this.data = {
        userId: userId
      };
      if (!this.isAdd) {
        request.get({
          url: `/sys/user/qry/${userId}`
        }).then((data) => {
          this.data = data;
        });
      }
      this.visible = true
    },
    close() {
      this.visible = false;
    },
    save({validateResult, firstError, e}) {
      e.preventDefault();
      if (validateResult !== true) {
        return false;
      }
      this.$loading(true);
      request.post({
        url: this.isAdd ? '/sys/user/add' : '/sys/user/modify',
        data: this.data,
      }).then(() => {
        this.close();
        this.$message.success('保存成功');
        this.$emit('refresh');
      }).finally(() => {
        this.$loading(false);
      });
    },
    checkPassword(val) {
      if (val && this.data.password !== val) {
        return {result: false, message: '两次密码输入不一致'};
      }
      return {result: true}
    },
    checkUserName(val) {
      if (val) {
        return new Promise((resolve) => {
          request.post({
            url: '/sys/user/qry_user_page',
            data: {
              username: val,
            }
          }).then(({total, data}) => {
            if (total === 0) {
              resolve({result: true});
              return;
            }
            if (!this.isAdd && data[0].userId === this.data.userId) {
              resolve({result: true});
              return;
            }
            resolve({result: false, message: '用户名已存在'});
          });
        });
      }
      return {result: true}
    }
  }
}
</script>
