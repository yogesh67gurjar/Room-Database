package com.yogesh.room.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yogesh.room.R
import com.yogesh.room.data.models.TeacherEntity
import com.yogesh.room.databinding.ActivityMainBinding
import com.yogesh.room.databinding.BsCreateOrUpdateTeacherBinding
import com.yogesh.room.ui.views.adapters.TeachersAdapter
import com.yogesh.room.utils.Constants
import com.yogesh.room.utils.RecyclerViewClicklistenerWithType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), RecyclerViewClicklistenerWithType {
    private lateinit var activityMainBinding: ActivityMainBinding

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bottomSheetDialogBinding: BsCreateOrUpdateTeacherBinding
    private var teachersList: MutableList<TeacherEntity> = mutableListOf()
    private lateinit var teachersAdapter: TeachersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)


        initSetup()
        clickEvents()
    }

    private fun clickEvents() {
        activityMainBinding.addBtn.setOnClickListener {
            autoWriteAvailableDataToBottomSheetThenShow(null)
        }

        bottomSheetDialogBinding.createOrUpdateTeacherBtn.setOnClickListener {

        }
    }

    private fun autoWriteAvailableDataToBottomSheetThenShow(teacherEntity: TeacherEntity?) {
        if (teacherEntity == null) {
            bottomSheetDialogBinding.nameEt.setText("")
            bottomSheetDialogBinding.createOrUpdateTeacherBtn.setText(getString(R.string.save))
        } else {
            bottomSheetDialogBinding.nameEt.setText(teacherEntity.name)
            bottomSheetDialogBinding.createOrUpdateTeacherBtn.setText(getString(R.string.save_changes))
        }



        bottomSheetDialog.show()
    }

    private fun initSetup() {

        teachersList.add(TeacherEntity(name = "yogesh"))
        teachersList.add(TeacherEntity(name = "muskan"))
        teachersList.add(TeacherEntity(name = "sachin"))

        teachersAdapter = TeachersAdapter(teachersList, this)
        activityMainBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        activityMainBinding.recyclerView.adapter = teachersAdapter


        bottomSheetDialog = BottomSheetDialog(this@MainActivity)
        bottomSheetDialogBinding = BsCreateOrUpdateTeacherBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bottomSheetDialogBinding.root)

        setVisibilityAsPerData(teachersList)
    }

    private fun setVisibilityAsPerData(teachersList: MutableList<TeacherEntity>) {
        if (teachersList.size == 0) {
            activityMainBinding.recyclerView.visibility = View.GONE
            activityMainBinding.noDataFound.visibility = View.VISIBLE
        } else {
            activityMainBinding.recyclerView.visibility = View.VISIBLE
            activityMainBinding.noDataFound.visibility = View.GONE
        }
    }

    override fun onClick(position: Int, type: String) {
        if (type == Constants.TEACHER) {
            autoWriteAvailableDataToBottomSheetThenShow(teachersList[position])
        }
    }
}