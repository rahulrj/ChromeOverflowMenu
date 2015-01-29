package swipe.android.com.chromeoverflowmenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ListPopupWindow;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    LayoutInflater mInflater;
    ImageView mOverflowMenu;
    ListPopupWindow mPopupWindow;
    String mPopupWindowItems[]=new String[]{"New Tab","New Incognito Tab","Bookmarks","Recent Tabs","History","Share","Print","Find in page","Add To Home screen","Request desktop site"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupTitleBar();


        mPopupWindow=new ListPopupWindow(this);
        mPopupWindow.setAnimationStyle(R.style.Animation);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_background));
        mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final PopupAdapter adapter=new PopupAdapter(mPopupWindowItems);
        setClickListener();


        findViewById(R.id.root).post(new Runnable() {
            public void run() {

                mPopupWindow.setAnchorView(getSupportActionBar().getCustomView().findViewById(R.id.abc));
                mPopupWindow.setAdapter(adapter);
                mPopupWindow.setWidth(dpToPx(getResources().getInteger(R.integer.popup_width))); // note: don't use pixels, use a dimen resource
                mPopupWindow.setHeight(dpToPx(getResources().getInteger(R.integer.popup_height)));
                mPopupWindow.setDropDownGravity(Gravity.END);

               // popupWindow.setHorizontalOffset(-130);

            }
        });

    }


    private void setClickListener(){


        mOverflowMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPopupWindow.show();
                LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate_from_top), 0.3f);
                mPopupWindow.getListView().setLayoutAnimation(lac);
                mPopupWindow.getListView().post(new Runnable() {
                    @Override
                    public void run() {

                        ViewGroup viewGroup = (LinearLayout) mPopupWindow.getListView().getChildAt(0).findViewById(R.id.first_root);
                        LayoutAnimationController lac2 = new LayoutAnimationController(AnimationUtils.loadAnimation(MainActivity.this, R.anim.first_row_animation), 0.3f);
                        viewGroup.setLayoutAnimation(lac2);

                    }
                });


            }
        });

    }

    private void setupTitleBar() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        mOverflowMenu=(ImageView)getSupportActionBar().getCustomView().findViewById(R.id.overflow_menu);

    }

    private class PopupAdapter extends BaseAdapter {

        private String[] menuItems;
        public PopupAdapter(String[] menuItems){

            this.menuItems=menuItems;

        }

        private  class ViewHolder{

            TextView menuItemView;
        }

        @Override
        public int getCount() {
            return menuItems.length;
        }

        @Override
        public Object getItem(int position) {
          return menuItems[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

                   ViewHolder holder;

                    if(convertView==null){
                        holder=new ViewHolder();
                        if(position==0) {
                            convertView = mInflater.inflate(R.layout.menu_top_row, null);
                        }
                        else
                            convertView=mInflater.inflate(R.layout.listview_row,null);

                        holder.menuItemView=(TextView)convertView.findViewById(R.id.menu_name);
                        convertView.setTag(holder);
                    }
                    else{

                        holder=(ViewHolder)convertView.getTag();
                    }

                    if(holder.menuItemView!=null)
                        holder.menuItemView.setText(menuItems[position - 1]);

          return convertView;


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    public  int dpToPx(int dp) {
        float density = getApplicationContext().getResources()
                .getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
