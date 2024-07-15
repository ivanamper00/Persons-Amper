package com.amper.personapp.ui

import android.os.Bundle
import android.text.util.Linkify
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.amper.personapp.R
import com.amper.personapp.data.model.PersonDto
import com.amper.personapp.databinding.FragmentProfileBinding
import com.amper.personapp.util.DateUtility
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PersonFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var viewBinding: FragmentProfileBinding

    private var person: PersonDto? = null

    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    companion object {
        const val TAG = "PersonFragment"
        const val ARGUMENT_PERSON = "ARGUMENT_PERSON"

        fun newInstance(
            person: PersonDto,
        ): PersonFragment {
            return PersonFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARGUMENT_PERSON, person)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentProfileBinding.bind(view)
        setupView()
        setupListener()
    }

    private fun setupView() {
        person = arguments?.getSerializable(ARGUMENT_PERSON) as? PersonDto

        val displayMetrics: DisplayMetrics = requireActivity().resources.displayMetrics
        val height: Int = displayMetrics.heightPixels
        val maxHeight = (height * 0.85).toInt()
        bottomSheetBehavior =
            BottomSheetBehavior.from(viewBinding.profileBottomSheet.bottomSheetContainer)
        bottomSheetBehavior.peekHeight = maxHeight

        with(viewBinding.profileBottomSheet) {

            //Labels
            firstName.textViewLabel.text = getString(R.string.profile_first_name)
            lastName.textViewLabel.text = getString(R.string.profile_last_name)
            birthDate.textViewLabel.text = getString(R.string.profile_bod)
            age.textViewLabel.text = getString(R.string.profile_age)
            unitNumber.textViewLabel.text = getString(R.string.profile_unit_no)
            streetName.textViewLabel.text = getString(R.string.profile_street)
            state.textViewLabel.text = getString(R.string.profile_state)
            city.textViewLabel.text = getString(R.string.profile_city)
            country.textViewLabel.text = getString(R.string.profile_country)
            postalCode.textViewLabel.text = getString(R.string.profile_postal_code)
            email.textViewLabel.text = getString(R.string.profile_email)
            phone.textViewLabel.text = getString(R.string.profile_mobile_no)

            //Content
            person?.let {
                it.picture?.large?.let{ url ->
                    imageProfile.transitionName = person?.id
                    imageProfile.loadImage(url)
                }
                firstName.textViewContent.text = it.firstName.orEmpty()
                lastName.textViewContent.text = it.lastName.orEmpty()
                birthDate.textViewContent.text = it.birthDay?.run(DateUtility::toDateFormat).orEmpty()
                age.textViewContent.text = it.getAge().toString()
                unitNumber.textViewContent.text = it.address?.street?.number.toString()
                streetName.textViewContent.text = it.address?.street?.name.orEmpty()
                state.textViewContent.text = it.address?.state.orEmpty()
                city.textViewContent.text = it.address?.city.orEmpty()
                country.textViewContent.text = it.address?.country.orEmpty()
                postalCode.textViewContent.text = it.address?.postcode.orEmpty()
                email.textViewContent.text = it.email.orEmpty()
                Linkify.addLinks(email.textViewContent, Linkify.EMAIL_ADDRESSES)
                phone.textViewContent.text = it.mobileNumber.orEmpty()
                Linkify.addLinks(phone.textViewContent, Linkify.PHONE_NUMBERS)
            }
        }
    }

    private fun setupListener() {
        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.popBackStack()
                Log.d("setupListener", "handleOnBackPressed: pressed!")
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)

        viewBinding.imageBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}

