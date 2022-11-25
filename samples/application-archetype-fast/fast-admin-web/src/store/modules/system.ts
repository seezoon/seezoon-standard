import {defineStore} from 'pinia';
import {request} from '@/utils/request';
import store from "@/store";

const SystemInfo = {
  fileUrlPrefix: undefined,
  dicts: undefined
}
export const useSystemStore = defineStore('system', {
  state: () => ({
    inited: false,
    systemInfo: SystemInfo
  }),
  getters: {
    dictMap: (state) => {
      if (state.systemInfo.dicts) {
        const dictMap = new Map();
        for (const item of state.systemInfo.dicts) {
          if (!dictMap.get(item.type)) {
            dictMap.set(item.type, new Array(item));
          } else {
            dictMap.get(item.type).push(item)
          }
        }
        return dictMap;
      } else {
        return new Map()
      }
    },
  },
  actions: {
    async getSystemInfo() {
      const data = await request.get({
        url: '/sys/param/qry/all',
      });
      this.systemInfo = data;
      this.inited = true;
      return data;
    },
  },
  persist: false,
});

export function getSystemStore() {
  return useSystemStore(store);
}
