<template>
  <t-card title="个人信息">
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
</template>

<script>
import {request} from '@/utils/request';

export default {
  data() {
    return {
      detail: {}
    }
  },
  mounted() {
    this.query();
  },
  methods: {
    query() {
      this.$loading(true);
      request.get({
        url: `/sys/user/personal`,
      }).then(({info}) => {
        this.detail = info;
      }).finally(() => {
        this.$loading(false);
      });
    }
  }
}

</script>
