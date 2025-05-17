<script setup>
import { ref } from 'vue'

const state = ref('')
const grade = ref('')
const level = ref('')
const jci = ref(null)
const cap = ref(null)

//文章列表数据模型
const hospitals = ref([
    {
        "id": 1,
        "name": "复旦大学附属华山医院",
        "address": "上海市静安区乌鲁木齐中路12号（总院）/浦东新区红枫路525号（东院）",
        "postalCode": "200040",
        "phone": "021-52889999（总机）",
        "officialWebsite": "https://www.huashan.org.cn",
        "state": '公立医院',
        "grade": '三级甲等',
        "level": '国家级',
        "JCI": true,
        "CAP": true
    },
    {
        "id": 2,
        "name": "中国医学科学院北京协和医院",
        "address": "北京市东城区帅府园1号（东院）/西城区大木仓胡同41号（西院）",
        "postalCode": "100730",
        "phone": "010-69156114（总机）",
        "officialWebsite": "https://www.pumch.cn",
        "state": '公立医院',
        "grade": '三级甲等',
        "level": '国家级',
        "JCI": true,
        "CAP": true
    },
    {
        "id": 3,
        "name": "四川大学华西医院",
        "address": "四川省成都市武侯区国学巷37号（主院区）/高新区天府大道南段2555号（温江院区）",
        "postalCode": "610041",
        "phone": "028-85422114（总机）",
        "officialWebsite": "https://www.cd120.com",
        "state": '公立医院',
        "grade": '三级甲等',
        "level": '国家级',
        "JCI": true,
        "CAP": true
    },
    {
        "id": 4,
        "name": "中山大学附属第一医院",
        "address": "广东省广州市越秀区中山二路58号（主院区）/黄埔区开创大道2693号（黄埔院区）",
        "postalCode": "510080",
        "phone": "020-28823388（总机）",
        "officialWebsite": "https://www.gzsums.net",
        "state": '公立医院',
        "grade": '三级甲等',
        "level": '国家级',
        "JCI": true,
        "CAP": true
    },
    {
        "id": 5,
        "name": "浙江大学医学院附属第一医院",
        "address": "浙江省杭州市上城区庆春路79号（庆春院区）/余杭区文一西路1367号（总部一期）",
        "postalCode": "310003",
        "phone": "0571-87236114（总机）",
        "officialWebsite": "https://www.zy91.com",
        "state": '公立医院',
        "grade": '三级甲等',
        "level": '国家级',
        "JCI": true,
        "CAP": true
    },
    {
        "id": 6,
        "name": "华中科技大学同济医学院附属同济医院",
        "address": "湖北省武汉市硚口区解放大道1095号（主院区）/东湖高新区高新大道501号（光谷院区）",
        "postalCode": "430030",
        "phone": "027-83662688（总机）",
        "officialWebsite": "https://www.tjh.com.cn",
        "state": '公立医院',
        "grade": '三级甲等',
        "level": '国家级',
        "JCI": true,
        "CAP": true
    },
    {
        "id": 7,
        "name": "上海交通大学医学院附属瑞金医院",
        "address": "上海市黄浦区瑞金二路197号（总院）/嘉定区双丁路888号（北部院区）",
        "postalCode": "200025",
        "phone": "021-64370045（总机）",
        "officialWebsite": "https://www.rjh.com.cn",
        "state": '公立医院',
        "grade": '三级甲等',
        "level": '国家级',
        "JCI": true,
        "CAP": true
    },
    {
        "id": 8,
        "name": "和睦家医疗（上海）医院",
        "address": "上海市长宁区仙霞路1139号（浦东院区：上海市浦东新区沪南路2121号）",
        "postalCode": "200336",
        "phone": "400-639-3900（服务热线）",
        "officialWebsite": "https://shanghai.ufh.com.cn",
        "state": "私立医院",
        "grade": "",
        "level": "",
        "JCI": true,
        "CAP": true,
    },
    {
        "id": 9,
        "name": "上海市普陀区中心医院",
        "address": "上海市普陀区兰溪路164号",
        "postalCode": "200062",
        "phone": "021-22233222",
        "officialWebsite": "http://www.ptzxyy.com",
        "state": "公立医院",
        "grade": "二级甲等",
        "level": "区县级",
        "JCI": false,
        "CAP": false
    },
    {
        "id": 10,
        "name": "成都市武侯区第三人民医院",
        "address": "四川省成都市武侯区簇桥龙井中街1号",
        "postalCode": "610043",
        "phone": "028-85010372",
        "officialWebsite": "",
        "state": "公立医院",
        "grade": "二级乙等",
        "level": "区县级",
        "JCI": false,
        "CAP": false
    }

])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    hospitalList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    hospitalList()
}

//获取文章列表数据
const hospitalList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        state: state.value ? state.value : null,
        grade: grade.value ? grade.value : null,
        level: level.value ? level.value : null,
        jci: jci.value ? jci.value : null,
        cap: cap.value ? cap.value : null,
    }
    let result = await hospitalListService(params);

    //渲染视图
    total.value = result.data.total;
    hospitals.value = result.data.items;
}


