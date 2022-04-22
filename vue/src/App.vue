<template>
  <div>
    <!--头部-->
    <Header />
    <!--主体-->
    <div style="display:flex">
      <!--侧边栏-->
      <Aside />
      <!--内容区域-->
      <router-view style="flex: 1"/>
    </div>
  </div>

</template>

<style>

</style>
<script>

  import Header from './components/Header1'
  import Aside from "./components/Aside1";

  export default {
      name: "Layout",
      components:{
          Aside,
          Header
      }
  }
</script>