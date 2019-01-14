package com.example.iem.test.Don;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.api.internal.HttpClient;
import com.example.iem.test.Constants;
import com.example.iem.test.R;



import java.util.HashMap;
import java.util.Map;

public class Donation extends AppCompatActivity {

   private String token;
   private String amount;
   private String  fiscalReduction;
   private String  pattern;
   private int fiscalReductionLength;
   private HashMap<String, String> paramHash;
   private Button btnPay;
   private EditText etAmount;
   private LinearLayout llHolder;
   private TextView fiscalView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        llHolder = findViewById(R.id.llHolder);
        etAmount = findViewById(R.id.etPrice);
        btnPay = findViewById(R.id.btnPay);
        fiscalView = findViewById(R.id.fiscalView);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBraintreeSubmit();
            }
        });
        Thread t = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()){
                    try {
                        Thread.sleep(1000); // 1000ms = 1s
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fiscalReductionWithRegex();
                        }
                    });
                }
            }
        };
        t.start();
        new HttpRequest().execute();
    }
    private void fiscalReductionWithRegex() {
        fiscalReduction = etAmount.getText().toString();
        fiscalReductionLength = fiscalReduction.length();
        if (fiscalReductionLength == 1){
            pattern = "[^a-zA-Z]";
        } else if (fiscalReductionLength == 2){
            pattern = "[^a-zA-Z][^a-zA-Z]";
        }else if (fiscalReductionLength ==  3){
            pattern = "[^a-zA-Z][^a-zA-Z][^a-zA-Z]";
        }else if (fiscalReductionLength == 4){
            pattern = "[^a-zA-Z][^a-zA-Z][^a-zA-Z][^a-zA-Z]";
        }else if (fiscalReductionLength >=5){
            //Toast.makeText(getApplicationContext(),"Veuillez prendre contact avec l'équipe de dev", Toast.LENGTH_SHORT);
        }

        if (fiscalReduction.isEmpty()) {
            fiscalView.setText("");
        } else if (!fiscalReduction.matches(pattern)) {
            fiscalView.setText("");
        } else {
            double donAfterReduction = Double.parseDouble(fiscalReduction);
            donAfterReduction = Math.floor((donAfterReduction * 0.34)*100)/100;
            fiscalView.setText(String.valueOf("Vous revient à " + donAfterReduction + "€ après déduction d'impôts"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String stringNonce = nonce.getNonce();
                Log.d(Constants.BRAINTREE_TAG, "Result: " + stringNonce);
                if (!etAmount.getText().toString().isEmpty()) {
                    amount = etAmount.getText().toString();
                    paramHash = new HashMap<>();
                    paramHash.put("amount", amount);
                    paramHash.put("nonce", stringNonce);
                    sendPaymentDetails();
                    amount.isEmpty();
                } else
                    Toast.makeText(Donation.this, "Rentrez un montant valide", Toast.LENGTH_SHORT).show();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Log.d(Constants.BRAINTREE_TAG, "Annulée");
            } else {
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d(Constants.BRAINTREE_TAG, "Error : " + error.toString());
            }
        }
    }

    public void onBraintreeSubmit() {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token);
        startActivityForResult(dropInRequest.getIntent(this), Constants.REQUEST_CODE);
    }

    private void sendPaymentDetails() {
        RequestQueue queue = Volley.newRequestQueue(Donation.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.send_payment_details,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("Successful"))
                        {
                            Toast.makeText(Donation.this, "Transaction : réussi ", Toast.LENGTH_LONG).show();
                        }
                        else Toast.makeText(Donation.this, "Transaction : echec", Toast.LENGTH_LONG).show();
                        Log.d(Constants.BRAINTREE_TAG, "Final Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(Constants.BRAINTREE_TAG, "Volley error : " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                if (paramHash == null)
                    return null;
                Map<String, String> params = new HashMap<>();
                for (String key : paramHash.keySet()) {
                    params.put(key, paramHash.get(key));
                    Log.d(Constants.BRAINTREE_TAG, "Key : " + key + " Value : " + paramHash.get(key));
                }

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private class HttpRequest extends AsyncTask {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(Donation.this, android.R.style.Theme_DeviceDefault_Dialog);
            progress.setCancelable(false);
            progress.setMessage("Connexion à nos serveurs en cours, Veuillez patienter");
            progress.setTitle("Creation du token");
            progress.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient client = new HttpClient();
            client.get(Constants.get_token, new HttpResponseCallback() {
                @Override
                public void success(String responseBody) {
                    Log.d("BRAINTREE", responseBody);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Donation.this, "Successfully got token", Toast.LENGTH_SHORT).show();
                            llHolder.setVisibility(View.VISIBLE);
                        }
                    });
                    token = responseBody;
                }

                @Override
                public void failure(Exception exception) {
                    final Exception ex = exception;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Donation.this, "Failed to get token: " + ex.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progress.dismiss();
        }
    }
}