// articleCategoryList()
hospitalList();

import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import { Plus } from '@element-plus/icons-vue'
//控制抽屉是否显示
const visibleDrawer = ref(false)
//添加表单数据模型
const articleModel = ref({
    title: '',
    categoryId: '',
    coverImg: '',
    content: '',
    state: '',
    type: [],
})


//导入token
import { useTokenStore } from '@/stores/token.js';
const tokenStore = useTokenStore();

//上传成功的回调函数
// const uploadSuccess = (result) => {
//     articleModel.value.coverImg = result.data;
//     console.log(result.data);
// }

//添加文章
import { ElMessage } from 'element-plus'
import { hospitalListService } from '../../api/hospital'
// const addArticle = async (clickState) => {
//     //把发布状态赋值给数据模型
//     articleModel.value.state = clickState;

//     //调用接口
//     let result = await articleAddService(articleModel.value);

//     ElMessage.success(result.msg ? result.msg : '添加成功');

//     //让抽屉消失
//     visibleDrawer.value = false;

//     //刷新当前列表
//     articleList()
// }
// </script>








<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>体检机构查询</span>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="医院性质：" style="width: 200px">
                <el-select placeholder="请选择" v-model="state">
                    <el-option label="公立医院" value="公立医院"></el-option>
                    <el-option label="私立医院" value="私立医院"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="医院等级：">
                <el-select placeholder="请选择" v-model="grade" style="width: 150px">
                    <el-option label="三级甲等" value="三级甲等"></el-option>
                    <el-option label="三级乙等" value="三级乙等"></el-option>
                    <el-option label="二级甲等" value="二级甲等"></el-option>
                    <el-option label="二级乙等" value="二级乙等"></el-option>
                    <el-option label="一级甲等" value="一级甲等"></el-option>
                    <el-option label="一级乙等" value="一级乙等"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="行政层级：">
                <el-select placeholder="请选择" v-model="level" style="width: 150px">
                    <el-option label="国家级" value="国家级"></el-option>
                    <el-option label="省级" value="省级"></el-option>
                    <el-option label="市级" value="市级"></el-option>
                    <el-option label="区县级" value="区县级"></el-option>
                    <el-option label="社区/乡镇级" value="社区/乡镇级"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="评级认证：">
                <el-checkbox v-model="jci" label="JCI" />
                <el-checkbox v-model="cap" label="CAP" />
            </el-form-item>
            <el-form-item class="el-form-item--right">
                <el-button type="primary" @click="hospitalList">搜索</el-button>
                <el-button @click="state = ''; grade = ''; level = ''; jci = null; cap = null;">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 文章列表 -->
        <el-table :data="hospitals" style="width: 100%">
            <el-table-column label="医院名称" width="270" prop="name"></el-table-column>
            <el-table-column label="地址" prop="address"></el-table-column>
            <el-table-column label="邮编" prop="postalCode"> </el-table-column>
            <el-table-column label="联系电话" prop="phone">
                <template #default="{ row }">
                    <div style="white-space: normal; word-break: break-all;">{{ row.phone }}</div>
                </template>
            </el-table-column>
            <el-table-column label="官网">
                <template #default="{ row }">
                    <a v-if="row.officialWebsite" :href="row.officialWebsite" target="_blank" rel="noopener noreferrer">
                        {{ row.officialWebsite }}
                    </a>
                    <span v-else>暂无官网</span>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
        <!-- 抽屉 -->
        <!-- <el-drawer v-model="visibleDrawer" title="添加文章" direction="rtl" size="50%">
            添加文章表单
            <el-form :model="articleModel" label-width="100px">
                <el-form-item label="文章标题">
                    <el-input v-model="articleModel.title" placeholder="请输入标题"></el-input>
                </el-form-item>
                <el-form-item label="文章分类">
                    <el-select placeholder="请选择" v-model="articleModel.categoryId">
                        <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="文章封面">
                    
                        auto-upload:设置是否自动上传
                        action:设置服务器接口路径
                        name:设置上传的文件字段名
                        headers:设置上传的请求头
                        on-success:设置上传成功的回调函数
                     
                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false" action="/api/upload"
                        name="file" :headers="{ 'Authorization': tokenStore.token }" :on-success="uploadSuccess">
                        <img v-if="articleModel.coverImg" :src="articleModel.coverImg" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="文章内容">
                    <div class="editor">
                        <quill-editor theme="snow" v-model:content="articleModel.content" contentType="html">
                        </quill-editor>
                    </div>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addArticle('已发布')">发布</el-button>
                    <el-button type="info" @click="addArticle('草稿')">草稿</el-button>
                </el-form-item>
            </el-form>
        </el-drawer> -->
    </el-card>
</template>
<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}

/* 抽屉样式 */
.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}

.editor {
    width: 100%;

    :deep(.ql-editor) {
        min-height: 200px;
    }
}
</style>