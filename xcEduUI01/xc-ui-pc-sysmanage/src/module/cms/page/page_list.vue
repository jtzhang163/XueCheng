<template>
  <div>
    <!--查询表单-->
    <el-form :model="params">
      <el-select v-model="params.siteId" placeholder="请选择站点">
        <el-option
          v-for="item in siteList"
          :key="item.siteId"
          :label="item.siteName"
          :value="item.siteId">
        </el-option>
      </el-select>
      页面别名：<el-input v-model="params.pageAliase" style="width: 100px"></el-input>
      <el-button type="primary" v-on:click="query" size="small">查询</el-button>

      <router-link :to="{path:'/cms/page/add', query:{
          page:this.params.page,
          siteId:this.params.siteId,
      }}">
        <el-button type="primary" @click="query">新增</el-button>
      </router-link>

    </el-form>

    <!--编写页面静态部分，即view部分-->
    <el-table
      :data="list"
      stripe
      style="width: 100%">
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="pageName" label="页面名称" width="120">
      </el-table-column>
      <el-table-column prop="pageAliase" label="别名" width="80">
      </el-table-column>
      <el-table-column prop="pageType" label="页面类型" width="80">
      </el-table-column>
      <el-table-column prop="pageWebPath" label="访问路径" width="250">
      </el-table-column>
      <el-table-column prop="pagePhysicalPath" label="物理路径" width="250">
      </el-table-column>
      <el-table-column prop="pageCreateTime" label="创建时间" width="80" >
      </el-table-column>
      <el-table-column label="操作" width="80">
        <template slot-scope="page">
          <el-button size="small" type="text"
                     @click="preview(page.row.pageId)">预览
          </el-button>
          <el-button size="small" type="text"
                     @click="edit(page.row.pageId)">编辑
          </el-button>
          <el-button size="small" type="text"
                     @click="del(page.row.pageId)">删除
          </el-button>
        </template>
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
        siteList: [],
        list: [],
        total: 0,
        params: {
          siteId:'',
          pageAliase:'',
          page: 1,
          size: 10
        }
      }
    },
    methods: {
      query: function () {
        cmsApi.page_list(this.params.page, this.params.size, this.params).then((res) => {
          this.list = res.queryResult.list;
          this.total = res.queryResult.total;
        })
      },
      changePages: function (page) {
        this.params.page = page;
        this.query();
      },
      preview: function(pageId) {
        window.open('http://www.xuecheng.com:8000/cms/preview/' + pageId);
      },
      edit: function (pageId) {
        this.$router.push({path: '/cms/page/edit/' + pageId});
      },
      del: function (pageId) {

        this.$confirm('确认删除吗？', '提示', {}).then(() => {
          cmsApi.page_del(pageId).then((res) => {
            if (res.success) {
              this.$message.success('删除成功');
              //刷新页面
              this.query()
            } else {
              this.$message.error('删除失败');
            }
          });
        })
      }
    },

    created() {
      this.params.page = Number.parseInt(this.$route.query.page || 1);
      this.params.siteId = this.$route.query.siteId;
    },

    mounted() {
      //当DOM页面全部加载完成时查询
      this.query();

      this.siteList = [
        {
          siteId: "5a751fab6abb5044e0d19ea1",
          siteName: '门户主站'
        },
        {
          siteId: '1123334',
          siteName: '测试站'
        }
      ]
    }
  }
</script>
<style>
  /*编写页面样式，不是必须*/
</style>
