<template>
  <t-form
    ref="form"
    :class="['item-container', `login-password`]"
    :data="formData"
    label-width="0"
    @submit="login"
  >
    <t-form-item :rules="[
       { required: true,message:'请输入您的账号'},
       {whitespace: true,message:'请输入您的账号'},
       { min: 5,message:'账号最少5位'}
    ]" name="username">
      <t-input v-model="formData.username" placeholder="请输入账号" size="large">
        <template #prefix-icon>
          <t-icon name="user"/>
        </template>
      </t-input>
    </t-form-item>

    <t-form-item :rules="[
       { required: true,message:'请输入您的密码'},
       {whitespace: true,message:'请输入您的密码'},
       { min: 6,message:'密码最少6位'}]" name="password">
      <t-input
        v-model="formData.password"
        :type="showPsw ? 'text' : 'password'"
        autocomplete="new-password"
        clearable
        placeholder="请输入登录密码" size="large"
      >
        <template #prefix-icon>
          <t-icon name="lock-on"/>
        </template>
        <template #suffix-icon>
          <t-icon :name="showPsw ? 'browse' : 'browse-off'" @click="showPsw = !showPsw"/>
        </template>
      </t-input>
    </t-form-item>

    <div class="check-container remember-pwd">
      <t-checkbox v-model="formData.rememberMe">记住我</t-checkbox>
    </div>

    <t-form-item class="btn-container">
      <t-button block size="large" type="submit"> 登录</t-button>
    </t-form-item>
  </t-form>
</template>
<script setup>
import {ref} from 'vue';

const formData = ref({
  rememberMe: false
});
const showPsw = ref(false);

</script>
<script>
import {request} from '@/utils/request';
import {useUserStore} from '@/store';
import router from "@/router";

const userStore = useUserStore();
export default {
  name: 'PwdLogin',
  methods: {
    login({validateResult, firstError, e}) {
      e.preventDefault();
      if (validateResult !== true) {
        return false;
      }
      request.post({
        url: '/login/username_password',
        data: this.formData
      }).then(({token}) => {
        let i = userStore.login(token, this.formData.rememberMe);
        console.log(i)
        //  debugger
        // console.log(this.$route.query?.redirect ? this.$route.query?.redirect : '/dashboard/base')
        router.push(this.$route.query?.redirect ? this.$route.query?.redirect : '/dashboard/base')
        this.$message.success('登陆成功');

      })
    }
  }
}
</script>
<style lang="less" scoped>
@import url('../index.less');
</style>
