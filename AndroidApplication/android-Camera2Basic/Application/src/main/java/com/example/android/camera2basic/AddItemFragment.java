package com.example.android.camera2basic;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.net.URI;


public class AddItemFragment extends Fragment  implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private ItemRegister mItemToRegister;
    public AddItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemToRegister = new ItemRegister();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {

        view.findViewById(R.id.BarCodeScanButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scanBar(v);
            }
        });

        view.findViewById(R.id.TakeDateImageButton).setOnClickListener(this);

        Button addItemButton = (Button) view.findViewById(R.id.finalAddItemButton);
        addItemButton.setOnClickListener(this);
        addItemButton.setEnabled(false);

    }

    public void onClick(View clickedView) {
//        showToast("" + clickedView.getId());
        Log.i("a", "1");
        Log.i("a", "" + clickedView.getId());
        switch (clickedView.getId()) {
            case R.id.TakeDateImageButton: {
                try {
                    Log.i("a", "2");
                    //start the scanning activity from the com.google.zxing.client.android.SCAN intent
                    Intent intent;
                    intent = new Intent(this.getActivity(), CameraActivity.class);
                    Log.i("a", "3");
                    startActivityForResult(intent, 1);
                    Log.i("a", "4");
                } catch (ActivityNotFoundException anfe) {}
                break;

            }
            case R.id.finalAddItemButton: {
                new AddProductTask().execute();
                Intent intent=new Intent();
                intent.putExtra("barcode", mItemToRegister.getBarcode());
                intent.putExtra("date", mItemToRegister.getExpirationDateFile().getAbsolutePath());

                Log.i("AddItemFragment", mItemToRegister.toString());
                getActivity().setResult(3, intent);
                getActivity().finish();//finishing activity
                break;
            }
        }
    }

    private void checkAndChangeAddItemButtonState(){
        if ( mItemToRegister.getBarcode() != null && mItemToRegister.getExpirationDateFile() != null ) {
            final Activity activity = getActivity();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Button addItemButton = (Button) activity.findViewById(R.id.finalAddItemButton);
                        addItemButton.setEnabled(true);
                    }
                });
            }
        }
    }

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    private void showToast(final String text) {
        final Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    //product barcode mode
    public void scanBar(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showToast( "Didn't found activity, download zxing");

        }
    }
    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Crop.REQUEST_CROP && resultCode == 0) {
            showToast("Image Croped");
        }
        if (requestCode == 0 && intent != null) {

            //get the extras that are returned from the intent
            final String contents = intent.getStringExtra("SCAN_RESULT");
            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
            mItemToRegister.setBarcode(contents);
            new GetProductName().execute(new String[]{contents});
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView barCodeNumberTextView = (TextView) getView().findViewById(R.id.BarCodeNumberTextView);
                    barCodeNumberTextView.setText(contents);
                }
            });

            showToast("Content:" + contents + " Format:" + format);

        }
        if (requestCode == 1 ) {
            Log.i("AddItemFragment", "return from camera");
            final String filePath = intent.getStringExtra("FILEPath");
            Uri inputFileURi = Uri.fromFile(new File(filePath));
            Crop.of(inputFileURi, inputFileURi).start(getActivity());
            //Crop.of(inputFileURi , inputFileURi).asSquare().start(getActivity());
            if ( filePath != null ) {
                mItemToRegister.setExpirationDateFile(new File(filePath));
            }

        }

        checkAndChangeAddItemButtonState();
    }

    private class GetProductName extends AsyncTask {

        protected Object doInBackground(Object[] barcodes) {
            Log.d("AAAAAAAAAAAAAAAAAAAA", "doInBackground");

            String balbla = Utils.getDataFromUrl(String.format(Utils.GET_BARCODE, barcodes[0] ));
            //"[   {     \"id\": \"123\",     \"name\": \"קוטג תנובה 5%\",     \"expirationDate\": \"12/12/12\"   },   {     \"id\": \"432\",     \"name\": \"חלב תנובה 3%\",     \"expirationDate\": \"05/12/15\"   },   {     \"id\": \"6812\",     \"name\": \"גבינת עמק 27%\",     \"expirationDate\": \"28/12/15\"   },   {     \"id\": \"1233\",     \"name\": \"נוטלה\",     \"expirationDate\": \"30/12/16\"   },   {     \"id\": \"6812\",     \"name\": \"גבינת עמק 28%\",     \"expirationDate\": \"28/11/15\"   },   {     \"id\": \"434\",     \"name\": \"חלב תנובה 3%\",     \"expirationDate\": \"10/12/15\"   } ] "
            Log.d("AAAAAAAAAAAAAAA", balbla);
            return balbla;
        }

        protected void onPostExecute(Object result) {
            String bla = (String) result;

            Log.d("AAAAAAAAA", "postExcute");
            Log.d("AAAAAAAAA", bla);
            bla = bla.replaceAll("(?m)^[ \t]*\r?\n", "").trim();
            TextView barCodeNumberTextView = (TextView) getView().findViewById(R.id.BarCodeNumberTextView);
            barCodeNumberTextView.setText(bla);

        }

    }

    private class AddProductTask extends AsyncTask {

        protected Object doInBackground(Object[] barcodes) {

            return Utils.addProduct(mItemToRegister.getBarcode(), mItemToRegister.getExpirationDateFile().getAbsolutePath());

        }
        protected void onPostExecute(Object result) {
            String bla = (String) result;

            Log.d("AAAAAAAAA", "postExcute");
            Log.d("AAAAAAAAA", bla);
            bla = bla.replaceAll("(?m)^[ \t]*\r?\n", "").trim();
            TextView barCodeNumberTextView = (TextView) getView().findViewById(R.id.BarCodeNumberTextView);
            barCodeNumberTextView.setText(bla);

        }
    }
    public static Fragment newInstance() {
        return new AddItemFragment();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

