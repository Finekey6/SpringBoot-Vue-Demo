<template>
  <div style="padding: 10px">
    <!--功能区域-->
    <div style="margin: 10px 0">
      <el-button type= "primary" @click="add">新增</el-button>
      <el-button type="primary ">导入</el-button>
      <el-button type="primary">导出</el-button>
    </div>
    <!--搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入内容" style="width:20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>

    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column prop="id" label="ID" sortable />
      <el-table-column prop="username" label="用户名"  />
      <el-table-column prop="nickName" label="昵称" />
      <el-table-column prop="age" label="年龄" />
      <el-table-column prop="sex" label="性别" />
      <el-table-column prop="address" label="地址" />
      <el-table-column  fixed="right" label="Operations" width="120">
        <template #default="scope">
          <el-button type="text" size="small" style="color:cornflowerblue" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="Are you sure to delete this?" @confirm="handleDelete(scope.row.id)">
            <!--@confirm-->
            <template #reference>
               <el-button type="text" size="small" style="color:red;">删除</el-button>
            </template>
          </el-popconfirm>


        </template>
      </el-table-column>
    </el-table>

    <div style="margin:10px 0">
      <el-pagination
              v-model:currentPage="currentPage"
              v-model:page-size="pageSize"
              :page-sizes="[5, 10, 20]"
              :small="small"
              :disabled="disabled"
              :background="background"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange">
      </el-pagination>

      <el-dialog v-model="dialogVisible" title="Tips" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="用户名">
            <el-input v-model="form.username" style="width:80% " />
          </el-form-item>
        </el-form>
        <el-form :model="form" label-width="120px">
          <el-form-item label="昵称">
            <el-input v-model="form.nickName" style="width:80% " />
          </el-form-item>
        </el-form>
        <el-form :model="form" label-width="120px">
          <el-form-item label="年龄">
            <el-input v-model="form.age" style="width:80% " />
          </el-form-item>
        </el-form>
        <el-form :model="form" label-width="120px">
          <el-form-item label="性别">
            <el-radio v-model="form.sex" label="男" size="small">男</el-radio>
            <el-radio v-model="form.sex" label="女" size="small">女</el-radio>
            <el-radio v-model="form.sex" label="未知" size="small">未知</el-radio>
          </el-form-item>
        </el-form>
        <el-form :model="form" label-width="120px">
          <el-form-item label="地址">
            <el-input type="textarea" v-model="form.address" style="width:80% " />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">Cancel</el-button>
            <el-button type="primary" @click="save">Confirm</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import request from "../utils/request";


export default {
  name: 'HomeView',
  components: {
  },
    /*
    * 不了解Vue，但自我的理解为：前端页面初始加载时会自动调用export default下的方法，
    * 即data()&created()方法，
    * 但不是method:{}中的方法
    * */
  data(){
      return{ // 初始化页面中的一些属性数据
          form:{},
          dialogVisible: false,
          search:'',
          currentPage:1,
          pageSize:10,
          total:10,
          tableData:[]//存储页面表格的数据
      }
    },
    //页面初始加载时调用created()，内部再调用load(),显示数据
  created(){
      this.load();
    },
    methods:{
      load(){
          /*
          * 发送get请求：/user，并传请求参数pageNum/pageSize/search
          * 并由控制器springboot.springboot.controller.UserController标注了GetMapping的
          * findPage()处理请求;
          * findPage()方法调用了MybatisPlus的分页组件功能
          */
          //get请求以params:{}的方式传请求参数，与post、put请求传参方式不一样
          request.get("/user",{
              params:{
                  //将前端的数据传给后端
                  pageNum:this.currentPage,
                  pageSize:this.pageSize,
                  search:this.search
              }
          }).then(res => {
              console.log(res)//将数据打印到浏览器控制台json格式
              this.tableData=res.data.records//将后端的数据赋值给前端页面表格
              this.total=res.data.total
          })
      },
      save(){
          if(this.form.id){ //修改
              //put请求以对象(即this.form)的方式传请求参数,与get请求不同
              request.put("/user",this.form).then(res => {
                  console.log(res)
                  if(res.code === '0'){
                      this.$message({ //$message-->emelent ui自带弹窗提醒功能
                          type:"success",
                          message:"更新成功"
                      })
                  } else {
                      this.$message({
                          type:"error",
                          message:res.msg
                      })
                  }
                  /*
                  * 数据更新后不会立即显示在页面的表格中，需要刷新页面才会显示出来,
                  * 而且弹窗不会自动关闭，
                  * 为了解决这个问题加入以下2行代码即可*/
                  this.load()//调用load()，加载数据
                  this.dialogVisible=false//关闭弹窗
              })
          } else { //添加
              //post请求以对象(即this.form)的方式传请求参数，与get请求不同
              request.post("/user",this.form).then(res => {
                  //返回后端springboot.springboot.common.Result中设置的信息到浏览器控制台
                  console.log(res)
                  if(res.code === '0'){
                      this.$message({ //$message-->emelent ui自带功能
                          type:"success",
                          message:"添加成功"
                      })
                  } else {
                      this.$message({
                          type:"error",
                          message:res.msg
                      })
                  }

                  this.load()
                  this.dialogVisible=false
              });
              this.dialogVisible=false;
          }

      },
      add(){
          this.dialogVisible=true;
          //将弹出框中的信息都清空
          this.form={};
      },
      handleEdit(row){
          //深复制行对象row，使该条信息的内容显示在form对象绑定的弹框中
          this.form=JSON.parse(JSON.stringify(row));
          //true设置弹框可见
          this.dialogVisible=true
      },
      handleSizeChange(){ //改变当前每页的条目触发(pageSize进行了双向绑定-》v-model)
          /*直接调用load()即可，因为load()会根据前端的pageNum、pageSize数据的更改，
          * 而查询到相应的数据*/
          this.load()
      },
      handleCurrentChange(){ //改变当前页面触发 (currentPage进行了双向绑定-》v-model)
          /*直接调用load()即可，因为load()会根据前端的pageNum、pageSize数据的更改，
          * 而查询到相应的数据*/
          this.load()
      },
      handleDelete(id){
          // console.log(id)
          request.delete("/user/"+id).then(res => {
              console.log(res)
              if(res.code === '0'){
                  this.$message({ //$message-->emelent ui自带弹窗提醒功能
                      type:"success",
                      message:"删除成功"
                  })
              } else {
                  this.$message({
                      type:"error",
                      message:res.msg
                  })
              }
          })
          //删除之后重新加载表格:数据删除后，不会立即删除在表格中的信息，需要加载页面才会显示出来
          this.load()
      },
    }
}
</script>
