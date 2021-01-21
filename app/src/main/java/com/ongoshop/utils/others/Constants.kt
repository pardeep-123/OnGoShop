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
        val IsBusinessProfileAdded = "isBusinessAdded"

        const val TYPE_USER = 0
        const val TYPE_FRIEND = 2

        const val Name = "name"
        const val Age = "age"
        const val Country = "country"
        const val Email = "email"
        const val Password = "password"
        const val PhoneNumber = "phone_number"
        const val CountryCode = "country_code"
        const val TermsCondition = "termsAndConditions"
        const val FOR_GOOGLE_TYPE = "0"
        const val FOR_FACEBOOK_TYPE = "1"

        const val SignUp = BASE_URL + "signup"
        const val Login = BASE_URL + "login"
        const val SocialLogin = BASE_URL + "socail_login"
        const val ForgotPassword = BASE_URL + "forgotPassword"
        const val GetProfile = BASE_URL + "getProfile"
        const val EditProfile = BASE_URL + "editProfile"
        const val ChangePassword = BASE_URL + "changePassword"
        const val VerifyOtp = BASE_URL + "verifyOtp"
        const val ResendOtp = BASE_URL + "resendOtp"
        const val AboutUs = BASE_URL + "aboutUs"
        const val PrivacyPolicy = BASE_URL + "privacyPolicy"

        const val HomeListing = BASE_URL + "homeListing"              // Home
        const val CategoryListing = BASE_URL + "categoryListing"
        const val GetCardsList = BASE_URL + "get_cards_list"

        const val NotificationOnOff = BASE_URL + "notification_on_off"
        const val NotificationsList = BASE_URL + "notifications_list"
        const val RemoveNotification = BASE_URL + "remove_push"


        const val HomeUserSearch = BASE_URL + "home_user_search"
        const val Logout = BASE_URL + "logout"
        }

}