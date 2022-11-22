<template>
  <t-form :data="searchForm" colon @submit="query()">
    <t-row :gutter="[0, 20]">
      <t-col :span="3">
        <t-form-item label="用户名" name="username">
          <t-input v-model="searchForm.username"></t-input>
        </t-form-item>
      </t-col>
      <t-col :span="3">
        <t-form-item label="手机号" name="mobile">
          <t-input v-model="searchForm.mobile"></t-input>
        </t-form-item>
      </t-col>
      <t-col :span="3">
        <t-form-item label="姓名" name="name">
          <t-input v-model="searchForm.name"></t-input>
        </t-form-item>
      </t-col>
      <t-col :span="3">
        <t-button class="s-ml-10" type="submit">查询</t-button>
        <t-button theme="default" type="reset">重置</t-button>
        <t-button theme="default" @click="this.$refs.userForm.open('添加用户')">添加用户</t-button>
      </t-col>
    </t-row>
  </t-form>

  <t-table
    :columns="columns"
    :data="data"
    :loading="loading"
    :pagination="pagination"
    class="s-mt-30"
    hover
    row-key="userId"
    @page-change="pageChange"
    @sort-change="sortChange"
  >
    <template #name="{row}">
      <t-link theme="primary" @click="this.$router.push({
      path:'/sys/user/detail',
      query:{
        userId: row.userId
      } })">{{ row.name }}
      </t-link>
    </template>
    <template #status="{ row }">
      <t-tag v-if="row.status === RECORD_STATUS.enable.value" theme="success" variant="light">启用</t-tag>
      <t-tag v-if="row.status === RECORD_STATUS.disable.value" theme="danger" variant="light">停用</t-tag>
    </template>
    <template #op="{row}">
      <a class="t-button-link" @click="this.$refs.userForm.open('修改用户',row.userId)">修改</a>
      <t-dropdown>
        <a class="t-button-link">
          <t-space :size="0">
            更多
            <t-icon name="chevron-down"/>
          </t-space>
        </a>
        <t-dropdown-menu>
          <t-dropdown-item :divider="true" @click="this.$refs.changePwd.open(row.userId)">修改密码</t-dropdown-item>
          <t-dropdown-item theme="error" @click="remove(row.userId,row.name)">删除</t-dropdown-item>
        </t-dropdown-menu>
      </t-dropdown>
    </template>
  </t-table>
  <change-pwd ref="changePwd"/>
  <!-- 使用this.$refs.userForm.open() 操作子组件函数，子组件不可以用script setup 语法糖
  https://cn.vuejs.org/api/sfc-script-setup.html#defineexpose
  -->
  <user-form ref="userForm" @refresh="fetchData()"/>
</template>
<script setup>
import {RECORD_STATUS} from "@/constants";
import ChangePwd from './ChangePwd.vue'
import UserForm from './UserForm.vue'

const columns = [{
  colKey: 'name',
  title: '姓名',
  ellipsis: true
},
  {
    colKey: 'username',
    title: '用户名',
  }, {
    colKey: 'mobile',
    title: '手机号',
  },
  {
    colKey: 'status',
    title: '状态',
    width: 80
  },
  {
    colKey: 'createTime',
    title: '创建时间',
    sorter: true,
    width: 200
  },
  {
    colKey: 'op',
    title: '操作',
    width: 130,
    fixed: 'right'
  }]
</script>
<script>
import {request} from '@/utils/request';
import {SORT_ORDER} from "@/constants";

export default {
  name: 'SMain',
  mounted() {
    this.fetchData()
  },
  data() {
    return {
      loading: false,
      searchForm: {},
      searchData: {},
      data: [],
      pagination: {
        current: 1,
        pageSize: 10
      }
    }
  },
  methods: {
    remove(userId, name) {
      const confirmDialog = this.$dialog.confirm({
        header: `请确认删除${name}`,
        body: '删除用户将清空用户关联数据',
        theme: 'warning',
        onConfirm: () => {
          request.post({
            url: '/sys/user/delete/' + userId
          }).then(() => {
            // 请求成功后，销毁弹框
            confirmDialog.destroy();
            this.$message.success("删除成功");
            this.fetchData();
          })
        },
      });
    },
    query() {
      this.pagination.current = 1;
      this.searchData = this.searchForm;
      this.fetchData();
    },
    pageChange(pageInfo) {
      this.pagination.current = pageInfo.current
      this.pagination.pageSize = pageInfo.pageSize
      this.fetchData();
    },
    sortChange(sort) {
      this.pagination.sortBy = sort?.sortBy;
      this.pagination.sortOrder = sort ? (sort?.descending ? SORT_ORDER.desc : SORT_ORDER.asc) : undefined;
      this.fetchData();
    },
    fetchData() {
      this.loading = true;
      request.post({
        url: '/sys/user/qry_user_page',
        data: {
          ...this.searchData,
          page: this.pagination.current,
          pageSize: this.pagination.pageSize,
          sortField: this.pagination.sortBy,
          sortOrder: this.pagination.sortOrder
        },
      }).then(({total, data}) => {
        this.data = data;
        this.pagination.total = total;
      }).finally(() => {
        this.loading = false;
      })
    }
  }
}
</script>
