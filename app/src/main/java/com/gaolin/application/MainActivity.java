package com.gaolin.application;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gaolin.application.bean.DaoMaster;
import com.gaolin.application.bean.DaoSession;
import com.gaolin.application.bean.Student;
import com.gaolin.application.bean.StudentDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private StudentDao mStudentDao;

    @BindView(R.id.textView)
    TextView mTextView;

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id
            .button8, R.id.button9})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                addStu();
                break;
            case R.id.button2:
                addStuList();
                break;
            case R.id.button3:
                queryStu();
                break;
            case R.id.button4:
                searchByName();
                break;
            case R.id.button5:
                searchByOrder();
                break;
            case R.id.button6:
                searchByOrder1();
                break;
            case R.id.button7:
                smallToE();
                break;
            case R.id.button8:
                frontThree();
                break;
            case R.id.button9:
                frontThreeExceptFirst();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.bind(this);
        getStuDao();
    }

    private void getStuDao() {
        DaoMaster.DevOpenHelper lDevOpenHelper = new DaoMaster.DevOpenHelper(mContext, "hlq.db", null);
        DaoMaster lDaoMaster = new DaoMaster(lDevOpenHelper.getWritableDatabase());
        DaoSession lDaoSession = lDaoMaster.newSession();
        mStudentDao = lDaoSession.getStudentDao();
    }

    private void addStu() {
        Student lStudent = new Student(null, "001", "各大多", "男孩", "14");
        long end = mStudentDao.insert(lStudent);
        String msg;
        if (end > 0) {
            msg = "001新增成功";
        } else {
            msg = "新增失败";
        }
        mStudentDao.insert(new Student(null, "002", "各大多", "男孩", "14"));
        mStudentDao.insert(new Student(null, "003", "搭", "男孩", "14"));
        mStudentDao.insert(new Student(null, "004", "大多", "男孩", "14"));
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    private void addStuList() {
        List<Student> lStudents = new ArrayList<>();
        lStudents.add(new Student(null, "005", "各大多", "男孩", "23"));
        lStudents.add(new Student(null, "006", "各大多", "男孩", "14"));
        lStudents.add(new Student(null, "007", "各大多", "男孩", "16"));
        mStudentDao.insertInTx(lStudents);
        Toast.makeText(mContext, "新增成功", Toast.LENGTH_SHORT).show();
    }

    private void queryStu() {
        List<Student> lStudents = mStudentDao.queryBuilder().list();
        if (lStudents != null) {
            String searchAllInfo = "";
            for (int i = 0; i < lStudents.size(); i++) {
                Student lStudent = lStudents.get(i);
                searchAllInfo += "id：" + lStudent.getStuId() + "编号：" + lStudent.getStuNo() + "姓名：" + lStudent
                        .getStuName() + "性别：" + lStudent.getStuSex() + "成绩：" + lStudent.getStuScore() + "\n";
            }
            mTextView.setText(searchAllInfo);
        }
    }

    private void searchByName() {
        List<Student> lStudents = mStudentDao.queryBuilder().where(StudentDao.Properties.StuName.eq("各大多")).list();
        Toast.makeText(mContext, "" + lStudents.size(), Toast.LENGTH_SHORT).show();
    }

    private void searchByOrder() {
        String searchAssignOrderDesc = "";
        List<Student> stuList = mStudentDao.queryBuilder().where(StudentDao.Properties.StuName.eq("各大多")).orderDesc
                (StudentDao.Properties.StuScore).list();
        for (int i = 0; i < stuList.size(); i++) {
            Student stu = stuList.get(i);
            searchAssignOrderDesc += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() +
                    "性别：" + stu.getStuSex() + "成绩：" + stu.getStuScore() + "\n";
        }
        mTextView.setText(searchAssignOrderDesc);
    }

    private void searchByOrder1() {
        String searchAssignOrderDesc = "";
        List<Student> stuList = mStudentDao.queryBuilder().where(StudentDao.Properties.StuName.eq("各大多")).orderAsc
                (StudentDao.Properties.StuScore).list();
        for (int i = 0; i < stuList.size(); i++) {
            Student stu = stuList.get(i);
            searchAssignOrderDesc += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() +
                    "性别：" + stu.getStuSex() + "成绩：" + stu.getStuScore() + "\n";
        }
        mTextView.setText(searchAssignOrderDesc);
    }

    private void smallToE() {
        String searchAssignOrderDesc = "";
        QueryBuilder<Student> stuQB = mStudentDao.queryBuilder();
        stuQB.where(StudentDao.Properties.StuName.eq("各大多"), StudentDao.Properties.StuScore.le("60"));
        List<Student> stuList = stuQB.list();
        for (int i = 0; i < stuList.size(); i++) {
            Student stu = stuList.get(i);
            searchAssignOrderDesc += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() +
                    "性别：" + stu.getStuSex() + "成绩：" + stu.getStuScore() + "\n";
        }
        mTextView.setText(searchAssignOrderDesc);
    }

    private void frontThree() {
        List<Student> stuList = mStudentDao.queryBuilder().limit(3).list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() + "性别：" +
                        stu.getStuSex() + "成绩：" + stu.getStuScore() + "\n";
            }
            mTextView.setText(searchAllInfo);
        }
    }

    private void frontThreeExceptFirst() {
        List<Student> stuList = mStudentDao.queryBuilder().limit(3).offset(1).list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() + "性别：" +
                        stu.getStuSex() + "成绩：" + stu.getStuScore() + "\n";
            }
            mTextView.setText(searchAllInfo);
        }
    }
}
