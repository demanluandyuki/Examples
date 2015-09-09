package com.htc.globalsearch.imagesearch.service.aidl;

import com.htc.globalsearch.imagesearch.service.aidl.PersonImageItem;
interface ICallBack{
    void onQueryResult(in List<PersonImageItem> items,String path,int filter);
    void onServiceStatusChanged(int status);
}