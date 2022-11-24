/**
 * Global authority directive
 * Used for fine-grained control of component permissions
 * @Example v-auth="['p1','p2']" or  v-auth="'p1'"
 */
import type {App, Directive, DirectiveBinding} from 'vue';

import {getUserStore} from '@/store';

function isArray(val: any): val is Array<any> {
  return val && Array.isArray(val);
}

function intersection(a1, a2) {
  let a = new Set(a1);
  let b = new Set(a2);
  let arr = Array.from(new Set([...b].filter(x => a.has(x))));
  return arr;
}

export function hasPermission(value?: string | string[]): boolean {
  if (!value) {
    return true;
  } else {
    const userStore = getUserStore();
    const roles = userStore.roles;
    if (!isArray(value)) {
      return roles?.includes(value);
    }
    return (intersection(value, roles)).length > 0;
  }
}


function isAuth(el: Element, binding: any) {

  const value = binding.value;
  if (!value) return;
  if (!hasPermission(value)) {
    el.parentNode?.removeChild(el);
  }
}

const mounted = (el: Element, binding: DirectiveBinding<any>) => {
  isAuth(el, binding);
};

const authDirective: Directive = {
  mounted,
};

export function setupPermissionDirective(app: App) {
  app.directive('auth', authDirective);
}

export default authDirective;
