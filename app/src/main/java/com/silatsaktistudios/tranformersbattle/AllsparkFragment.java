package com.silatsaktistudios.tranformersbattle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class AllsparkFragment extends Fragment {

    public AllsparkFragment() { }

    private AllsparkViewModel viewModel;
    private CompositeDisposable disposables = new CompositeDisposable();

    private TextView textView;
    private ProgressBar progressBar;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null && getActivity().getApplication() != null) {
            viewModel = new ViewModelProvider(
                    getViewModelStore(),
                    ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())
            ).get(AllsparkViewModel.class);

            initSubs();

            viewModel.getTheAllSpark();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allspark, container, false);

        textView = view.findViewById(R.id.allspark_textview);
        progressBar = view.findViewById(R.id.allspark_progress_bar);

        button = view.findViewById(R.id.allspark_button);
        button.setOnClickListener(v -> viewModel.getTheAllSpark());

        return view;
    }

    @Override
    public void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }

    private void initSubs() {
        disposables.add(
                viewModel.sparkState
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                sparkState -> {
                                    switch (sparkState) {
                                        case GETTING_SPARK:
                                            button.setVisibility(View.INVISIBLE);
                                            textView.setText(R.string.getting_allspark_message);
                                            textView.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.VISIBLE);
                                            break;
                                        case SPARK_OBTAINED:
                                            textView.setText(R.string.spark_obtained_message);
                                            progressBar.setVisibility(View.INVISIBLE);

                                            //goto main list.
                                            NavController navController = NavHostFragment.findNavController(this);
                                            navController.navigate(AllsparkFragmentDirections.actionAllsparkFragmentToTransformersListFragment());
                                            break;
                                        case FAILED_TO_GET_SPARK:
                                            initFailToGetSparkUiState();
                                            break;
                                    }
                                },
                                onError -> initFailToGetSparkUiState()
                        )
        );
    }

    private void initFailToGetSparkUiState() {
        textView.setText(R.string.spark_obtain_fail_message);
        progressBar.setVisibility(View.INVISIBLE);
        button.setVisibility(View.VISIBLE);
    }
}
