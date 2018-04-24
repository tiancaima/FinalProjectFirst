package com.superma.dataprocess;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.superma.content.Button;
import com.superma.content.Container;
import com.superma.content.Picture;
import com.superma.content.Text;
import com.superma.createfile.CreateFile;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;



public class DataProcess {
	
	private ArrayList<ArrayList<Container>> pagelist = new ArrayList<ArrayList<Container>>();//每个页面保存数据
//	private ArrayList<Container> containerlist = new ArrayList<Container>();//每个页面的自由容器内容
	private ArrayList<Button> buttonlist = new ArrayList<Button>();//按钮内容
	private ArrayList<Picture> picturelist = new ArrayList<Picture>();//图片内容
	private ArrayList<Text> textlist = new ArrayList<Text>();//文本框内容
	
	

	public void Data(String jsondata) throws IOException{
		
		try {
			JsonParser parser = new JsonParser();
			JsonObject jsonobject = (JsonObject) parser.parse(jsondata);
			
			int pagenumber = jsonobject.get("pagenumber").getAsInt();//页面数量
			
			//存储所有页面内容
			
			//循环所有页面
			for (int i = 1; i <= pagenumber; i++) {
				ArrayList<Container> containerlist = new ArrayList<Container>();//每次需要更新页面自由容器list
				
				JsonArray pagearray = jsonobject.get("pagecontent"+i).getAsJsonArray();
				for (int j = 0; j < pagearray.size(); j++) {
					//循环一个页面的所有自由容器
					Container container = new Container();//创建添加元素
					ArrayList<String> conlist = new ArrayList<String>();//临时存储list
					
					JsonObject containerobject = pagearray.get(j).getAsJsonObject();
					container.setText(containerobject.get("text").getAsString());
					JsonArray conarray = containerobject.get("conlist").getAsJsonArray();//每个容器的内容
					//
					for (int k = 0; k < conarray.size(); k++) {
						String temp = conarray.get(k).getAsString();
						conlist.add(temp); 
					}
					//
					container.setConlist(conlist);
					containerlist.add(container);
				}
				pagelist.add(containerlist);
			}
			
			//存储text信息进入textlist
			JsonArray textarray = jsonobject.get("text").getAsJsonArray();
			for (int i = 0; i < textarray.size(); i++) {
				JsonObject textobject = textarray.get(i).getAsJsonObject();
				Text textob = new Text();
				
				textob.setId(textobject.get("id").getAsString());
				textob.setTop(textobject.get("top").getAsString());
				textob.setLeft(textobject.get("left").getAsString());
				textob.setWidth(textobject.get("width").getAsString());
				textob.setHeight(textobject.get("height").getAsString());
				textob.setText(textobject.get("text").getAsString());
				textob.setTextsize(textobject.get("textsize").getAsString());
				textob.setTextstyle(textobject.get("textstyle").getAsString());
				textlist.add(textob);
			}
			
			
			//存储button信息进入buttonlist
			JsonArray buttonarray = jsonobject.get("button").getAsJsonArray();
			for (int i = 0; i < buttonarray.size(); i++) {
				JsonObject buttonobject = buttonarray.get(i).getAsJsonObject();
				Button buttonob = new Button();
				
				buttonob.setId(buttonobject.get("id").getAsString());
				buttonob.setTop(buttonobject.get("top").getAsString());
				buttonob.setLeft(buttonobject.get("left").getAsString());
				buttonob.setWidth(buttonobject.get("width").getAsString());
				buttonob.setHeight(buttonobject.get("height").getAsString());
				buttonob.setText(buttonobject.get("text").getAsString());
				buttonob.setTextsize(buttonobject.get("textsize").getAsString());
				buttonob.setTextcolor(buttonobject.get("textcolor").getAsString());
				buttonob.setButtonstyle(buttonobject.get("buttonstyle").getAsString());
				buttonob.setButtoncolor(buttonobject.get("buttoncolor").getAsString());
				buttonlist.add(buttonob);
			}
			
			//存储picture信息进入picturelist
			JsonArray picturearray = jsonobject.get("picture").getAsJsonArray();
			for (int i = 0; i < picturearray.size(); i++) {
				JsonObject pictureobject = picturearray.get(i).getAsJsonObject();
				Picture pictureob = new Picture();
				
				pictureob.setId(pictureobject.get("id").getAsString());
				pictureob.setTop(pictureobject.get("top").getAsString());
				pictureob.setLeft(pictureobject.get("left").getAsString());
				pictureob.setWidth(pictureobject.get("width").getAsString());
				pictureob.setHeight(pictureobject.get("height").getAsString());
				pictureob.setSrc(pictureobject.get("src").getAsString());
				picturelist.add(pictureob);
			}
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//调用文件创建方法
		CreateFile cf = new CreateFile();
		cf.createFile(pagelist, buttonlist, picturelist, textlist);
	}
}
