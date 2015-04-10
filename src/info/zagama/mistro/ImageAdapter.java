package info.zagama.mistro;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

//建�?BaseAdapter並�?此Adapter置入Gallery??
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
  //Gallery?��?總數?�int?��?大�?，目?�為?��?迴�?
  public int getCount()
  {
      return Integer.MAX_VALUE;
  }

  @Override
  //?��??��?位置?�以?��?總數?��?餘數
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
      //建�??��?
      ImageView img = new ImageView(this.mContext);
      //將�??�置?�img，置?��??��??�目?��?置�??��??�以?��?總數?��??��?此�??�為?��?????��??��?�?
      img.setImageResource(mPics[position % mPics.length]);
      //保�??��??�寬比�?
      img.setAdjustViewBounds(true);
      //縮放?�置�?
      img.setScaleType(ImageView.ScaleType.FIT_CENTER);
      //設置?��??�寬
      img.setLayoutParams(new Gallery.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
              
      //?�傳此建立�??��?
      return img;
  } 
}