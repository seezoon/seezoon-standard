// 合同状态枚举
import {getDictName} from "@/utils/param";

// 排序
export const SORT_ORDER = {
  asc: 'asc',
  desc: 'desc',
};
export const DICT_RECORD_STATUS = "record-status";
// DB默认状态字典
export const RECORD_STATUS = {
  enable: {value: 1, text: getDictName(DICT_RECORD_STATUS, 1)},
  disable: {value: 0, text: getDictName(DICT_RECORD_STATUS, 0)},
};


