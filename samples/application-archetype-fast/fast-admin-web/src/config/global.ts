export const prefix = 'tdesign-starter';
export const TOKEN_NAME = 'token';
export const getStorageToken = function () {
  return localStorage.getItem(TOKEN_NAME) || sessionStorage.getItem(TOKEN_NAME);
}
export const getStorageHeaderToken = function () {
  return {"Authorization": "Bearer " + getStorageToken()}
}
export const removeStorageToken = function () {
  localStorage.removeItem(TOKEN_NAME);
  sessionStorage.removeItem(TOKEN_NAME);
}
