package com.example.rateofcurrency.ui.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rateofcurrency.R
import com.example.rateofcurrency.databinding.FragmentCurrencyInformationBinding
import com.example.rateofcurrency.models.entities.SignalResponse
import com.example.rateofcurrency.models.viewmodel.GeneralViewModel
import com.example.rateofcurrency.utilits.MainAdapter
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.*
import kotlin.time.ExperimentalTime


class CurrencyInformationFragment : BaseFragment(R.layout.fragment_currency_information) {

    private var _binding: FragmentCurrencyInformationBinding? = null
    private val binding get() = _binding!!

    @ExperimentalSerializationApi
    @ExperimentalTime
    private val generalViewModel: GeneralViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyInformationBinding.inflate(inflater, container, false)
        return binding.root
    }


    @ExperimentalTime
    @ExperimentalSerializationApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
        initObservers()
        generalViewModel.getTokenPartner()
    }


    @ExperimentalTime
    @ExperimentalSerializationApi
    private fun getSignalData(authToken: String) {
        generalViewModel.getSignal(authToken)
    }

    private fun createRecyclerView(signalList: ArrayList<SignalResponse>) {
        val viewAdapter = MainAdapter(signalList)
        val viewManager = LinearLayoutManager(requireContext())

        binding.rvSignalList.apply {
            setHasFixedSize(true)
            adapter = viewAdapter
            layoutManager = viewManager
        }
    }


    @ExperimentalSerializationApi
    @ExperimentalTime
    @SuppressLint("SetTextI18n")
    private fun initButton() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Подключение слушателя нажатий кнопок
        binding.btnDateFrom.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), { view, _year, monthOfYear, dayOfMonth ->

                Log.d("DATA", "$_year $monthOfYear $dayOfMonth")
                // Сохраняем выбраную дату в ViewModel
                generalViewModel.setDateFrom(dayOfMonth, monthOfYear, _year)

                // Запрос новых данных с обновленым FROM
                generalViewModel.getSignal()

            }, year, month, day)
            dpd.show()
        }

        binding.btnDateTo.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), { view, _year, monthOfYear, dayOfMonth ->

                Log.d("DATA", "$_year $monthOfYear $dayOfMonth")
                // Сохраняем выбраную дату в ViewModel
                generalViewModel.setDateTo(dayOfMonth, monthOfYear, _year)

                // Запрос новых данных с обновленым TO
                generalViewModel.getSignal()

            }, year, month, day)
            dpd.show()
        }

        binding.btnGetPair.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it)
            popupMenu.menuInflater.inflate(R.menu.currency_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.eur_usd -> generalViewModel.setPair(pair = "EURUSD")
                    R.id.gpb_usd -> generalViewModel.setPair(pair = "GBPUSD")
                    R.id.usd_jpu -> generalViewModel.setPair(pair = "USDJPY")
                    R.id.usd_chf -> generalViewModel.setPair(pair = "USDCHF")
                    R.id.usd_cad -> generalViewModel.setPair(pair = "USDCAD")
                    R.id.aud_usd -> generalViewModel.setPair(pair = "AUDUSD")
                    R.id.nzd_usd -> generalViewModel.setPair(pair = "NZDUSD")
                }
                true
            })
            popupMenu.show()
        }
    }


    @SuppressLint("SetTextI18n")
    @ExperimentalSerializationApi
    @ExperimentalTime
    private fun initObservers() {

        generalViewModel.tokenPartner.observe(viewLifecycleOwner) {
            it.takeIf { !it.isNullOrEmpty() }.apply { getSignalData(it) }
        }

        generalViewModel.signalList.observe(viewLifecycleOwner) {
            it.takeIf { !it.isNullOrEmpty() && it.size > 0 }.apply { createRecyclerView(it) }
        }

        // После выбора даты изменяем название кнопки
        generalViewModel.dateFrom.observe(viewLifecycleOwner) {
            binding.btnDateFrom.text = "Дата с: $it"
        }

        generalViewModel.dateTo.observe(viewLifecycleOwner) {
            binding.btnDateTo.text = "Дата по: $it"
        }

        // Слушатель значения валютной пары, если изменяется то новый запрос к api
        generalViewModel.pair.observe(viewLifecycleOwner) {
            generalViewModel.getSignal(pairs = it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}