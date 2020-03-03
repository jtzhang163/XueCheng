<template>
  <div>
    <el-button type="primary" @click="query">查询</el-button>
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
      :total="total"
      :current-page="params.page"
      :page-size="params.size"
      :page-count="8"
      @current-change="changePages"
      style="float: right">
    </el-pagination>
  </div>
</template>
<script>
  import * as cmsApi from '../api/cms'
  /*编写页面静态部分，即model及vm部分。*/
  export default {
    data() {
      return {
        list: [],
        total: 0,
        params: {
          page: 1,
          size: 10
        }
      }
    },
    methods: {
      query: function () {
        cmsApi.page_list(this.params.page, this.params.size).then((res) => {
          this.list = res.queryResult.list;
          this.total = res.queryResult.total;
        })
      },
      changePages: function (page) {
        this.params.page = page;
        this.query();
      }
    },
    mounted(){
      //当DOM页面全部加载完成时查询
      this.query();
    }
  }
</script>
<style>
  /*编写页面样式，不是必须*/
</style>
