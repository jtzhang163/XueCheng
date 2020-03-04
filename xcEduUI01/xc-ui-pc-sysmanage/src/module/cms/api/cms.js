import http from './../../../base/api/public'
import querystring from 'querystring'
let sysConfig = require('@/../config/sysConfig')
let apiUrl = sysConfig.xcApiUrlPre;


//定义方法，请求服务端的页面查询接口
export const page_list = (page, size, params) => {

  let query = querystring.stringify(params);
  //请求服务端页面查询接口
  return http.requestQuickGet(apiUrl + '/cms/page/list/' + page + '/' + size + '?' + query);
}


//定义方法，请求服务端的新增页面接口
export const page_add = (params) => {
  return http.requestPost(apiUrl + '/cms/page/add/', params);
}


//查询某个页面
export const page_get = (id) => {
  return http.requestQuickGet(apiUrl + '/cms/page/get/'+ id);
}


//编辑某个页面
export const page_edit = (id, params) => {
  return http.requestPut(apiUrl + '/cms/page/edit/'+ id, params);
}

