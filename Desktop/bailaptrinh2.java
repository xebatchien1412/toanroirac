package bailaptrinh2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class bailaptrinh2 {
    
    static int m;  // So dinh
	static int n;  // So canh
	static int[][] listEdge, ListLevelPoint, listPointWithColor; // Khai bao danh sach canh, danh sach bac ung voi dinh, danh sach cac mau ung voi dinh
	static int startPoint, maxLevel;
	static List<Integer> listPoint = new ArrayList<>();
	static List<String> listColor = new ArrayList<>();
	
	// Ham nhap danh sach
	public static void nhapDanhSachCanh() {		
		Scanner sc = new Scanner(System.in);
		String inputLine = sc.nextLine();
		m = Integer.parseInt(inputLine.split(" ")[0]);   
	    n = Integer.parseInt(inputLine.split(" ")[1]);   
        listEdge = new int[2][n];
        ListLevelPoint = new int[2][m];
        for(int i = 0; i < n; i++) {
       
        	     inputLine = sc.nextLine();
        	     listEdge[0][i] =  Integer.parseInt(inputLine.split(" ")[0]); 
        	     listEdge[1][i] =  Integer.parseInt(inputLine.split(" ")[1]);  
        }	    
	}
	// Ham tim cac bac ung voi dinh
	public static void timCacBacUngVoiDinh() {
		for(int i = 0 ; i < m ; i++) {
			ListLevelPoint[0][i] = i + 1;    
			ListLevelPoint[1][i] = 0;
			
			for(int j = 0 ; j < n ; j++) {
			     if((i+1) == listEdge[0][j] || (i+1) == listEdge[1][j]) 
			     {  
			    	    ListLevelPoint[1][i] = ListLevelPoint[1][i] + 1; 	
			     }		
			}	
			
		}
	}
	// Tim dinh bat dau co bac la 1
	public static void timDinhBatDau() {
		// Tim bac lon nhat
		maxLevel = ListLevelPoint[1][0];
		for(int i = 1; i < m; i++) {
			if(ListLevelPoint[1][i] > maxLevel)
				maxLevel = ListLevelPoint[1][i];
		}
		// Tim dinh bat dau dau tien co bac bang bac lon nhat - 1
		for(int i = 0; i < m; i++) {
			
			if(ListLevelPoint[1][i] == (maxLevel-1)) {
				startPoint = ListLevelPoint[0][i];
			    break;
			}
			else startPoint = maxLevel;
		}
		
	}
	
	//Sap xep cac dinh theo thu tu cho thuat toan tham lam su dung k mau
	public static void sapThuTuCacDinh() {
	     listPoint.add(startPoint);     // Them dinh dau tien vao thu tu sap
	   // Duyet tung phan tu trong danh sach thu tu sap  
	      for(int j = 0 ; j < m ; j++) { 
	    	// Duyet tung canh trong danh sach canh
			     for(int i = 0 ; i < n ; i++) {
			    	 // Kiem tra hang xom
				     if(listPoint.get(j) == listEdge[0][i]) 
				     {  				    	 
				    	 // Kiem tra xem hang xom da duoc liet ke truoc do hay chua
				    	     if(!listPoint.contains(listEdge[1][i])) {
				    	    	     listPoint.add(listEdge[1][i]); 
				    	     }
				      	 
				     }
				     // Kiem tra hang xom
				     if(listPoint.get(j) == listEdge[1][i]) 
				     {  
				      // Kiem tra xem hang xom da duoc liet ke truoc do hay chua
				    	 	 if(!listPoint.contains(listEdge[0][i])) {
				      		 listPoint.add(listEdge[0][i]);  
				      	  }
				     }	
				  }	
	       }	
	}
	
	//
		//To mau bang thuat toan tham lam theo danh sach sap thu tu cac dinh
		public static void toMauChoCacDinh() {
			Collections.reverse(listPoint);
			listPointWithColor = new int[2][m];      // Khoi tao mang chua cac dinh ung voi index cua cac mau trong mang listColor
			listPointWithColor[0][0] = listPoint.get(0);  // Them dinh dau tien	
			listPointWithColor[1][0] = 0; // Gan mau dau tien cho dinh dau tien
			for(int i = 1 ; i < m; i++) {
				listPointWithColor[0][i] = listPoint.get(i);  // Them cac dinh vao mang
				List<Integer> connected = new ArrayList<>(); // Tao list chua cac dinh hang xom cua [0][i] tam thoi
				for(int j = 0 ; j < n; j++) {
					if(listPointWithColor[0][i]==listEdge[0][j]) {   // Neu tim thay 1 dinh la hang xom cua dinh [0][i]
						connected.add(listEdge[1][j]);     					
					}
					if(listPointWithColor[0][i]==listEdge[1][j]) {   // Neu tim thay 1 dinh la hang xom cua dinh [0][i]
						connected.add(listEdge[0][j]);     					
					}
				}
				
				if(!connected.isEmpty()) {  // Neu danh sach hang xom cua [0][i] khong rong~
					 
					  for(int k = 0 ; k < i; k++) {
						if(!connected.contains(listPointWithColor[0][k])) {   // Tim dinh khong phai la hang xom cua [0][i] trong danh sach cac dinh da duoc to mau
							boolean ok = true;
							for(int h = 0; h < connected.size(); h++) {
								
								for(int g = 0; g < i ; g++) {
									 if(listPointWithColor[0][g]==connected.get(h)) {
										 if(listPointWithColor[1][g]==listPointWithColor[1][k]) {
											 ok = false;
											 break;
										 }									
									 }
								}	
								if(!ok) break;
							}
							
							if(ok) {
								listPointWithColor[1][i] = listPointWithColor[1][k];
								break;
								}
							continue;
							
						}
					    
						listPointWithColor[1][i] = listPointWithColor[1][k] + 1; // Neu khong thi to mau moi						
						
		   			 		   			      			
					 }
				}
				else { listPointWithColor[1][i] = listPointWithColor[1][0];}// Neu dinh [0][i] khong co canh, dung mau so 0
				
				
			}
		
		}
		//In do thi da to mau bang thuat toan tham lam
		public static void inDoThiDaToMau() {
			// Them 7 mau
			listColor.add("red");
			listColor.add("blue");
			listColor.add("green");
			listColor.add("yellow");
			listColor.add("orange");
			listColor.add("pink");
			listColor.add("black");
			// Dao nguoc chuoi cac dinh
			
			
			// In ra do thi da to mau
			System.out.println("graph dothi");
			System.out.println("{");
			for(int i = 0; i < m ;i++) {
			System.out.println(listPointWithColor[0][i] + " [fillcolor=" + listColor.get(listPointWithColor[1][i]) + ", style=filled];");
			}
			for(int i = 0; i < n ;i++) {
			    System.out.println(listEdge[0][i] + " -- " + listEdge[1][i]);
			}
			System.out.println("}");
			
		}

	
	public static void main(String[] args) {
		
		nhapDanhSachCanh();
		timCacBacUngVoiDinh();
		timDinhBatDau();
		sapThuTuCacDinh();
		toMauChoCacDinh();	
		inDoThiDaToMau();

	}

}

