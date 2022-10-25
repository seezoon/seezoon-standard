<template>
  <header class="login-header">
    <logo-full-icon class="logo"/>
    <div v-if="false" class="operations-container">
      <t-button shape="square" theme="default" variant="text" @click="navToGitHub">
        <t-icon class="icon" name="logo-github"/>
      </t-button>
      <t-button shape="square" theme="default" variant="text" @click="navToHelper">
        <t-icon class="icon" name="help-circle"/>
      </t-button>
      <t-button shape="square" theme="default" variant="text" @click="toggleSettingPanel">
        <t-icon class="icon" name="setting"/>
      </t-button>
    </div>
  </header>
</template>

<script lang="ts" setup>
import LogoFullIcon from '@/assets/assets-logo-full.svg?component';
import {useSettingStore} from '@/store';

const settingStore = useSettingStore();
const toggleSettingPanel = () => {
  settingStore.updateConfig({
    showSettingPanel: true,
  });
};

const navToGitHub = () => {
  window.open('https://github.com/tencent/tdesign-vue-next-starter');
};

const navToHelper = () => {
  window.open('http://tdesign.tencent.com/starter/docs/get-started');
};
</script>

<style lang="less" scoped>
.login-header {
  height: 64px;
  padding: 0 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  backdrop-filter: blur(5px);
  color: var(--td-text-color-primary);

  .logo {
    width: 188px;
    height: 64px;
  }

  .operations-container {
    display: flex;
    align-items: center;

    .t-button {
      margin-left: 16px;
    }

    .icon {
      height: 20px;
      width: 20px;
      padding: 6px;
      box-sizing: content-box;

      &:hover {
        cursor: pointer;
      }
    }
  }
}
</style>
