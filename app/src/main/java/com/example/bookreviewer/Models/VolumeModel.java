
package com.example.bookreviewer.Models;

import android.app.Activity;

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

    public class Items {
        private String kind;
        private String id;
        private VolInfo volumeInfo;
        private SaleInfo saleInfo;
        private AccessInfo accessInfo;

        public String getKind() {
            return kind;
        }

        public String getId() {
            return id;
        }

        public VolInfo getVolumeInfo() {
            return volumeInfo;
        }

        public SaleInfo getSaleInfo() {
            return saleInfo;
        }

        public AccessInfo getAccessInfo() {
            return accessInfo;
        }

        public class VolInfo {
            public String title;
            private String subtitle;
            private ArrayList<String> authors;
            private String publisher;
            private String publishedDate;
            private String description;
            private int pageCount;
            private ArrayList<String> categories;
            private int averageRating;
            private int ratingsCount;
            private String maturityRating;
            private ImageObject imageLinks;
            private String infoLink;

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

            public int getPageCount() {
                return pageCount;
            }

            public ArrayList<String> getCategories() {
                return categories;
            }

            public int getAverageRating() {
                return averageRating;
            }

            public int getRatingsCount() {
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

            public class ImageObject {
                private String smallThumbnail;
                private String thumbnail;

                public String getSmallThumbnail() {
                    return smallThumbnail;
                }

                public String getThumbnail() {
                    return thumbnail;
                }
            }
        }

        public class SaleInfo {
            private String country;
            private String saleability;
            private boolean isEbook;
            private ListPrice listPrice;
            private RetailPrice retailPrice;
            private String buyLink;

            public String getCountry() {
                return country;
            }

            public String getSaleability() {
                return saleability;
            }

            public boolean isEbook() {
                return isEbook;
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

            public class ListPrice {
                private double amount;
                private String currencyCode;

                public double getAmount() {
                    return amount;
                }

                public String getCurrencyCode() {
                    return currencyCode;
                }
            }

            public class RetailPrice {
                private double amount;
                private String currencyCode;

                public double getAmount() {
                    return amount;
                }

                public String getCurrencyCode() {
                    return currencyCode;
                }
            }
        }

        public class AccessInfo {
            private String country;
            private String webReaderLink;

            public String getCountry() {
                return country;
            }

            public String getWebReaderLink() {
                return webReaderLink;
            }

            public class Epub {
                private boolean isAvailable;
                @SerializedName(value = "acsTokenLink")
                private String token;

                public boolean isAvailable() {
                    return isAvailable;
                }

                public String getToken() {
                    return token;
                }
            }
        }
    }
}
