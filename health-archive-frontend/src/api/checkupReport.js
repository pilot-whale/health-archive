import request from '@/utils/request.js'



//医院列表查询
export const checkupReportListService = (params) => {
   return request.get('/checkupReport', { params: params })
}

export const checkupReportDeleteService = (id) => {
   return request.delete(`/checkupReport/delete/${id}`)
}

export const checkupDataService = (params) => {
   return request.get('/checkupData', { params: params })
}

export const checkupItemService = () => {
   return request.get('/checkupItem')
}

export const checkupIndicatorService = (id) => {
   return request.get(`/checkupIndicator/${id}`)
}

export const checkupVisualService = (params) => {
   return request.get('/checkupData/visual', { params: params })
}

export const checkupAdminService = (params) => {
   return request.get('/checkupReport/admin', { params: params })
}