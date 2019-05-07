package com.autils.api;

import com.autils.api.response.BaseResponseFunc;
import com.autils.api.response.RefreshTokenFunction;
import com.autils.api.response.TokenRefreshResponseFunc;
import com.autils.api.response.TokenResponseFunc;
import com.autils.api.response.model.AppVersion;
import com.autils.api.response.model.CompanyImages;
import com.autils.api.response.model.FaceToken;
import com.autils.api.response.model.SellerInfo;
import com.autils.api.response.model.Token;
import com.autils.api.response.model.TokenCheck;
import com.autils.api.response.model.UploadCompany;
import com.autils.api.response.model.UserDetail;
import com.autils.api.response.model.UserInfo;
import com.autils.api.utils.FileUtils;
import com.autils.api.utils.StringUtils;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * Created by jisuyun on 2017/8/29.
 */

public final class DataSource {

    private APIService apiService;

    public DataSource(Retrofit retrofitApi) {
        apiService = retrofitApi.create(APIService.class);
    }

    public Observable<AppVersion> version(
            String version
    ) {
        return apiService.version(
                ServerClient.getInstance().getServerConfig().getAppname(),
                "android",
                version
        ).map(new BaseResponseFunc<AppVersion>());
    }

    public Observable<Token> token(
            String mobile,
            String password,
            String device_sn
    ) {
        return apiService.token(
                mobile,
                StringUtils.aesEncrypt(password),
                System.currentTimeMillis() + "",
                device_sn
        ).map(new TokenResponseFunc());
    }

    public Observable<TokenCheck> token_check(
    ) {
        return apiService.token_check(
                ServerClient.getInstance().getServerToken().getAccess_token()
        ).map(new BaseResponseFunc<TokenCheck>());
    }

    public Observable<Token> token_refresh(
    ) {
        return apiService.token_refresh(
                ServerClient.getInstance().getServerToken().getRefresh_token()
        ).map(new TokenRefreshResponseFunc());
    }

    public Observable<SellerInfo> seller_info(
    ) {
        return Observable.just(new Object())
                .flatMap(new Function<Object, ObservableSource<SellerInfo>>() {
                    @Override
                    public ObservableSource<SellerInfo> apply(Object object) {
                        return apiService.seller_info(
                                ServerClient.getInstance().getServerToken().getAccess_token()
                        ).map(new BaseResponseFunc<SellerInfo>());
                    }
                })
                .retryWhen(new RefreshTokenFunction());
    }

    public Observable<Object> reset_password(
            final String password,
            final String new_password
    ) {
        return Observable.just(new Object())
                .flatMap(new Function<Object, ObservableSource<Object>>() {
                    @Override
                    public ObservableSource<Object> apply(Object object) {
                        return apiService.reset_password(
                                ServerClient.getInstance().getServerToken().getAccess_token(),
                                password,
                                new_password
                        ).map(new BaseResponseFunc<>());
                    }
                })
                .retryWhen(new RefreshTokenFunction());
    }

    public Observable<UserDetail> user_detail(
            final String customer_id
    ) {
        return Observable.just(new Object())
                .flatMap(new Function<Object, ObservableSource<UserDetail>>() {
                    @Override
                    public ObservableSource<UserDetail> apply(Object object) {
                        return apiService.user_detail(
                                ServerClient.getInstance().getServerToken().getAccess_token(),
                                customer_id
                        ).map(new BaseResponseFunc<UserDetail>());
                    }
                })
                .retryWhen(new RefreshTokenFunction());
    }

    public Observable<FaceToken> face_token(
            final String customer_id,
            final String name,
            final String idcard
    ) {
        return Observable.just(new Object())
                .flatMap(new Function<Object, ObservableSource<FaceToken>>() {
                    @Override
                    public ObservableSource<FaceToken> apply(Object object) {
                        return apiService.face_token(
                                ServerClient.getInstance().getServerToken().getAccess_token(),
                                customer_id,
                                name,
                                idcard
                        ).map(new BaseResponseFunc<FaceToken>());
                    }
                })
                .retryWhen(new RefreshTokenFunction());
    }

