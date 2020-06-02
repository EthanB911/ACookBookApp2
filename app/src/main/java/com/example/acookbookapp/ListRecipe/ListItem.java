package com.example.acookbookapp.ListRecipe;

public class ListItem {
    //this is used to declare some properties which will  be shown in each card item in the recycler view.
    private String head;
    private String desc;
    private String id;
    public ListItem(String id, String head, String desc)
    {
        this.id =id;
        this.head= head;
        this.desc = desc;

    }


    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }
}
