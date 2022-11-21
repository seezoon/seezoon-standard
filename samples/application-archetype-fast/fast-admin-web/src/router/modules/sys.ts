import Layout from '@/layouts/index.vue';
import SettingIcon from '@/assets/custom/setting.svg'

export default [
  {
    path: '/sys',
    component: Layout,
    redirect: '/sys/user',
    name: 'Sys',
    meta: {title: '系统管理', icon: SettingIcon, orderNo: -1},
    children: [
      {
        path: 'user',
        name: 'SysUser',
        component: () => import('@/pages/sys/user/index.vue'),
        meta: {title: '用户管理'},
      },
      {
        path: 'user/:userId',
        name: 'SysUserDetail',
        component: () => import('@/pages/sys/user/Detail.vue'),
        meta: {title: '用户详情', hidden: true},
      },
    ],
  },
];
