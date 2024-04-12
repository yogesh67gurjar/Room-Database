package com.yogesh.room.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yogesh.room.R
import com.yogesh.room.data.models.TeacherEntity
import com.yogesh.room.databinding.ActivityMainBinding
import com.yogesh.room.databinding.BsCreateOrUpdateTeacherBinding
import com.yogesh.room.ui.viewModels.SchoolViewModel
import com.yogesh.room.ui.views.adapters.TeachersAdapter
import com.yogesh.room.utils.Constants
import com.yogesh.room.utils.RecyclerViewClicklistenerWithType
import com.yogesh.room.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), RecyclerViewClicklistenerWithType {
    private lateinit var activityMainBinding: ActivityMainBinding

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bottomSheetDialogBinding: BsCreateOrUpdateTeacherBinding
    private var teachersList: MutableList<TeacherEntity> = mutableListOf()
    private lateinit var teachersAdapter: TeachersAdapter

    private val schoolViewModel: SchoolViewModel by viewModels()
    private var teacherEntity: TeacherEntity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)


        initSetup()

        clickEvents()
        attachObservers()
        getAllTeachers()
    }

    private fun attachObservers() {
        lifecycleScope.launch {
            schoolViewModel.getAllTeachersStateFlow.collect {
                when (it) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        teachersList.clear()
                        teachersList.addAll(it.data)
                        teachersAdapter.notifyDataSetChanged()
                        setVisibilityAsPerData(teachersList)
                    }

                    is Resource.Failed -> {
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> {}
                }
            }
        }

        lifecycleScope.launch {
            schoolViewModel.updateTeacherStateFlow.collect {
                when (it) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        if (it.data > 0) {
                            handleAddOrUpdateSuccess()
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Some error occured while adding teacher",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                    }

                    is Resource.Failed -> {
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> {}
                }

            }
        }

        lifecycleScope.launch {
            schoolViewModel.insertTeacherStateFlow.collect {
                when (it) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        if (it.data > 0) {
                            handleAddOrUpdateSuccess()

//                            teachersList.add(teacherEntity!!)
//                            teachersAdapter.notifyDataSetChanged()
//                            if (bottomSheetDialog.isShowing) {
//                                bottomSheetDialog.dismiss()
//                            }
//                            teacherEntity = null
//                            setVisibilityAsPerData(teachersList)
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Some error occured while adding teacher",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                    }

                    is Resource.Failed -> {
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> {}
                }

            }
        }
    }

    private fun handleAddOrUpdateSuccess() {
        schoolViewModel.getAllTeachers()
        teacherEntity = null
        if (bottomSheetDialog.isShowing) {
            bottomSheetDialog.dismiss()
        }
        setVisibilityAsPerData(teachersList)
    }

    private fun getAllTeachers() {
        schoolViewModel.getAllTeachers()
    }

    private fun clickEvents() {
        activityMainBinding.addBtn.setOnClickListener {
            autoWriteAvailableDataToBottomSheetThenShow(null)
        }

        bottomSheetDialogBinding.createOrUpdateTeacherBtn.setOnClickListener {
            if (bottomSheetDialogBinding.nameEt.text.toString().isEmpty()) {
                bottomSheetDialogBinding.nameEt.error = "Please enter name"
                bottomSheetDialogBinding.nameEt.requestFocus()
            } else {
                teacherEntity!!.name = bottomSheetDialogBinding.nameEt.text.toString().trim()
                schoolViewModel.addOrUpdateTeacher(teacherEntity!!)
            }
        }
    }

    private fun autoWriteAvailableDataToBottomSheetThenShow(teacherEntity: TeacherEntity?) {
        if (teacherEntity == null) {
            bottomSheetDialogBinding.nameEt.setText("")
            bottomSheetDialogBinding.createOrUpdateTeacherBtn.text = getString(R.string.save)
            bottomSheetDialogBinding.addOrUpdateTv.text = getString(R.string.add)
            bottomSheetDialogBinding.deleteBtn.visibility = View.GONE
            this.teacherEntity = TeacherEntity(name = "")
        } else {
            bottomSheetDialogBinding.nameEt.setText(teacherEntity.name)
            bottomSheetDialogBinding.createOrUpdateTeacherBtn.text =
                getString(R.string.save_changes)
            bottomSheetDialogBinding.addOrUpdateTv.text = getString(R.string.update)
            bottomSheetDialogBinding.deleteBtn.visibility = View.VISIBLE

        }
        bottomSheetDialogBinding.nameEt.setSelection(bottomSheetDialogBinding.nameEt.text.toString().length)
        bottomSheetDialogBinding.nameEt.requestFocus()
        bottomSheetDialog.show()
    }

    private fun initSetup() {
        teachersAdapter = TeachersAdapter(teachersList, this, this)
        activityMainBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        activityMainBinding.recyclerView.adapter = teachersAdapter

        bottomSheetDialog = BottomSheetDialog(this@MainActivity)
        bottomSheetDialogBinding = BsCreateOrUpdateTeacherBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bottomSheetDialogBinding.root)
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
        if (type == Constants.EDIT) {
            teacherEntity = teachersList[position]
            autoWriteAvailableDataToBottomSheetThenShow(teachersList[position])
        } else if (type == Constants.DELETE) {
            Toast.makeText(this@MainActivity, teachersList[position].name, Toast.LENGTH_SHORT)
                .show()
        }
    }
}