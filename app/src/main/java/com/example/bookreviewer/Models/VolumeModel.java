
package com.example.bookreviewer.Models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.bookreviewer.DataFiles.ListConverter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VolumeModel {
    private String kind;
    private double totalItems;
    private ArrayList<Items> items;

    public String getKind() {
        return kind;
    }

    public double getTotalItems() {
        return totalItems;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    @Entity(tableName = "favourite_books")
    public static class Items {
        @PrimaryKey(autoGenerate = true)
        private int primary_key;
        private String kind;
        private String id;
        @Embedded
        private VolInfo volumeInfo;
        @Embedded
        private SaleInfo saleInfo;

        public int getPrimary_key() {
            return primary_key;
        }

        public void setPrimary_key(int primary_key) {
            this.primary_key = primary_key;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setVolumeInfo(VolInfo volumeInfo) {
            this.volumeInfo = volumeInfo;
        }

        public void setSaleInfo(SaleInfo saleInfo) {
            this.saleInfo = saleInfo;
        }

        public VolInfo getVolumeInfo() {
            return volumeInfo;
        }

        public SaleInfo getSaleInfo() {
            return saleInfo;
        }

        public static class VolInfo {
            public String title;
            private String subtitle;
            @TypeConverters(ListConverter.class)
            private ArrayList<String> authors;
            private String publisher;
            private String publishedDate;
            private String description;
            private String pageCount;
            @TypeConverters(ListConverter.class)
            private ArrayList<String> categories;
            private String averageRating;
            private String ratingsCount;
            private String maturityRating;
            @Embedded
            private ImageObject imageLinks;
            private String infoLink;

            public VolInfo(String title, String subtitle, ArrayList<String> authors, String publisher, String publishedDate, String description, String pageCount, ArrayList<String> categories, String averageRating, String ratingsCount, String maturityRating, ImageObject imageLinks, String infoLink) {
                this.title = title;
                this.subtitle = subtitle;
                this.authors = authors;
                this.publisher = publisher;
                this.publishedDate = publishedDate;
                this.description = description;
                this.pageCount = pageCount;
                this.categories = categories;
                this.averageRating = averageRating;
                this.ratingsCount = ratingsCount;
                this.maturityRating = maturityRating;
                this.imageLinks = imageLinks;
                this.infoLink = infoLink;
            }

            public String getTitle() {
                return title;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public ArrayList<String> getAuthors() {
                return authors;
            }

            public String getPublisher() {
                return publisher;
            }

            public String getPublishedDate() {
                return publishedDate;
            }

            public String getDescription() {
                return description;
            }

            public String getPageCount() {
                return pageCount;
            }

            public ArrayList<String> getCategories() {
                return categories;
            }

            public String getAverageRating() {
                return averageRating;
            }

            public String getRatingsCount() {
                return ratingsCount;
            }

            public String getMaturityRating() {
                return maturityRating;
            }

            public ImageObject getImageLinks() {
                return imageLinks;
            }

            public String getInfoLink() {
                return infoLink;
            }

            public static class ImageObject {
                private String thumbnail;

                public ImageObject(String thumbnail) {
                    this.thumbnail = thumbnail;
                }

                public String getThumbnail() {
                    return thumbnail;
                }
            }
        }

        public static class SaleInfo {
            private String country;
            private String saleability;
            @Embedded
            private ListPrice listPrice;
            @Embedded
            private RetailPrice retailPrice;
            private boolean isEbook;
            private String buyLink;

            public SaleInfo(String country, String saleability, ListPrice listPrice, RetailPrice retailPrice, boolean isEbook, String buyLink) {
                this.country = country;
                this.saleability = saleability;
                this.listPrice = listPrice;
                this.retailPrice = retailPrice;
                this.isEbook = isEbook;
                this.buyLink = buyLink;
            }

            public boolean isEbook() {
                return isEbook;
            }

            public String getCountry() {
                return country;
            }

            public String getSaleability() {
                return saleability;
            }

            public ListPrice getListPrice() {
                return listPrice;
            }

            public RetailPrice getRetailPrice() {
                return retailPrice;
            }

            public String getBuyLink() {
                return buyLink;
            }

            public static class ListPrice {
                @SerializedName("amount")
                private double list_price_amount;
                @SerializedName("currencyCode")
                private String list_price_currencyCode;

                public ListPrice(double list_price_amount, String list_price_currencyCode) {
                    this.list_price_amount = list_price_amount;
                    this.list_price_currencyCode = list_price_currencyCode;
                }

                public double getList_price_amount() {
                    return list_price_amount;
                }

                public String getList_price_currencyCode() {
                    return list_price_currencyCode;
                }

                public void setList_price_amount(double list_price_amount) {
                    this.list_price_amount = list_price_amount;
                }

                public void setList_price_currencyCode(String list_price_currencyCode) {
                    this.list_price_currencyCode = list_price_currencyCode;
                }
            }

            public static class RetailPrice {
                @SerializedName("amount")
                private double retail_price_amount;
                @SerializedName("currencyCode")
                private String retail_price_currencyCode;

                public RetailPrice(double retail_price_amount, String retail_price_currencyCode) {
                    this.retail_price_amount = retail_price_amount;
                    this.retail_price_currencyCode = retail_price_currencyCode;
                }

                public double getRetail_price_amount() {
                    return retail_price_amount;
                }

                public String getRetail_price_currencyCode() {
                    return retail_price_currencyCode;
                }

                public void setRetail_price_amount(double retail_price_amount) {
                    this.retail_price_amount = retail_price_amount;
                }

                public void setRetail_price_currencyCode(String retail_price_currencyCode) {
                    this.retail_price_currencyCode = retail_price_currencyCode;
                }
            }

        }
    }
}






