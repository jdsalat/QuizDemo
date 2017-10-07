package com.machinetest.quiz.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.machinetest.quiz.R;
import com.machinetest.quiz.beans.Questions;
import com.machinetest.quiz.beans.ResponseBean;
import com.machinetest.quiz.beans.UserAnswerBean;
import com.machinetest.quiz.presenters.QuestionsPresenter;
import com.machinetest.quiz.presenters.SaveUserAnswerPresenter;
import com.machinetest.quiz.utils.Constants;
import com.machinetest.quiz.utils.Utilities;
import com.machinetest.quiz.views.QuestionView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GKTestFragment extends Fragment implements QuestionView, RadioGroup.OnCheckedChangeListener {
    public static final String TAG = GKTestFragment.class.getSimpleName();

    ProgressDialog pd = null;
    QuestionsPresenter questionsPresenter;
    @BindView(R.id.tv_question)
    TextView tvQuestion;
    @BindView(R.id.optionA)
    RadioButton optionA;
    @BindView(R.id.optionB)
    RadioButton optionB;
    @BindView(R.id.optionC)
    RadioButton optionC;
    @BindView(R.id.optionD)
    RadioButton optionD;
    @BindView(R.id.rgAnswers)
    RadioGroup rgAnswers;

    List<Questions> questionsList;
    int userAnswerId = 0;
    List<UserAnswerBean> userAnswerBeanList = new ArrayList<>();

    int questionCount = 0;
    int maxQuestionCount = 0;
    @BindView(R.id.btn_previous)
    Button btnPrevious;
    @BindView(R.id.btn_next)
    Button btnNext;

    SaveUserAnswerPresenter saveUserAnswerPresenter;

    public GKTestFragment() {
        // Required empty public constructor
    }


    public static GKTestFragment newInstance() {
        GKTestFragment fragment = new GKTestFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gktest, container, false);
        ButterKnife.bind(this, view);
        initialize();

        rgAnswers.setOnCheckedChangeListener(this);

        return view;
    }

    private void initialize() {
        btnPrevious.setVisibility(View.INVISIBLE);
        btnNext.setText(getString(R.string.btn_next));
        questionCount = 0;
        maxQuestionCount = 0;
        questionsPresenter = new QuestionsPresenter();
        questionsPresenter.setView(this);
        questionsPresenter.fetchQuestions(getContext());
    }

    @Override
    public void loadQuestions(List<Questions> questionsList) {

        if (questionsList != null && !questionsList.isEmpty()) {
            this.questionsList = questionsList;
            populateQuestion(questionCount);
            maxQuestionCount = this.questionsList.size();

        }
    }

    @Override
    public void onSaveSuccess(ResponseBean responseBean) {
        dismissDialog();
        if (responseBean.getCallFrom().equalsIgnoreCase(Constants.CALL_DATABASE)) {
            displayDialog(getString(R.string.thank_you_message) + "\n" + getString(R.string.save_data_base_message));
        } else {
            if (responseBean.getResponse().equalsIgnoreCase(Constants.SERVER_RESPONSE_SUCCESS)) {
                displayDialog(getString(R.string.thank_you_message) + "\n" + getString(R.string.upload_data_message));
            } else {
                displayDialog(getString(R.string.error_uploading_data));
            }
        }

    }

    @Override
    public void onSaveFailure() {
        dismissDialog();

    }

    @OnClick({R.id.btn_previous, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_previous:
                if (questionCount >= 0) {
                    this.userAnswerBeanList.remove(questionCount-1);
                    questionCount--;
                    if (questionCount == 0) {
                        btnPrevious.setVisibility(View.INVISIBLE);
                    } else {
                        btnNext.setText(getString(R.string.btn_next));
                    }
                    populateQuestion(questionCount);
                }
                break;
            case R.id.btn_next:
                if (btnNext.getText().equals(getString(R.string.btn_next))) {
                    if (questionCount <= maxQuestionCount - 1) {
                        btnPrevious.setVisibility(View.VISIBLE);
                        questionCount++;
                        populateQuestion(questionCount);
                        if (questionCount == maxQuestionCount - 1) {
                            btnNext.setText(getString(R.string.btn_submit));
                        } else {
                            btnNext.setText(getString(R.string.btn_next));
                        }

                    }
                } else {
                    showDialog();
                    saveUserAnswerPresenter = new SaveUserAnswerPresenter();
                    saveUserAnswerPresenter.setView(this);
                    if (Utilities.isNetworkAvailable(getContext())) {
                        saveUserAnswerPresenter.uploadDataToServer(getContext(), userAnswerBeanList);
                    } else {
                        saveUserAnswerPresenter.saveAnswers(getContext(), userAnswerBeanList);
                    }
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton rb = (RadioButton) group.findViewById(checkedId);
        if (null != rb && checkedId > -1 && rb.isChecked()) {
            switch (checkedId) {
                case R.id.optionA:
                    userAnswerId = this.questionsList.get(questionCount).getOptionA().getAnswerdId();
                    break;

                case R.id.optionB:
                    userAnswerId = this.questionsList.get(questionCount).getOptionB().getAnswerdId();
                    break;

                case R.id.optionC:
                    userAnswerId = this.questionsList.get(questionCount).getOptionC().getAnswerdId();
                    break;

                case R.id.optionD:
                    userAnswerId = this.questionsList.get(questionCount).getOptionD().getAnswerdId();
                    break;

                default:
                    break;
            }

            UserAnswerBean userAnswerBean = new UserAnswerBean();
            userAnswerBean.setQuestionId(this.questionsList.get(questionCount).getID());
            userAnswerBean.setAnswerId(userAnswerId);
            userAnswerBeanList.add(userAnswerBean);
        }

    }


    private void populateQuestion(int position) {
        rgAnswers.clearCheck();
        tvQuestion.setText(this.questionsList.get(position).getQuestion());
        optionA.setText(this.questionsList.get(position).getOptionA().getAnswerText());
        optionB.setText(this.questionsList.get(position).getOptionB().getAnswerText());
        optionC.setText(this.questionsList.get(position).getOptionC().getAnswerText());
        optionD.setText(this.questionsList.get(position).getOptionD().getAnswerText());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void displayDialog(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.setMessage(message);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        initialize();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }


    public void showDialog() {
        pd = ProgressDialog.show(getContext(), "", getString(R.string.loading_message), true);
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        });
    }

    public void dismissDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }
}
