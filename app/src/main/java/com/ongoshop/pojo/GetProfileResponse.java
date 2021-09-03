package com.ongoshop.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetProfileResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("body")
    @Expose
    private Body body;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }


    public class Body {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("role")
        @Expose
        private Integer role;
        @SerializedName("verified")
        @Expose
        private Integer verified;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("countryCode")
        @Expose
        private String countryCode;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("forgotPasswordHash")
        @Expose
        private String forgotPasswordHash;
        @SerializedName("facebookId")
        @Expose
        private String facebookId;
        @SerializedName("googleId")
        @Expose
        private String googleId;


        @SerializedName("otp")
        @Expose
        private Integer otp;
        @SerializedName("created")
        @Expose
        private Integer created;
        @SerializedName("updated")
        @Expose
        private Integer updated;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("vendorDeliveryOptions")
        @Expose
        private ArrayList<VendorDeliveryOption> vendorDeliveryOptions = null;
        @SerializedName("vendorDeliveryCharges")
        @Expose
        private ArrayList<VendorDeliveryCharge> vendorDeliveryCharges = null;

        @SerializedName("vendorDetail")
        @Expose
        private VendorDetail vendorDetail = null;

        @SerializedName("approvalStatus")
        @Expose
        private Integer approvalStatus;
        @SerializedName("approvalStatusReason")
        @Expose
        private String approvalStatusReason;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("shopName")
        @Expose
        private String shopName;
        @SerializedName("shopLogo")
        @Expose
        private String shopLogo;
        @SerializedName("shopCategory")
        @Expose
        private String shopCategory;
        @SerializedName("abn")
        @Expose
        private String abn;
        @SerializedName("buildingNumber")
        @Expose
        private String buildingNumber;
        @SerializedName("streetNumber")
        @Expose
        private String streetNumber;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("postalCode")
        @Expose
        private String postalCode;
        @SerializedName("shopOpenTime")
        @Expose
        private String shopOpenTime;
        @SerializedName("shopCloseTime")
        @Expose
        private String shopCloseTime;
        @SerializedName("homeDelivery")
        @Expose
        private Integer homeDelivery;
        @SerializedName("deliveriesPerDay")
        @Expose
        private Integer deliveriesPerDay;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("geoLocation")
        @Expose
        private String geoLocation;
        @SerializedName("shopAddress")
        @Expose
        private String shopAddress;
        @SerializedName("shopDescription")
        @Expose
        private String shopDescription;
        @SerializedName("paymentPolicy")
        @Expose
        private String paymentPolicy;
        @SerializedName("deliveryPolicy")
        @Expose
        private String deliveryPolicy;
        @SerializedName("sellerInformation")
        @Expose
        private String sellerInformation;
        @SerializedName("taxInPercent")
        @Expose
        private Integer taxInPercent;
        @SerializedName("taxValue")
        @Expose
        private Integer taxValue;
        @SerializedName("bankName")
        @Expose
        private String bankName;
        @SerializedName("bankBranch")
        @Expose
        private String bankBranch;
        @SerializedName("accountHolderName")
        @Expose
        private String accountHolderName;
        @SerializedName("accountNumber")
        @Expose
        private String accountNumber;
        @SerializedName("bsbNumber")
        @Expose
        private String bsbNumber;
        @SerializedName("ifscSwiftCode")
        @Expose
        private String ifscSwiftCode;
        @SerializedName("bankAddress")
        @Expose
        private String bankAddress;
        @SerializedName("isShopAdded")
        @Expose
        private Integer isShopAdded;
        @SerializedName("isHomeDeliveryAdded")
        @Expose
        private Integer isHomeDeliveryAdded;
        @SerializedName("isDeliveryOptionsAdded")
        @Expose
        private Integer isDeliveryOptionsAdded;
        @SerializedName("isDeliveryDaysAdded")
        @Expose
        private Integer isDeliveryDaysAdded;
        @SerializedName("isDeliveryChargesAdded")
        @Expose
        private Integer isDeliveryChargesAdded;
        @SerializedName("userId")
        @Expose
        private Integer userId;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getRole() {
            return role;
        }

        public void setRole(Integer role) {
            this.role = role;
        }

        public Integer getVerified() {
            return verified;
        }

        public void setVerified(Integer verified) {
            this.verified = verified;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getForgotPasswordHash() {
            return forgotPasswordHash;
        }

        public void setForgotPasswordHash(String forgotPasswordHash) {
            this.forgotPasswordHash = forgotPasswordHash;
        }

        public String getFacebookId() {
            return facebookId;
        }

        public void setFacebookId(String facebookId) {
            this.facebookId = facebookId;
        }

        public String getGoogleId() {
            return googleId;
        }

        public void setGoogleId(String googleId) {
            this.googleId = googleId;
        }

        public Integer getOtp() {
            return otp;
        }

        public void setOtp(Integer otp) {
            this.otp = otp;
        }

        public Integer getCreated() {
            return created;
        }

        public void setCreated(Integer created) {
            this.created = created;
        }

        public Integer getUpdated() {
            return updated;
        }

        public void setUpdated(Integer updated) {
            this.updated = updated;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public ArrayList<VendorDeliveryOption> getVendorDeliveryOptions() {
            return vendorDeliveryOptions;
        }

        public void setVendorDeliveryOptions(ArrayList<VendorDeliveryOption> vendorDeliveryOptions) {
            this.vendorDeliveryOptions = vendorDeliveryOptions;
        }

        public ArrayList<VendorDeliveryCharge> getVendorDeliveryCharges() {
            return vendorDeliveryCharges;
        }

        public void setVendorDeliveryCharges(ArrayList<VendorDeliveryCharge> vendorDeliveryCharges) {
            this.vendorDeliveryCharges = vendorDeliveryCharges;
        }

        public VendorDetail getVendorDetail() {
            return vendorDetail;
        }

        public void setVendorDetail(VendorDetail vendorDetail) {
            this.vendorDetail = vendorDetail;
        }

        public Integer getApprovalStatus() {
            return approvalStatus;
        }

        public void setApprovalStatus(Integer approvalStatus) {
            this.approvalStatus = approvalStatus;
        }

        public String getApprovalStatusReason() {
            return approvalStatusReason;
        }

        public void setApprovalStatusReason(String approvalStatusReason) {
            this.approvalStatusReason = approvalStatusReason;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopLogo() {
            return shopLogo;
        }

        public void setShopLogo(String shopLogo) {
            this.shopLogo = shopLogo;
        }

        public String getShopCategory() {
            return shopCategory;
        }

        public void setShopCategory(String shopCategory) {
            this.shopCategory = shopCategory;
        }

        public String getAbn() {
            return abn;
        }

        public void setAbn(String abn) {
            this.abn = abn;
        }

        public String getBuildingNumber() {
            return buildingNumber;
        }

        public void setBuildingNumber(String buildingNumber) {
            this.buildingNumber = buildingNumber;
        }

        public String getStreetNumber() {
            return streetNumber;
        }

        public void setStreetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getShopOpenTime() {
            return shopOpenTime;
        }

        public void setShopOpenTime(String shopOpenTime) {
            this.shopOpenTime = shopOpenTime;
        }

        public String getShopCloseTime() {
            return shopCloseTime;
        }

        public void setShopCloseTime(String shopCloseTime) {
            this.shopCloseTime = shopCloseTime;
        }

        public Integer getHomeDelivery() {
            return homeDelivery;
        }

        public void setHomeDelivery(Integer homeDelivery) {
            this.homeDelivery = homeDelivery;
        }

        public Integer getDeliveriesPerDay() {
            return deliveriesPerDay;
        }

        public void setDeliveriesPerDay(Integer deliveriesPerDay) {
            this.deliveriesPerDay = deliveriesPerDay;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getGeoLocation() {
            return geoLocation;
        }

        public void setGeoLocation(String geoLocation) {
            this.geoLocation = geoLocation;
        }

        public String getShopAddress() {
            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
        }

        public String getShopDescription() {
            return shopDescription;
        }

        public void setShopDescription(String shopDescription) {
            this.shopDescription = shopDescription;
        }

        public String getPaymentPolicy() {
            return paymentPolicy;
        }

        public void setPaymentPolicy(String paymentPolicy) {
            this.paymentPolicy = paymentPolicy;
        }

        public String getDeliveryPolicy() {
            return deliveryPolicy;
        }

        public void setDeliveryPolicy(String deliveryPolicy) {
            this.deliveryPolicy = deliveryPolicy;
        }

        public String getSellerInformation() {
            return sellerInformation;
        }

        public void setSellerInformation(String sellerInformation) {
            this.sellerInformation = sellerInformation;
        }

        public Integer getTaxInPercent() {
            return taxInPercent;
        }

        public void setTaxInPercent(Integer taxInPercent) {
            this.taxInPercent = taxInPercent;
        }

        public Integer getTaxValue() {
            return taxValue;
        }

        public void setTaxValue(Integer taxValue) {
            this.taxValue = taxValue;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankBranch() {
            return bankBranch;
        }

        public void setBankBranch(String bankBranch) {
            this.bankBranch = bankBranch;
        }

        public String getAccountHolderName() {
            return accountHolderName;
        }

        public void setAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getBsbNumber() {
            return bsbNumber;
        }

        public void setBsbNumber(String bsbNumber) {
            this.bsbNumber = bsbNumber;
        }

        public String getIfscSwiftCode() {
            return ifscSwiftCode;
        }

        public void setIfscSwiftCode(String ifscSwiftCode) {
            this.ifscSwiftCode = ifscSwiftCode;
        }

        public String getBankAddress() {
            return bankAddress;
        }

        public void setBankAddress(String bankAddress) {
            this.bankAddress = bankAddress;
        }

        public Integer getIsShopAdded() {
            return isShopAdded;
        }

        public void setIsShopAdded(Integer isShopAdded) {
            this.isShopAdded = isShopAdded;
        }

        public Integer getIsHomeDeliveryAdded() {
            return isHomeDeliveryAdded;
        }

        public void setIsHomeDeliveryAdded(Integer isHomeDeliveryAdded) {
            this.isHomeDeliveryAdded = isHomeDeliveryAdded;
        }

        public Integer getIsDeliveryOptionsAdded() {
            return isDeliveryOptionsAdded;
        }

        public void setIsDeliveryOptionsAdded(Integer isDeliveryOptionsAdded) {
            this.isDeliveryOptionsAdded = isDeliveryOptionsAdded;
        }

        public Integer getIsDeliveryDaysAdded() {
            return isDeliveryDaysAdded;
        }

        public void setIsDeliveryDaysAdded(Integer isDeliveryDaysAdded) {
            this.isDeliveryDaysAdded = isDeliveryDaysAdded;
        }

        public Integer getIsDeliveryChargesAdded() {
            return isDeliveryChargesAdded;
        }

        public void setIsDeliveryChargesAdded(Integer isDeliveryChargesAdded) {
            this.isDeliveryChargesAdded = isDeliveryChargesAdded;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

    }

 }
