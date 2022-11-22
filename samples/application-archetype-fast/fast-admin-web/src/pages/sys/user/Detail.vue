<template>
  <t-card :bordered="false">
    <template #title>
      <t-space size="small">
        <t-link hover="color">
          <t-icon name="arrow-left" size="24px" @click="this.$router.back()"></t-icon>
        </t-link>
        <span>用户信息</span>
      </t-space>
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
      <t-col :span="2" class="s-item">
        <div class="s-item-title">状态</div>
        <div class="s-item-detail">
          <t-tag v-if="detail.status === RECORD_STATUS.enable.value" theme="success" variant="light">启用</t-tag>
          <t-tag v-if="detail.status === RECORD_STATUS.disable.value" theme="danger" variant="light">停用</t-tag>
        </div>
      </t-col>
    </t-row>
  </t-card>
</template>
<script>
import {request} from '@/utils/request';
import {RECORD_STATUS} from "@/constants";

export default {
  // 无name或者名字和路由不一致就不会keep alive
  // name: 'SysUserDetail',
  setup() {
    return {RECORD_STATUS};
  },
  data() {
    return {
      userId: undefined,
      detail: {}
    }
  },
  mounted() {
    this.userId = this.$route.query.userId;
    this.query();
  },
  methods: {
    query() {
      this.$loading(true);
      request.get({
        url: `/sys/user/qry/${this.userId}`,
      }).then((data) => {
        this.detail = data;
      }).finally(() => {
        this.$loading(false);
      });
    }
  }
}
</script>
