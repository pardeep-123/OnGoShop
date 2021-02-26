package com.ongoshop.manager.restApi


import com.ongoshop.pojo.*
import com.ongoshop.utils.others.Constants
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*


interface RestApiInterface {

    @Multipart
    @POST(Constants.SignUp)
    fun signUp(
            @PartMap map: HashMap<String, RequestBody>, @Part image: MultipartBody.Part
    ): Observable<SignupResponsess>

    @FormUrlEncoded
    @PUT(Constants.Login)
    fun login(
            @FieldMap map: HashMap<String, String>
    ): Observable<LoginResponse>

    @FormUrlEncoded
    @PUT(Constants.Logout)
    fun getLogout(
            @FieldMap map: HashMap<String, String>):
            Observable<LogoutResponse>


    @FormUrlEncoded
    @PUT(Constants.ForgotPassword)
    fun forgotPassword(
            @FieldMap map: HashMap<String, String>
    ): Observable<ForgotPasswordResponse>

    @FormUrlEncoded
    @PUT(Constants.ChangePassword)
    fun changePassword(
            @FieldMap map: HashMap<String, String>
    ): Observable<ChangePasswordResponse>

    @FormUrlEncoded
    @PUT(Constants.VerifyOtp)
    fun verifyOtp(
            @FieldMap map: HashMap<String, String>
    ): Observable<VerifyOTPResponse>

    @FormUrlEncoded
    @PUT(Constants.ResendOtp)
    fun resendOtp(
            @FieldMap map: HashMap<String, String>
    ): Observable<ResendOTPResponse>

    @FormUrlEncoded
    @POST(Constants.AddCard)
    fun addCard(
            @FieldMap map: HashMap<String, String>
    ): Observable<AddCardResponse>

    @GET(Constants.GetProductList)
    fun getProductList():
            Observable<MyProductListingResponse>

    @GET(Constants.Notification)
    fun notificationList():
            Observable<NotificationsListResponse>

    @FormUrlEncoded
    @POST(Constants.OrdersList)
    fun ordersList(
            @FieldMap map: HashMap<String, String>
    ): Observable<OrderListResponse>

     @FormUrlEncoded
    @POST(Constants.Acceptorder)
    fun acceptorder(
            @FieldMap map: HashMap<String, String>
    ): Observable<AcceptOrderResponse>

    @FormUrlEncoded
    @POST(Constants.ShippedOrder)
    fun shippedOrder(
            @FieldMap map: HashMap<String, String>
    ): Observable<ShippedOrderResponse>

    @FormUrlEncoded
    @POST(Constants.IsSelfpickupOrders)
    fun isSelfpickupOrders(
            @FieldMap map: HashMap<String, String>
    ): Observable<DeliveryAndPickupOrderListResponse>

    @FormUrlEncoded
    @POST(Constants.FinishPacking)
    fun finishPacking(
            @FieldMap map: HashMap<String, String>
    ): Observable<FinishPackingResponse>

    @FormUrlEncoded
    @POST(Constants.Pastorders)
    fun pastOrdersList(
            @FieldMap map: HashMap<String, String>
    ): Observable<PastOrderListResponse>


    @FormUrlEncoded
    @POST(Constants.OrdersItems)
    fun ordersItems(
            @FieldMap map: HashMap<String, String>
    ): Observable<OrderItemsListResponse>

    @FormUrlEncoded
    @POST(Constants.CheckBarcode)
    fun checkBarcode(
            @FieldMap map: HashMap<String, String>
    ): Observable<CheckBarCodeAvailabilityResponse>

    @FormUrlEncoded
    @POST(Constants.GetProductbarcode)
    fun getProductbarcode(
            @FieldMap map: HashMap<String, String>
    ): Observable<GetProductbarcodeResponse>

