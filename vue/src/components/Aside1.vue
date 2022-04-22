<template>
    <div>
        <el-menu
                style="width: 200px; min-height: 100vh"
                default-active="2"
                class="el-menu-vertical-demo">
            <el-sub-menu index="1">
                <template #title>
                    选项1
                </template>
                    <el-menu-item index="1-3">选项1-1</el-menu-item>
            </el-sub-menu>
            <el-menu-item index="2">
                <el-icon><icon-menu /></el-icon>
                <template #title>Navigator Two</template>
            </el-menu-item>
            <el-menu-item index="4">
                <el-icon><setting /></el-icon>
                <template #title>Navigator Four</template>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script>
    export default {
        name: "Aside"
    }
</script>

<style scoped>

</style>