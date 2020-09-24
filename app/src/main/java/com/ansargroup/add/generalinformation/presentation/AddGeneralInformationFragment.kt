package com.ansargroup.add.generalinformation.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ansargroup.R
import com.ansargroup.main.notifications.NotificationsFragment
import kotlinx.android.synthetic.main.fragment_add_general_information.*
import kotlinx.android.synthetic.main.layout_in_person.*

class AddGeneralInformationFragment : Fragment(R.layout.fragment_add_general_information) {

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setActionsListener()
    }

    private fun setActionsListener() {
        radioGroupPeopleReach.setOnCheckedChangeListener { p0, p1 ->
            if (radioButtonInPerson.isChecked) {
                inPersonImport.inflate()
                setAppointmentActionsListener()
                setButtonsActions()
            }
        }
    }

    private fun setAppointmentActionsListener() {
        radioGroupHowMakeAppointment.setOnCheckedChangeListener { p0, p1 ->
            groupAppointmentViews.visibility = View.VISIBLE
            tvAppointmentHint.text =
                when {
                    radioButtonBookTime.isChecked -> {
                        getString(R.string.book_time_hint)
                    }
                    radioButtonDropIn.isChecked -> {
                        getString(R.string.drop_in_hint)
                    }
                    else -> {
                        ""
                    }
                }
        }
    }

    private fun setButtonsActions() {
        btnBack.setOnClickListener {
            navController.navigateUp()
        }
    }

    companion object {
        const val PARENT_ID = "parent_id"
        const val ID = "id"
    }

}