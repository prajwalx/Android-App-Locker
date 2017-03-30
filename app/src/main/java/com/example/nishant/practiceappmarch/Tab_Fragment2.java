package com.example.nishant.practiceappmarch;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Tab_Fragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {

    ListView listView;
    ProgressDialog progressDialog;
    ArrayList<Pdf> pdfList=new ArrayList<Pdf>();
    PdfListAdapter pdfListAdapter;
    String PDF_FETCH_URL="http://192.168.1.10/AndroidPdUploads/getPdfs.php";
    String email="";
    Button get;
    SwipeRefreshLayout swipeRefreshLayout;

    public Tab_Fragment2() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_tab__fragment2, container, false);
    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView=(ListView)getView().findViewById(R.id.Listview);
        //get=(Button)getView().findViewById(R.id.GetPdf);
        progressDialog=new ProgressDialog(getActivity());
        swipeRefreshLayout=(SwipeRefreshLayout)getView().findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        Bundle bundle=this.getArguments();
        if(bundle!=null)
        {email=bundle.getString("email");}
        MainActivity activity=(MainActivity)getActivity();
        email=activity.getEmail();


                pdfList.clear();
                Toast.makeText(getActivity(),email,Toast.LENGTH_LONG).show();
                getPdfs();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Pdf pdf = (Pdf) parent.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(pdf.getUrl()));
                startActivity(intent);


            }
        });



        //getPdfs();
    }

    private void getPdfs()
    {
        //progressDialog.setMessage("Fetching Pdfs... Please wait");
        //progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, PDF_FETCH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            JSONArray jsonArray = obj.getJSONArray("pdfs");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Pdf pdf = new Pdf();
                                String PdfName = jsonObject.getString("name");
                                String PdfUrl = jsonObject.getString("url");
                                pdf.setName(PdfName);
                                pdf.setUrl(PdfUrl);
                                pdfList.add(pdf);
                            }

                            pdfListAdapter = new PdfListAdapter(getActivity(), R.layout.list_layout, pdfList);
                            listView.setAdapter(pdfListAdapter);
                            pdfListAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            /**
             * Returns a Map of parameters to be used for a POST or PUT request.  Can throw
             * {@link AuthFailureError} as authentication may be required to provide these values.
             * <p>
             * <p>Note that you can directly override {@link #getBody()} for custom data.</p>
             *
             * @throws AuthFailureError in the event of auth failure
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();
                params.put("email",email);
               // return super.getParams();
                return params;
            }
        };
        RequestQueue request= Volley.newRequestQueue(getActivity());
        request.add(stringRequest);
    }



    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {

        pdfList.clear();
        getPdfs();
        swipeRefreshLayout.setRefreshing(false);

    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */

}
