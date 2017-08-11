package com.duongstudio.obj;

public class ItemCategory {
    public String idCategory;
    public String nameCategory;

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    @Override
    public String toString() {
        return "ItemCategory{" +
                "idCategory='" + idCategory + '\'' +
                ", nameCategory='" + nameCategory + '\'' +
                '}';
    }

    public ItemCategory(String idCategory, String nameCategory) {

        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
    }
}
