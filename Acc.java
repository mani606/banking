package utils;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import db.Db;
public class Acc{
	public static String getDate(){
		Calendar c=Calendar.getInstance();
		int d=c.get(Calendar.DATE);
		int m=c.get(Calendar.MONTH)+1;
		int y=c.get(Calendar.YEAR)%100;
		return d+"/"+m+"/"+y;
	}
	public static String addZeros(int n,int dig){
		String sn=n+"";
		int ndig=sn.length();
		if(ndig<dig){
			int dif=dig-ndig;
			for(int i=1;i<=dif;i++)
				sn="0"+sn;
		}
		return sn;
	}
	public static String crAcNum()throws Exception{ 
		String dt=getDate();
		StringTokenizer st=new StringTokenizer(dt,"/");
		String toks[]=new String[st.countTokens()];
		int i=0;
		while(st.hasMoreTokens()){
			toks[i]=st.nextToken();
			i++;
		}
		int d=Integer.parseInt(toks[0]);
		int m=Integer.parseInt(toks[1]);
		int y=Integer.parseInt(toks[2]);
		String sd=addZeros(d,2);
		String sm=addZeros(m,2);
		String sy=addZeros(y,2);
		Db db=new Db();
		db.conn();
		int num=db.getNumAcc(getDate());
		//System.out.println(num);
		Statement st1=Db.c.createStatement();
		if(num==0){
			num++;
			String q="insert into nacc values('"+dt+"',"+num+")";
			st1.executeUpdate(q);
		}
		else{
			num++;
			String q="update nacc set num="+num+" where dt='"+dt+"'";
			st1.executeUpdate(q);
		}
		String snum=addZeros(num,3);
		return sy+sm+sd+snum;
	}
}
