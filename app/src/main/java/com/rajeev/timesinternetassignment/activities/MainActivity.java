package com.rajeev.timesinternetassignment.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rajeev.timesinternetassignment.R;
import com.rajeev.timesinternetassignment.adapters.ProductsAdapter;
import com.rajeev.timesinternetassignment.beans.DataBean;
import com.rajeev.timesinternetassignment.beans.ResponseBean;
import com.rajeev.timesinternetassignment.constants.AppConstants;
import com.rajeev.timesinternetassignment.databinding.ActivityMainBinding;
import com.rajeev.timesinternetassignment.retrofit.ApiClientConnection;
import com.rajeev.timesinternetassignment.retrofit.ApiInterface;
import com.rajeev.timesinternetassignment.utils.CustomDialog;
import com.rajeev.timesinternetassignment.utils.EndlessRecyclerVScrollListener;
import com.rajeev.timesinternetassignment.utils.InternetCheck;
import com.rajeev.timesinternetassignment.utils.MyApplication;
import com.rajeev.timesinternetassignment.utils.ServiceParameters;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private InternetCheck internetCheck;
    private ArrayList<DataBean> dataBeansList = null;
    private ProductsAdapter adapter;
    private ActivityMainBinding binding;
    private int offset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUpStart();

    }

    private void setUpStart() {
        //Initialize
        dataBeansList = new ArrayList<>();

        /*//      TOOLBAR*/
        Toolbar toolbar = binding.toolbarHome;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(15F);
        }

        setSupportActionBar(toolbar);

        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
//            ab.setHomeAsUpIndicator(R.drawable.navi_menu); // set a custom icon for the default home button
//            ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
            ab.setDisplayShowTitleEnabled(false); // disable the default title element here (for centered title)
        }

        internetCheck = new InternetCheck(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.w(TAG, "onResume");

        //      REGISTER
        registerReceiver(internetCheck, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

        // GO TO NEXT WHENEVER CONNECTED NOW
        internetCheck.setInternetCheckInterface(isConnected -> {
            if (isConnected) {
//                    MyApplication.makeASuccessSnack(binding.getRoot(), AppConstants.kWeOnlineInternet);

                Log.w(TAG, "isConnected: " + true);

                if (MainActivity.this.dataBeansList != null && MainActivity.this.dataBeansList.size() == 0) {
                    getProductsService(); //SERVICE HIT..
                }

            } else {
                MyApplication.makeAFailureSnack(binding.getRoot(), AppConstants.kMakeSureInternet);
                binding.tvNoData.setVisibility(View.VISIBLE);

            }
        });


    }


    @Override
    public void onStop() {
        super.onStop();

        //      UNREGISTER
        unregisterReceiver(internetCheck);
    }

    /*SERVICES*/
    //    METHOD: TO REQUEST GET getProducts SERVICE..
    public void getProductsService() {

        final CustomDialog dialog;

//                  REQUESTING GET getProducts  SERVICE..
        try {

            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();


            Call<ResponseBean> call = apiInterface.getProducts("json", String.valueOf(20),String.valueOf(offset));

            dialog = CustomDialog.showDialog(MainActivity.this);

            ServiceParameters.setUpParameters("json", String.valueOf(20),String.valueOf(offset));

            call.enqueue(new Callback<ResponseBean>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBean> call, @NonNull Response<ResponseBean> response) {
                    CustomDialog.hideCloseDialog(dialog);
                    if (response.isSuccessful()) {
                        ResponseBean bean = response.body();
                        if (bean != null) {

                            ArrayList<DataBean> list = bean.getDataBeanList();

                            if (list != null && list.size() > 0)
                            {
                                if (adapter == null) {
                                    dataBeansList = list;
                                    setUpRecyclerV(dataBeansList);
                                } else {
                                    dataBeansList.addAll(list);
                                    adapter.setArrayList(dataBeansList);
                                    adapter.notifyDataSetChanged();
                                }

                            }

                        } else {
                            MyApplication.makeCenterToast(AppConstants.kMessageSomeWentWrong);
                        }

                    } else {
                        Toast.makeText(MainActivity.this, AppConstants.kMessageServerNotRespondingError, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBean> call, @NonNull Throwable t) {
                    CustomDialog.hideCloseDialog(dialog);
                    t.printStackTrace();
                    MyApplication.showOnFailureMessages(MainActivity.this, t);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setUpRecyclerV(ArrayList<DataBean> dataBeansList) {
        if (dataBeansList != null && dataBeansList.size() > 0) {
            binding.tvNoData.setVisibility(View.GONE);
            binding.recyclerV.setVisibility(View.VISIBLE);

            Log.w(TAG, "dataBeansList size: " + dataBeansList.size());

            if(adapter == null) {

                GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
                binding.recyclerV.setLayoutManager(layoutManager);

                adapter = new ProductsAdapter(MainActivity.this, dataBeansList);
                binding.recyclerV.setAdapter(adapter);

                EndlessRecyclerVScrollListener scrollListener = new EndlessRecyclerVScrollListener(layoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        Log.i(TAG, "Page: " + page + " TotalItems: " + totalItemsCount);

                        if (totalItemsCount >= 10) {
                            offset = offset+20;
                            getProductsService();   //SERVICE HIT...
                        }
                    }
                };

                binding.recyclerV.addOnScrollListener(scrollListener);


//                // ADAPTER CALLBACKS
                adapter.setListener(new ProductsAdapter.MyProductsListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        if (adapter != null && adapter.getArrayList().size() > 0) {
                            DataBean bean = adapter.getArrayList().get(pos);
                            dispatchInfoAct(bean);
                        }
                    }
                });

            }
            else {
                adapter.setArrayList(dataBeansList);
                adapter.notifyDataSetChanged();
            }


        } else {
            binding.tvNoData.setVisibility(View.VISIBLE);
            binding.recyclerV.setVisibility(View.GONE);
        }

    }

    private void dispatchInfoAct(DataBean dataBean) {
        Intent i = new Intent(this, ItemInfoAct.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("dataBean", dataBean);
        i.putExtras(bundle);
        startActivity(i);
    }


}
