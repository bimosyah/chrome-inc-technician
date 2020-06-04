package bimo.syahputro.chromeinc.activity.tambahTransaksi.baru;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import bimo.syahputro.chromeinc.R;
import bimo.syahputro.chromeinc.activity.tambahTransaksi.baru.fragment.BarangFragment;
import bimo.syahputro.chromeinc.activity.tambahTransaksi.baru.fragment.CheckoutFragment;
import bimo.syahputro.chromeinc.activity.tambahTransaksi.baru.fragment.CustomerFragment;

public class TransaksiCustomerBaruActivity extends AppCompatActivity implements CustomerFragment.OnDataPassCustomer {
    public static String ID_CUSTOMER = "ID_CUSTOMER";
    public static String NAMA_CUSTOMER = "NAMA_CUSTOMER";
    public static String ALAMAT_CUSTOMER = "ALAMAT_CUSTOMER";
    public static String NOTELP_CUSTOMER = "NOTELP_CUSTOMER";

    Fragment fragmentCustomer, fragmentBarang, fragmentCheckout;
    ImageView ivKiri, ivTengah, ivKanan;
    TextView tvKiri, tvTengah, tvKanan;
    int posisi_fragment = 1;
    LinearLayout btnBack, btnNext;
    String namaCustomer, alamatCustomer, notelpCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Transaksi Baru");
        }

        init();
        openFragment(fragmentCustomer);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (posisi_fragment == 1) {
                    posisi_fragment = 2;

                    openFragment(fragmentBarang);
                    callCustomerData();

                    tvKiri.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.transaksi_header));
                    tvTengah.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                    ivKiri.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_brightness, getApplicationContext().getTheme()));

                } else if (posisi_fragment == 2) {
                    posisi_fragment = 3;

                    openFragment(fragmentCheckout);

                    tvTengah.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.transaksi_header));
                    tvKanan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                    ivTengah.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_brightness, getApplicationContext().getTheme()));
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (posisi_fragment == 3) {
                    posisi_fragment = 2;

                    openFragment(fragmentBarang);

                    tvKanan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.transaksi_header));
                    tvTengah.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                    ivTengah.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_adjust, getApplicationContext().getTheme()));
                } else if (posisi_fragment == 2) {
                    posisi_fragment = 1;

                    openFragment(fragmentCustomer);

                    tvKiri.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                    tvTengah.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.transaksi_header));
                    ivKiri.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_adjust, getApplicationContext().getTheme()));
                }
            }
        });
    }

    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container_transaksi, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void callCustomerData() {
        CustomerFragment fragment = (CustomerFragment) getSupportFragmentManager().findFragmentById(R.id.container_transaksi);
        if (fragment != null) {
            fragment.passData();
        }
    }


    private void init() {
        ivKiri = findViewById(R.id.iv_posisi_kiri);
        ivTengah = findViewById(R.id.iv_posisi_tengah);
        tvKanan = findViewById(R.id.tv_posisi_kanan);
        tvKiri = findViewById(R.id.tv_posisi_kiri);
        tvTengah = findViewById(R.id.tv_posisi_tengah);
        ivKanan = findViewById(R.id.iv_posisi_kanan);
        fragmentCustomer = new CustomerFragment();
        fragmentBarang = new BarangFragment();
        fragmentCheckout = new CheckoutFragment();
        btnBack = findViewById(R.id.btn_back);
        btnNext = findViewById(R.id.btn_next);
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

    @Override
    public void passDataCustomer(String nama, String alamat, String notelp) {
        namaCustomer = nama;
        alamatCustomer = alamat;
        notelpCustomer = notelp;
    }
}