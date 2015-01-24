package swipe.android.com.chromeoverflowmenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ListPopupWindow;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    LayoutInflater inflater;
    ImageView overflowMenu;
    ListPopupWindow popupWindow;
    String def[]=new String[]{"New Tab","New Incognito Tab","Bookmarks","Recent Tabs","History","Share","Print","Find in page","Add To Home screen","Request desktop site"};
    boolean listenerSet=false;
    android.os.Handler mHandler=new android.os.Handler();
    View mConvertViewCopy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupTitleBar();


        popupWindow=new ListPopupWindow(this);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final HamburgerMenuAdapter adapter=new HamburgerMenuAdapter(def);
        overflowMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("rahul","clicked");
                popupWindow.show();
                //setDismissListener();


            }
        });

        findViewById(R.id.root).post(new Runnable() {
            public void run() {

                popupWindow.setAnchorView(getSupportActionBar().getCustomView().findViewById(R.id.abc));
                popupWindow.setAdapter(adapter);
                setClickListener();
                popupWindow.setWidth(480); // note: don't use pixels, use a dimen resource
                popupWindow.setHeight(1000);
                //popupWindow.setDropDownGravity(Gravity.RIGHT);
                popupWindow.setHorizontalOffset(300);

            }
        });

    }

//    private void setDismissListener(){
//
//        listenerSet=true;
//        popupWindow.getListView().post(new Runnable() {
//            @Override
//            public void run() {
//
//                View view=popupWindow.getListView().getChildAt(0);
//                View overflowView=view.findViewById(R.id.overflow);
//                overflowView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        popupWindow.dismiss();
//                    }
//                });
//            }
//        });
//
//
//    }

    private void setClickListener(){


//       View firstRow=popupWindow.getListView().getChildAt(0);
//       View overflowMenu=firstRow.findViewById(R.id.overflow_menu);
//        overflowMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                popupWindow.dismiss();
//            }
//        });

    }

    private void setupTitleBar() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        overflowMenu=(ImageView)getSupportActionBar().getCustomView().findViewById(R.id.overflow_menu);

    }

    private class HamburgerMenuAdapter extends BaseAdapter {

        private String[] menuItems;
        public HamburgerMenuAdapter(String[] menuItems){

            this.menuItems=menuItems;

        }

        private  class ViewHolder{

            TextView hamburgerMenuItem;
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
                        if(position==0)
                            convertView=inflater.inflate(R.layout.menu_top_row,null);
                        else
                            convertView=inflater.inflate(R.layout.listview_row,null);

                        holder.hamburgerMenuItem=(TextView)convertView.findViewById(R.id.menu_name);
                        convertView.setTag(holder);
                    }
                    else{

                        holder=(ViewHolder)convertView.getTag();
                    }

                    if(holder.hamburgerMenuItem!=null)
                        holder.hamburgerMenuItem.setText(menuItems[position - 1]);

                if(position==0) {
                    Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.first_row_animation);
                    convertView.startAnimation(animation);
                }



          Log.d("rahul",""+convertView);
          return convertView;


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);



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
}
