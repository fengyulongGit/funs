package com.autils.api;

import com.autils.api.response.BaseResponse;
import com.autils.api.response.model.AppVersion;
import com.autils.api.response.model.CompanyImages;
import com.autils.api.response.model.FaceToken;
import com.autils.api.response.model.SellerInfo;
import com.autils.api.response.model.Token;
import com.autils.api.response.model.TokenCheck;
import com.autils.api.response.model.UploadCompany;
import com.autils.api.response.model.UserDetail;
import com.autils.api.response.model.UserInfo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jisuyun on 2017/8/29.
 */

public interface APIService {
    @GET("internal/appbiz/version")
    Observable<BaseResponse<AppVersion>> version(
            @Query("name") String name,
            @Query("os") String os,
            @Query("version") String version
    );

    @FormUrlEncoded
    @POST("internal/selleroauth/token")
    Observable<BaseResponse<Token>> token(
            @Field("user_name") String user_name,
            @Field("password") String password,
            @Field("random_str") String random_str,
            @Field("device_sn") String device_sn
    );

    @FormUrlEncoded
    @POST("internal/selleroauth/token_check")
    Observable<BaseResponse<TokenCheck>> token_check(
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("internal/selleroauth/token_refresh")
    Observable<BaseResponse<Token>> token_refresh(
            @Field("refresh_token") String refresh_token
    );

    @GET("internal/seller/info")
    Observable<BaseResponse<SellerInfo>> seller_info(
            @Query("token") String token
    );

    @FormUrlEncoded
    @POST("internal/seller/reset_password")
    Observable<BaseResponse<Object>> reset_password(
            @Field("token") String token,
            @Field("password") String password,
            @Field("new_password") String new_password
    );

    @GET("internal/customer/user_detail")
    Observable<BaseResponse<UserDetail>> user_detail(
            @Query("token") String token,
            @Query("customer_id") String customer_id
    );


    @GET("internal/facebiz/face_token")
    Observable<BaseResponse<FaceToken>> face_token(
            @Query("token") String token,
            @Query("customer_id") String customer_id,
            @Query("name") String name,
            @Query("idcard") String idcard
    );

    @FormUrlEncoded
    @POST("internal/facebiz/face_callback")
    Observable<BaseResponse<Object>> face_callback(
            @Field("token") String token,
            @Field("customer_id") String customer_id,
            @Field("face_token") String facetoken
    );

    @FormUrlEncoded
    @POST("internal/sellerbiz/user_info")
    Observable<BaseResponse<UserInfo>> user_info(
            @Field("token") String token,
            @Field("customer_id") String customer_id
    );

    @GET("internal/companybiz/images")
    Observable<BaseResponse<CompanyImages>> images(
            @Query("token") String token,
            @Query("customer_id") String customer_id
    );

    @POST("internal/companybiz/image_upload")
    Observable<BaseResponse<UploadCompany>> image_upload(
            @Body RequestBody body
    );

    @FormUrlEncoded
    @POST("internal/companybiz/images")
    Observable<BaseResponse<Object>> images(
            @Field("token") String token,
            @Field("customer_id") String customer_id,
            @Field("skip") String skip,
            @Field("license_cert_image") String license_cert_image,
            @Field("industry_cert_image") String industry_cert_image,
            @Field("site_contract_image") String site_contract_image,
            @Field("company_image") String company_image,
            @Field("league_image") String league_image,
            @Field("indoor_image") String indoor_image,
            @Field("other_image") String other_image
    );
}
