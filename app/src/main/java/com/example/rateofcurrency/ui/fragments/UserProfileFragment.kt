package com.example.rateofcurrency.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.rateofcurrency.R
import com.example.rateofcurrency.databinding.FragmentUserProfileBinding
import com.example.rateofcurrency.models.viewmodel.GeneralViewModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.time.ExperimentalTime


class UserProfileFragment: BaseFragment(R.layout.fragment_user_profile) {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    @ExperimentalSerializationApi
    @ExperimentalTime
    private val generalViewModel: GeneralViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalSerializationApi
    @ExperimentalTime
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        generalViewModel.exceptionMessage.observe(viewLifecycleOwner) {
            binding.textvew.text = it
        }

        generalViewModel.userProfile.observe(viewLifecycleOwner) {
            binding.textvew.text = it.toString()
        }

        generalViewModel.getUserProfile()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}