    public Observable<Object> face_callback(
            final String customer_id,
            final String face_token
    ) {
        return Observable.just(new Object())
                .flatMap(new Function<Object, ObservableSource<Object>>() {
                    @Override
                    public ObservableSource<Object> apply(Object object) {
                        return apiService.face_callback(
                                ServerClient.getInstance().getServerToken().getAccess_token(),
                                customer_id,
                                face_token
                        ).map(new BaseResponseFunc<>());
                    }
                })
                .retryWhen(new RefreshTokenFunction());
    }

    public Observable<UserInfo> user_info(
            final String customer_id
    ) {
        return Observable.just(new Object())
                .flatMap(new Function<Object, ObservableSource<UserInfo>>() {
                    @Override
                    public ObservableSource<UserInfo> apply(Object object) {
                        return apiService.user_info(
                                ServerClient.getInstance().getServerToken().getAccess_token(),
                                customer_id
                        ).map(new BaseResponseFunc<UserInfo>());
                    }
                })
                .retryWhen(new RefreshTokenFunction());
    }

    public Observable<CompanyImages> images(
            final String customer_id
    ) {
        return Observable.just(new Object())
                .flatMap(new Function<Object, ObservableSource<CompanyImages>>() {
                    @Override
                    public ObservableSource<CompanyImages> apply(Object object) {
                        return apiService.images(
                                ServerClient.getInstance().getServerToken().getAccess_token(),
                                customer_id
                        ).map(new BaseResponseFunc<CompanyImages>());
                    }
                })
                .retryWhen(new RefreshTokenFunction());
    }

    public Observable<UploadCompany> image_upload(
            final String customer_id,
            final CompanyImages.Type type,
            final String filePath
    ) {
        return Observable.just(new Object())
                .flatMap(new Function<Object, ObservableSource<UploadCompany>>() {
                    @Override
                    public ObservableSource<UploadCompany> apply(Object object) {
                        File mFile = new File(filePath);
                        RequestBody totalMB = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("token", ServerClient.getInstance().getServerToken().getAccess_token())
                                .addFormDataPart("customer_id", customer_id)
                                .addPart(FileUtils.fileToHeader(mFile, type.getType()), FileUtils.fileToRequestBody(mFile))
                                .build();

                        return apiService.image_upload(
                                totalMB
                        ).map(new BaseResponseFunc<UploadCompany>());
                    }
                })
                .retryWhen(new RefreshTokenFunction());
    }

    public Observable<Object> images(
            final String customer_id,
            final CompanyImages companyImages
    ) {
        return Observable.just(new Object())
                .flatMap(new Function<Object, ObservableSource<Object>>() {
                    @Override
                    public ObservableSource<Object> apply(Object object) {
                        String skip = "0";
                        if (StringUtils.isNullOrEmpty(companyImages.getLicense_cert_image()) &&
                                StringUtils.isNullOrEmpty(companyImages.getIndustry_cert_image()) &&
                                StringUtils.isNullOrEmpty(companyImages.getSite_contract_image()) &&
                                StringUtils.isNullOrEmpty(companyImages.getCompany_image()) &&
                                StringUtils.isNullOrEmpty(companyImages.getLeague_image()) &&
                                StringUtils.isNullOrEmpty(companyImages.getIndoor_image()) &&
                                StringUtils.isNullOrEmpty(companyImages.getOther_image())
                        ) {
                            skip = "1";
                        }
                        return apiService.images(
                                ServerClient.getInstance().getServerToken().getAccess_token(),
                                customer_id,
                                skip,
                                companyImages.getLicense_cert_image(),
                                companyImages.getIndustry_cert_image(),
                                companyImages.getSite_contract_image(),
                                companyImages.getCompany_image(),
                                companyImages.getLeague_image(),
                                companyImages.getIndoor_image(),
                                companyImages.getOther_image()
                        ).map(new BaseResponseFunc<>());
                    }
                })
                .retryWhen(new RefreshTokenFunction());
    }
}
