import request from '@/utils/request.js'



//医院列表查询
export const hospitalListService = (params)=>{
   return request.get('/hospital',{params:params})
}