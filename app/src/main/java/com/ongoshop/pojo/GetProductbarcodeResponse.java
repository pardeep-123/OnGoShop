package com.ongoshop.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProductbarcodeResponse {

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
        @SerializedName("isApproved")
        @Expose
        private Integer isApproved;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("isAvailable")
        @Expose
        private Integer isAvailable;
        @SerializedName("taxCategoryId")
        @Expose
        private Integer taxCategoryId;
        @SerializedName("vendorId")
        @Expose
        private Integer vendorId;
        @SerializedName("categoryId")
        @Expose
        private Integer categoryId;
        @SerializedName("subCategoryId")
        @Expose
        private Integer subCategoryId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("countryOfOrigin")
        @Expose
        private String countryOfOrigin;
        @SerializedName("gtinNumber")
        @Expose
        private String gtinNumber;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("isBarcodeItem")
        @Expose
        private Integer isBarcodeItem;
        @SerializedName("barcode")
        @Expose
        private String barcode;
        @SerializedName("barcodeImage")
        @Expose
        private String barcodeImage;
        @SerializedName("sku")
        @Expose
        private String sku;
        @SerializedName("skuImage")
        @Expose
        private String skuImage;
        @SerializedName("brandName")
        @Expose
        private String brandName;
        @SerializedName("mrp")
        @Expose
        private String mrp;
        @SerializedName("minimumSellingPrice")
        @Expose
        private String minimumSellingPrice;
        @SerializedName("percentageDiscount")
        @Expose
        private Integer percentageDiscount;
        @SerializedName("length")
        @Expose
        private String length;
        @SerializedName("width")
        @Expose
        private String width;
        @SerializedName("height")
        @Expose
        private String height;
        @SerializedName("dimensionsUnit")
        @Expose
        private Integer dimensionsUnit;
        @SerializedName("weight")
        @Expose
        private String weight;
        @SerializedName("weightUnit")
        @Expose
        private Integer weightUnit;
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

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getIsApproved() {
            return isApproved;
        }

        public void setIsApproved(Integer isApproved) {
            this.isApproved = isApproved;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(Integer isAvailable) {
            this.isAvailable = isAvailable;
        }

        public Integer getTaxCategoryId() {
            return taxCategoryId;
        }

        public void setTaxCategoryId(Integer taxCategoryId) {
            this.taxCategoryId = taxCategoryId;
        }

        public Integer getVendorId() {
            return vendorId;
        }

        public void setVendorId(Integer vendorId) {
            this.vendorId = vendorId;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Integer getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(Integer subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCountryOfOrigin() {
            return countryOfOrigin;
        }

        public void setCountryOfOrigin(String countryOfOrigin) {
            this.countryOfOrigin = countryOfOrigin;
        }

        public String getGtinNumber() {
            return gtinNumber;
        }

        public void setGtinNumber(String gtinNumber) {
            this.gtinNumber = gtinNumber;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getIsBarcodeItem() {
            return isBarcodeItem;
        }

        public void setIsBarcodeItem(Integer isBarcodeItem) {
            this.isBarcodeItem = isBarcodeItem;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getBarcodeImage() {
            return barcodeImage;
        }

        public void setBarcodeImage(String barcodeImage) {
            this.barcodeImage = barcodeImage;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getSkuImage() {
            return skuImage;
        }

        public void setSkuImage(String skuImage) {
            this.skuImage = skuImage;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public String getMinimumSellingPrice() {
            return minimumSellingPrice;
        }

        public void setMinimumSellingPrice(String minimumSellingPrice) {
            this.minimumSellingPrice = minimumSellingPrice;
        }

        public Integer getPercentageDiscount() {
            return percentageDiscount;
        }

        public void setPercentageDiscount(Integer percentageDiscount) {
            this.percentageDiscount = percentageDiscount;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public Integer getDimensionsUnit() {
            return dimensionsUnit;
        }

        public void setDimensionsUnit(Integer dimensionsUnit) {
            this.dimensionsUnit = dimensionsUnit;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public Integer getWeightUnit() {
            return weightUnit;
        }

        public void setWeightUnit(Integer weightUnit) {
            this.weightUnit = weightUnit;
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

    }
}