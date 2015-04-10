package info.zagama.mistro;

import java.util.LinkedList; 
import java.util.List; 
import android.app.Activity; 
import android.app.Application; 
/**
 * ä¸?¸ªç±??¨æ¥ç»“æ?????å°activity
 * @author Administrator
 *
 */
public class SysApplication extends Application {
	//è¿ç”¨list?¥ä?å­˜ä»¬æ¯ä?ä¸ªactivity?¯å…³??
    private List<Activity> mList = new LinkedList<Activity>();
    //ä¸ºä?å®ç°æ¯æ¬¡ä½¿ç”¨è¯¥ç±»?¶ä??›å»º?°ç?å¯¹è±¡?Œå?å»ºç??™æ?å¯¹è±¡
    private static SysApplication instance; 
    //?„é??¹æ?
    private SysApplication(){}
    //å®ä??–ä?æ¬?
    public synchronized static SysApplication getInstance(){ 
        if (null == instance) { 
            instance = new SysApplication(); 
        } 
        return instance; 
    } 
    // add Activity  
    public void addActivity(Activity activity) { 
        mList.add(activity); 
    } 
    //?³é—­æ¯ä?ä¸ªlist?…ç?activity
    public void exit() { 
        try { 
            for (Activity activity:mList) { 
                if (activity != null) 
                    activity.finish(); 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally { 
            System.exit(0); 
        } 
    } 
    //???ç¨?
    public void onLowMemory() { 
        super.onLowMemory();     
        System.gc(); 
    }  
}
 

