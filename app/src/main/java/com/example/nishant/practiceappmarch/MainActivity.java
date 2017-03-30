package com.example.nishant.practiceappmarch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {
    //private Button buttonSelect;
    //private Button buttonUpload;
    //private EditText editText;
    private String email="";
    private static final String UPLOAD_URL="http://192.168.1.10/AndroidPdUploads/upload.php";
    public TabLayout tabLayout;
    public ViewPager pager;
    private int PICK_PDF_REQUEST=1;

    private static final int STORAGE_PERMISSION_CODE=123;
    private Uri filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //buttonSelect=(Button)findViewById(R.id.Select);
        //buttonUpload=(Button)findViewById(R.id.upload);
        //editText=(EditText)findViewById(R.id.editText8);

        tabLayout=(TabLayout)findViewById(R.id.tab_layout);
        pager=(ViewPager)findViewById(R.id.pager);

        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("My Uploads"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Intent iin=getIntent();
        Bundle b=iin.getExtras();
        if(b!=null)
        {
            email=(String)b.get("email");
            Toast.makeText(this,email,Toast.LENGTH_LONG).show();
            //Sending E-mail from here to fragment 2 for fetching uploaded pdfs
            Bundle bundle=new Bundle();
            bundle.putString("email",email);
            Tab_Fragment2 fragObj=new Tab_Fragment2();
            fragObj.setArguments(bundle);
            //done Let's See (Update not working Null Pointer Exception on Bundle)
        }


        setSupportActionBar(toolbar);
        //buttonSelect.setOnClickListener(this);
        //buttonUpload.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
                //Intent login=new Intent(getApplicationContext(),MyLogin.class);
                //startActivity(login);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //////User defined or Override methods below//////////////////////////////////////////
    public String getEmail()
    {
        return email;
    }



    public void uploadMultiPart()
    {
        String name="";//editText.getText().toString().trim();

        String path= FIlePath.getPath(this,filepath);

        if(path==null)
        {
            Toast.makeText(this,"Please move your .pdf file to internal storage and retry",Toast.LENGTH_LONG).show();
        }
        else
        {
           try
           {
               String uploadedId= UUID.randomUUID().toString();

               new MultipartUploadRequest(this,uploadedId,UPLOAD_URL)
                       .addFileToUpload(path,"pdf")
                       .addParameter("name",name)
                       .addParameter("email",email)
                       .setNotificationConfig(new UploadNotificationConfig())
                       .setMaxRetries(2)
                       .startUpload();

           }
           catch (Exception e)
           {
               e.printStackTrace();
               Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
           }
        }
    }

    private void showFileChooser()
    {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf"),PICK_PDF_REQUEST);
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_PDF_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            filepath=data.getData();
        }
    }

    public void selectFragment(int pos)
    {
        pager.setCurrentItem(pos,true);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view)
    {
        //if(view==buttonSelect)
            //showFileChooser();
        //if(view==buttonUpload)
            //uploadMultiPart();
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {

    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */

}
