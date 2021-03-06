package com.ongoshop.utils.others

class Constants {
    companion object
    {
        const val BASE_URL = "http://54.252.10.181:8300/api/"
        val success_code = 200
        val errorCode = 403
        val Device_Type = "0"      //(0 => Android, 1 => IOS)
        var SecurityKey = "securitykey"
        var SecurityKeyValue = "__ongo"
        val token = "token"
        val AuthKey = "Authorization"
        val UserData = "userData"

        const val TYPE_USER = "3"
        const val Name = "name"
        const val openTime = "openTime"
        const val closeTime = "closeTime"
        const val Email = "email"
        const val Password = "password"
        const val PhoneNumber = "phone_number"
        const val CountryCode = "country_code"
        var  currentFragment = "Home"
        const val TermsCondition = "termsAndConditions"

        const val SignUp = BASE_URL + "signup"
        const val Login = BASE_URL + "login"
        const val ForgotPassword = BASE_URL + "forgotPassword"
        const val GraphDetail = BASE_URL + "graphDetail"
        const val GetProfile = BASE_URL + "getProfile"
        const val EditProfile = BASE_URL + "editProfile"
        const val ChangePassword = BASE_URL + "changePassword"
        const val VerifyOtp = BASE_URL + "verifyOtp"
        const val ResendOtp = BASE_URL + "resendOtp"
        const val SocialLogin = BASE_URL + "socialLogin"
        const val AboutUs = BASE_URL + "aboutUs"
        const val PrivacyPolicy = BASE_URL + "privacyPolicy"
        const val SubCategoryList = BASE_URL + "categoryListing"
        const val CategoryList = BASE_URL + "shop_categories"
        const val ProductListing = BASE_URL + "subCategoryProductListing"
        const val AddCard = BASE_URL + "addCard"
        const val AllCards = BASE_URL + "allCards"
        const val DeleteCard = BASE_URL + "deleteCard"
        const val UpdateCard = BASE_URL + "updateCard"
        const val ChangeAvailability = BASE_URL + "changeAvailability"
        const val EditPrice = BASE_URL + "editPrice"
        const val SetDefaultCard = BASE_URL + "setDefaultCard"
        const val AddProduct = BASE_URL + "addProduct"
        const val DeleteAccount = BASE_URL + "deleteAccount"
        const val GetSubscriptions = BASE_URL + "getSubscriptions"
        const val CheckBarcode = BASE_URL + "checkBarcode"
        const val GetProductbarcode = BASE_URL + "getProductbarcode"
        const val OrdersList = BASE_URL + "ordersList"
        const val Pastorders = BASE_URL + "pastorders"
        const val OrdersItems = BASE_URL + "ordersItems"
        const val GetProductList = BASE_URL + "getProductlist"
        const val Notification = BASE_URL + "notification"
        const val Acceptorder = BASE_URL + "acceptorder"
        const val ShippedOrder = BASE_URL + "shippedOrder"
        const val FinishPacking = BASE_URL + "finishPacking"
        const val IsSelfpickupOrders = BASE_URL + "isSelfpickupOrders"


        const val CategoryListing = BASE_URL + "categoryListing"
        const val GetCardsList = BASE_URL + "get_cards_list"
        const val NotificationsList = BASE_URL + "notifications_list"
        const val RemoveNotification = BASE_URL + "remove_push"

        const val Logout = BASE_URL + "logout"

    }
}