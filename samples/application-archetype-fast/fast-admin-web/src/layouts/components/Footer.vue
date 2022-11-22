<template>
  <div :class="prefix + '-footer'">Copyright @ 2018-{{ new Date().getFullYear() }} Seezoon. All Rights Reserved</div>
</template>

<script lang="ts" setup>
import {prefix} from '@/config/global';</script>

<style lang="less" scoped>
.@{starter-prefix}-footer {
  color: var(--td-text-color-placeholder);
  line-height: 20px;
  text-align: center;
}
</style>
