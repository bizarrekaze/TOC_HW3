/******************************************************
	學號:F74002109
	姓名:蔡翔任
	HW4
*******************************************************/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
class Jsonreader
{
	public Jsonreader(URL url,JSONArray arr,String str1,String str2,String str3) throws IOException, JSONException
	{
		int message,money=0,count=0;
		String str="",str_money;
		JSONObject obj = new JSONObject();
		InputStream in = url.openStream();
		InputStreamReader read = new InputStreamReader(in, "UTF-8");
		BufferedReader br = new BufferedReader(read);
		JSONTokener jsonParser;
		while ((message = br.read()) != -1) 
		{
			 if (((char)message) != '\n') 
			 {
				if(!( message==125|| message==91 || message==93))
				{
					str = str + (char)message;
				}
				else if(message==125)//}
				{
					str = str + (char)message;
					jsonParser = new JSONTokener(str);
					String s=null,s2=null,s3=null;	
					try{
						obj=(JSONObject)jsonParser.nextValue();
						s = obj.getString("鄉鎮市區");
						s2 =obj.getString("土地區段位置或建物區門牌");
						s3 =Integer.toString(obj.getInt("交易年月"));
						
						if ((s.indexOf(str1) != -1) && (s2.indexOf(str2)!=-1) && (s3.indexOf(str3)!=-1))
							{
								str_money =Integer.toString(obj.getInt("總價元"));
								System.out.println(s +"  "+ s2 +"  "+ s3 +"  "+str_money);
								money += Integer.parseInt(str_money);
								count++;
							}
					}catch (Exception e){}
				
					obj = new JSONObject();
					str="";
					message = br.read();
					if((char)message=='\n')
						message = br.read();
					else
						break;
				}
				else if( message==91 || message==93)
					;
				else
				{
					str = str + (char)message;
				}
			 }
		}
		System.out.println(money/count);
		read.close();
		br.close();
		in.close();
	}
}
public class TocHw3 {
  public static void main(String[] args) throws IOException, JSONException, InterruptedException {
		String str1 = args[1];
		String str2 = args[2];
		String str3 = args[3];
		URL url = new URL(args[0]);
		JSONArray arr = new JSONArray();
		Jsonreader jr = new Jsonreader(url,arr,str1,str2,str3);
  }
}
