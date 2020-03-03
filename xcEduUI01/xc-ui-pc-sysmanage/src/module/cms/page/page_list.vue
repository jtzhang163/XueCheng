<template>
  <div>
    <el-button type="primary">主要按钮</el-button>
    <!--编写页面静态部分，即view部分-->
    <el-table
      :data="list"
      stripe
      style="width: 100%">
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="pageName" label="页面名称" width="120">
      </el-table-column>
      <el-table-column prop="pageAliase" label="别名" width="120">
      </el-table-column>
      <el-table-column prop="pageType" label="页面类型" width="150">
      </el-table-column>
      <el-table-column prop="pageWebPath" label="访问路径" width="250">
      </el-table-column>
      <el-table-column prop="pagePhysicalPath" label="物理路径" width="250">
      </el-table-column>
      <el-table-column prop="pageCreateTime" label="创建时间" width="180" >
      </el-table-column>
    </el-table>

    <el-pagination
      layout="prev, pager, next"
      :total="50"
      :current-page="params.page"
      :page-size="params.size"
      :page-count="8"
      style="float: right">
    </el-pagination>
  </div>
</template>
<script>
  /*编写页面静态部分，即model及vm部分。*/
  export default {
    data() {
      return {
        list: [
          {
            "siteId": "5a751fab6abb5044e0d19ea1",
            "pageId": "5a754adf6abb500ad05688d9",
            "pageName": "index.html",
            "pageAliase": "首页",
            "pageWebPath": "/index.html",
            "pageParameter": null,
            "pagePhysicalPath": "F:\\develop\\xc_portal_static\\",
            "pageType": "0",
            "pageTemplate": null,
            "pageHtml": null,
            "pageStatus": null,
            "pageCreateTime": "2018-02-03T05:37:53.256+0000",
            "templateId": "5a962b52b00ffc514038faf7",
            "pageParams": null,
            "htmlFileId": "5a7c1c54d019f14d90a1fb23",
            "dataUrl": null
          },
          {
            "siteId": "5a751fab6abb5044e0d19ea1",
            "pageId": "5a795ac7dd573c04508f3a56",
            "pageName": "index_banner.html",
            "pageAliase": "轮播图",
            "pageWebPath": "/include/index_banner.html",
            "pageParameter": null,
            "pagePhysicalPath": "F:\\develop\\xc_portal_static\\include\\",
            "pageType": "0",
            "pageTemplate": null,
            "pageHtml": null,
            "pageStatus": null,
            "pageCreateTime": "2018-02-06T07:34:21.255+0000",
            "templateId": "5a962bf8b00ffc514038fafa",
            "pageParams": null,
            "htmlFileId": "5a795bbcdd573c04508f3a59",
            "dataUrl": null
          }
        ],
        params:{
          page:3,
          size:5
        }
      }
    }
  }
</script>
<style>
  /*编写页面样式，不是必须*/
</style>
