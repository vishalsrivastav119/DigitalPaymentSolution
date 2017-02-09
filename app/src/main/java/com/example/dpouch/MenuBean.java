package com.example.dpouch;
 

public class MenuBean {

	
	private String title;
    private int icon;
    
    public MenuBean(){}

	public MenuBean(String title, int icon){
        this.title = title;
        this.icon = icon;

    }
     
     
	public String getTitle() 
        {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	
	
}
