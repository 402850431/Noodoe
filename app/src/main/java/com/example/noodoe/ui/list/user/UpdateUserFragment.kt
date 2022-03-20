package com.example.noodoe.ui.list.user

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TimePicker
import com.example.noodoe.R
import com.example.noodoe.ui.base.BaseFragment
import com.example.noodoe.ui.list.ListViewModel
import kotlinx.android.synthetic.main.fragment_update_user.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*


class UpdateUserFragment : BaseFragment<ListViewModel>() {
    override val viewModel: ListViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initOnClick()
        initObserver()
    }

    private fun initView() {
        tv_email.text = viewModel.reportEmail
        tv_time.text = viewModel.updateTime
    }

    private fun datePicker() {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR) //取得現在的日期年月日
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        context?.let {
            DatePickerDialog(it, { view, year, month, day ->
                viewModel.selectDate = "$year-${dateTimeStr(month)}-${dateTimeStr(day)}"
                timePicker()
            }, year, month, day).show()
        }
    }

    private fun timePicker() {
        val calendar: Calendar = Calendar.getInstance()
        val hourOfDay: Int = calendar.get(Calendar.HOUR)
        val minute: Int = calendar.get(Calendar.MINUTE)
        TimePickerDialog(context, { view, hourOfDay, minute ->
            viewModel.selectTime = "${dateTimeStr(hourOfDay)}:${dateTimeStr(minute)}"
            viewModel.updateTime()
        }, hourOfDay, minute, true).show()
    }

    private val dateTimeStr = { number: Int ->
        if (number< 10) "0$number" else "$number"
    }

    private fun initOnClick() {
        tv_time.setOnClickListener {
            datePicker()
        }
    }

    private fun initObserver() {
        viewModel.userTimeResult.observe(viewLifecycleOwner) {
            it?.results?.firstOrNull()?.updatedAt.let { updateTime ->
                tv_time.text = updateTime
            }
        }
    }

}