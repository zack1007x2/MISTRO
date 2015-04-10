package info.zagama.mistro;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

//å»ºç?BaseAdapterä¸¦å?æ­¤Adapterç½®å…¥Gallery??
public class ImageAdapter extends BaseAdapter
{
  private Context mContext ;
  private int[] mPics ;
                         
  public ImageAdapter(Context c , int[] pics)
  {
      this.mContext = c;
      mPics = pics ;
  }

  @Override
  //Gallery?–ç?ç¸½æ•¸?ºint?„æ?å¤§å?ï¼Œç›®?„ç‚º?¡é?è¿´å?
  public int getCount()
  {
      return Integer.MAX_VALUE;
  }

  @Override
  //?®å??–ç?ä½ç½®?¤ä»¥?–ç?ç¸½æ•¸?ç?é¤˜æ•¸
  public Object getItem(int position)
  {
      return position % mPics.length ;
  }
      
  @Override
  public long getItemId(int position)
  {
      return position;
  }
        
  @Override
  public View getView(int position, View convertView, ViewGroup parent)
  {
      //å»ºç??–ç?
      ImageView img = new ImageView(this.mContext);
      //å°‡å??‡ç½®?¥imgï¼Œç½®?¥ç??–ç??ºç›®?ä?ç½®ç??–ç??¤ä»¥?–ç?ç¸½æ•¸?–é??¸ï?æ­¤é??¸ç‚º?–ç?????„å??‡ä?ç½?
      img.setImageResource(mPics[position % mPics.length]);
      //ä¿æ??–ç??·å¯¬æ¯”ä?
      img.setAdjustViewBounds(true);
      //ç¸®æ”¾?ºç½®ä¸?
      img.setScaleType(ImageView.ScaleType.FIT_CENTER);
      //è¨­ç½®?–ç??·å¯¬
      img.setLayoutParams(new Gallery.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
              
      //?å‚³æ­¤å»ºç«‹ç??–ç?
      return img;
  } 
}