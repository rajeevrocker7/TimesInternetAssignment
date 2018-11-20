package com.rajeev.timesinternetassignment.retrofit;

///**
// * Created by Rajeev Sharma [rajeevrocker7@gmail.com] on 13/11/2018.
// */


import com.rajeev.timesinternetassignment.beans.ResponseBean;
import com.rajeev.timesinternetassignment.constants.AppConstants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    /**
     * https://api.getmeashop.com/seller2/api/v2/product/?format=json&limit=20&offset=0
     * format:json
     * offset:0 - 20
     */
    @GET(AppConstants.kProductsList)
    Call<ResponseBean> getProducts(@Query(AppConstants.kFormat) String format,
                                   @Query(AppConstants.kLimit) String limit,
                                   @Query(AppConstants.kOffset) String offset);

    /**
     * https://api.getmeashop.com/seller2/api/v2/product/?format=json&id=705655
     * format:json
     * id:705655
     */
    @GET(AppConstants.kProductsList)
    Call<ResponseBean> getProductInfo(@Query(AppConstants.kFormat) String format,
                                      @Query(AppConstants.kItemInfoId) String itemId);


}