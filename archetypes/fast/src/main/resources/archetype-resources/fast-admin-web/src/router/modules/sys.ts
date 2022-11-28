import Layout from '@/layouts/index.vue';
import SettingIcon from '@/assets/custom/setting.svg'
// meta 中roleCode: ''，来控制权限
export default [
  {
    path: '/sys',
    component: Layout,
    redirect: '/sys/user',
    name: 'Sys',
    meta: {title: '系统管理', icon: SettingIcon, orderNo: 100},
    children: [
      {
        path: 'user',
        name: 'SysUser',
        component: () => import('@/pages/sys/user/index.vue'),
        meta: {title: '用户管理'},
      },
      {
        path: 'user/detail',
        name: 'SysUserDetail',
        component: () => import('@/pages/sys/user/Detail.vue'),
        meta: {title: '用户详情', hidden: true},
      },
      {
        path: 'user/center',
        name: 'SysUserCenter',
        component: () => import('@/pages/sys/user/Center.vue'),
        meta: {title: '用户中心', hidden: true},
      },
    ],
  },
];
