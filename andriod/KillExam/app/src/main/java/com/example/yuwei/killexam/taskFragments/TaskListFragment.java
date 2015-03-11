package com.example.yuwei.killexam.taskFragments;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yuwei.killexam.MainActivity;
import com.example.yuwei.killexam.R;
import com.example.yuwei.killexam.adapter.TaskListAdapter;
import com.example.yuwei.killexam.database.MyDatabaseHelper;
import com.example.yuwei.killexam.map.HeaderTimeMapString;
import com.example.yuwei.killexam.tools.Task;
import com.example.yuwei.killexam.tools.TaskTree;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class TaskListFragment extends Fragment {

    public static TaskTree taskTree;

    private static MainActivity mMainActivity;

    public static StickyListHeadersListView taskListView;

    static TaskListAdapter adapter;

    static ArrayList<Task> sortedTODOTaskArrayList;

    View mView;

    public TaskListFragment(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_task_list, container, false);

        initFloatButton();


        taskListView = (StickyListHeadersListView)mView.findViewById(R.id.taskListView);


        initTaskList();

        if (sortedTODOTaskArrayList != null){
            initActionBar();
        }

        return mView;
    }

//  更新taskList的数据（重新初始化，重新设置adapter），
//  调用方法更新ui线程，
//  设置之前选中的位置
    public static void renewListView(){
        initTaskList();

        int position = taskListView.getFirstVisiblePosition();

        adapter.notifyDataSetChanged();

        taskListView.setSelection(position);

        initActionBar();

    }

//  通过读取数据库获取最新的taskArray，来初始化taskTree
    public static void initTaskList(){
        ArrayList<Task> taskArrayList = MyDatabaseHelper.getTaskArray(mMainActivity);
        if (!taskArrayList.isEmpty()){
            taskTree = TaskTree.newInstance(taskArrayList, mMainActivity);

            sortedTODOTaskArrayList = TaskTree.getSortedTODOTaskArrayList();

            adapter = new TaskListAdapter(mMainActivity, R.layout.task_item, sortedTODOTaskArrayList);
            taskListView.setAdapter(adapter);

        }
    }


    private static void initActionBar(){
        HeaderTimeMapString headerTimeMapString = new HeaderTimeMapString();
        int imageId = headerTimeMapString.getImageId(sortedTODOTaskArrayList.get(0));
        mMainActivity.getSupportActionBar().setBackgroundDrawable(mMainActivity.getResources().getDrawable(imageId));
    }

    private void initFloatButton(){



        final View actionB = mView.findViewById(R.id.action_b);

        FloatingActionButton actionC = new FloatingActionButton(mMainActivity.getBaseContext());
        actionC.setTitle("Hide/Show Action above");
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        ((FloatingActionsMenu) mView.findViewById(R.id.multiple_actions)).addButton(actionC);


        final FloatingActionButton actionA = (FloatingActionButton) mView.findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionA.setTitle("Action A clicked");
            }
        });

    }
}
