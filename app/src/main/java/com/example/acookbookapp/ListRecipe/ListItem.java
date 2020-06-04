package com.example.acookbookapp.ListRecipe;

public class ListItem {
    //this is used to declare some properties which will  be shown in each card item in the recycler view.
    private String head;
    private String desc;
    private String id;
    private String time;
    public byte[] image;
    public ListItem(String id, String head, String desc,   byte[] image, String time)
    {
        this.id =id;
        this.head= head;
        this.desc = desc;
        this.image = image;
        this.time = time;
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
    public byte[] getImage(){ return image; }

    public String getTime() {
        return time;
    }
}
