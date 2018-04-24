package com.superma.createfile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.superma.content.Button;
import com.superma.content.Container;
import com.superma.content.Picture;
import com.superma.content.Text;
import com.superma.fileDao.FileDao;

public class CreateFile {
	private ArrayList<ArrayList<Container>> pagelist;
	private ArrayList<Button> buttonlist; 
	private ArrayList<Picture> picturelist;
	private ArrayList<Text> textlist;
	
	private String wxtml; // 创建微信html
	private String wxjs; //创建微信js
 	private String[] filename = {"index","second","third","fourth","fifth"};
	
	public void createFile(ArrayList<ArrayList<Container>> pagelist, ArrayList<Button> buttonlist, ArrayList<Picture> picturelist,ArrayList<Text> textlist) throws IOException{
		this.pagelist = pagelist;
		this.buttonlist = buttonlist;
		this.textlist = textlist;
		this.picturelist = picturelist;
		//分析页面数据
		
		//每个页面循环
		int i = 1;
		Iterator<ArrayList<Container>> conlistiter = pagelist.iterator();
		while (conlistiter.hasNext()) {
			ArrayList<Container> containerslist = conlistiter.next();
			String html = "";
			wxtml = html; //每个页面更新文件串
			//每个页面内自由容器循环
			Iterator<Container> conitor = containerslist.iterator();
			while (conitor.hasNext()) {
				Container container = conitor.next();
				if (container.getText() == "") {
					wxtml += "<view class='free-content'>\r\n";
				}else{
					wxtml += "<view class='free-content'>\r\n<view class='free-title'>"+container.getText()+"</view>\r\n";
				}
				//container.getText();//获取每个页面中每个容器标题内容
				ArrayList<String> conconarray = container.getConlist();
				Iterator<String> conconitor = conconarray.iterator();
				while (conconitor.hasNext()) {
					String tem = conconitor.next();
					findcon(tem);
					//conconitor.next();//获取该自由容器内所有内容id
				}
				wxtml += "</view>\r\n";
			}
			FileDao fd = new FileDao();
			fd.create(filename[i-1], wxtml, ".wxss");
			System.out.print(wxtml+"\n");
			i++;
		}
		
	}
	
	//寻找内容
		public void findcon(String conname){
			//检测是否是文本框
			Iterator<Text> textitor = textlist.iterator();
			while (textitor.hasNext()) {
				Text cotext = textitor.next();
				if (cotext.getId().equals(conname)) {
					wxtml += "<text>"+ cotext.getText() +"</text>\r\n";
					break;
				}
			}
			
			//检测是否是按钮
			Iterator<Button> buttonitor = buttonlist.iterator();
			while (buttonitor.hasNext()) {
				Button button = buttonitor.next();
				if (button.getId().equals(conname)) {
					wxtml += "<button class='butt'>"+ button.getText() +"</button>\r\n";
					break;
				}
			}
			
			//检测是否是图片
			Iterator<Picture> pictureitor = picturelist.iterator();
			while (pictureitor.hasNext()) {
				Picture picture = pictureitor.next();
				if (picture.getId().equals(conname)) {
					wxtml += "<image src='../"+ picture.getSrc() +"'></image>\r\n";
					break;
				}
			}
		}
}
