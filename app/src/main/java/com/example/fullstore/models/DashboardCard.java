package com.example.fullstore.models;

public class DashboardCard {
    private String title;
    private int iconResource;
    private Class<?> fragmentClass;

    public DashboardCard(String title, int iconResource, Class<?> fragmentClass) {
        this.title = title;
        this.iconResource = iconResource;
        this.fragmentClass = fragmentClass;
    }

    public String getTitle() {
        return title;
    }

    public int getIconResource() {
        return iconResource;
    }

    public Class<?> getFragmentClass() {
        return fragmentClass;
    }
}