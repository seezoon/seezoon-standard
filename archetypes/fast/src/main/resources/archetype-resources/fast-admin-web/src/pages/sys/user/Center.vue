<template>
  <t-card title="个人信息">
    <template #actions>
      <t-button theme="success" variant="text" @click="openPwdDialog()">修改密码</t-button>
    </template>
    <t-row>
      <t-col :span="2" class="s-item">
        <div class="s-item-title">姓名</div>
        <div class="s-item-detail">{{ detail.name }}</div>
      </t-col>
      <t-col :span="2" class="s-item">
        <div class="s-item-title">用户名</div>
        <div class="s-item-detail">{{ detail.username }}</div>
      </t-col>
      <t-col :span="2" class="s-item">
        <div class="s-item-title">手机号</div>
        <div class="s-item-detail">{{ detail.mobile }}</div>
      </t-col>
      <t-col :span="2" class="s-item">
        <div class="s-item-title">邮箱</div>
        <div class="s-item-detail">{{ detail.emial }}</div>
      </t-col>
      <t-col :span="2" class="s-item">
        <div class="s-item-title">创建时间</div>
        <div class="s-item-detail">{{ detail.createTime }}</div>
      </t-col>
    </t-row>
  </t-card>
  <t-dialog v-model:visible="visible" :destroy-on-close="true" :on-confirm="()=>this.$refs.form.submit()" header="修改密码"
            scroll-to-first-error="smooth" width="500">
    <template #body>
      <t-form ref="form" :data="passwordData" style="min-height: 180px;" @submit="save">
        <t-form-item
          :rules="[
            { required: true },
            { whitespace: true },
            { min: 6, message: '不能少于6个字符' },
          ]"
          label="原密码"
          name="oldPassword"
        >
          <t-input
            v-model="passwordData.oldPassword"
            :maxcharacter="100"
            autocomplete="new-password"
            placeholder="请输入原密码"
            type="password"
          />
        </t-form-item>
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
            v-model="passwordData.password"
            :maxcharacter="100"
            autocomplete="new-password"
            placeholder="为了账号安全，请输入较为复杂的密码"
            type="password" @change="this.$refs.form.validate({
            fields: ['rePassword'],
          });"
          />
        </t-form-item>
        <t-form-item
          :rules="[
            { required: true },
            { whitespace: true },
            { min: 6, message: '不能少于6个字符' },
            { validator: checkPassword},
          ]"
          label="再次输入" name="rePassword">
          <t-input v-model="passwordData.rePassword" :maxcharacter="100" autocomplete="new-password"
                   placeholder="请重复输入上述密码"
                   type="password"/>
        </t-form-item>
      </t-form>
    </template>
  </t-dialog>
</template>

<script>
import {request} from '@/utils/request';

export default {
  data() {
    return {
      visible: false,
      detail: {},
      passwordData: {}
    }
  },
  mounted() {
    this.query();
  },
  methods: {
    openPwdDialog() {
      this.visible = true;
      this.passwordData = {};
    },
    query() {
      this.$loading(true);
      request.get({
        url: `/sys/user/personal`,
      }).then(({info}) => {
        this.detail = info;
      }).finally(() => {
        this.$loading(false);
      });
    },
    save({validateResult, firstError, e}) {
      e.preventDefault();
      if (validateResult !== true) {
        return false;
      }
      this.$loading(true);
      request.post({
        url: '/sys/user/change/my_pwd',
        data: this.passwordData
      }).then(() => {
        this.visible = false;
        this.$message.success('修改成功');
      })
      .finally(() => {
        this.$loading(false);
      });
    },
    checkPassword(val) {
      if (val && this.passwordData.password !== val) {
        return {result: false, message: '两次密码输入不一致'};
      }
      return {result: true}
    }
  }
}

</script>
