package bimo.syahputro.chromeinc.activity.tambahTransaksi.lama;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import bimo.syahputro.chromeinc.R;
import bimo.syahputro.chromeinc.network.ApiClient;
import bimo.syahputro.chromeinc.network.ApiService;
import bimo.syahputro.chromeinc.network.entity.Customer;
import bimo.syahputro.chromeinc.network.response.CustomerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL;

public class DaftarCustomerActivity extends AppCompatActivity {
    RecyclerView rvCustomer;
    ApiService apiService;
    List<Customer> customerList = new ArrayList<>();
    DaftarCustomerAdapter daftarCustomerAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_customer);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Daftar Customer");
        }

        init();
        loadCustomer();
    }

    private void init() {
        progressBar = findViewById(R.id.progressbar);
        apiService = ApiClient.getClient().create(ApiService.class);
        rvCustomer = findViewById(R.id.rv_customer);
    }

    private void loadCustomer() {
        progressBar.setVisibility(View.VISIBLE);
        apiService.daftarCustomer().enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, final Response<CustomerResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    customerList = response.body().getDataCustomer();
                                    setupRecyclerView();
                                }
                            }, 3000);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, final Throwable t) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Snackbar.make(rvCustomer, t.getMessage(), Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }, 3000);
            }
        });
    }

    private void setupRecyclerView() {
        if (daftarCustomerAdapter == null) {
            daftarCustomerAdapter = new DaftarCustomerAdapter(DaftarCustomerActivity.this, customerList);
            DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);
            rvCustomer.addItemDecoration(itemDecor);
            rvCustomer.setLayoutManager(new LinearLayoutManager(this));
            rvCustomer.setAdapter(daftarCustomerAdapter);
        } else {
            daftarCustomerAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}