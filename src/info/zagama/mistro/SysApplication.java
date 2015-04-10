package info.zagama.mistro;

import java.util.LinkedList; 
import java.util.List; 
import android.app.Activity; 
import android.app.Application; 
/**
 * �?���??�来结�?????�台activity
 * @author Administrator
 *
 */
public class SysApplication extends Application {
	//运用list?��?存们每�?个activity?�关??
    private List<Activity> mList = new LinkedList<Activity>();
    //为�?实现每次使用该类?��??�建?��?对象?��?建�??��?对象
    private static SysApplication instance; 
    //?��??��?
    private SysApplication(){}
    //实�??��?�?
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
    //?�闭每�?个list?��?activity
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
    //???�?
    public void onLowMemory() { 
        super.onLowMemory();     
        System.gc(); 
    }  
}
 

