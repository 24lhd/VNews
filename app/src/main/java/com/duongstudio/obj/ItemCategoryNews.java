package com.duongstudio.obj;

public class ItemCategoryNews {
    public String idCategory;
    public String link;

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public String toString() {
        return "ItemCategoryNews{" +
                "idCategory='" + idCategory + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ItemCategoryNews(String idCategory, String link) {

        this.idCategory = idCategory;
        this.link = link;
    }
}
