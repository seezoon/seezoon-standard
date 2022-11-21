<template>
  <t-dialog v-model:visible="visible" :destroy-on-close="true" :on-confirm="()=>this.$refs.form.submit()" header="修改密码"
            scroll-to-first-error="smooth" width="500">
    <template #body>
      <t-form ref="form" :data="data" class="pwd-form" @submit="save">
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
        <t-form-item
          :rules="[
            { required: true },
            { whitespace: true },
            { min: 6, message: '不能少于6个字符' },
            { validator: checkPassword},
          ]"
          label="再次输入" name="rePassword">
          <t-input v-model="data.rePassword" :maxcharacter="100" autocomplete="new-password" placeholder="请重复输入上述密码"
                   type="password"/>
        </t-form-item>
      </t-form>
    </template>
  </t-dialog>
</template>
<script>
import {request} from '@/utils/request';

export default {
  name: 'ChangePwd',
  data() {
    return {
      visible: false,
      userId: undefined,
      data: {}
    }
  },
  methods: {
    open(userId) {
      this.userId = userId;
      this.visible = true
      this.data = {};
    },
    save({validateResult, firstError, e}) {
      e.preventDefault();
      if (validateResult !== true) {
        return false;
      }
      this.$loading(true);
      request.post({
        url: '/sys/user/change/pwd',
        data: {
          userId: this.userId,
          password: this.data.password
        },
      }).then(() => {
        this.visible = false;
        this.$message.success('修改成功');
      })
      .finally(() => {
        this.$loading(false);
      });
    },
    checkPassword(val) {
      if (val && this.data.password !== val) {
        return {result: false, message: '两次密码输入不一致'};
      }
      return {result: true}
    }
  }
}
</script>
<style lang="less" scoped>
.pwd-form {
  min-height: 120px;
}
</style>
