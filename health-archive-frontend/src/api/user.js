//导入request.js请求工具
import request from '@/utils/request.js'

//提供调用注册接口的函数
export const userRegisterService = (data) => {
    return request.post('/user/register', data)
};
//提供调用登录接口的函数
export const userLoginService = (data) => {
    // request({
    //     'url': '/user/login',
    //     'method': 'post',
    //     data
    //   })

    return request.post('/user/login', data)
};



//获取用户详细信息
export const userInfoService = () => {
    return request.get('/user/userInfo')
}

export const userLogInfoService = (cardNumber) => {
    return request.get(`/user/userLogInfo/${cardNumber}`)
}

export const userTempLogin = (id) => {
    return request.post(`/user/tempLogin/${id}`)
}

export const userInfoCheckService = (originalPassword) => {
    return request.get(`/user/userCheck/${originalPassword}`)
}

//修改个人信息
export const userInfoUpdateService = (userInfoData) => {
    return request.put('/user/update', userInfoData)
}

//修改头像
export const userAvatarUpdateService = (avatarUrl) => {
    const params = new URLSearchParams();
    params.append('avatarUrl', avatarUrl)
    return request.patch('/user/updateAvatar', params)
}

export const userInfoListService = (params) => {
    return request.get('/user/listAll', { params: params })
}
