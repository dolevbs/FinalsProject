package com.example.android.camera2basic;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class AddItemFragment extends Fragment  implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    public AddItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    }

    public void onClick(View clickedView) {
//        showToast("" + clickedView.getId());
        switch (clickedView.getId()) {
            case R.id.TakeDateImageButton: {
                try {
                    //start the scanning activity from the com.google.zxing.client.android.SCAN intent
                    Intent intent;
                    intent = new Intent(this.getActivity(), CameraActivity.class);
                    startActivity(intent);
                } catch (ActivityNotFoundException anfe) {}
                break;

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
        if (requestCode == 0) {

            //get the extras that are returned from the intent
            final String contents = intent.getStringExtra("SCAN_RESULT");
            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView barCodeNumberTextView = (TextView) getView().findViewById(R.id.BarCodeNumberTextView);
                    barCodeNumberTextView.setText(contents);
                }
            });
            showToast("Content:" + contents + " Format:" + format);

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

