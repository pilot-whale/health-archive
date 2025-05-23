<script setup>
import {
    Management,
    Promotion,
    UserFilled,
    User,
    Crop,
    EditPen,
    SwitchButton,
    CaretBottom,
    HomeFilled,
    Connection,
    Search,
    House
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'

import { userInfoService } from '@/api/user.js'
import { familyInfoService } from '@/api/family.js'
import useUserInfoStore from '@/stores/userInfo.js'
import useFamilyInfoStore from '@/stores/familyInfo.js'
import { useTokenStore } from '@/stores/token.js'
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const tokenStore = useTokenStore();
const userInfoStore = useUserInfoStore();
const familyInfoStore = useFamilyInfoStore();
//调用函数,获取用户详细信息
const getUserInfo = async () => {
    //调用接口
    let result = await userInfoService();
    //数据存储到pinia中
    userInfoStore.setInfo(result.data);
    if (userInfoStore.info.familyGroup) {
        getFamilyInfo();
    }
}
getUserInfo();

const getFamilyInfo = async () => {
    //调用接口
    let result = await familyInfoService(userInfoStore.info.familyGroup);
    //数据存储到pinia中
    familyInfoStore.setInfo(result.data);
}



//条目被点击后,调用的函数
import { useRouter } from 'vue-router'
const router = useRouter();
import { ElMessage, ElMessageBox } from 'element-plus'
import { unique } from 'element-plus/es/utils/arrays.mjs'
const handleCommand = (command) => {
    //判断指令
    if (command === 'logout') {
        //退出登录
        ElMessageBox.confirm(
            '您确认要退出吗?',
            '温馨提示',
            {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
            }
        )
            .then(async () => {
                //退出登录
                //1.清空pinia中存储的token以及个人信息
                tokenStore.removeToken()
                userInfoStore.removeInfo()
                familyInfoStore.removeInfo()

                //2.跳转到登录页面
                router.push('/login')
                ElMessage({
                    type: 'success',
                    message: '退出登录成功',
                })

            })
            .catch(() => {
                ElMessage({
                    type: 'info',
                    message: '用户取消了退出登录',
                })
            })
    } else {
        //路由
        router.push('/user/' + command)
    }
}

const MyActiveMenu = computed(() => {
    // 处理子路由失去高亮的特殊处理
    if (route.path.startsWith('/visualization')) {
        return '/visualization'
    }
    if (route.path.startsWith('/checkupReport')) {
        return '/checkupReport'
    }
    return route.path
})

console.log(userInfoStore.info)
</script>

<template>
    <!-- element-plus中的容器 -->
    <el-container class="layout-container" v-if="userInfoStore.info.permission == 2">
        <!-- 左侧菜单 -->
        <el-aside width="200px">
            <div class="el-aside__logo"></div>
            <!-- element-plus的菜单标签 -->
            <el-menu :default-active="MyActiveMenu" active-text-color="#ffd04b" background-color="#232323"
                text-color="#fff" router>
                <el-menu-item index="/hospital/search">
                    <el-icon>
                        <Search />
                    </el-icon>
                    <span>体检机构查询</span>
                </el-menu-item>

                <el-menu-item index="/checkupReport/management">
                    <el-icon>
                        <Promotion />
                    </el-icon>
                    <span>体检记录</span>
                </el-menu-item>

                <el-menu-item index="/visualization">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>体检数据可视化</span>
                </el-menu-item>


                <el-sub-menu index="2">
                    <template #title>
                        <el-icon>
                            <UserFilled />
                        </el-icon>
                        <span>个人中心</span>
                    </template>
                    <el-menu-item index="/user/info">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>基本资料</span>
                    </el-menu-item>
                    <el-menu-item index="/user/resetPassword">
                        <el-icon>
                            <EditPen />
                        </el-icon>
                        <span>重置密码</span>
                    </el-menu-item>
                </el-sub-menu>

                <el-sub-menu index="3">
                    <template #title>
                        <el-icon>
                            <HomeFilled />
                        </el-icon>
                        <span>用户组中心</span>
                    </template>
                    <el-menu-item index="/family/switch">
                        <el-icon>
                            <Connection />
                        </el-icon>
                        <span>更换用户组</span>
                    </el-menu-item>
                    <el-menu-item index="/family/member">
                        <el-icon>
                            <House />
                        </el-icon>
                        <span>用户组成员</span>
                    </el-menu-item>
                    <!-- <el-menu-item index="/family/resetPassword">
                        <el-icon>
                            <EditPen />
                        </el-icon>
                        <span>重置密码</span>
                    </el-menu-item> -->
                </el-sub-menu>
            </el-menu>
        </el-aside>
        <!-- 右侧主区域 -->
        <el-container>
            <!-- 头部区域 -->
            <el-header>
                <div>当前用户：<strong>{{ userInfoStore.info.userName }}</strong></div>
                <!-- 下拉菜单 -->
                <!-- command: 条目被点击后会触发,在事件函数上可以声明一个参数,接收条目对应的指令 -->
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :src="userInfoStore.info.userPic ? userInfoStore.info.userPic : avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>
                            <el-dropdown-item command="avatar" :icon="Crop">更换头像</el-dropdown-item>
                            <el-dropdown-item command="resetPassword" :icon="EditPen">重置密码</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <!-- 中间区域 -->
            <el-main>
                <!-- <div style="width: 1290px; height: 570px;border: 1px solid red;">
                    内容展示区
                </div> -->
                <router-view></router-view>
            </el-main>
            <!-- 底部区域 -->
            <el-footer>健康档案系统 ©2025 Created by 朱信童</el-footer>
        </el-container>
    </el-container>













    <!-- 管理端页面布局 -->
    <el-container class="layout-container" v-else>
        <!-- 左侧菜单 -->
        <el-aside width="200px">
            <div class="el-aside__logo"></div>
            <!-- element-plus的菜单标签 -->
            <el-menu :default-active="MyActiveMenu" active-text-color="#ffd04b" background-color="#232323"
                text-color="#fff" router>
                <el-menu-item index="/hospital/admin">
                    <el-icon>
                        <Search />
                    </el-icon>
                    <span>体检机构管理</span>
                </el-menu-item>

                <el-menu-item index="/user/admin">
                    <el-icon>
                        <UserFilled />
                    </el-icon>
                    <span>用户总览</span>
                </el-menu-item>

                <el-menu-item index="/family/admin">
                    <el-icon>
                        <HomeFilled />
                    </el-icon>
                    <span>用户组总览</span>
                </el-menu-item>

                <el-menu-item index="/checkupReport/admin">
                    <el-icon>
                        <Promotion />
                    </el-icon>
                    <span>体检记录管理</span>
                </el-menu-item>

                <!-- <el-menu-item index="/family/resetPassword">
                        <el-icon>
                            <EditPen />
                        </el-icon>
                        <span>重置密码</span>
                    </el-menu-item> -->
            </el-menu>
        </el-aside>
        <!-- 右侧主区域 -->
        <el-container>
            <!-- 头部区域 -->
            <el-header>
                <div>当前管理员：<strong>{{ userInfoStore.info.userName }}</strong></div>
                <!-- 下拉菜单 -->
                <!-- command: 条目被点击后会触发,在事件函数上可以声明一个参数,接收条目对应的指令 -->
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :src="userInfoStore.info.userPic ? userInfoStore.info.userPic : avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>
                            <el-dropdown-item command="avatar" :icon="Crop">更换头像</el-dropdown-item>
                            <el-dropdown-item command="resetPassword" :icon="EditPen">重置密码</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <!-- 中间区域 -->
            <el-main>
                <!-- <div style="width: 1290px; height: 570px;border: 1px solid red;">
                    内容展示区
                </div> -->
                <router-view></router-view>
            </el-main>
            <!-- 底部区域 -->
            <el-footer>健康档案系统 ©2025 Created by 朱信童</el-footer>
        </el-container>
    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    height: 100vh;

    .el-aside {
        background-color: #232323;

        &__logo {
            height: 120px;
            background: url('@/assets/front.png') no-repeat center / 180px auto;
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
    }
}
</style>