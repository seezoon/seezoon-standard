export default {
  isRequestProxy: true,
  // 如果为空就是当前前端的地址，也可以指定，host:'https://seezoon.com'
  development: {
    // 开发环境接口请求
    host: '',
    // 开发环境 cdn 路径
    cdn: '',
  },
  test: {
    // 测试环境接口地址
    host: '',
    // 测试环境 cdn 路径
    cdn: '',
  },
  release: {
    // 正式环境接口地址
    host: '',
    // 正式环境 cdn 路径
    cdn: '',
  },
};
