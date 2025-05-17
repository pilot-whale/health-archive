import { createRouter, createWebHistory } from 'vue-router'

//导入组件
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'

import UserInfoVue from '@/views/user/UserInfo.vue'
import UserResetPasswordVue from '@/views/user/UserResetPassword.vue'
import HospitalSearchVue from '@/views/hospital/HospitalSearch.vue'
import CheckupReportManagementVue from '@/views/checkupReport/CheckupReportManagement.vue'
import DataVue from '@/views/visualization/Data.vue'
import FamilySwitchVue from '@/views/family/FamilySwitch.vue'
import FamilyMemberVue from '@/views/family/FamilyMember.vue'
import FamilyResetPasswordVue from '@/views/family/FamilyResetPassword.vue'
import CheckupReportViewVue from '@/views/checkupReport/CheckupReportView.vue'
import ChartVue from '@/views/visualization/Chart.vue'
import TableVue from '@/views/visualization/Table.vue'

import UserAdminVue from '@/views/user/UserAdmin.vue'
import HospitalAdminVue from '@/views/hospital/HospitalAdmin.vue'
import FamilyAdminVue from '@/views/family/FamilyAdmin.vue'
import CheckupReportAdminVue from '@/views/checkupReport/CheckupReportAdmin.vue'

import { userInfoService } from '@/api/user.js'
const userInfoStore = userInfoService();

//定义路由关系
const routes = [
    { path: '/login', component: LoginVue },
    {
        path: '/', component: LayoutVue, redirect: '/hospital/search', children: [
            { path: '/hospital/search', component: HospitalSearchVue },
            { path: '/hospital/admin', component: HospitalAdminVue },
            { path: '/checkupReport/management', component: CheckupReportManagementVue },
            { path: '/checkupReport/view', component: CheckupReportViewVue, props: true },
            { path: '/checkupReport/admin', component: CheckupReportAdminVue },
            {
                path: '/visualization', component: DataVue,
                children: [
                    {
                        path: 'chart',
                        component: ChartVue
                    },
                    {
                        path: 'table',
                        component: TableVue
                    }
                ],
                props: true
            },
            { path: '/user/info', component: UserInfoVue },
            { path: '/user/resetPassword', component: UserResetPasswordVue },
            { path: '/user/Admin', component: UserAdminVue },
            { path: '/family/switch', component: FamilySwitchVue },
            { path: '/family/member', component: FamilyMemberVue },
            { path: '/family/resetPassword', component: FamilyResetPasswordVue },
            { path: '/family/Admin', component: FamilyAdminVue }
        ]
    }
]

//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

//导出路由
export default router
