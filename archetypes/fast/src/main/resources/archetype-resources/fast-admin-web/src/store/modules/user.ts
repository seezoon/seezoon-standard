import {defineStore} from 'pinia';
import {removeStorageToken, TOKEN_NAME} from '@/config/global';
import {store} from '@/store';
import {request} from '@/utils/request';
import {resolveFile} from "@/utils/param";

const InitUserInfo = {
  name: undefined,
  username: undefined,
  photo: undefined,
  roles: undefined,
};

export const useUserStore = defineStore('user', {
  state: () => ({
    // token: getStorageToken(), 这里用了localStorage 持久化 不安全
    userInfo: InitUserInfo,
  }),
  getters: {
    roles: (state) => {
      return state.userInfo?.roles;
    },
  },
  actions: {
    async login(token: string, rememberMe: boolean) {
      localStorage.clear();
      sessionStorage.clear();
      //this.token = token;
      if (rememberMe) {
        localStorage.setItem(TOKEN_NAME, token);
      } else {
        sessionStorage.setItem(TOKEN_NAME, token);
      }
    },
    async getUserInfo() {
      const {info, roles, permissions} = await request.get({
        url: '/sys/user/personal'
      });
      this.userInfo = info;
      this.userInfo.photo = resolveFile(info.photo);
      this.userInfo.roles = roles.concat(permissions);
    },
    async logout() {
      removeStorageToken();
      // this.token = '';
      this.userInfo = InitUserInfo;
      localStorage.clear();
      sessionStorage.clear();
    },
    async removeToken() {
      // this.token = '';
    },
  },
  /* persist: {
     afterRestore: (ctx) => {
       if (ctx.store.roles) {
         const permissionStore = usePermissionStore(store);
         permissionStore.initRoutes(ctx.store.roles);
       }
     },
   },*/
  persist: false
});

export function getUserStore() {
  return useUserStore(store);
}
