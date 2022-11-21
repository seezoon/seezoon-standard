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
        <div class="s-item-detail">dfenghuang</div>
      </t-col>
      <t-col :span="2" class="s-item">
        <div class="s-item-title">姓名</div>
        <div class="s-item-detail">dfenghuang</div>
      </t-col>
      <t-col :span="2" class="s-item">
        <div class="s-item-title">姓名</div>
        <div class="s-item-detail">dfenghuang</div>
      </t-col>
      <t-col :span="2" class="s-item">
        <div class="s-item-title">姓名</div>
        <div class="s-item-detail">dfenghuang</div>
      </t-col>
      <t-col :span="2" class="s-item">
        <div class="s-item-title">姓名</div>
        <div class="s-item-detail">dfenghuang</div>
      </t-col>
      <t-col :span="2" class="s-item">
        <div class="s-item-title">姓名</div>
        <div class="s-item-detail">dfenghuang</div>
      </t-col>
      <t-col :span="2" class="s-item">
        <div class="s-item-title">姓名</div>
        <div class="s-item-detail">dfenghuang</div>
      </t-col>
      <t-col :span="2" class="s-item">
        <div class="s-item-title">姓名</div>
        <div class="s-item-detail">dfenghuang</div>
      </t-col>
      <t-col :span="2" class="s-item">
        <div class="s-item-title">姓名</div>
        <div class="s-item-detail">dfenghuang</div>
      </t-col>
    </t-row>
  </t-card>
</template>
<script>
export default {
  name: 'SysUserDetail',
  mounted() {
    alert(this.id = this.$route.params.userId)
  }
}
</script>