    @GET(Constants.AllCards)
    fun allCards():
            Observable<GetAddedCardListResponse>


/*

    @DELETE(Constants.DeleteCard)
    fun deleteCard(
            @Field("id") cardId: String
    ): Observable<DeleteCardResponse>
*/

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "deleteCard", hasBody = true)
    fun deleteCard(@Field("id") id: String?): Observable<DeleteCardResponse>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "deleteproduct", hasBody = true)
    fun deleteProduct(@Field("id") id: String?): Observable<DeleteProductResponse>

    @FormUrlEncoded
    @PUT(Constants.UpdateCard)
    fun updateCard(
            @FieldMap map: HashMap<String, String>
    ): Observable<EditCardResponse>

    @FormUrlEncoded
    @POST(Constants.ChangeAvailability)
    fun changeAvailability(
            @FieldMap map: HashMap<String, String>
    ): Observable<ChangeAvailabilityResponse>

    @FormUrlEncoded
    @POST(Constants.DeleteAccount)
    fun deleteAccount(
            @FieldMap map: HashMap<String, String>
    ): Observable<DeleteCardResponse>

    @FormUrlEncoded
    @POST(Constants.EditPrice)
    fun editPrice(
            @FieldMap map: HashMap<String, String>
    ): Observable<EditProductPriceResponse>

    @FormUrlEncoded
    @PUT(Constants.SetDefaultCard)
    fun setDefaultCard(
            @FieldMap map: HashMap<String, String>
    ): Observable<SetDefaultCardResponse>

    @FormUrlEncoded
    @POST(Constants.CategoryList)
    fun getCategoryList(
            @FieldMap map: HashMap<String, String>):
            Observable<CategoryListResponse>

    @FormUrlEncoded
    @POST(Constants.CategoryList)
    fun getSubCategoryList(
            @FieldMap map: HashMap<String, String>):
            Observable<SubCategoryListResponse>

    @FormUrlEncoded
    @POST(Constants.ProductListing)
    fun getProductListing(
            @FieldMap map: HashMap<String, String>):
            Observable<ProductListingResponse>

    @GET(Constants.GetProfile)
    fun getProfile():
            Observable<GetProfileResponse>

    @GET(Constants.GetSubscriptions)
    fun getSubscriptions():
            Observable<SubscriptionListResponse>

    @Multipart
    @PUT(Constants.EditProfile)
    fun editProfile(
            @PartMap map: HashMap<String, RequestBody>, @Part image: MultipartBody.Part
    ): Observable<EditProfileAddShopResponsess>


    @Multipart
    @PUT(Constants.EditProfile)
    fun updateProfileWithoutImage(
            @PartMap map: HashMap<String, RequestBody>
    ): Observable<EditProfileAddShopResponsess>


    @Multipart
    @POST(Constants.AddProduct)
    fun addProduct(
            @PartMap map: HashMap<String, RequestBody>, @Part image: MultipartBody.Part
    ): Observable<AddProductResponse>


    /*Add Shop and Delivery Details API*/
    @Multipart
    @PUT(Constants.EditProfile)
    fun addShopAndDeliveryDetails(
            @PartMap map: HashMap<String, RequestBody>, @Part image: MultipartBody.Part):
            Observable<EditProfileAddShopResponsess>

    @GET(Constants.TermsCondition)
    fun termsCondition():
            Observable<TermsConditionsResponse>

    @GET(Constants.PrivacyPolicy)
    fun privacyPolicy():
            Observable<PrivacyPolicyResponse>

    @GET(Constants.AboutUs)
    fun aboutUs():
            Observable<AboutUsResponse>


/*
    @FormUrlEncoded
    @POST(Constants.AddCard)
    fun addCard(
        @FieldMap map: HashMap<String, String>
    ): Observable<AddCardResponse>

    @FormUrlEncoded
    @POST(Constants.EditCard)
    fun editCard(
        @FieldMap map: HashMap<String, String>
    ): Observable<EditCardResponse>

    @FormUrlEncoded
    @POST(Constants.SetDefaultCard)
    fun setDefaultCard(
        @FieldMap map: HashMap<String, String>
    ): Observable<SetDefaultCardResponse>
*/


/*
    @GET(Constants.GetCardsList)
    fun getCardList():
            Observable<GetCardListResponse>

    @GET(Constants.GetMyTraits)
    fun getMyTraits():
            Observable<GetMyTraitsListResponse>

    @GET(Constants.NotificationsList)
    fun notificationsList():
            Observable<NotificationListResponse>

    @GET(Constants.PaymentHistory)
    fun paymentHistory():
            Observable<PaymentHistoryListResponse>

    @GET(Constants.Home)
    fun getHomeTraits():
            Observable<HomeListResponse>


    @GET(Constants.GetFAQList)
    fun getFAQList():
            Observable<FaqListResponse>

    @GET(Constants.GetTraits)
    fun getFreeTraitsList():
            Observable<GetFreeTraitsListResponse>

    @GET(Constants.GetConnectionRequests)
    fun getConnectionRequests():
            Observable<GetConnectionRequestsListResponse>

    @GET(Constants.Connections)
    fun getMyConnections():
            Observable<GetMyConnectionsListResponse>

    @FormUrlEncoded
    @POST(Constants.AllConnections)
    fun getAllConnections(
        @FieldMap map: HashMap<String, String>):
            Observable<GetAllConnectionsListResponse>
*/


    /*


     @FormUrlEncoded
     @POST(Constants.HomeUser)
     fun getHomeUser(@FieldMap map: HashMap<String, String>):
             Observable<HomeUserResponse>

     @FormUrlEncoded
     @POST(Constants.HomeUserSearch)
     fun getHomeUserSearch(@Field ("keyword") keyword:String):
             Observable<HomeUserResponse>


   
     @FormUrlEncoded
     @POST(Constants.deleteImage)
     fun deleteImage(@FieldMap map:HashMap<String,String>):
             Observable<CommonResponse>

     @FormUrlEncoded
     @POST(Constants.VideoNotification)
     fun videoNotification(@FieldMap map:HashMap<String,String>):
             Observable<CommonResponse>*/


}