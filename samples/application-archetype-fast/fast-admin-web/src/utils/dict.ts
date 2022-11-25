import {getSystemStore} from "@/store";

export function getDict(type: string) {
  const systemStore = getSystemStore();
  return systemStore.dictMap.get(type);
}

export function getDictName(type: string, value: number | string) {
  const systemStore = getSystemStore();
  const dicts = systemStore.dictMap.get(type)
  if (dicts) {
    for (const dict of dicts) {
      if (dict.value == value) {
        return dict.label
      }
    }
  }
  return null;
}
